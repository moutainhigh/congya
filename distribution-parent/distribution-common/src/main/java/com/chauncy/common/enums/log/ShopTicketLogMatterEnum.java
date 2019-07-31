package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/31 0:26
 */
public enum ShopTicketLogMatterEnum implements BaseEnum {
    
    /**
     * APP用户购物券流水
     * 31.消费抵扣  用户支出
     * 32.购物奖励  用户支出
     * 33.经验包  用户收入
     * 34.系统赠送  用户收入
     * 35.新人礼包  用户收入
     */
    ORDER_PAYMENT(31, "消费抵扣"),
    SHOPPING_REWARD(32, "购物奖励"),
    EXPERIENCE_PACK(33, "经验包"),
    PLATFORM_GIVE(34, "系统赠送"),
    NEW_GIFT(35, "新人礼包"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    ShopTicketLogMatterEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static ShopTicketLogMatterEnum getById(Integer id) {
        for (ShopTicketLogMatterEnum type : ShopTicketLogMatterEnum.values()) {
            if (type.getId().equals(id))
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
