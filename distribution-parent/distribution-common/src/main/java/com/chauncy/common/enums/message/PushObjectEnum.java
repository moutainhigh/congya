package com.chauncy.common.enums.message;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-08 17:42
 *
 * 推送对象
 * 1--> 全部用户
 * 2--> 指定用户
 * 3--> 指定会员等级
 */
public enum PushObjectEnum implements BaseEnum {

    ALLUSER(1,"全部用户"),
    SPECIFYUSER(2,"指定用户"),
    SPECIFYMEMBERLEVEL(3,"指定会员等级");

    private Integer id;
    private String name;
    PushObjectEnum(Integer id, String name){
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
    public static PushObjectEnum getPushObjectEnumById(Integer id) {
        for (PushObjectEnum type : PushObjectEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static PushObjectEnum fromName(String name) {
        for (PushObjectEnum type : PushObjectEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过Name获取结果
    public static PushObjectEnum getByName(String name) {
        for (PushObjectEnum type : PushObjectEnum.values()) {
            if (type.name().equals(name))
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
