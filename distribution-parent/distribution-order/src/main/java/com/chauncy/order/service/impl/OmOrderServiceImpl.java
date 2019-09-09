package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.enums.log.PaymentWayEnum;
import com.chauncy.common.enums.goods.GoodsTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.rabbit.RabbitUtil;
import com.chauncy.data.bo.app.logistics.LogisticsDataBo;
import com.chauncy.data.bo.app.order.RewardBo;
import com.chauncy.data.bo.app.order.my.OrderRewardBo;
import com.chauncy.data.bo.app.order.rabbit.RabbitOrderBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderLogisticsPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.pay.PayUserRelationPo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.dto.app.order.my.SearchMyOrderDto;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchSendOrderDto;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderLogisticsMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.order.OmShoppingCartMapper;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.pay.PayUserRelationMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.vo.app.car.ShopTicketSoWithCarGoodVo;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailGoodsVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailStoreVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailVo;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.manage.order.list.SearchOrderVo;
import com.chauncy.data.vo.supplier.order.*;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IPayOrderService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class OmOrderServiceImpl extends AbstractService<OmOrderMapper, OmOrderPo> implements IOmOrderService {

    @Autowired
    private OmOrderMapper mapper;

    @Autowired
    private IPayOrderMapper payOrderMapper;

    @Autowired
    private OmGoodsTempMapper goodsTempMapper;

    @Autowired
    private PmGoodsSkuMapper goodsSkuMapper;

    @Autowired
    private OmShoppingCartMapper shoppingCartMapper;

    @Autowired
    private IPayOrderService payOrderService;

    @Autowired
    private OmOrderLogisticsMapper orderLogisticsMapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private RabbitUtil rabbitUtil;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PayUserRelationMapper payUserRelationMapper;

    @Autowired
    private SecurityUtil securityUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean closeOrderByPayId(Long payOrderId) {
        //修改支付单状态
        PayOrderPo payOrderPo=new PayOrderPo();
        payOrderPo.setExpireTime(LocalDateTime.now()).setStatus(PayOrderStatusEnum.ALREADY_CANCEL.getId())
        .setId(payOrderId);
        payOrderMapper.updateById(payOrderPo);

        //修改订单状态
        //查出订单
        QueryWrapper orderWrapper=new QueryWrapper();
        orderWrapper.eq("pay_order_id",payOrderId);
        orderWrapper.eq("status",OrderStatusEnum.NEED_PAY.getId());
        List<OmOrderPo> updateOrders = mapper.selectList(orderWrapper);
        //如果所有订单被支付了，就不需要取消订单了
        if (ListUtil.isListNullAndEmpty(updateOrders)){
            return true;
        }
        List<Long> orderIds = updateOrders.stream().map(OmOrderPo::getId).collect(Collectors.toList());

        //修改状态
        OmOrderPo updateOrder=new OmOrderPo();
        updateOrder.setStatus(OrderStatusEnum.ALREADY_CANCEL);
        updateOrder.setCloseTime(LocalDateTime.now());
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.in("id",orderIds);
        this.update(updateOrder,updateWrapper);

        //查找skuid和数量
        QueryWrapper goodsTempWrapper=new QueryWrapper();
        goodsTempWrapper.in("order_id",orderIds);
        List<OmGoodsTempPo> goodsTemps = goodsTempMapper.selectList(goodsTempWrapper);
        //把库存加回去
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList= Lists.newArrayList();
        goodsTemps.forEach(x->{
            ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo=new ShopTicketSoWithCarGoodVo();
            //加库存要变为负数
            shopTicketSoWithCarGoodVo.setNumber(x.getNumber()*-1);
            shopTicketSoWithCarGoodVo.setId(x.getSkuId());
            shopTicketSoWithCarGoodVoList.add(shopTicketSoWithCarGoodVo);
        });
        goodsSkuMapper.updateStock(shopTicketSoWithCarGoodVoList);
        //红包与购物券退还
        PayOrderPo queryPayOrder = payOrderMapper.selectById(payOrderId);
        shoppingCartMapper.updateDiscount(BigDecimalUtil.safeMultiply(-1,queryPayOrder.getTotalRedEnvelops()),
                BigDecimalUtil.safeMultiply(-1,queryPayOrder.getTotalShopTicket()),queryPayOrder.getUmUserId() );
        return true;
    }

    @Override
    public boolean closeOrderByOrderId(Long orderId) {
        //把支付单禁用,以后该支付单下的其他订单都单独生成支付单
        OmOrderPo orderPo = mapper.selectById(orderId);
        //支付单失效
        PayOrderPo updatePayOrder=new PayOrderPo();
        updatePayOrder.setId(orderPo.getPayOrderId()).setEnabled(false);
        payOrderMapper.updateById(updatePayOrder);
        //订单改状态
        orderPo.setStatus(OrderStatusEnum.ALREADY_CANCEL);
        orderPo.setRealMoney(null);
        orderPo.setCloseTime(LocalDateTime.now());
        this.updateById(orderPo);

        //查找skuid和数量
        QueryWrapper goodsTempWrapper=new QueryWrapper();
        goodsTempWrapper.eq("order_id",orderId);
        List<OmGoodsTempPo> goodsTemps = goodsTempMapper.selectList(goodsTempWrapper);
        //把库存加回去
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList= Lists.newArrayList();
        goodsTemps.forEach(x->{
            ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo=new ShopTicketSoWithCarGoodVo();
            shopTicketSoWithCarGoodVo.setId(x.getSkuId());
            //加库存要变为负数
            shopTicketSoWithCarGoodVo.setNumber(x.getNumber()*-1);
            shopTicketSoWithCarGoodVoList.add(shopTicketSoWithCarGoodVo);
        });
        goodsSkuMapper.updateStock(shopTicketSoWithCarGoodVoList);

        //购物券和红包加回去
        shoppingCartMapper.updateDiscount(BigDecimalUtil.safeMultiply(-1,orderPo.getRedEnvelops()),
                BigDecimalUtil.safeMultiply(-1,orderPo.getShopTicket()),orderPo.getUmUserId() );
        return true;
    }

    /**
     * 微信支付成功通知
     * @param payOrderPo  支付订单
     * @param notifyMap  微信回调参数
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wxPayNotify(PayOrderPo payOrderPo, Map<String, String> notifyMap) throws Exception {
        //更新PayOrderPo
        UpdateWrapper<PayOrderPo> payOrderPoUpdateWrapper = new UpdateWrapper<>();
        payOrderPoUpdateWrapper.lambda().eq(PayOrderPo::getId, payOrderPo.getId());
        //状态设置为已支付
        payOrderPoUpdateWrapper.lambda().set(PayOrderPo::getStatus, PayOrderStatusEnum.ALREADY_PAY.getId());
        //支付类型 微信
        payOrderPoUpdateWrapper.lambda().set(PayOrderPo::getPayTypeCode, PaymentWayEnum.WECHAT.getName());
        //微信支付单号
        payOrderPoUpdateWrapper.lambda().set(PayOrderPo::getPayOrderNo, notifyMap.get("transaction_id"));
        //支付金额
        BigDecimal payAmount = BigDecimalUtil.safeDivide(new BigDecimal(notifyMap.get("cash_fee")), new BigDecimal(100));
        payOrderPoUpdateWrapper.lambda().set(PayOrderPo::getPayAmount, payAmount);
        payOrderPoUpdateWrapper.lambda().set(PayOrderPo::getPayTime, notifyMap.get("time_end"));
        payOrderService.update(payOrderPoUpdateWrapper);
        //付款成功后需要做的操作
        afterPayDo(payOrderPo.getId());
       /* //更新OmOrderPo
        UpdateWrapper<OmOrderPo> omOrderPoUpdateWrapper = new UpdateWrapper<>();
        omOrderPoUpdateWrapper.lambda().eq(OmOrderPo::getPayOrderId, payOrderPo.getId());
        omOrderPoUpdateWrapper.lambda().set(OmOrderPo::getPayOrderId, payOrderPo.getId());
        //设置待发货状态
        omOrderPoUpdateWrapper.lambda().set(OmOrderPo::getStatus, OrderStatusEnum.NEED_SEND_GOODS);
        omOrderPoUpdateWrapper.lambda().set(OmOrderPo::getPayTime, notifyMap.get("time_end"));
        this.update(omOrderPoUpdateWrapper);*/
    }

    @Override
    public PageInfo<SearchOrderVo> search(SearchOrderDto searchOrderDto) {
        PageInfo<SearchOrderVo> searchOrderVoPageInfo = PageHelper.startPage(searchOrderDto.getPageNo(), searchOrderDto.getPageSize())
                .doSelectPageInfo(() -> mapper.search(searchOrderDto));
        return searchOrderVoPageInfo;
    }

    @Override
    public PageInfo<SmSearchOrderVo> searchSmOrderList(SmSearchOrderDto smSearchOrderDto) {
        PageInfo<SmSearchOrderVo> searchOrderVoPageInfo = PageHelper.startPage(smSearchOrderDto.getPageNo(), smSearchOrderDto.getPageSize())
                .doSelectPageInfo(() -> mapper.searchSmOrder(smSearchOrderDto));
        return searchOrderVoPageInfo;
    }

    @Override
    public PageInfo<SmSendOrderVo> searchSmSendOrderList(SmSearchSendOrderDto smSendOrderDto) {
        PageInfo<SmSendOrderVo> smSendOrderVoPageInfo = PageHelper.startPage(smSendOrderDto.getPageNo(), smSendOrderDto.getPageSize())
                .doSelectPageInfo(() -> mapper.searchSendOrderVos(smSendOrderDto));
        smSendOrderVoPageInfo.getList().forEach(x->{
            List<SmSendGoodsTempVo> smSendGoodsTempVos = mapper.searchSendGoodsTemp(x.getOrderId());
            x.setSmSendGoodsTempVos(smSendGoodsTempVos);
        });

        return smSendOrderVoPageInfo;
    }


    @Override
    public OrderDetailVo getDetailById(Long id) {
        OrderDetailVo orderDetailVo = mapper.loadById(id);
        orderDetailVo.setGoodsTempVos(mapper.searchGoodsTempVos(id));
        return orderDetailVo;
    }

    @Override
    public SmOrderDetailVo getSmDetailById(Long id) {
        SmOrderDetailVo smOrderDetailVo = mapper.loadSmById(id);
        smOrderDetailVo.setGoodsTempVos(mapper.searchGoodsTempVos(id));
        smOrderDetailVo.setRewardShopTicket(mapper.getRewardShopTicketByOrderId(id));

        return smOrderDetailVo;
    }

    @Override
    public SmOrderLogisticsVo getLogisticsById(Long id) {
        return mapper.loadLogisticsById(id);
    }

    @Override
    public PageInfo<AppSearchOrderVo> searchAppOrder(Long userId, SearchMyOrderDto searchMyOrderDto) {
        PageInfo<AppSearchOrderVo> appSearchOrderVoPageInfo = PageHelper.startPage(searchMyOrderDto.getPageNo(), searchMyOrderDto.getPageSize())
                .doSelectPageInfo(() -> mapper.searchAppOrder(userId,searchMyOrderDto.getStatus()));
        appSearchOrderVoPageInfo.getList().forEach(x->{
            List<SmSendGoodsTempVo> smSendGoodsTempVos = mapper.searchSendGoodsTemp(x.getOrderId());
            x.setSmSendGoodsTempVos(smSendGoodsTempVos);
        });
        return appSearchOrderVoPageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long payOrder(Long orderId) {
        //找出原先的支付单
        PayOrderPo queryPayOrder = mapper.getPayOrderByOrderId(orderId);
        //把原先的支付单禁用掉
        if (queryPayOrder.getEnabled()){
           queryPayOrder.setEnabled(false);
           payOrderMapper.updateById(queryPayOrder);
        }
        //生成新的支付单
        PayOrderPo savePayOrderPo=new PayOrderPo();
        BeanUtils.copyProperties(queryPayOrder,savePayOrderPo,"id");

        OmOrderPo queryOrder = mapper.selectById(orderId);

        //新的支付单生成新的金额 没有活动，优惠金额为0
        savePayOrderPo.setTotalRealPayMoney(queryOrder.getRealMoney()).setTotalDiscount(BigDecimal.ZERO)
        .setTotalShipMoney(queryOrder.getShipMoney()).setTotalTaxMoney(queryOrder.getTaxMoney()).setTotalRedEnvelops(queryOrder.getRedEnvelops())
        .setTotalShopTicket(queryOrder.getShopTicket()).setTotalMoney(queryOrder.getTotalMoney()).setTotalNumber(queryOrder.getTotalNumber())
        .setTotalShopTicketMoney(queryOrder.getShopTicketMoney()).setTotalRedEnvelopsMoney(queryOrder.getRedEnvelopsMoney())
        .setEnabled(true);

        payOrderMapper.insert(savePayOrderPo);

        //订单外键改为新的支付单
        OmOrderPo updateOrder=new OmOrderPo();
        updateOrder.setPayOrderId(savePayOrderPo.getId());
        updateOrder.setId(queryOrder.getId());
        mapper.updateById(updateOrder);

        return savePayOrderPo.getId();
    }

    @Override
    public AppMyOrderDetailVo getAppMyOrderDetailVoByOrderId(Long orderId) {
        //获取订单详情基本信息
        AppMyOrderDetailVo appMyOrderDetailVo=mapper.getAppMyOrderDetailVoByOrderId(orderId);
        //获取商品信息
        List<AppMyOrderDetailGoodsVo> appMyOrderDetailGoodsVos=mapper.getAppMyOrderDetailGoodsVoByOrderId(orderId);
        //组装店铺信息
        AppMyOrderDetailStoreVo appMyOrderDetailStoreVo=new AppMyOrderDetailStoreVo();
        appMyOrderDetailStoreVo.setStoreId(appMyOrderDetailVo.getStoreId()).setStoreName(appMyOrderDetailVo.getStoreName())
        .setAppMyOrderDetailGoodsVos(appMyOrderDetailGoodsVos);
        //物流节点信息
        //根据订单号号获取物流信息
        OmOrderLogisticsPo orderLogistics = orderLogisticsMapper.selectOne(new QueryWrapper<OmOrderLogisticsPo>().eq("order_id", orderId));
        if (orderLogistics != null) {
            List<LogisticsDataBo> logisticsDataBos = JSONUtils.toJSONArray(orderLogistics.getData());
            appMyOrderDetailVo.setLogisticsData(logisticsDataBos);
        }
        //订单返回购物券、积分、经验值
        OrderRewardBo orderRewardBo=mapper.getOrderRewardByOrderId(orderId);
        BeanUtils.copyProperties(orderRewardBo,appMyOrderDetailVo);

        appMyOrderDetailVo.setAppMyOrderDetailStoreVo(appMyOrderDetailStoreVo);

        return appMyOrderDetailVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveOrder(Long orderId) {
        OmOrderPo queryOrder=mapper.selectById(orderId);
        if (queryOrder==null){
            throw new ServiceException(ResultCode.PARAM_ERROR,"订单id不存在！");
        }
        if (queryOrder.getStatus()!=OrderStatusEnum.NEED_RECEIVE_GOODS){
            throw new ServiceException(ResultCode.FAIL,"该订单不是待收货订单，不能进行确认收货操作！");
        }
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        Integer refundDay;
        //如果是含税商品
        if (queryOrder.getTaxMoney().compareTo(BigDecimal.ZERO)!=0){
            refundDay=basicSettingPo.getTaxRefundDay();
        }
        else {
            refundDay=basicSettingPo.getRefundDay();
        }
        //计算截止时间
        LocalDateTime afterSaleDeadline=LocalDateTime.now().plusDays(refundDay);
        OmOrderPo updateOrder = new OmOrderPo();
        updateOrder.setId(orderId).setReceiveTime(LocalDateTime.now()).setAfterSaleDeadline(afterSaleDeadline)
        .setStatus(OrderStatusEnum.NEED_EVALUATE);
        mapper.updateById(updateOrder);

        //多久自动评价(毫秒)
        String expiration=basicSettingPo.getAutoCommentDay()*24*60*60*1000+"";
        //多久售后截止(毫秒)
        String afterExpiration = refundDay * 24 * 60 * 60 * 1000 + "";

        RabbitOrderBo rabbitOrderBo=new RabbitOrderBo();
        rabbitOrderBo.setOrderId(orderId).setOrderStatusEnum(OrderStatusEnum.NEED_EVALUATE);

        //添加自动评价延时队列
        rabbitUtil.sendDelayMessage(1*60*1000+"",rabbitOrderBo);
        //添加售后截止延时队列
        RabbitOrderBo afterRabbitOrderBo=new RabbitOrderBo();
        afterRabbitOrderBo.setOrderId(orderId);
        rabbitUtil.sendDelayMessage(1*60*1000+"",afterRabbitOrderBo);

        LoggerUtil.info("【确认收货等待自动评价发送时间】:" + LocalDateTime.now());
        LoggerUtil.info("【确认收货等待售后截止】:" + LocalDateTime.now());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void afterPayDo(Long payOrderId) {
        PayOrderPo queryPayOrder = payOrderMapper.selectById(payOrderId);
        //改订单状态
        QueryWrapper<OmOrderPo> queryOrderWrapper=new QueryWrapper<>();
        queryOrderWrapper.lambda().eq(OmOrderPo::getPayOrderId,payOrderId);
        List<OmOrderPo> queryOrders = mapper.selectList(queryOrderWrapper);
        queryOrders.forEach(x->{
            OmOrderPo updateOrder=new OmOrderPo();
            updateOrder.setId(x.getId()).setPayTime(queryPayOrder.getPayTime());
            //自取与服务类商品没有发货状态
            if (x.getGoodsType().equals(GoodsTypeEnum.PICK_UP_INSTORE.getName())||
                    x.getGoodsType().equals(GoodsTypeEnum.SERVICES.getName()) ){
                updateOrder.setStatus(OrderStatusEnum.NEED_USE);
            }
            else {
                updateOrder.setStatus(OrderStatusEnum.NEED_SEND_GOODS);
            }
            mapper.updateById(updateOrder);
        });

        //sku和商品增加销量
        QueryWrapper<OmGoodsTempPo> queryGoodsTempWrapper=new QueryWrapper<>();
        queryGoodsTempWrapper.lambda().in(OmGoodsTempPo::getOrderId,queryOrders.stream().map(OmOrderPo::getId).collect(Collectors.toList()));
        List<OmGoodsTempPo> queryGoodsTemps = goodsTempMapper.selectList(queryGoodsTempWrapper);
        queryGoodsTemps.forEach(x->{
            goodsSkuMapper.addASalesVolume(x.getSkuId(),x.getNumber());
            goodsMapper.addASalesVolume(x.getGoodsId(),x.getNumber());
        });


    }

    @Override
    public void orderDeadline(Long orderId) {
        OmOrderPo queryOrder = mapper.selectById(orderId);
        //订单总金额
        BigDecimal totalMoney=queryOrder.getTotalMoney();

        Long userId=Long.parseLong(queryOrder.getCreateBy());

        //查出需要返佣的用户
        QueryWrapper<PayUserRelationPo> payUserWrapper=new QueryWrapper<>();
        payUserWrapper.lambda().eq(PayUserRelationPo::getOrderId,orderId);
        PayUserRelationPo queryPayUser=payUserRelationMapper.selectOne(payUserWrapper);

        //查出下单用户需要返的购物券、积分、经验值
        RewardBo rewardBo=mapper.getRewardBoByOrder(orderId);
        //用户增加返佣数据




    }

}
