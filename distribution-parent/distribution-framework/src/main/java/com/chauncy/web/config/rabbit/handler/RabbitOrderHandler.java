package com.chauncy.web.config.rabbit.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.bo.app.order.rabbit.RabbitAfterBo;
import com.chauncy.data.bo.app.order.rabbit.RabbitOrderBo;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.bo.order.log.AccountLogBo;
import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.temp.order.service.IOmGoodsTempService;
import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
import com.chauncy.order.evaluate.service.impl.OmEvaluateServiceImpl;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.order.pay.IWxService;
import com.chauncy.order.service.IOmAfterSaleLogService;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IPayOrderService;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 做一些订单到期后的具体工作:取消订单、自动收货、自动评价、自动售后
 *
 * @Author zhangrt
 * @Date 2019/7/22 15:34
 **/

@Component
public class RabbitOrderHandler {

    @Autowired
    private IPayOrderService payOrderService;

    @Autowired
    private IOmOrderService orderService;

    @Autowired
    private IOmGoodsTempService goodsTempService;

    @Autowired
    private OmEvaluateServiceImpl evaluateService;

    @Autowired
    private IOmAfterSaleOrderService afterSaleOrderService;

    @Autowired
    private IOmAfterSaleLogService afterSaleLogService;

    @Autowired
    private IOmAccountLogService omAccountLogService;

    @Autowired
    private IWxService wxService;

