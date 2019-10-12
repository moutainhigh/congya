package com.chauncy.common.enums.pay;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/11 15:46
 */
@Getter
public enum DeclareStatusEnum  implements BaseEnum {

    /**
    * 海关申报状态码
    **/
    UNDECLARED(1, "未申报"),
    SUBMITTED(2, "申报已提交"),
    PROCESSING(3, "申报中"),
    SUCCESS(4, "申报成功"),
    FAIL(5, "申报失败"),
    EXCEPT(6, "海关接口异常"),
    ;

    private Integer id;

    private String name;

    DeclareStatusEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + "_" + this.name;
    }

    public static String value(String name) {
        return name;
    }

    //通过Id获取结果
    public static DeclareStatusEnum getSpellGroupMainTypeEnumById(Integer id) {
        for (DeclareStatusEnum type : DeclareStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static DeclareStatusEnum fromName(String name) {
        for (DeclareStatusEnum type : DeclareStatusEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static DeclareStatusEnum fromEnumName(String name) {
        for (DeclareStatusEnum validCodeEnum : DeclareStatusEnum.values()) {
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
