package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/29 21:23
 */
public enum WithdrawalWayEnum implements BaseEnum {


    /**
     * 提现方式
     * 1.微信
     * 2.支付宝
     */
    WECHAT(1, "微信"),
    ALIPAY(2, "支付宝"),
    ;


    private Integer id;
    @EnumValue
    private String name;
    WithdrawalWayEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static WithdrawalWayEnum getById(Integer id) {
        for (WithdrawalWayEnum type : WithdrawalWayEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }


    //通过名称来获取结果
    public static WithdrawalWayEnum fromName(String name) {
        for (WithdrawalWayEnum type : WithdrawalWayEnum.values()) {
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

        return Objects.nonNull(fromName(field.toString()));
    }

}
