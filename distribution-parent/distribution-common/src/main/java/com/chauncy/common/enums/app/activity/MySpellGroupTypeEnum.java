package com.chauncy.common.enums.app.activity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/6 23:03
 */
@Getter
public enum  MySpellGroupTypeEnum implements BaseEnum {


    MY_LAUNCH(1,"我发起的"),
    MY_PARTAKE(2,"我参与的"),
    ;
        
    private Integer id;

    private String name;

    MySpellGroupTypeEnum(Integer id, String name) {
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
    public static MySpellGroupTypeEnum getMySpellGroupTypeEnumById(Integer id) {
        for (MySpellGroupTypeEnum type : MySpellGroupTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static MySpellGroupTypeEnum fromName(String name) {
        for (MySpellGroupTypeEnum type : MySpellGroupTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static MySpellGroupTypeEnum fromEnumName(String name) {
        for (MySpellGroupTypeEnum validCodeEnum : MySpellGroupTypeEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getMySpellGroupTypeEnumById(Integer.parseInt(field.toString())));
    }


}
