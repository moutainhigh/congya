package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;
import lombok.Data;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-06-21 10:17
 *
 * 商品状态 1-未审核 2-审核通过 3-驳回
 */
public enum GoodsVerifyStatusEnum implements BaseEnum {

    USUAL(1,"未审核"),
    BONDED(2,"审核通过"),
    OVERSEA(3,"不通过/驳回");

    private Integer id;
    private String name;
    GoodsVerifyStatusEnum(Integer id, String name){
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
    public static GoodsVerifyStatusEnum getVerifyStatusById(Integer id) {
        for (GoodsVerifyStatusEnum type : GoodsVerifyStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static GoodsVerifyStatusEnum fromName(String name) {
        for (GoodsVerifyStatusEnum type : GoodsVerifyStatusEnum.values()) {
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
        return Objects.nonNull(getVerifyStatusById(Integer.parseInt(field.toString())));
    }}

