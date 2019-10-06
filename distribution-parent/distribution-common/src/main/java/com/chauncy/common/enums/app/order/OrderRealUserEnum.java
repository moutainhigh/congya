package com.chauncy.common.enums.app.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * 订单实名认证状态枚举类
 * @Author zhangrt
 * @Date 2019/7/22 22:50
 **/
@Getter
public enum OrderRealUserEnum implements BaseEnum {


    PASS(1,"认证成功"),
    FAIL(1,"认证失败");


    @EnumValue
    private final Integer id;

    private final String name;

    OrderRealUserEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    //通过name获取结果
    public static OrderRealUserEnum getOrderStatusEnum(Integer id) {
        for (OrderRealUserEnum orderRealUserEnum : OrderRealUserEnum.values()) {
            if (orderRealUserEnum.getId().equals(id))
                return orderRealUserEnum;
        }
        return null;
    }



    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getOrderStatusEnum(Integer.valueOf(field.toString())));
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
