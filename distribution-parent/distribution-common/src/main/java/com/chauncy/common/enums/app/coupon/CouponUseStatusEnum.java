package com.chauncy.common.enums.app.coupon;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-18 12:36
 */
@Getter
public enum CouponUseStatusEnum implements BaseEnum {

    USED(1,"已使用"),
    NOT_USED(2,"未使用"),
    FAILURE(3,"已失效");

    private Integer id;

    private String name;

    CouponUseStatusEnum(Integer id, String name) {
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
    public static CouponUseStatusEnum getCouponUseStatusEnumById(Integer id) {
        for (CouponUseStatusEnum type : CouponUseStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static CouponUseStatusEnum fromName(String name) {
        for (CouponUseStatusEnum type : CouponUseStatusEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static CouponUseStatusEnum fromEnumName(String name) {
        for (CouponUseStatusEnum couponScopeEnum : CouponUseStatusEnum.values()) {
            if (couponScopeEnum.name().equals(name))
                return couponScopeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        if (field==null){
            return true;
        }else
        return Objects.nonNull(getCouponUseStatusEnumById(Integer.parseInt(field.toString())));
    }
}
