package com.chauncy.common.enums.user;

import com.chauncy.common.enums.BaseEnum;

/**
 * @Author zhangrt
 * @Date 2019/7/1 16:47
 **/
public enum ValidCodeEnum implements BaseEnum {


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

    ValidCodeEnum(Integer id, String name) {
        this.id=id;
        this.name=name;
    }


    @Override
    public boolean isExist(Object field) {
        return false;
    }
}
