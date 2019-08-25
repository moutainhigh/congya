package com.chauncy.common.enums.app.order.afterSale;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Author zhangrt
 * @Date 2019/8/21 11:47
 **/
@Getter
public enum AfterSaleStatusEnum {

    NEED_VERIFY(1,"待审核"),
    PROCESSING(2,"处理中"),
    SUCCESS(3,"售后成功"),
    FAIL(4,"售后失败");

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