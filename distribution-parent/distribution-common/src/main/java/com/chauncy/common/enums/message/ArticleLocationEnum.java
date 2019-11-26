package com.chauncy.common.enums.message;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-06-26 11:27
 *
 * 文章位置枚举
 *
 */
public enum ArticleLocationEnum implements BaseEnum {

    INVITE_FRIENDS(1,"邀请好友"),
    INVITE_FRIENDS_CONTENT(2,"邀请好友内容"),
    REGISTRATION_AGREEMENT(3,"注册协议"),
    BUSINESS_QUALIFICATION(4,"经营资质"),
    SPELL_GROUP_SHOW(5,"拼团说明"),
    IDENTITY_CARD_VERIFICATION(6,"身份证验证"),
    RED_ENVELOPES(7,"红包说明"),
    PURCHASE_EXPERIENCE_PACK(8,"购买经验包说明"),
    RULE_DESCRIPTION_OF_EXPERIENCE(9,"经验包规则说明"),
    APPLICATION_FOR(10,"合作申请"),
    ABOUT_US(11,"关于我们"),
    PLACE_THE_ORDER_AND_POLITE(12,"下单有礼"),
    HELP_CENTER(13,"帮助中心"),
    INTEGRALS_DESCRIPTION(14,"抵扣说明"),
    RETURN_TICKET_RULES(15,"返券规则");

    private Integer id;
    private String name;
    ArticleLocationEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.id + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static ArticleLocationEnum getArticalLocationById(Integer id) {
        for (ArticleLocationEnum location : ArticleLocationEnum.values()) {
            if (location.getId() == id)
                return location;
        }
        return null;
    }
    //通过名称来获取结果
    public static ArticleLocationEnum fromName(String name) {
        for (ArticleLocationEnum location : ArticleLocationEnum.values()) {
            if (location.getName().equals(name))
                return location;
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
        return Objects.nonNull(fromName(field.toString()));
    }}


