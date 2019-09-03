package com.chauncy.common.enums.order;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * 商家操作售后订单
 * @author zhangrt
 * @since 2019/7/23 13:00
 */
public enum OperateAfterOrderEnum implements BaseEnum {

    PERMIT_REFUND (1, "确认退款"),
    REFUSE_REFUND(2, "拒绝退款"),
    PERMIT_RETURN_GOODS (3, "确认退货"),
    REFUSE_RETURN_GOODS(4, "拒绝退货"),
    PERMIT_RECEIVE_GOODS(5, "确认收货"),
    ;

    private Integer id;
    private String name;
    OperateAfterOrderEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static OperateAfterOrderEnum getById(Integer id) {
        for (OperateAfterOrderEnum type : OperateAfterOrderEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isExist(Object field) {

        return Objects.nonNull(getById(Integer.parseInt(field.toString())));
    }

}
