package com.chauncy.common.enums.system;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2020-03-27 20:33
 */
@Getter
public enum VersionTypeEnum implements BaseEnum {

    ANDROID(1, "android"),
    IOS(2, "ios"),
    ;

    private Integer id;

    private String name;

    VersionTypeEnum(Integer id, String name) {
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
    public static VersionTypeEnum getVersionTypeEnumById(Integer id) {
        for (VersionTypeEnum type: VersionTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static VersionTypeEnum fromName(String name) {
        for (VersionTypeEnum type : VersionTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static VersionTypeEnum fromEnumName(String name) {
        for (VersionTypeEnum couponScopeEnum : VersionTypeEnum.values()) {
            if (couponScopeEnum.name().equals(name))
                return couponScopeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        if (field == null) {
            return true;
        } else
            return Objects.nonNull(getVersionTypeEnumById(Integer.parseInt(field.toString())));
    }
}

