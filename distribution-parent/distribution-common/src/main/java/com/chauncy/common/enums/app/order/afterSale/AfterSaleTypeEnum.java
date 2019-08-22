package com.chauncy.common.enums.app.order.afterSale;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Author zhangrt
 * @Date 2019/8/21 11:47
 **/
@Getter
public enum  AfterSaleTypeEnum {

    ONLY_REFUND(1,"仅退款"),
    RETURN_GOODS(2,"退货退款");

    @EnumValue
    private final Integer id;

    private final String name;

    AfterSaleTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
