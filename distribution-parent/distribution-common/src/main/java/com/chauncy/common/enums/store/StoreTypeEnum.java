package com.chauncy.common.enums.store;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author: xiaoye
 * @Date: 2019/6/12 16:17
 */
public enum StoreTypeEnum implements BaseEnum {

    /**
     * 店铺类型
     * 1->普通店铺
     * 2->推广店铺
     */
    ORDINARY_STORE(1, "普通店铺"),
    POPULARIZE_STORE(2, "推广店铺"),
    ;

    private Integer id;
    private String name;
    StoreTypeEnum(Integer id, String name){
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
    public static StoreTypeEnum getStoreTypeById(Integer id) {
        for (StoreTypeEnum type : StoreTypeEnum.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
    //通过名称来获取结果
    public static StoreTypeEnum fromName(String name) {
        for (StoreTypeEnum type : StoreTypeEnum.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
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
    public boolean isExist(Object field) {
        return Objects.nonNull(getStoreTypeById(Integer.parseInt(field.toString())));
    }
}
