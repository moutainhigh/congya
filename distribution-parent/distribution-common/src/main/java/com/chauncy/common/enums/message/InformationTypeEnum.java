package com.chauncy.common.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/2 15:13
 */
public enum InformationTypeEnum implements BaseEnum {

    /**
     * 资讯标签
     * 1-关注  用户关注的店铺发布的资讯
     * 2-推荐
     */
    ALL_LIST(1, "全部资讯列表"),
    FOCUS_LIST(2,"关注资讯列表"),
    RECOMMEND_LIST(3,"推荐资讯列表"),
    CATEGORY_LIST(4,"分类资讯列表"),
    SEARCH_LIST(5,"搜索资讯列表"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    InformationTypeEnum(Integer id, String name){
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
    public static InformationTypeEnum getById(Integer id) {
        for (InformationTypeEnum type : InformationTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static InformationTypeEnum getByName(String name) {
        for (InformationTypeEnum type : InformationTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过枚举名称来获取结果
    public static InformationTypeEnum fromName(String name) {
        for (InformationTypeEnum type : InformationTypeEnum.values()) {
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

        return Objects.nonNull(getById((Integer)field));
    }
}
