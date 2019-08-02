package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/12  0:37
 * @Version 1.0
 * <p>
 * 税率类型
 */
public enum TaxRateTypeEnum implements BaseEnum {

    PLATFORM_TAX_RATE (1, "平台税率"),
    CUSTOM_TAX_RATE (2, "自定义税率"),
    NULL_TAX_RATE (3, "无税率");

    private Integer id;
    private String name;

    TaxRateTypeEnum (Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString () {
        return this.id + "_" + this.name;
    }

    public static String value (String name) {
        return name;
    }

    //通过Id获取结果
    public static TaxRateTypeEnum getRateById (Integer id) {
        for (TaxRateTypeEnum attribute : TaxRateTypeEnum.values ()) {
            if (attribute.getId () == id)
                return attribute;
        }
        return null;
    }

    //通过名称来获取结果
    public static TaxRateTypeEnum fromName (String name) {
        for (TaxRateTypeEnum attribute : TaxRateTypeEnum.values ()) {
            if (attribute.getName () == name)
                return attribute;
        }
        throw new IllegalArgumentException (name);
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public boolean isExist (Object field) {
        return Objects.nonNull (getRateById (Integer.parseInt (field.toString ())));
    }
}
