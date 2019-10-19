package com.chauncy.common.enums.user;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author yeJH
 * @description: 用户不能提现原因枚举
 * @since 2019/10/17 18:27
 */
@Getter
public enum RefuseWithdrawalEnum  implements BaseEnum {

    CAN_WITHDRAWAL(0, "可以提现"),
    NON_REAL_NAME(1,"未完成实名认证"),
    INCOMPLETE(2,"有提现未完成"),
    ;

    private Integer id;

    private String name;

    RefuseWithdrawalEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.id + "_" +this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static RefuseWithdrawalEnum getSpellGroupMainTypeEnumById(Integer id) {
        for (RefuseWithdrawalEnum type : RefuseWithdrawalEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static RefuseWithdrawalEnum fromName(String name) {
        for (RefuseWithdrawalEnum type : RefuseWithdrawalEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static RefuseWithdrawalEnum fromEnumName(String name) {
        for (RefuseWithdrawalEnum validCodeEnum : RefuseWithdrawalEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getSpellGroupMainTypeEnumById(Integer.parseInt(field.toString())));
    }


}
