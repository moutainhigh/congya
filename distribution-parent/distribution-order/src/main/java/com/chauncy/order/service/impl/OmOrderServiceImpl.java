package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.enums.log.PaymentWayEnum;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSendOrderDto;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.order.OmShoppingCartMapper;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.vo.app.car.ShopTicketSoWithCarGoodVo;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.manage.order.list.SearchOrderVo;
import com.chauncy.data.vo.supplier.order.SmOrderDetailVo;
import com.chauncy.data.vo.supplier.order.SmSearchOrderVo;
import com.chauncy.data.vo.supplier.order.SmSendGoodsTempVo;
import com.chauncy.data.vo.supplier.order.SmSendOrderVo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IPayOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.security.core.parameters.P;
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
        List<OmOrderPo> updateOrders = mapper.selectList(orderWrapper);
        List<Long> orderIds = updateOrders.stream().map(OmOrderPo::getId).collect(Collectors.toList());

        //修改状态
        OmOrderPo updateOrder=new OmOrderPo();
        updateOrder.setStatus(OrderStatusEnum.ALREADY_CANCEL);
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
        //把支付单删掉,以后该支付单下的其他订单都单独生成支付单
        OmOrderPo orderPo = mapper.selectById(orderId);
        //支付单失效
        PayOrderPo updatePayOrder=new PayOrderPo();
        updatePayOrder.setId(orderPo.getPayOrderId()).setEnabled(false);
        payOrderMapper.updateById(updatePayOrder);
        //订单改状态
        orderPo.setStatus(OrderStatusEnum.ALREADY_CANCEL);
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
        //更新OmOrderPo
        UpdateWrapper<OmOrderPo> omOrderPoUpdateWrapper = new UpdateWrapper<>();
        omOrderPoUpdateWrapper.lambda().eq(OmOrderPo::getPayOrderId, payOrderPo.getId());
        omOrderPoUpdateWrapper.lambda().set(OmOrderPo::getPayOrderId, payOrderPo.getId());
        //设置待发货状态
        omOrderPoUpdateWrapper.lambda().set(OmOrderPo::getStatus, OrderStatusEnum.NEED_SEND_GOODS);
        omOrderPoUpdateWrapper.lambda().set(OmOrderPo::getPayTime, notifyMap.get("time_end"));
        this.update(omOrderPoUpdateWrapper);
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
    public PageInfo<SmSendOrderVo> searchSmSendOrderList(SmSendOrderDto smSendOrderDto) {
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

        return smOrderDetailVo;
    }
}
