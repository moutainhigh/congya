package com.chauncy.common.enums.store;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.chauncy.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: xiaoye
 * @Date: 2019/6/12 16:17
 */
public enum StoreTypeEnum implements BaseEnum, IEnum {

    /**
     * 店铺类型
     * 1->推广店铺
     * 2->商品店铺
     */
    ORDINARY_STORE(1, "推广店铺"),
    POPULARIZE_STORE(2, "商品店铺"),
    ;

    @EnumValue
    private Integer type;
    private String typeName;
    StoreTypeEnum(Integer type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    @Override
    public String toString(){
        return this.type + "_" + this.typeName;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static StoreTypeEnum getStoreTypeById(Integer id) {
        for (StoreTypeEnum type : StoreTypeEnum.values()) {
            if (type.getType().equals(id)) {
                return type;
            }
        }
        return null;
    }
    //通过名称来获取结果
    public static StoreTypeEnum fromName(String name) {
        for (StoreTypeEnum type : StoreTypeEnum.values()) {
            if (type.getTypeName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException(name);
    }


    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getStoreTypeById(Integer.parseInt(field.toString())));
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @JsonValue
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public Serializable getValue() {
        return type;
    }
}
