package com.chauncy.common.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/26 11:19
 */
public enum UserTypeEnum implements BaseEnum {


    /**
     * 用户类型
     * 1.APP用户
     * 2.平台
     * 3.商家
     */
    APP_USER (1, "APP用户"),
    PLATFORM(2, "平台"),
    STORE(3, "商家"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    UserTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name() + ":"  + this.name;
    }

    //通过名称来获取结果
    public static UserTypeEnum getById(Integer id) {
        for (UserTypeEnum type : UserTypeEnum.values()) {
            if (type.getId().equals(id))
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

        return Objects.nonNull(getById(Integer.parseInt(field.toString())));
    }

}
