package com.chauncy.common.enums.log;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/25 17:42
 */
public enum  LogTriggerEventEnum  implements BaseEnum {

    /**
     * 流水触发的事件
     * 1.店铺利润、货款账单提现
     * 2.APP用户提现红包
     * 3.订单下单
     * 4.礼包充值
     * 5.系统赠送
     * 6.售后退款
     * 7.新人礼包
     * 8.购物奖励  （订单完成  下单用户获得购物券 积分）
     * 9.好友助攻  （订单完成  下单用户获得红包）
     */
    STORE_WITHDRAWAL(1, "店铺利润、货款账单提现"),
    APP_WITHDRAWAL(2, "APP用户提现红包"),
    APP_ORDER(3, "订单下单"),
    GIFT_RECHARGE(4, "礼包充值"),
    PLATFORM_GIVE(5, "系统赠送"),
    ORDER_REFUND(6, "售后退款"),
    NEW_GIFT(7, "新人礼包"),
    SHOPPING_REWARD(8, "购物奖励"),
    FRIENDS_ASSIST(9, "好友助攻"),
    ;


    private Integer id;
    private String name;
    LogTriggerEventEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static LogTriggerEventEnum getById(Integer id) {
        for (LogTriggerEventEnum type : LogTriggerEventEnum.values()) {
            if (type.getId().equals(id))
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

        return Objects.nonNull(getById(Integer.parseInt(field.toString())));
    }


}
