package com.chauncy.common.enums.store;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * 店铺关联关系类型枚举
 * @author yeJH
 * @since 2019/7/7 23:16
 */
public enum StoreRelationEnum  implements BaseEnum {

    /**
     *
     */
    PRODUCT_AGENT(1,"产品代理"),
    TEAM_WORK (2,"团队合作"),

    ;
    private Integer id;
    private String name;
    StoreRelationEnum(Integer id, String name){
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
    public static StoreRelationEnum getById(Integer id) {
        for (StoreRelationEnum storeRelationEnum : StoreRelationEnum.values()) {
            if (storeRelationEnum.getId() == id)
                return storeRelationEnum;
        }
        return null;
    }
    //通过名称来获取结果
    public static StoreRelationEnum fromName(String name) {
        for (StoreRelationEnum storeRelationEnum : StoreRelationEnum.values()) {
            if (storeRelationEnum.getName() == name)
                return storeRelationEnum;
        }
        throw new IllegalArgumentException(name);
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
    public  boolean isExist(Object field) {
        return Objects.nonNull(getById(Integer.parseInt(field.toString())));
    }
}
