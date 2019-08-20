package com.chauncy.web.config.rabbit.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.util.LoggerUtil;
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
 * @Author zhangrt
 * @Date 2019/7/22 15:34
 **/

@Component
public class AutoCloseOrderHandler {

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
        if (queryPayOrder.getStatus().equals(PayOrderStatusEnum.NEED_PAY.getId())){
            orderService.closeOrderByPayId(payOrderId);
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
// TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }

    @RabbitListener(queues = {RabbitConstants.AUTO_COMMENT_QUEUE})
    public void autoCommentQueue(Long orderId, Message message, Channel channel) {
        LoggerUtil.info(String.format("[自动评价队列 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), orderId));
        //如果订单状态为未支评价,就去自动评价
        OmOrderPo queryOrder=orderService.getById(orderId);
        if (queryOrder!=null&&queryOrder.getStatus().equals(OrderStatusEnum.NEED_EVALUATE)){
            //查出商品快照列表
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("order_id",orderId);
            List<OmGoodsTempPo> goodsTempPos=goodsTempService.list(queryWrapper);

            List<OmEvaluatePo> evaluatePos= Lists.newArrayListWithExpectedSize(goodsTempPos.size());
            goodsTempPos.forEach(x->{
                OmEvaluatePo saveEvaluate=new OmEvaluatePo();
                saveEvaluate.setOrderId(orderId).setAttitudeStartLevel(5).setContent("用户默认好评！")
                .setDescriptionStartLevel(5).setShipStartLevel(5).setSkuId(x.getSkuId());
                evaluatePos.add(saveEvaluate);
            });
            //添加默认评价
            evaluateService.saveBatch(evaluatePos);

            //更改订单状态
            OmOrderPo updateOrder = new OmOrderPo();
            updateOrder.setId(orderId).setStatus(OrderStatusEnum.ALREADY_EVALUATE);
            orderService.updateById(updateOrder);
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
// TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }

}
