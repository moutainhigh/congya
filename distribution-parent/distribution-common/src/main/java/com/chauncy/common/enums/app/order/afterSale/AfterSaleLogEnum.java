package com.chauncy.common.enums.app.order.afterSale;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 售后节点
 *
 * @Author zhangrt
 * @Date 2019/8/21 11:47
 **/
@Getter
public enum AfterSaleLogEnum {

    ONLY_REFUND_BUYER_START(1, "仅退款-买家发起", "您已成功发起退款申请，请耐心等待商家处理",
            "商家同意或超时未处理，系统将退款给您,如果商家拒绝，您可以修改退款申请后再次发起，商家会重新处理"),

    ONLY_REFUND_BUYER_UPDATE(2, "仅退款-买家修改", "您已成功修改退款申请，请耐心等待商家处理",
            "商家同意或超时未处理，系统将退款给您，如果商家拒绝，您可以修改退款申请后再次发起，商家会重新处理"),

    ONLY_REFUND_BUYER_CANCEL(3, "仅退款-买家撤销", "您已撤销退款申请，如问题仍未解决，您可以重新发起申请",
            null),


    ONLY_REFUND_STORE_OVERTIME(4, "仅退款-商家超时未处理", null,
            null),

    ONLY_REFUND_STORE_AGREE(5, "仅退款-商家同意退款", null,
            null),

    ONLY_REFUND_STORE_REFUSE(6, "仅退款-商家拒绝退款", "商家拒绝该退款申请，请尽快处理该退款申请",
            null),

    ONLY_REFUND_BUYER_OVERTIME(7, "仅退款-买家超时未处理", "超时未处理，系统自动关闭，如问题仍未解决，您可以重新发起申请",
            null),

    ONLY_REFUND_BUYER_AGREE(8, "仅退款-买家同意取消", "您已同意取消退款申请，退款关闭。",
            null),

    BUYER_START(9, "退货退款-买家发起", "您已成功发起退款申请，请耐心等待商家处理",
            "商家同意后：请按照给出的退货地址退货\n" +
                    "-如商家拒绝，您可以修改退款申请后再次发起，商家会重新处理\n" +
                    "-如商家超时未处理，退货申请将达成，请按系统给出的退货地址退货"),

    BUYER_UPDATE(10, "退货退款-买家修改",
            "您已成功修改退款申请，请耐心等待商家处理",
            "商家同意后：请按照给出的退货地址退货\n" +
                    "-如商家拒绝，您可以修改退款申请后再次发起，商家会重新处理\n" +
                    "-如商家超时未处理，退货申请将达成，请按系统给出的退货地址退货"),

    BUYER_CANCEL(11, "退货退款-买家撤销", "您已撤销退款申请，如问题仍未解决，您可以重新发起申请",
            null),

    STORE_OVERTIME_UNHANDLED_GOODS(12, "退货退款-商家超时未处理退货", "商家超时未处理，系统自动通过该退款申请，请按系统给出的退货地址尽早退货",
            null),

    STORE_AGREE_GOODS(13, "退货退款-商家同意申请退货", "\n" +
            "商家已同意退货申请，请尽早退货。\n",
            null),

    STORE_REFUSE_GOODS(14, "退货退款-商家拒绝退货", "\n" +
            "商家拒绝该退货申请，请尽快处理该退款申请\n",
            null),

    BUYER_OVERTIME(15, "退货退款-买家超时未处理", "超时未处理，系统自动关闭，如问题仍未解决，您可以重新发起申请",
            null),

    BUYER_AGREE_CANCEL(16, "退货退款-买家同意取消", "\n" +
            "您已同意取消退款申请，退款关闭。\n",
            null),

    BUYER_RETURN_GOODS(17, "退货退款-买家已退货", "您已退货，请等待商家收货后处理",
            null),

    STORE_AGREE_REFUND(18, "退货退款-商家同意退款", null,
            null),

    STORE_REFUSE_REFUND(19, "退货退款-商家拒绝退款", "商家拒绝该退款申请，您可以协商修改或关闭申请。",
            null),

    STORE_OVERTIME_UNHANDLED_REFUND(20, "退货退款-商家超时未处理退款", "\n" +
            "商家超时未处理，系统自动退款",
            null);


    @EnumValue
    private Integer id;

    private String name;

    private String contentTips;

    private String contentExplain;

    AfterSaleLogEnum(Integer id, String name, String contentTips, String contentExplain) {
        this.id = id;
        this.name = name;
        this.contentTips = contentTips;
        this.contentExplain = contentExplain;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    }