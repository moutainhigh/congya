package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-06-17 17:25
 *
 * 平台运费模版枚举类
 *
 * 运费模版类型 1--平台运费模版。2--商家运费模版
 */
public enum GoodsShipTemplateEnum implements BaseEnum {

    PLATFORM_SHIP(1,"平台运费模版"),
    MERCHANT_SHIP(2,"商家运费模版");

    private Integer id;
    private String name;
    GoodsShipTemplateEnum(Integer id, String name){
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
    public static GoodsShipTemplateEnum getGoodsShipTemplateById(Integer id) {
        for (GoodsShipTemplateEnum type : GoodsShipTemplateEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static GoodsShipTemplateEnum fromName(String name) {
        for (GoodsShipTemplateEnum type : GoodsShipTemplateEnum.values()) {
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
        return Objects.nonNull(getGoodsShipTemplateById(Integer.parseInt(field.toString())));
    }

}
