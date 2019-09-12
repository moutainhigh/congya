package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/9/11 18:57
 */
public enum LogDetailExplainEnum  implements BaseEnum {
    /**
     * 流水详情说明
     */
    popularize_INCOME(1, "推广收入"),
    EXPERIENCE_content(2, "经验包内容"),
    Shopping_REWARD(3, "购物奖励"),
    ORDER_PAYMENT(4, "消费抵扣"),
    PLATFORM_GIVE(5, "系统赠送"),
    Return_Wallet(6, "已退回我的钱包"),
    WITHDRAWAL_SUCCESS(7, "会员奖励"),
    STORE_REFUND(8, "卖家已退款"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    LogDetailExplainEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static LogDetailExplainEnum getById(Integer id) {
        for (LogDetailExplainEnum type : LogDetailExplainEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static LogDetailExplainEnum fromName(String name) {
        for (LogDetailExplainEnum type : LogDetailExplainEnum.values()) {
            if (type.name().equals(name))
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
