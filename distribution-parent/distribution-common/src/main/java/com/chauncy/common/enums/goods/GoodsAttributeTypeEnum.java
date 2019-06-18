package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author huangwancheng
 * @create 2019-05-30 09:39
 * 类型 1->平台服务说明 2->商家服务说明 3->平台活动说明
 * 4->商品参数 5->商品标签 6->购买须知说明 7->商品规格 8->品牌管理 9->敏感词
 *
 */
public enum GoodsAttributeTypeEnum implements BaseEnum {
    PLATFORM_SERVICE(1,"平台服务说明"),
    MERCHANT_SERVICE(2,"商家服务说明"),
    PLATFORM_ACTIVITY(3,"平台活动说明"),
    GOODS_PARAM(4,"商品参数"),
    LABEL(5,"商品标签"),
    PURCHASE(6,"购买须知说明"),
    STANDARD(7,"商品规格"),
    BRAND(8,"商品品牌"),
    SENSITIVE(9,"敏感词");

    private Integer id;
    private String name;
    GoodsAttributeTypeEnum(Integer id, String name){
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
    public static GoodsAttributeTypeEnum getGoodsAttributeById(Integer id) {
        for (GoodsAttributeTypeEnum attribute : GoodsAttributeTypeEnum.values()) {
            if (attribute.getId() == id)
                return attribute;
        }
        return null;
    }
    //通过名称来获取结果
    public static GoodsAttributeTypeEnum fromName(String name) {
        for (GoodsAttributeTypeEnum attribute : GoodsAttributeTypeEnum.values()) {
            if (attribute.getName() == name)
                return attribute;
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
        return Objects.nonNull(getGoodsAttributeById(Integer.parseInt(field.toString())));
    }}

