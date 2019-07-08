package com.chauncy.common.enums.message;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-08 17:42
 *
 * 推送方式
 * 1-->通知栏推送、
 * 2-->APP内消息中心推送
 */
public enum PushTypeEnum implements BaseEnum {

    NOTIFICATIONBAR(1,"通知栏推送"),
    APPMESSAGE(2,"APP内消息中心推送");

    private Integer id;
    private String name;
    PushTypeEnum(Integer id, String name){
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
    public static PushTypeEnum getPushTypeEnumById(Integer id) {
        for (PushTypeEnum type : PushTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static PushTypeEnum fromName(String name) {
        for (PushTypeEnum type : PushTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过Name获取结果
    public static PushTypeEnum getByName(String name) {
        for (PushTypeEnum type : PushTypeEnum.values()) {
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
        return Objects.nonNull(getPushTypeEnumById(Integer.parseInt(field.toString())));
    }}
