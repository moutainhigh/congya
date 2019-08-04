package com.chauncy.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import com.chauncy.common.enums.app.activity.ActivityStatusEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-08-02 01:11
 *
 * 快递单当前签收状态: 0在途，1揽收，2疑难，3签收，4退签，5派件，6退回
 */
@Getter
public enum LogisticsStatusEnum implements BaseEnum {
    IN_TRANSIT("0","在途"),
    LANSHOU("1","揽收"),
    DIFFICULT("2","疑难"),
    RECEIVING("3","签收"),
    BACK("4","退签"),
    SEND("5","派件"),
    BACKTO("6","退回");

    @EnumValue
    private String id;

    private String name;

    LogisticsStatusEnum(String id, String name) {
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
    public static LogisticsStatusEnum getLogisticsStatusEnumById(String id) {
        for (LogisticsStatusEnum type : LogisticsStatusEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static LogisticsStatusEnum fromName(String name) {
        for (LogisticsStatusEnum type : LogisticsStatusEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static LogisticsStatusEnum fromEnumName(String name) {
        for (LogisticsStatusEnum validCodeEnum : LogisticsStatusEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getLogisticsStatusEnumById(field.toString()));
    }

}

