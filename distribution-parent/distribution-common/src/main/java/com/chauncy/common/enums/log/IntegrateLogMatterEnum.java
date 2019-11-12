package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/31 0:27
 */
public enum IntegrateLogMatterEnum  implements BaseEnum {

    /**
     * APP用户积分流水
     * 41.消费抵扣  用户支出
     * 42.购物奖励  用户支出
     * 43.经验包  用户收入
     * 44.系统赠送  用户收入
     * 45.新人礼包  用户收入
     * 46.登录  用户收入
     * 47.分享  用户收入
     */
    ORDER_PAYMENT(41, "消费抵扣"),
    SHOPPING_REWARD(42, "购物奖励"),
    EXPERIENCE_PACK(43, "经验包"),
    PLATFORM_GIVE(44, "系统赠送"),
    NEW_GIFT(45, "新人礼包"),
    /*LOGIN(46, "登录"),
    SHARE(47, "分享"),*/
    MEMBER_AWARD(48, "会员奖励"),
    FRIENDS_ASSIST(49, "好友助攻"),
    CANCEL_ORDER (50, "取消订单"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    IntegrateLogMatterEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static IntegrateLogMatterEnum getById(Integer id) {
        for (IntegrateLogMatterEnum type : IntegrateLogMatterEnum.values()) {
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
        if(null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }



}
