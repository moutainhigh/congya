package com.chauncy.common.enums.pay;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/11 16:14
 */
@Getter
public enum CertCheckResultEnum  implements BaseEnum {

    /**
     * 海关申报状态码
     **/
    UNCHECKED(1, "商户未上传订购人身份信息"),
    SAME(2, "商户上传的订购人身份信息与支付人身份信息一致"),
    DIFFERENT(3, "商户上传的订购人身份信息与支付人身份信息不一致"),
    ;

    private Integer id;

    private String name;

    CertCheckResultEnum(Integer id, String name) {
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
    public static CertCheckResultEnum getSpellGroupMainTypeEnumById(Integer id) {
        for (CertCheckResultEnum type : CertCheckResultEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static CertCheckResultEnum fromName(String name) {
        for (CertCheckResultEnum type : CertCheckResultEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static CertCheckResultEnum fromEnumName(String name) {
        for (CertCheckResultEnum validCodeEnum : CertCheckResultEnum.values()) {
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
