package com.chauncy.common.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/9/14 15:49
 */
public enum NoticeTitleEnum   implements BaseEnum {
    /**
     * 奖励通知  消息标题枚举
     */
    UPGRADE(1, "升级达成"),
    SHOPPING_REWARD(2, "消费奖励"),
    FRIENDS_ASSIST(3, "好友助攻"),
    GET_INTEGRATE(4, "积分到账"),
    GET_SHOP_TICKET(5, "消费券到账"),
    GET_RED_ENVELOPS(6, "红包到账"),
    /**
     * 交易物流  消息标题枚举
     */
    ALREADY_SIGNED(7, "您的订单已签收"),
    SHIPPED(8, "您的订单已发货"),
    REFUND_SUCCESSFUL(9, "退款已经成功"),
    RETURN_GOODS(10, "卖家同意售后，需要您退货"),
    WITHDRAWAL_SUCCESS(11, "提现成功"),
    /**
     * 系统通知
     */
    SYSTEM_NOTICE(12, "系统通知"),
    ;

    @EnumValue
    private Integer id;
    private String name;

    NoticeTitleEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    //通过名称来获取结果
    public static NoticeTitleEnum getById(Integer id) {
        for (NoticeTitleEnum type : NoticeTitleEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static NoticeTitleEnum fromName(String name) {
        for (NoticeTitleEnum type : NoticeTitleEnum.values()) {
            if (type.name().equals(name))
                return type;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isExist(Object field) {
        if (null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }
}

