package com.chauncy.common.enums.app.coupon;

import com.chauncy.common.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-09-09 22:16
 *
 * 用户拥有的优惠券类型 1--领取 2--购买
 *
 */
@Getter
@ApiModel(description = "用户拥有的优惠券类型")
public enum CouponBeLongTypeEnum implements BaseEnum {

    RECEIVE(1,"领取"),
    PURCHASE(2,"购买");

    private Integer id;

    private String name;

    CouponBeLongTypeEnum(Integer id, String name) {
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
    public static CouponBeLongTypeEnum getCouponScopeEnumById(Integer id) {
        for (CouponBeLongTypeEnum type : CouponBeLongTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static CouponBeLongTypeEnum fromName(String name) {
        for (CouponBeLongTypeEnum type : CouponBeLongTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static CouponBeLongTypeEnum fromEnumName(String name) {
        for (CouponBeLongTypeEnum couponScopeEnum : CouponBeLongTypeEnum.values()) {
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

