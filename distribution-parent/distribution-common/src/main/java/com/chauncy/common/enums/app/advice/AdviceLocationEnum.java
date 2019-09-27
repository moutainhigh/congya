package com.chauncy.common.enums.app.advice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-17 22:45
 *
 *  广告位置枚举类
 *
 *  "SALE": "首页特卖",
 *     "TOP_UP_ENTRY": "充值入口",
 *     "SPELL_GROUP": "拼团",
 *     "MIDDLE_TWO_SHUFFLING": "首页中部2轮播图",
 *     "LEFT_UP_CORNER_SHUFFLING": "首页左上角轮播图",
 *     "YOUXUAN_INSIDE_SHUFFLING": "优选内部轮播图",
 *     "MIDDLE_ONE_SHUFFLING": "首页中部1轮播图",
 *     "BAIHUO": "葱鸭百货",
 *     "BAIHUO_INSIDE_SHUFFLING": "葱鸭百货内部轮播图",
 *     "FIRST_CATEGORY_DETAIL": "一级分类详情轮播图",
 *     "SHOUYE_ZHUTI": "首页主题",
 *     "YOUPIN_DETAIL": "品牌详情轮播图",
 *     "SALE_INSIDE_SHUFFLING": "特卖内部轮播图",
 *     "PERSONAL_CENTER": "个人中心",
 *     "STORE_DETAIL": "首页有店+店铺分类详情",
 *     "YOUXUAN": "葱鸭优选",
 *     "YOUPIN_INSIDE_SHUFFLING": "首页有品内部轮播图",
 *     "BOTTOM_SHUFFLING": "首页底部轮播图",
 *     "YOUDIAN_INSIDE_SHUFFLING": "首页有店内部轮播图",
 *     "SHOUYE_YOUPIN": "首页有品",
 *     "information_recommended": "资讯分类推荐",
 *     "MIDDLE_THREE_SHUFFLING": "首页中部3轮播图"
 */
@Getter
public enum AdviceLocationEnum implements BaseEnum {

    //有选项卡的广告位置
    SHOUYE_YOUPIN(1,"首页有品+品牌轮播图广告"),

    STORE_DETAIL(2,"首页有店+店铺分类详情"),

    INTEGRALS_ACTIVITY(29,"积分活动广告"),

    REDUCED_ACTIVITY(30,"满减活动广告"),

    SHOUYE_ZHUTI(3,"首页主题"),

    SALE(4,"首页特卖"),

    YOUXUAN(5,"葱鸭优选"),

    //无关联轮播图位置

    BOTTOM_SHUFFLING(6,"首页底部轮播图"),

    LEFT_UP_CORNER_SHUFFLING(7,"首页左上角轮播图"),

    MIDDLE_ONE_SHUFFLING(8,"首页中部1轮播图"),

    MIDDLE_TWO_SHUFFLING(9,"首页中部2轮播图"),

    MIDDLE_THREE_SHUFFLING(10,"首页中部3轮播图"),

    YOUPIN_INSIDE_SHUFFLING(11,"首页有品内部轮播图"),

    YOUDIAN_INSIDE_SHUFFLING(12,"首页有店内部轮播图"),

    SALE_INSIDE_SHUFFLING(13,"特卖内部轮播图"),

    YOUXUAN_INSIDE_SHUFFLING(14,"优选内部轮播图"),

    BAIHUO_INSIDE_SHUFFLING(15,"葱鸭百货内部轮播图"),

    PERSONAL_CENTER(16,"个人中心"),

    INTEGRALS_INSIDE_HUFFLING(26,"积分内部轮播图"),

    REDUCED_INSIDE_SHUFFLING(27,"满减内部轮播图"),

//    SPELL_INSIDE_SHUFFLING(28,"拼团内部轮播图"),

    //有关联轮播图位置
//    YOUPIN_DETAIL(17, "品牌详情轮播图"),

    FIRST_CATEGORY_DETAIL(18,"一级分类详情轮播图"),

    //利用广告形式设置推荐
    BAIHUO(19,"葱鸭百货"),

    information_recommended(20,"资讯分类推荐"),

    //其它
    TOP_UP_ENTRY(21,"充值入口"),

    SPELL_GROUP_SHUFFLING(22,"拼团内部轮播图"),

    COUPON(23,"领券"),

    EXPERIENCE_PACKAGE(24,"经验包"),

    INVITATION(25,"邀请有礼");

    private Integer id;

    @EnumValue
    private String name;

    AdviceLocationEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    //通过Id获取结果
    public static AdviceLocationEnum getAdviceLocationEnum(Integer id) {
        for (AdviceLocationEnum type : AdviceLocationEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static AdviceLocationEnum fromName(String name) {
        for (AdviceLocationEnum type : AdviceLocationEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static AdviceLocationEnum fromEnumName(String name) {
        for (AdviceLocationEnum validCodeEnum : AdviceLocationEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(fromEnumName(field.toString()));
    }
}
