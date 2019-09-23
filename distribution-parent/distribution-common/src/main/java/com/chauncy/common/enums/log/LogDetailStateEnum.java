package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/9/11 18:56
 */
public enum LogDetailStateEnum   implements BaseEnum {
    /**
     * 流水详情当前状态
     */
    DEPOSIT_WALLET(1, "已存入钱包"),
    PAYMENT_SUCCESS(2, "支付成功"),
    WITHDRAWN_APPLY(3, "提现申请"),
    WITHDRAWAL_FAIL(4, "提现失败"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    LogDetailStateEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static LogDetailStateEnum getById(Integer id) {
        for (LogDetailStateEnum type : LogDetailStateEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static LogDetailStateEnum fromName(String name) {
        for (LogDetailStateEnum type : LogDetailStateEnum.values()) {
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
