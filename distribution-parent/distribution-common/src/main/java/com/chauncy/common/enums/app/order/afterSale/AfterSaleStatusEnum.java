package com.chauncy.common.enums.app.order.afterSale;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author zhangrt
 * @Date 2019/8/21 11:47
 **/
@Getter
public enum AfterSaleStatusEnum {

    NEED_STORE_DO(1,"待商家处理"),
    NEED_BUYER_DO(2,"待买家处理"),
    NEED_BUYER_RETURN(3,"待买家退货"),
    NEED_STORE_REFUND(4,"待商家退款"),
    CLOSE(5,"退款关闭"),
    SUCCESS(6,"退款成功");

    @EnumValue
    private final Integer id;


    private final String name;

    AfterSaleStatusEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }


}