package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.log.PaymentWayEnum;
import com.chauncy.common.enums.goods.GoodsTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.*;
import com.chauncy.common.util.rabbit.RabbitUtil;
import com.chauncy.data.bo.app.logistics.LogisticsDataBo;
import com.chauncy.data.bo.app.order.reward.RewardBuyerBo;
import com.chauncy.data.bo.app.order.my.OrderRewardBo;
import com.chauncy.data.bo.app.order.rabbit.RabbitOrderBo;
import com.chauncy.data.bo.app.order.reward.RewardRedBo;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.*;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.pay.PayUserRelationPo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.my.SearchMyOrderDto;
import com.chauncy.data.dto.app.order.store.WriteOffDto;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchSendOrderDto;
import com.chauncy.data.mapper.order.*;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.pay.PayUserRelationMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.car.ShopTicketSoWithCarGoodVo;
import com.chauncy.data.vo.app.order.cart.SubmitOrderVo;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailGoodsVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailStoreVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailVo;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.manage.order.list.SearchOrderVo;
import com.chauncy.data.vo.supplier.order.*;
import com.chauncy.order.report.service.IOmOrderReportService;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IPayOrderService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.user.service.IUmUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private IUmUserService umUserService;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IOmOrderReportService omOrderReportService;

    @Autowired
    private OmRealUserMapper realUserMapper;

    @Autowired
    private PayUserRelationNextLevelMapper payUserRelationNextLevelMapper;

    @Value("${jasypt.encryptor.password}")
    private String password;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean closeOrderByPayId(Long payOrderId) {
        //修改支付单状态
        PayOrderPo payOrderPo = new PayOrderPo();
        payOrderPo.setExpireTime(LocalDateTime.now()).setStatus(PayOrderStatusEnum.ALREADY_CANCEL.getId())
                .setId(payOrderId);
        payOrderMapper.updateById(payOrderPo);

        //修改订单状态
        //查出订单
        QueryWrapper orderWrapper = new QueryWrapper();
        orderWrapper.eq("pay_order_id", payOrderId);
        orderWrapper.eq("status", OrderStatusEnum.NEED_PAY.getId());
        List<OmOrderPo> updateOrders = mapper.selectList(orderWrapper);
        //如果所有订单被支付了，就不需要取消订单了
        if (ListUtil.isListNullAndEmpty(updateOrders)) {
            return true;
        }
        List<Long> orderIds = updateOrders.stream().map(OmOrderPo::getId).collect(Collectors.toList());

        //修改状态
        OmOrderPo updateOrder = new OmOrderPo();
        updateOrder.setStatus(OrderStatusEnum.ALREADY_CANCEL);
        updateOrder.setCloseTime(LocalDateTime.now());
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.in("id", orderIds);
        this.update(updateOrder, updateWrapper);

        //查找skuid和数量
        QueryWrapper goodsTempWrapper = new QueryWrapper();
        goodsTempWrapper.in("order_id", orderIds);
        List<OmGoodsTempPo> goodsTemps = goodsTempMapper.selectList(goodsTempWrapper);
        //把库存加回去
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList = Lists.newArrayList();
        goodsTemps.forEach(x -> {
            ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo = new ShopTicketSoWithCarGoodVo();
            //加库存要变为负数
            shopTicketSoWithCarGoodVo.setNumber(x.getNumber() * -1);
            shopTicketSoWithCarGoodVo.setId(x.getSkuId());
            shopTicketSoWithCarGoodVoList.add(shopTicketSoWithCarGoodVo);
        });
        goodsSkuMapper.updateStock(shopTicketSoWithCarGoodVoList);
        //红包与购物券退还
        PayOrderPo queryPayOrder = payOrderMapper.selectById(payOrderId);
        shoppingCartMapper.updateDiscount(BigDecimalUtil.safeMultiply(-1, queryPayOrder.getTotalRedEnvelops()),
                BigDecimalUtil.safeMultiply(-1, queryPayOrder.getTotalShopTicket()), queryPayOrder.getUmUserId());
        return true;
    }

    @Override
    public boolean closeOrderByOrderId(Long orderId) {
        //把支付单禁用,以后该支付单下的其他订单都单独生成支付单
        OmOrderPo orderPo = mapper.selectById(orderId);
        //支付单失效
        PayOrderPo updatePayOrder = new PayOrderPo();
        updatePayOrder.setId(orderPo.getPayOrderId()).setEnabled(false);
        payOrderMapper.updateById(updatePayOrder);
        //订单改状态
        orderPo.setStatus(OrderStatusEnum.ALREADY_CANCEL);
        orderPo.setRealMoney(null);
        orderPo.setCloseTime(LocalDateTime.now());
        this.updateById(orderPo);

        //查找skuid和数量
        QueryWrapper goodsTempWrapper = new QueryWrapper();
        goodsTempWrapper.eq("order_id", orderId);
        List<OmGoodsTempPo> goodsTemps = goodsTempMapper.selectList(goodsTempWrapper);
        //把库存加回去
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList = Lists.newArrayList();
        goodsTemps.forEach(x -> {
            ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo = new ShopTicketSoWithCarGoodVo();
            shopTicketSoWithCarGoodVo.setId(x.getSkuId());
            //加库存要变为负数
            shopTicketSoWithCarGoodVo.setNumber(x.getNumber() * -1);
            shopTicketSoWithCarGoodVoList.add(shopTicketSoWithCarGoodVo);
        });
        goodsSkuMapper.updateStock(shopTicketSoWithCarGoodVoList);

        //购物券和红包加回去
        shoppingCartMapper.updateDiscount(BigDecimalUtil.safeMultiply(-1, orderPo.getRedEnvelops()),
                BigDecimalUtil.safeMultiply(-1, orderPo.getShopTicket()), orderPo.getUmUserId());
        return true;
    }

    /**
     * 微信支付成功通知
     *
     * @param payOrderPo 支付订单
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
        afterPayDo(payOrderPo.getId()) ;
        //订单下单流水生成
        UmUserPo umUserPo = userMapper.selectById(payOrderPo.getUmUserId());
        AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
        addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.APP_ORDER);
        addAccountLogBo.setRelId(payOrderPo.getId());
        addAccountLogBo.setOperator(String.valueOf(umUserPo.getId()));
        //listenerOrderLogQueue 消息队列
        this.rabbitTemplate.convertAndSend(
                RabbitConstants.ACCOUNT_LOG_EXCHANGE, RabbitConstants.ACCOUNT_LOG_ROUTING_KEY, addAccountLogBo);

        //海关申报 拆单之后的订单是海外直邮或者保税仓
        QueryWrapper<OmOrderPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(OmOrderPo::getPayOrderId, payOrderPo.getId())
                .and(wrapper -> wrapper.eq(OmOrderPo::getGoodsType, GoodsTypeEnum.BONDED.getName())
                .or().eq(OmOrderPo::getGoodsType, GoodsTypeEnum.OVERSEA.getName()));
        List<OmOrderPo> omOrderPoList = mapper.selectList(queryWrapper);
        System.out.println("==================将要海关消息队列了===================");
        System.out.println("=======================================================" + omOrderPoList);
        omOrderPoList.stream().forEach(omOrderPo -> this.rabbitTemplate.convertAndSend(
                RabbitConstants.CUSTOM_DECLARE_EXCHANGE, RabbitConstants.CUSTOM_DECLARE_ROUTING_KEY, omOrderPo.getId(),
                message -> {
                    System.out.println("==================进来海关消息队列了===================");
                    System.out.println("=======================================================" + omOrderPo);
                    //一分钟之后再执行   当前方法未执行完成，订单状态可能未更新
                    message.getMessageProperties().setExpiration(60*1000 + "");
                    LoggerUtil.info(String.format("订单支付【%s】发送消息队列时间：",omOrderPo.getId())
                            + LocalDateTime.now());
                    return message;
                }));

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
        smSendOrderVoPageInfo.getList().forEach(x -> {
            List<SmSendGoodsTempVo> smSendGoodsTempVos = mapper.searchSendGoodsTemp(x.getOrderId());
            x.setSmSendGoodsTempVos(smSendGoodsTempVos);
        });

        return smSendOrderVoPageInfo;
    }

    @Override
    public void storeSend(Long orderId) {
        //修改订单状态为已发货
        OmOrderPo order = mapper.selectById(orderId);
        if (order != null) {
            OmOrderPo saveOrder = new OmOrderPo();
            saveOrder.setId(order.getId()).setStatus(OrderStatusEnum.NEED_RECEIVE_GOODS)
                    .setSendTime(LocalDateTime.now());
            mapper.updateById(saveOrder);
        }

        //获取系统基本设置
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        //多久自动收货(毫秒)
        String expiration=basicSettingPo.getAutoReceiveDay()*24*60*60*1000+"";

        RabbitOrderBo rabbitOrderBo=new RabbitOrderBo();
        rabbitOrderBo.setOrderId(orderId).setOrderStatusEnum(OrderStatusEnum.NEED_RECEIVE_GOODS);
        //添加自动收货的消息队列
        //rabbitUtil.sendDelayMessage(10*60*1000+"",rabbitOrderBo);
        rabbitUtil.sendDelayMessage(expiration,rabbitOrderBo);
       // rabbitUtil.sendDelayMessage(expiration+"",rabbitOrderBo);
        LoggerUtil.info("【已发货等待自动收货消息发送时间】:" + LocalDateTime.now());

    }


    @Override
    public OrderDetailVo getDetailById(Long id) {
        //订单基本信息
        OrderDetailVo orderDetailVo = mapper.loadById(id);
        //订单实名认证信息
        if (orderDetailVo.getRealUserId()!=null){
            OmRealUserPo queryRealUser = realUserMapper.selectById(orderDetailVo.getRealUserId());
            //防止实名认证的创建时间复制过去
            BeanUtils.copyProperties(queryRealUser,orderDetailVo,"createTime");
        }
        //订单商品信息
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
    public PageInfo<AppSearchOrderVo> searchAppOrder(SearchMyOrderDto searchMyOrderDto) {
        //待核销和已完成是商家的
        if (searchMyOrderDto.getStatus()!=null&&searchMyOrderDto.getStatus().getId()>=OrderStatusEnum.WAIT_WRITE_OFF.getId()){
            SysUserPo currUser = securityUtil.getCurrUser();
            boolean isFinish=true;
            //商家待核销==》未使用 已完成==》待评价、已评价
            if (searchMyOrderDto.getStatus()==OrderStatusEnum.WAIT_WRITE_OFF){
                isFinish=false;
            }
            boolean finalIsFinish = isFinish;
            PageInfo<AppSearchOrderVo> appSearchOrderVoPageInfo = PageHelper.startPage(searchMyOrderDto.getPageNo(), searchMyOrderDto.getPageSize())
                    .doSelectPageInfo(() -> mapper.searchStoreAppOrder(currUser.getStoreId(), finalIsFinish));
            if (isFinish){
                appSearchOrderVoPageInfo.getList().forEach(x->x.setStatus(OrderStatusEnum.FINISH));
            }
            else {
                appSearchOrderVoPageInfo.getList().forEach(x->x.setStatus(OrderStatusEnum.WAIT_WRITE_OFF));
            }
            return appSearchOrderVoPageInfo;
        }
        else {
            UmUserPo appCurrUser = securityUtil.getAppCurrUser();
            PageInfo<AppSearchOrderVo> appSearchOrderVoPageInfo = PageHelper.startPage(searchMyOrderDto.getPageNo(), searchMyOrderDto.getPageSize())
                    .doSelectPageInfo(() -> mapper.searchAppOrder(appCurrUser.getId(), searchMyOrderDto.getStatus()));
            appSearchOrderVoPageInfo.getList().forEach(x -> {
                List<SmSendGoodsTempVo> smSendGoodsTempVos = mapper.searchSendGoodsTemp(x.getOrderId());
                x.setSmSendGoodsTempVos(smSendGoodsTempVos);
            });
            return appSearchOrderVoPageInfo;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SubmitOrderVo payOrder(Long orderId) {
        //找出原先的支付单
        PayOrderPo queryPayOrder = mapper.getPayOrderByOrderId(orderId);
        //把原先的支付单禁用掉
        if (queryPayOrder.getEnabled()) {
            queryPayOrder.setEnabled(false);
            payOrderMapper.updateById(queryPayOrder);
        }
        //生成新的支付单
        PayOrderPo savePayOrderPo = new PayOrderPo();
        BeanUtils.copyProperties(queryPayOrder, savePayOrderPo, "id");

        OmOrderPo queryOrder = mapper.selectById(orderId);

        //新的支付单生成新的金额 没有活动，优惠金额为0
        savePayOrderPo.setTotalRealPayMoney(queryOrder.getRealMoney()).setTotalDiscount(BigDecimal.ZERO)
                .setTotalShipMoney(queryOrder.getShipMoney()).setTotalTaxMoney(queryOrder.getTaxMoney()).setTotalRedEnvelops(queryOrder.getRedEnvelops())
                .setTotalShopTicket(queryOrder.getShopTicket()).setTotalMoney(queryOrder.getTotalMoney()).setTotalNumber(queryOrder.getTotalNumber())
                .setTotalShopTicketMoney(queryOrder.getShopTicketMoney()).setTotalRedEnvelopsMoney(queryOrder.getRedEnvelopsMoney())
                .setEnabled(true);

        payOrderMapper.insert(savePayOrderPo);

        //订单外键改为新的支付单
        OmOrderPo updateOrder = new OmOrderPo();
        updateOrder.setPayOrderId(savePayOrderPo.getId());
        updateOrder.setId(queryOrder.getId());
        mapper.updateById(updateOrder);

        SubmitOrderVo submitOrderVo=new SubmitOrderVo();
        submitOrderVo.setPayOrderId(savePayOrderPo.getId()).setTotalRealPayMoney(queryOrder.getRealMoney())
                .setTotalIntegral(BigDecimal.ZERO).setTotalShopTicket(queryOrder.getShopTicket()).setTotalRedEnvelops(queryOrder.getRedEnvelops());

        return submitOrderVo;
    }

    @Override
    public AppMyOrderDetailVo getAppMyOrderDetailVoByOrderId(Long orderId) {
        //获取订单详情基本信息
        AppMyOrderDetailVo appMyOrderDetailVo = mapper.getAppMyOrderDetailVoByOrderId(orderId);
        //获取商品信息
        List<AppMyOrderDetailGoodsVo> appMyOrderDetailGoodsVos = mapper.getAppMyOrderDetailGoodsVoByOrderId(orderId);
        //组装店铺信息
        AppMyOrderDetailStoreVo appMyOrderDetailStoreVo = new AppMyOrderDetailStoreVo();
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
        OrderRewardBo orderRewardBo = mapper.getOrderRewardByOrderId(orderId);
        BeanUtils.copyProperties(orderRewardBo, appMyOrderDetailVo);

        appMyOrderDetailVo.setAppMyOrderDetailStoreVo(appMyOrderDetailStoreVo);

        //如果是自取或者服务类的商品就有二维码
        if (appMyOrderDetailVo.getGoodsType().equals(GoodsTypeEnum.PICK_UP_INSTORE.getName())||
                appMyOrderDetailVo.getGoodsType().equals(GoodsTypeEnum.SERVICES.getName()) ){
            appMyOrderDetailVo.setQRCode(JasyptUtil.encyptPwd(password,appMyOrderDetailVo.getOrderId().toString()));

        }

        return appMyOrderDetailVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveOrder(Long orderId) {
        OmOrderPo queryOrder = mapper.selectById(orderId);
        if (queryOrder == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "订单id不存在！");
        }
        if (queryOrder.getStatus() != OrderStatusEnum.NEED_RECEIVE_GOODS) {
            throw new ServiceException(ResultCode.FAIL, "该订单不是待收货订单，不能进行确认收货操作！");
        }
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        Integer refundDay;
        //如果是含税商品
        if (queryOrder.getTaxMoney().compareTo(BigDecimal.ZERO) != 0) {
            refundDay = basicSettingPo.getTaxRefundDay();
        } else {
            refundDay = basicSettingPo.getRefundDay();
        }
        //计算截止时间
        LocalDateTime afterSaleDeadline = LocalDateTime.now().plusDays(refundDay);
        OmOrderPo updateOrder = new OmOrderPo();
        updateOrder.setId(orderId).setReceiveTime(LocalDateTime.now()).setAfterSaleDeadline(afterSaleDeadline)
                .setStatus(OrderStatusEnum.NEED_EVALUATE);
        mapper.updateById(updateOrder);

        //多久自动评价(毫秒)
        String expiration = basicSettingPo.getAutoCommentDay() * 24 * 60 * 60 * 1000 + "";
        //多久售后截止(毫秒)
        String afterExpiration = refundDay * 24 * 60 * 60 * 1000 + "";

        RabbitOrderBo rabbitOrderBo = new RabbitOrderBo();
        rabbitOrderBo.setOrderId(orderId).setOrderStatusEnum(OrderStatusEnum.NEED_EVALUATE);

        //添加自动评价延时队列
        rabbitUtil.sendDelayMessage(expiration + "", rabbitOrderBo);
        //添加售后截止延时队列,售后截止队列的状态为空
        RabbitOrderBo afterRabbitOrderBo = new RabbitOrderBo();
        afterRabbitOrderBo.setOrderId(orderId);
        rabbitUtil.sendDelayMessage(afterExpiration + "", afterRabbitOrderBo);

        LoggerUtil.info("【确认收货后：待评价===》自动评价发送时间】:" + LocalDateTime.now());
        LoggerUtil.info("【确认收货等待售后截止】:" + LocalDateTime.now());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void afterPayDo(Long payOrderId) {
        PayOrderPo queryPayOrder = payOrderMapper.selectById(payOrderId);
        //改订单状态
        QueryWrapper<OmOrderPo> queryOrderWrapper = new QueryWrapper<>();
        queryOrderWrapper.lambda().eq(OmOrderPo::getPayOrderId, payOrderId);
        List<OmOrderPo> queryOrders = mapper.selectList(queryOrderWrapper);
        queryOrders.forEach(x -> {
            OmOrderPo updateOrder = new OmOrderPo();
            updateOrder.setId(x.getId()).setPayTime(queryPayOrder.getPayTime());
            //自取与服务类商品没有发货状态
            if (x.getGoodsType().equals(GoodsTypeEnum.PICK_UP_INSTORE.getName()) ||
                    x.getGoodsType().equals(GoodsTypeEnum.SERVICES.getName())) {
                updateOrder.setStatus(OrderStatusEnum.NEED_USE);
            } else {
                updateOrder.setStatus(OrderStatusEnum.NEED_SEND_GOODS);
            }
            mapper.updateById(updateOrder);
        });

        //sku和商品增加销量
        QueryWrapper<OmGoodsTempPo> queryGoodsTempWrapper = new QueryWrapper<>();
        queryGoodsTempWrapper.lambda().in(OmGoodsTempPo::getOrderId, queryOrders.stream().map(OmOrderPo::getId).collect(Collectors.toList()));
        List<OmGoodsTempPo> queryGoodsTemps = goodsTempMapper.selectList(queryGoodsTempWrapper);
        queryGoodsTemps.forEach(x -> {
            goodsSkuMapper.addASalesVolume(x.getSkuId(), x.getNumber());
            goodsMapper.addASalesVolume(x.getGoodsId(), x.getNumber());
        });


    }

    /**
     * @Author yeJH
     * @Date 2019/9/21 14:37
     * @Description 售后时间关闭   订单返佣
     * 返佣的流水分两种  分配给下单用户本人的是购物奖励  分配给下单用户之外的人是好友助攻 此处是购物奖励
     * 只有积分，购物券有购物奖励
     *
     * @Update yeJH
     *
     * @Param   relId 关联订单id
     * @Param   umUserId  获得流水用户
     * @Param   integrate  获得积分
     * @Param   shopTicket  获得购物券
     * @return void
     **/
    private void addShoppingRewardLog(Long relId, Long umUserId, BigDecimal integrate, BigDecimal shopTicket) {
        //返佣的流水分两种  分配给下单用户本人的是购物奖励  分配给下单用户之外的人是好友助攻 此处是购物奖励
        AddAccountLogBo shoppingRewardLogBo = new AddAccountLogBo();
        shoppingRewardLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.SHOPPING_REWARD);
        shoppingRewardLogBo.setRelId(relId);
        shoppingRewardLogBo.setOperator("auto");
        shoppingRewardLogBo.setMarginIntegral(integrate);
        shoppingRewardLogBo.setMarginShopTicket(shopTicket);
        shoppingRewardLogBo.setUmUserId(umUserId);
        //listenerAccountLogQueue 消息队列
        this.rabbitTemplate.convertAndSend(
                RabbitConstants.ACCOUNT_LOG_EXCHANGE, RabbitConstants.ACCOUNT_LOG_ROUTING_KEY, shoppingRewardLogBo);

    }

    /**
     * @Author yeJH
     * @Date 2019/9/21 14:37
     * @Description 售后时间关闭   订单返佣
     * 返佣的流水分两种  分配给下单用户本人的是购物奖励  分配给下单用户之外的人是好友助攻 此处是购物奖励
     * 只有积分，红包有好友助攻
     *
     * @Update yeJH
     *
     * @Param   relId 关联订单id
     * @Param   umUserId  获得流水用户
     * @Param   redEnvelops  红包
     * @Param   integrate  积分
     * @return void
     **/
    private void addFriendsAssistLog(Long relId, Long umUserId, BigDecimal integrate, BigDecimal redEnvelops) {
        //返佣的流水分两种  红包的是好友助攻  积分购物券的是购物奖励 此处是好友助攻
        AddAccountLogBo shoppingRewardLogBo = new AddAccountLogBo();
        shoppingRewardLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.FRIENDS_ASSIST);
        shoppingRewardLogBo.setRelId(relId);
        shoppingRewardLogBo.setOperator("auto");
        shoppingRewardLogBo.setMarginRedEnvelops(redEnvelops);
        shoppingRewardLogBo.setMarginIntegral(integrate);
        shoppingRewardLogBo.setUmUserId(umUserId);
        //listenerAccountLogQueue 消息队列
        this.rabbitTemplate.convertAndSend(
                RabbitConstants.ACCOUNT_LOG_EXCHANGE, RabbitConstants.ACCOUNT_LOG_ROUTING_KEY, shoppingRewardLogBo);

    }

    /**
     * 根据商品快照返佣
     * @param goodsTempId 商品快照id
     */
    @Override
    public void rakeBack(Long goodsTempId) {

        OmGoodsTempPo queryGoodsTemp = goodsTempMapper.selectById(goodsTempId);

        // TODO: 2019/9/10 俊浩流水(完成)

        //查出下单用户需要返的购物券、积分、经验值
        RewardBuyerBo rewardBuyerBo = mapper.getRewardBoByGoodsTempId(goodsTempId);

        //用户返购物券 积分 经验值 订单数  消费额
        UmUserPo addUser = new UmUserPo();
        addUser.setCurrentExperience(rewardBuyerBo.getRewardExperience()).setCurrentIntegral(rewardBuyerBo.getRewardIntegrate())
                .setCurrentShopTicket(rewardBuyerBo.getRewardShopTicket()).setId(Long.parseLong(queryGoodsTemp.getCreateBy())).setTotalOrder(1).
                setTotalConsumeMoney(rewardBuyerBo.getRealPayMoney());
        userMapper.updateAdd(addUser);

        //购物奖励  下单用户本人有获得积分，购物券
        addShoppingRewardLog(queryGoodsTemp.getOrderId(),
                Long.parseLong(queryGoodsTemp.getCreateBy()),
                rewardBuyerBo.getRewardIntegrate(),
                rewardBuyerBo.getRewardShopTicket());

        //查出需要返佣的用户
        QueryWrapper<PayUserRelationPo> payUserWrapper = new QueryWrapper<>();
        payUserWrapper.lambda().eq(PayUserRelationPo::getOrderId, queryGoodsTemp.getOrderId());
        PayUserRelationPo queryPayUser = payUserRelationMapper.selectOne(payUserWrapper);
        //set下一级的用户集合
        QueryWrapper<PayUserRelationNextLevelPo> nextUserWrapper = new QueryWrapper<>();
        nextUserWrapper.lambda().eq(PayUserRelationNextLevelPo::getPayUserRealtionId,queryPayUser.getId());
        List<PayUserRelationNextLevelPo> payUserRelationNextLevelPos = payUserRelationNextLevelMapper.selectList(nextUserWrapper);
        queryPayUser.setNextUserIds(payUserRelationNextLevelPos.stream().map(x->x.getNextUserId()).collect(Collectors.toList()));

        //基本参数设置
        BasicSettingPo queryBasicSetting = basicSettingMapper.selectOne(new QueryWrapper<>());

        //订单实付金额
        BigDecimal realPayMoney = rewardBuyerBo.getRealPayMoney();
        if (queryPayUser != null) {
            //返红包
            rewardRed(queryPayUser, queryBasicSetting,queryGoodsTemp);
            //上两级用户
            if (queryPayUser.getLastTwoUserId() != null) {
                //得到积分
                BigDecimal lastTwoIntegrate = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastTwoLevelIntegrate(), 100));
                //得到经验值
                BigDecimal lastTwoExperience = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastTwoLevelExperience(), 100));
                //增加积分和经验值
                UmUserPo updateLastTwo=new UmUserPo();
                updateLastTwo.setId(queryPayUser.getLastTwoUserId()).setCurrentExperience(lastTwoExperience).setCurrentIntegral(lastTwoIntegrate);
                userMapper.updateAdd(updateLastTwo);
                umUserService.updateLevel(queryPayUser.getLastTwoUserId());

                //好友助攻 上两级用户获得积分
                addFriendsAssistLog(queryGoodsTemp.getOrderId(), queryPayUser.getLastTwoUserId(), lastTwoIntegrate, BigDecimal.ZERO);

            }
            //上一级用户
            if (queryPayUser.getLastOneUserId() != null) {
                //得到积分
                BigDecimal integrate = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastLevelIntegrate(), 100));
                //得到经验值
                BigDecimal experience = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastLevelExperience(), 100));
                //增加积分和经验值
                UmUserPo updateUser=new UmUserPo();
                updateUser.setId(queryPayUser.getLastOneUserId()).setCurrentExperience(experience).setCurrentIntegral(integrate);
                userMapper.updateAdd(updateUser);
                umUserService.updateLevel(queryPayUser.getLastOneUserId());

                //好友助攻  上一级用户获得积分
                addFriendsAssistLog(queryGoodsTemp.getOrderId(), queryPayUser.getLastOneUserId(), integrate, BigDecimal.ZERO);

            }
            //下一级用户集合
            if (ListUtil.isListNullAndEmpty(queryPayUser.getNextUserIds())) {
                //得到积分
                BigDecimal integrate = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getNextLevelIntegrate(), 100));
                //得到经验值
                BigDecimal experience = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getNextLevelExperience(), 100));
                //增加积分和经验值
                queryPayUser.getNextUserIds().forEach(x->{
                    UmUserPo updateUser=new UmUserPo();
                    updateUser.setId(x).setCurrentExperience(experience).setCurrentIntegral(integrate);
                    userMapper.updateAdd(updateUser);
                    umUserService.updateLevel(x);

                });
            }

        }


    }

    public void orderDeadline(Long orderId) {

        OmOrderPo queryOrder = mapper.selectById(orderId);


        Long userId = Long.parseLong(queryOrder.getCreateBy());

        //查出下单用户需要返的购物券、积分、经验值
        RewardBuyerBo rewardBuyerBo = mapper.getRewardBoByOrder(orderId);
        //用户返购物券 积分 经验值
        UmUserPo addUser = new UmUserPo();
        addUser.setCurrentExperience(rewardBuyerBo.getRewardExperience()).setCurrentIntegral(rewardBuyerBo.getRewardIntegrate())
                .setCurrentShopTicket(rewardBuyerBo.getRewardShopTicket()).setId(userId).setTotalOrder(1).
                setTotalConsumeMoney(rewardBuyerBo.getRealPayMoney());
        userMapper.updateAdd(addUser);

        //购物奖励  下单用户本人有获得积分，购物券
        addShoppingRewardLog(queryOrder.getId(), userId, rewardBuyerBo.getRewardIntegrate(), rewardBuyerBo.getRewardShopTicket());

        //查出需要返佣的用户
        QueryWrapper<PayUserRelationPo> payUserWrapper = new QueryWrapper<>();
        payUserWrapper.lambda().eq(PayUserRelationPo::getOrderId, orderId);
        PayUserRelationPo queryPayUser = payUserRelationMapper.selectOne(payUserWrapper);
        //基本参数设置
        BasicSettingPo queryBasicSetting = basicSettingMapper.selectOne(Wrappers.emptyWrapper());

        //订单实付金额
        BigDecimal realPayMoney = rewardBuyerBo.getRealPayMoney();
        if (queryPayUser != null) {
            //返红包
            rewardRed(queryPayUser, queryBasicSetting);
            //上两级用户
            if (queryPayUser.getLastTwoUserId() != null) {
                //得到经验值
                BigDecimal lastTwoExperience = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastTwoLevelExperience(), 100));
                //得到积分
                BigDecimal lastTwoIntegrate = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastTwoLevelIntegrate(), 100));
                //增加积分和经验值
                UmUserPo updateLastTwo=new UmUserPo();
                updateLastTwo.setId(queryPayUser.getLastTwoUserId()).setCurrentExperience(lastTwoExperience).setCurrentIntegral(lastTwoIntegrate);
                userMapper.updateAdd(updateLastTwo);
                umUserService.updateLevel(queryPayUser.getLastTwoUserId());

                //好友助攻  上两级用户获得积分
                addFriendsAssistLog(queryOrder.getId(), queryPayUser.getLastTwoUserId(), lastTwoIntegrate, BigDecimal.ZERO);

            }
            //上一级用户
            if (queryPayUser.getLastOneUserId() != null) {
                //得到经验值
                BigDecimal experience = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastLevelExperience(), 100));
                //得到积分
                BigDecimal integrate = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getLastLevelIntegrate(), 100));
                //增加积分和经验值
                UmUserPo updateUser=new UmUserPo();
                updateUser.setId(queryPayUser.getLastOneUserId()).setCurrentExperience(experience).setCurrentIntegral(integrate);
                userMapper.updateAdd(updateUser);
                umUserService.updateLevel(queryPayUser.getLastOneUserId());

                //好友助攻  上一级用户获得积分
                addFriendsAssistLog(queryOrder.getId(), queryPayUser.getLastOneUserId(), integrate, BigDecimal.ZERO);

            }

            //下一级用户集合
            if (!ListUtil.isListNullAndEmpty(queryPayUser.getNextUserIds())) {
                //得到经验值
                BigDecimal experience = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getNextLevelExperience(), 100));
                //得到积分
                BigDecimal integrate = BigDecimalUtil.safeMultiply(realPayMoney, BigDecimalUtil.safeDivide(queryBasicSetting.getNextLevelIntegrate(), 100));
                //增加积分和经验值
                queryPayUser.getNextUserIds().forEach(x->{
                    UmUserPo updateUser=new UmUserPo();
                    updateUser.setId(x).setCurrentExperience(experience).setCurrentIntegral(integrate);
                    userMapper.updateAdd(updateUser);
                    umUserService.updateLevel(x);

                });
            }

        }

        //用户总消费、总订单数
        UmUserPo updateUser=new UmUserPo();
        updateUser.setId(userId).setTotalConsumeMoney(realPayMoney).setTotalOrder(1);
        userMapper.updateAdd(updateUser);

        //商品销售报表
        omOrderReportService.orderClosure(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void writeOffOrder(WriteOffDto writeOffDto) {
        SysUserPo currUser = securityUtil.getCurrUser();
        //二维码内容解密成订单id
        Long orderId = Long.parseLong(JasyptUtil.decyptPwd(password, writeOffDto.getQRCode()));
        OmOrderPo queryOrder = mapper.selectById(orderId);
        if (!currUser.getStoreId().equals(queryOrder.getStoreId())){
            throw new ServiceException(ResultCode.FAIL,"操作失败！不是该店铺的订单！");
        }
        if (queryOrder.getStatus()!=OrderStatusEnum.NEED_USE){
            throw new ServiceException(ResultCode.FAIL,String.format("该订单处于【%s】状态！不允许使用",queryOrder.getStatus().getName()));
        }

        //修改订单状态
        OmOrderPo updateOrder=new OmOrderPo();
        updateOrder.setId(orderId).setStatus(OrderStatusEnum.NEED_EVALUATE).setUpdateBy(currUser.getId());
        mapper.updateById(updateOrder);
        //延迟队列：待评价===》已评价
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        //多久自动评价(毫秒)
        String expiration = basicSettingPo.getAutoCommentDay() * 24 * 60 * 60 * 1000 + "";
        RabbitOrderBo rabbitOrderBo = new RabbitOrderBo();
        rabbitOrderBo.setOrderId(orderId).setOrderStatusEnum(OrderStatusEnum.NEED_EVALUATE);

        //商家核销后进行返佣
        orderDeadline(orderId);

        //添加自动评价延时队列
        rabbitUtil.sendDelayMessage(expiration + "", rabbitOrderBo);
        LoggerUtil.info("【确认收货后：待评价===》自动评价发送时间】:" + LocalDateTime.now());


    }

    /**
     * 返红包
     *
     * @param queryPayUser
     */
    private void rewardRed(PayUserRelationPo queryPayUser, BasicSettingPo basicSettingPo) {
        UmUserPo userPo = userMapper.selectById(queryPayUser.getCreateBy());
        PmMemberLevelPo queryMember = memberLevelMapper.selectById(userPo.getMemberLevelId());

        if (queryPayUser.getFirstUserId() == null) {
            return;
        }
        List<RewardRedBo> rewardBuyers = mapper.getRewardBuyer(queryPayUser.getOrderId());
        //计算红包
        final BigDecimal[] totalRed = {BigDecimal.ZERO};
        rewardBuyers.forEach(x -> {
            x.setPacketPresent(queryMember.getPacketPresent());
            x.setMoneyToRed(basicSettingPo.getMoneyToCurrentRedEnvelops());
            BigDecimal red = x.calculateRed();
            totalRed[0] = BigDecimalUtil.safeAdd(totalRed[0], BigDecimalUtil.safeMultiply(red, x.getNumber()));
        });
        UmUserPo queryFirstUser=umUserService.getById(queryPayUser.getFirstUserId());
        //如果只有第一级上级用户且一级用户有返佣资格
        if (queryPayUser.getSecondUserId() == null&&queryFirstUser.getCommissionStatus()) {
            UmUserPo updateFirst = new UmUserPo();
            updateFirst.setId(queryPayUser.getFirstUserId()).setCurrentRedEnvelops(totalRed[0]);
            userMapper.updateAdd(updateFirst);

            //好友助攻 返佣最高等级用户获得红包
            addFriendsAssistLog(queryPayUser.getOrderId(), queryPayUser.getFirstUserId(), BigDecimal.ZERO, totalRed[0]);

        }
        //如果有两个上级需要返佣
        else if (queryPayUser.getSecondUserId() != null) {
            //第一级用户的赠送比例
            BigDecimal firstPacketPresent = userMapper.getPacketPresent(queryPayUser.getFirstUserId());
            //第二级用户的赠送比例
            BigDecimal secondPacketPresent = userMapper.getPacketPresent(queryPayUser.getSecondUserId());
            //第一级用户占比
            BigDecimal firstRatio = BigDecimalUtil.safeDivide(firstPacketPresent, BigDecimalUtil.safeAdd(firstPacketPresent, secondPacketPresent));
            //第二级用户占比
            BigDecimal secondRatio = BigDecimalUtil.safeDivide(secondPacketPresent, BigDecimalUtil.safeAdd(firstPacketPresent, secondPacketPresent));
            //第一级别用户获得红包
            BigDecimal firstRed = BigDecimalUtil.safeMultiply(totalRed[0], firstRatio);
            //第二级别用户获得红包
            BigDecimal secondRed = BigDecimalUtil.safeMultiply(totalRed[0], secondRatio);

            UmUserPo querySecondUser=umUserService.getById(queryPayUser.getFirstUserId());

            //一级佣金判断资格
            if (queryFirstUser.getCommissionStatus()) {

                //好友助攻 返佣最高等级用户获得红包
                addFriendsAssistLog(queryPayUser.getOrderId(), queryPayUser.getFirstUserId(), BigDecimal.ZERO, firstRed);

                UmUserPo updateFirst = new UmUserPo();
                updateFirst.setId(queryPayUser.getFirstUserId()).setCurrentRedEnvelops(firstRed);
                userMapper.updateAdd(updateFirst);
            }

            //二级佣金判定资格
            if (querySecondUser.getCommissionStatus()) {
                UmUserPo updateSecond = new UmUserPo();
                updateSecond.setId(queryPayUser.getSecondUserId()).setCurrentRedEnvelops(secondRed);
                userMapper.updateAdd(updateSecond);

                //好友助攻 返佣最高等级用户获得红包
                addFriendsAssistLog(queryPayUser.getOrderId(), queryPayUser.getSecondUserId(), BigDecimal.ZERO, secondRed);

            }

        }
    }

    /**
     * goodsTemp返红包
     *
     * @param queryPayUser
     */
    private void rewardRed(PayUserRelationPo queryPayUser, BasicSettingPo basicSettingPo, OmGoodsTempPo queryGoodsTemp) {



        UmUserPo userPo = userMapper.selectById(queryPayUser.getCreateBy());
        PmMemberLevelPo queryMember = memberLevelMapper.selectById(userPo.getMemberLevelId());
        if (queryPayUser.getFirstUserId() == null) {
            return;
        }
        RewardRedBo rewardBuyer = mapper.getRewardBuyerByGoodsTempId(queryGoodsTemp.getId());
        //计算红包
        rewardBuyer.setPacketPresent(queryMember.getPacketPresent());
        rewardBuyer.setMoneyToRed(basicSettingPo.getMoneyToCurrentRedEnvelops());
        BigDecimal red = rewardBuyer.calculateRed();

        UmUserPo queryFirstUser=umUserService.getById(queryPayUser.getFirstUserId());
        //如果只有第一级上级用户且一级用户有返佣资格
        if (queryPayUser.getSecondUserId() == null&&queryFirstUser.getCommissionStatus()) {
            UmUserPo updateFirst = new UmUserPo();
            updateFirst.setId(queryPayUser.getFirstUserId()).setCurrentRedEnvelops(red);
            userMapper.updateAdd(updateFirst);

            //好友助攻 返佣最高等级用户获得红包
            addFriendsAssistLog(queryGoodsTemp.getOrderId(), queryPayUser.getFirstUserId(), BigDecimal.ZERO,  red);

        }
        //如果有两个上级需要返佣
        else if (queryPayUser.getSecondUserId() != null) {
            //第一级用户的赠送比例
            BigDecimal firstPacketPresent = userMapper.getPacketPresent(queryPayUser.getFirstUserId());
            //第二级用户的赠送比例
            BigDecimal secondPacketPresent = userMapper.getPacketPresent(queryPayUser.getSecondUserId());
            //第一级用户占比
            BigDecimal firstRatio = BigDecimalUtil.safeDivide(firstPacketPresent, BigDecimalUtil.safeAdd(firstPacketPresent, secondPacketPresent));
            //第二级用户占比
            BigDecimal secondRatio = BigDecimalUtil.safeDivide(secondPacketPresent, BigDecimalUtil.safeAdd(firstPacketPresent, secondPacketPresent));
            //第一级别用户获得红包
            BigDecimal firstRed = BigDecimalUtil.safeMultiply(red, firstRatio);
            //第二级别用户获得红包
            BigDecimal secondRed = BigDecimalUtil.safeMultiply(red, secondRatio);

            UmUserPo querySecondUser=umUserService.getById(queryPayUser.getFirstUserId());

            //一级佣金判断资格
            if (queryFirstUser.getCommissionStatus()) {
                UmUserPo updateFirst = new UmUserPo();
                updateFirst.setId(queryPayUser.getFirstUserId()).setCurrentRedEnvelops(firstRed);
                userMapper.updateAdd(updateFirst);

                //好友助攻 返佣最高等级用户获得红包
                addFriendsAssistLog(queryGoodsTemp.getOrderId(), queryPayUser.getFirstUserId(), BigDecimal.ZERO,  firstRed);

            }

            //二级佣金判定资格
            if (querySecondUser.getCommissionStatus()) {
                UmUserPo updateSecond = new UmUserPo();
                updateSecond.setId(queryPayUser.getSecondUserId()).setCurrentRedEnvelops(secondRed);
                userMapper.updateAdd(updateSecond);

                //好友助攻 返佣最高等级用户获得红包
                addFriendsAssistLog(queryGoodsTemp.getOrderId(), queryPayUser.getSecondUserId(), BigDecimal.ZERO, secondRed);

            }

        }
    }

}
