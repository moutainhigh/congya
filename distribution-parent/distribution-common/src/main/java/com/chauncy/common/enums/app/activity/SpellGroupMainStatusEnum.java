package com.chauncy.common.enums.app.activity;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/6 23:36
 */
@Getter
public enum SpellGroupMainStatusEnum implements BaseEnum {


    NEED_PAY(1,"发起未支付"),
    SPELL_GROUP(2,"拼团中"),
    SPELL_GROUP_SUCCESS(2,"拼团成功"),
    SPELL_GROUP_FAIL(2,"拼团失败"),
    ;

    private Integer id;

    private String name;

    SpellGroupMainStatusEnum(Integer id, String name) {
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
    public static SpellGroupMainStatusEnum getSpellGroupMainTypeEnumById(Integer id) {
        for (SpellGroupMainStatusEnum type : SpellGroupMainStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static SpellGroupMainStatusEnum fromName(String name) {
        for (SpellGroupMainStatusEnum type : SpellGroupMainStatusEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static SpellGroupMainStatusEnum fromEnumName(String name) {
        for (SpellGroupMainStatusEnum validCodeEnum : SpellGroupMainStatusEnum.values()) {
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
