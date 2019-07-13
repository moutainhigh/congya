package com.chauncy.common.enums.message;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-06-26 18:07
 *
 * 类别 1--商品 2--店铺 3--资讯
 */
public enum KeyWordTypeEnum implements BaseEnum {

    GOODS(1,"商品"),
    MERCHANT(2,"店铺"),
    INFORMATION(3,"资讯");

    private Integer id;
    private String name;
    KeyWordTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.id + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static KeyWordTypeEnum getKeyWordTypeById(Integer id) {
        for (KeyWordTypeEnum type : KeyWordTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static KeyWordTypeEnum fromName(String name) {
        for (KeyWordTypeEnum type : KeyWordTypeEnum.values()) {
            if (type.getName().equals(name))
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
        return Objects.nonNull(fromName(field.toString()));
    }}


