package com.chauncy.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-08-02 01:11
 *
 * 海关上传状态
 */
@Getter
public enum CustomsStatusEnum implements BaseEnum {
    NEED_SEND("1","待上传"),
    SUCCESS("2","上传成功"),
    FAIL("3","上传失败");

    @EnumValue
    private String id;

    private String name;

    CustomsStatusEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static CustomsStatusEnum getStatusEnumById(String id) {
        for (CustomsStatusEnum type : CustomsStatusEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static CustomsStatusEnum fromName(String name) {
        for (CustomsStatusEnum type : CustomsStatusEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static CustomsStatusEnum fromEnumName(String name) {
        for (CustomsStatusEnum validCodeEnum : CustomsStatusEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getStatusEnumById(field.toString()));
    }

}

