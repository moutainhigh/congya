package com.chauncy.common.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/9/14 15:05
 */
public enum NoticeTypeEnum  implements BaseEnum {
    /**
     * 消息通知类型
     */
    EXPRESS_LOGISTICS(1, "交易物流"),
    SYSTEM_NOTICE(2, "系统通知"),
    TASK_REWARD(3, "任务奖励"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    NoticeTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static NoticeTypeEnum getById(Integer id) {
        for (NoticeTypeEnum type : NoticeTypeEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static NoticeTypeEnum fromName(String name) {
        for (NoticeTypeEnum type : NoticeTypeEnum.values()) {
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
        if(null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }


}