    @RabbitListener(queues = {RabbitConstants.CLOSE_ORDER_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void listenerDelayQueue(Long payOrderId, Message message, Channel channel) {
        LoggerUtil.info(String.format("[closeOrderByPayId 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), payOrderId));
        PayOrderPo queryPayOrder = payOrderService.getById(payOrderId);
        //如果订单状态为未支付,就去取消订单
        if (queryPayOrder.getStatus().equals(PayOrderStatusEnum.NEED_PAY.getId())) {
            orderService.closeOrderByPayId(payOrderId);
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
// TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }

    @RabbitListener(queues = {RabbitConstants.ACCOUNT_LOG_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void listenerAccountLogQueue(AddAccountLogBo addAccountLogBo, Message message, Channel channel) {
        LoggerUtil.info(String.format("[saveAccountLog 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), addAccountLogBo));

        try {
            omAccountLogService.saveAccountLog(addAccountLogBo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }

    /**
     * @Author yeJH
     * @Date 2019/10/11 17:30
     * @Description 订单支付完成海外直邮跟保税仓的订单要报关，向海关申报信息
     *
     * @Update yeJH
     *
     * @param  omOrderId  订单
     * @param  message
     * @param  channel
     * @return void
     **/
    @RabbitListener(queues = {RabbitConstants.CUSTOM_DECLARE_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void listenerCustomDeclareQueue(Long omOrderId, Message message, Channel channel) {
        LoggerUtil.info(String.format("[customDeclareOrder 监听的消息] - [消费时间] - [%s] - [%s]",
                LocalDateTime.now(), String.valueOf(omOrderId)));

        try {
            wxService.customDeclareOrder(omOrderId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }

    @RabbitListener(queues = {RabbitConstants.PLATFORM_GIVE_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void listenerPlatformGiveQueue(AddAccountLogBo accountLogBo, Message message, Channel channel) {
        LoggerUtil.info(String.format("[saveAccountLog 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), accountLogBo));

        try {
            omAccountLogService.saveAccountLog(accountLogBo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }

    @RabbitListener(queues = {RabbitConstants.ORDER_REDIRECT_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void autoDoQueue(RabbitOrderBo rabbitOrderBo, Message message, Channel channel) {
        LoggerUtil.info(String.format("[订单队列 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), rabbitOrderBo.toString()));
        //如果订单状态为未支评价,就去自动评价
        OmOrderPo queryOrder = orderService.getById(rabbitOrderBo.getOrderId());
        //如果订单存在且状态为空，表示到了售后的截止时间
        if (queryOrder != null && rabbitOrderBo.getOrderStatusEnum()==null) {
            orderService.orderDeadline(rabbitOrderBo.getOrderId());
        }
            //如果订单存在且状态未发生改变
        if (queryOrder != null && queryOrder.getStatus().equals(rabbitOrderBo.getOrderStatusEnum())) {
            switch (queryOrder.getStatus()) {
                case NEED_PAY:
                    break;
                case NEED_SEND_GOODS:
                    break;
                case NEED_RECEIVE_GOODS:
                    orderService.receiveOrder(rabbitOrderBo.getOrderId());
                    break;
                case NEED_EVALUATE:
                    //查出商品快照列表
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("order_id", rabbitOrderBo.getOrderId());
                    List<OmGoodsTempPo> goodsTempPos = goodsTempService.list(queryWrapper);

                    List<OmEvaluatePo> evaluatePos = Lists.newArrayListWithExpectedSize(goodsTempPos.size());
                    goodsTempPos.forEach(x -> {
                        OmEvaluatePo saveEvaluate = new OmEvaluatePo();
                        saveEvaluate.setOrderId(rabbitOrderBo.getOrderId()).setAttitudeStartLevel(5).setContent("用户默认好评！")
                                .setDescriptionStartLevel(5).setShipStartLevel(5).setSkuId(x.getSkuId()).setCreateBy("auto");
                        evaluatePos.add(saveEvaluate);
                    });
                    //添加默认评价
                    evaluateService.saveBatch(evaluatePos);

                    //更改订单状态
                    OmOrderPo updateOrder = new OmOrderPo();
                    updateOrder.setId(rabbitOrderBo.getOrderId()).setStatus(OrderStatusEnum.ALREADY_EVALUATE);
                    orderService.updateById(updateOrder);
                    break;
                case ALREADY_EVALUATE:
                    break;
                case NEED_USE:
                    break;
                case ALREADY_CANCEL:
                    break;
            }
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            LoggerUtil.error(e);
        }

    }

    /**
     * 售后订单延迟处理
     *
     * @param rabbitAfterBo
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {RabbitConstants.AFTER_REDIRECT_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void afterDelay(RabbitAfterBo rabbitAfterBo, Message message, Channel channel) {


        try {
            LoggerUtil.info(String.format("[售后订单 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), rabbitAfterBo.toString()));
            OmAfterSaleOrderPo queryAfterOrder = afterSaleOrderService.getById(rabbitAfterBo.getAfterSaleOrderId());
            Duration duration = Duration.between(queryAfterOrder.getUpdateTime(),  rabbitAfterBo.getUpdateTime());
            //如果售后订单状态未改变、且修改时间不变,进行超时处理
            //没办法完全比较时间相等，留着两秒冗余， mysql会对时间进行四舍五入
            if ((duration.toMillis()<2000&&duration.toMillis()>-2000) &&
                    queryAfterOrder.getStatus() == rabbitAfterBo.getAfterSaleStatusEnum()) {
                //仅退款 待商家处理==》退款成功
                if (queryAfterOrder.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND &&
                        queryAfterOrder.getStatus() == AfterSaleStatusEnum.NEED_STORE_DO) {
                    wxService.refund(rabbitAfterBo.getAfterSaleOrderId(),true);
                }
                //仅退款和退货退款 待买家处理==》退款关闭
                if (queryAfterOrder.getStatus() == AfterSaleStatusEnum.NEED_BUYER_DO) {
                    afterSaleOrderService.cancel(rabbitAfterBo.getAfterSaleOrderId(),true);
                }
                //退货退款 待商家处理==》待买家发货
                if (queryAfterOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS &&
                        queryAfterOrder.getStatus() == AfterSaleStatusEnum.NEED_STORE_DO) {
                    afterSaleOrderService.permitReturnGoods(rabbitAfterBo.getAfterSaleOrderId(),true);
                }
                //退货退款 待买家退货==》退款关闭
                if (queryAfterOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS &&
                        queryAfterOrder.getStatus() == AfterSaleStatusEnum.NEED_BUYER_RETURN) {
                    afterSaleOrderService.cancel(rabbitAfterBo.getAfterSaleOrderId(),true);
                }
                //退货退款 待商家退款==》退款成功
                if (queryAfterOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS &&
                        queryAfterOrder.getStatus() == AfterSaleStatusEnum.NEED_STORE_REFUND) {
                    wxService.refund(rabbitAfterBo.getAfterSaleOrderId(),true);
                }
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException ex) {
                LoggerUtil.error(e);
            }

        }

    }

}
