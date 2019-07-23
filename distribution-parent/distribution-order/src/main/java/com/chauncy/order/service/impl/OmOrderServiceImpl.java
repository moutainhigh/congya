package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.order.OmShoppingCartMapper;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.vo.app.car.ShopTicketSoWithCarGoodVo;
import com.chauncy.order.service.IOmOrderService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean closeOrder(Long payOrderId) {
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
        updateOrder.setStatus(OrderStatusEnum.ALREADY_CANCEL.getId());
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
            shopTicketSoWithCarGoodVo.setId(x.getId());
            //加库存要变为负数
            shopTicketSoWithCarGoodVo.setNumber(x.getNumber()*-1);
            shopTicketSoWithCarGoodVoList.add(shopTicketSoWithCarGoodVo);
        });
        goodsSkuMapper.updateStock(shopTicketSoWithCarGoodVoList);
        //红包与购物券退还
        PayOrderPo queryPayOrder = payOrderMapper.selectById(payOrderId);
        shoppingCartMapper.updateDiscount(BigDecimalUtil.safeMultiply(-1,queryPayOrder.getTotalRedEnvelops()),
                BigDecimalUtil.safeMultiply(-1,queryPayOrder.getTotalShopTicket()),queryPayOrder.getUmUserId() );


        return true;
    }
}
