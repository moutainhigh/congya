package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author huangwancheng
 * @create 2019-06-04 16:26
 *
 * 商品类型：
 * 1->普通商品
 * 2->保税仓
 * 3->海外直邮
 * 4->自取
 * 5->服务类
 * 6->虚拟商品
 */
public enum GoodsTypeEnum implements BaseEnum {

    USUAL(1,"普通商品"),
    BONDED(2,"保税仓"),
    OVERSEA(3,"海外直邮"),
    PICK_UP_INSTORE(4,"自取"),
    SERVICES(5,"服务类"),
    VIRTUAL(6,"虚拟商品");

    private Integer id;
    private String name;
    GoodsTypeEnum(Integer id, String name){
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
    public static GoodsTypeEnum getGoodsTypeById(Integer id) {
        for (GoodsTypeEnum type : GoodsTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static GoodsTypeEnum fromName(String name) {
        for (GoodsTypeEnum type : GoodsTypeEnum.values()) {
            if (type.getName() == name)
                return type;
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
        return Objects.nonNull(getGoodsTypeById(Integer.parseInt(field.toString())));
    }}
