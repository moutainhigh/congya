package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 *  店铺分配虚拟库存给分店
 *  1.店铺自己的商品为 自有商品
 *  2.店铺被上级分配的商品为  分配商品
 *
 * @author yeJH
 * @since 2019/7/8 21:35
 */
public enum StoreGoodsTypeEnum implements BaseEnum {


    OWN_GOODS(1,"自有商品"),
    DISTRIBUTION_GOODS(2,"分配商品"),
    ;


    private Integer id;
    private String name;
    StoreGoodsTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name() + ":" + this.id + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static StoreGoodsTypeEnum getById(Integer id) {
        for (StoreGoodsTypeEnum type : StoreGoodsTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static StoreGoodsTypeEnum getByName(String name) {
        for (StoreGoodsTypeEnum type : StoreGoodsTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过枚举名称来获取结果
    public static StoreGoodsTypeEnum fromName(String name) {
        for (StoreGoodsTypeEnum type : StoreGoodsTypeEnum.values()) {
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

        return Objects.nonNull(getById(Integer.parseInt(field.toString())));
    }
}
