package com.chauncy.common.enums.app.coupon;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-18 12:36
 */
@Getter
public enum CouponScopeEnum implements BaseEnum {

    ALL_GOODS(1,"所有商品"),
    SPECIFIED_CATEGORY(2,"指定分类"),
    SPECIFIED_GOODS(3,"指定商品");

    private Integer id;

    private String name;

    CouponScopeEnum(Integer id, String name) {
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
    public static CouponScopeEnum getCouponScopeEnumById(Integer id) {
        for (CouponScopeEnum type : CouponScopeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static CouponScopeEnum fromName(String name) {
        for (CouponScopeEnum type : CouponScopeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static CouponScopeEnum fromEnumName(String name) {
        for (CouponScopeEnum couponScopeEnum : CouponScopeEnum.values()) {
            if (couponScopeEnum.name().equals(name))
                return couponScopeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getCouponScopeEnumById(Integer.parseInt(field.toString())));
    }
}
