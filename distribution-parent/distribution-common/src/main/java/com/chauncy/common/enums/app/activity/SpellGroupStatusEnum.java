package com.chauncy.common.enums.app.activity;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/8 12:33
 */
@Getter
public enum SpellGroupStatusEnum implements BaseEnum {


    SNAPPED_UP(1,"已抢购"),
    IN_PROGRESS(2,"抢购中"),
    ABOUT_TO_START(2,"即将开始"),
    ANNOUNCE_IN_ADVANCE(2,"预告"),
    ;

    private Integer id;

    private String name;

    SpellGroupStatusEnum(Integer id, String name) {
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
    public static SpellGroupStatusEnum getSpellGroupMainTypeEnumById(Integer id) {
        for (SpellGroupStatusEnum type : SpellGroupStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static SpellGroupStatusEnum fromName(String name) {
        for (SpellGroupStatusEnum type : SpellGroupStatusEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static SpellGroupStatusEnum fromEnumName(String name) {
        for (SpellGroupStatusEnum validCodeEnum : SpellGroupStatusEnum.values()) {
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
