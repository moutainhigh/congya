package com.chauncy.common.enums.app.coupon;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-18 12:36
 */
@Getter
public enum CouponFormEnum implements BaseEnum {

    WITH_PREFERENTIAL_REDUCTION(1,"满减优惠"),
    FIXED_DISCOUNT(2,"固定折扣"),
    PACKAGE_MAIL(3,"包邮");

    private Integer id;

    private String name;

    CouponFormEnum(Integer id, String name) {
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
    public static CouponFormEnum getCouponFormEnumById(Integer id) {
        for (CouponFormEnum type : CouponFormEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static CouponFormEnum fromName(String name) {
        for (CouponFormEnum type : CouponFormEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static CouponFormEnum fromEnumName(String name) {
        for (CouponFormEnum validCodeEnum : CouponFormEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getCouponFormEnumById(Integer.parseInt(field.toString())));
    }
}
