package com.chauncy.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/9/11 22:22
 */
public enum OrderPayTypeEnum implements BaseEnum {
    /**
     * 订单支付类型
     */
    GOODS_PAYMENT(1, "商品支付"),
    GIFT_RECHARGE(2, "礼包充值"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    OrderPayTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static OrderPayTypeEnum getById(Integer id) {
        for (OrderPayTypeEnum type : OrderPayTypeEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static OrderPayTypeEnum fromName(String name) {
        for (OrderPayTypeEnum type : OrderPayTypeEnum.values()) {
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
