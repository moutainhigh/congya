package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/26 14:31
 */
public enum PaymentWayEnum implements BaseEnum {


    /**
     * 付账方式
     * 1.微信
     * 2.支付宝
     * 3.银行卡
     * 4.线下支付
     */
    WECHAT(1, "微信"),
    ALIPAY(2, "支付宝"),
    BANK_CARD(3, "银行卡"),
    OFF_LINE(4, "线下支付"),
    ACCOUNT(4, "账目余额"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    PaymentWayEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name() + ":"  + this.name;
    }

    //通过名称来获取结果
    public static PaymentWayEnum getById(Integer id) {
        for (PaymentWayEnum type : PaymentWayEnum.values()) {
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
