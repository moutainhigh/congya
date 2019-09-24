package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/9/11 18:53
 */
public enum LogDetailTitleEnum   implements BaseEnum {
    /**
     * 流水详情标题
     */
    FROM_ASSISTS(1, "助攻-来自"),
    FROM_EXPERIENCE(2, "来自-经验包"),
    CONGYA_OFFICIAL(3, "葱鸭官方"),
    RED_ENVELOPS_WITHDRAWAL(4, "红包提现"),
    WITHDRAWAL_FAIL(5, "提现失败"),
    MEMBER_AWARD(6, "会员奖励"),
    REFUND(7, "退款"),
    ORDER_PAYMENT(7, "消费抵扣"),
    weiqueding(8, "商家名"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    LogDetailTitleEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static LogDetailTitleEnum getById(Integer id) {
        for (LogDetailTitleEnum type : LogDetailTitleEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static LogDetailTitleEnum fromName(String name) {
        for (LogDetailTitleEnum type : LogDetailTitleEnum.values()) {
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
