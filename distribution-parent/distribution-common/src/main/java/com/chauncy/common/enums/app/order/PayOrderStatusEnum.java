package com.chauncy.common.enums.app.order;

import com.chauncy.common.enums.BaseEnum;
import com.chauncy.common.enums.user.ValidCodeEnum;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

/**
 * 支付单状态枚举类
 * @Author zhangrt
 * @Date 2019/7/22 22:50
 **/
@Getter
public enum  PayOrderStatusEnum implements BaseEnum {


    NEED_PAY(0,"未支付"),
    ALREADY_PAY(1,"已支付"),
    ALREADY_CANCEL(2,"已取消");

    private Integer id;

    private String name;

    PayOrderStatusEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    //通过name获取结果
    public static PayOrderStatusEnum getOrderStatusEnum(Integer id) {
        for (PayOrderStatusEnum payOrderStatusEnum : PayOrderStatusEnum.values()) {
            if (payOrderStatusEnum.getId().equals(id))
                return payOrderStatusEnum;
        }
        return null;
    }


    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getOrderStatusEnum(Integer.valueOf(field.toString())));
    }
}
