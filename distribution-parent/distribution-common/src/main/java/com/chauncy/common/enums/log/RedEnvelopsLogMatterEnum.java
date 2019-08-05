package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/31 0:24
 */
public enum RedEnvelopsLogMatterEnum implements BaseEnum {


    /**
     * APP用户红包流水
     * 21.消费抵扣  用户支出
     * 22.提现  用户支出
     * 23.系统赠送  用户收入
     * 24.好友助攻  用户收入
     */
    ORDER_PAYMENT(21, "消费抵扣"),
    WITHDRAWAL(22, "提现"),
    FRIENDS_ASSIST(23, "好友助攻"),
    PLATFORM_GIVE(24, "系统赠送"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    RedEnvelopsLogMatterEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static RedEnvelopsLogMatterEnum getById(Integer id) {
        for (RedEnvelopsLogMatterEnum type : RedEnvelopsLogMatterEnum.values()) {
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