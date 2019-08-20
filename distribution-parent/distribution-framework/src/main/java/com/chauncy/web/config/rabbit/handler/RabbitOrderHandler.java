package com.chauncy.web.config.rabbit.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.bo.app.order.rabbit.RabbitOrderBo;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.temp.order.service.IOmGoodsTempService;
import com.chauncy.order.evaluate.service.impl.OmEvaluateServiceImpl;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IPayOrderService;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    @RabbitListener(queues = {RabbitConstants.CLOSE_ORDER_QUEUE})
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

    @RabbitListener(queues = {RabbitConstants.ORDER_REDIRECT_QUEUE})
    public void autoCommentQueue(RabbitOrderBo rabbitOrderBo, Message message, Channel channel) {
        LoggerUtil.info(String.format("[订单队列 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), rabbitOrderBo.toString()));
        //如果订单状态为未支评价,就去自动评价
        OmOrderPo queryOrder = orderService.getById(rabbitOrderBo.getOrderId());
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
                                .setDescriptionStartLevel(5).setShipStartLevel(5).setSkuId(x.getSkuId());
                        evaluatePos.add(saveEvaluate);
                    });
                    //添加默认评价
                    evaluateService.saveBatch(evaluatePos);

                    //更改订单状态
                    OmOrderPo updateOrder = new OmOrderPo();
                    updateOrder.setId(rabbitOrderBo.getOrderId()).setStatus(OrderStatusEnum.ALREADY_EVALUATE);
                    orderService.updateById(updateOrder);
                    try {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    } catch (IOException e) {
                        LoggerUtil.error(e);
                    }
                    break;
                case ALREADY_EVALUATE:
                    break;
                case NEED_USE:
                    break;
                case ALREADY_USE:
                    break;
                case ALREADY_CANCEL:
                    break;
            }
        }


    }

}
