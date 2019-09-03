package com.chauncy.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/8/25 18:12
 */
public enum BillSettlementStatusEnum implements BaseEnum {

    /**
     * 账单结算进度状态
     * 1.已完成
     * 2.未完成
     * 3.未开始
     */
    COMPLETED(1, "已完成"),
    INCOMPLETE(2, "未完成"),
    NOT_STARTED(3, "未开始"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    BillSettlementStatusEnum(Integer id, String name){
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
    public static BillSettlementStatusEnum getById(Integer id) {
        for (BillSettlementStatusEnum type : BillSettlementStatusEnum.values()) {
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
        if(null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }

}
