package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 *
 * 账目流水事由
 * 0 平台流水
 * 1 商家流水
 * 2 APP用户红包流水
 * 3 APP用户购物券流水
 * 4 APP用户积分流水
 *
 * @author yeJH
 * @since 2019/7/20 18:04
 */
public enum PlatformLogMatterEnum implements BaseEnum {


    /**
     * 平台流水
     * 1.订单收入  平台收入
     * 2.售后退款  平台支出
     * 3.订单取消  平台支出
     * 4.用户提现  平台支出
     * 5.商家货款提现  平台支出
     * 6.商家利润提现  平台支出
     */
    ORDER_INCOME(1, "订单收入"),
    ORDER_REFUND(2, "售后退款"),
    USER_RECHARGE(3, "用户充值"),
    USER_WITHDRAWAL(4, "用户提现"),
    PAYMENT_WITHDRAWAL(5, "商家货款提现"),
    PROFIT_WITHDRAWAL(6, "商家利润提现"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    PlatformLogMatterEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static PlatformLogMatterEnum getById(Integer id) {
        for (PlatformLogMatterEnum type : PlatformLogMatterEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static PlatformLogMatterEnum fromName(String name) {
        for (PlatformLogMatterEnum type : PlatformLogMatterEnum.values()) {
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
        if(null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }



}
