package com.chauncy.common.enums.app.advice;

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
 */
@Getter
@ApiModel(description = "广告位置枚举类")
public enum AdviceLocationEnum implements BaseEnum {

    @ApiModelProperty("优惠券顶部")
    COUPONS_TOP(1,"优惠券顶部"),

    @ApiModelProperty("推荐分类")
    RECOMMENDED_CATEGORY(2,"推荐分类"),

    @ApiModelProperty("满减专区")
    FULL_REDUCTION_ZONE(3,"满减专区"),

    @ApiModelProperty("满减专区-顶部")
    FULL_REDUCTION_ZONE_TOP(4,"满减专区-顶部"),

    @ApiModelProperty("满减专区-中部")
    FULL_REDUCTION_ZONE_MID(5,"满减专区-中部"),

    @ApiModelProperty("积分专区-顶部")
    INTEGRAL_ZONE_TOP(6,"积分专区-顶部"),

    @ApiModelProperty("积分专区-中部")
    INTEGRAL_ZONE_MID(7,"积分专区-中部"),

    @ApiModelProperty("积分专区-中部-内部")
    INTEGRAL_ZONE_MID_INNER(8,"积分专区-中部-内部"),

    @ApiModelProperty("秒杀")
    SECKILL(9,"秒杀"),

    @ApiModelProperty("秒杀-拼手速")
    SECKILL_HAND_SPEED(10,"秒杀-拼手速"),

    @ApiModelProperty("拼团-顶部")
    SPELL_GROUP_HEADER(11,"拼团-顶部"),

    @ApiModelProperty("拼团-中部")
    SPELL_GROUP_MID(12,"拼团-中部"),

    @ApiModelProperty("拼团专区-中部-内部")
    SPELL_GROUP_MID_INNER(13,"拼团专区-中部-内部"),

    @ApiModelProperty("分类")
    CATEGORY(14,"分类"),

    @ApiModelProperty("店铺专区")
    STORE_ZONE(15,"店铺专区"),

    @ApiModelProperty("品牌专区")
    BRAND_ZONE(16,"品牌专区"),

    @ApiModelProperty("品牌专区")
    RECOMMENDED_BRAND_ZONE(17,"品牌专区"),

    @ApiModelProperty("充值入口")
    TOP_UP_ENTRY(18,"充值入口");

    private Integer id;

    private String name;

    AdviceLocationEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //通过Id获取结果
    public static AdviceLocationEnum getAdviceLocationEnum(Integer id) {
        for (AdviceLocationEnum type : AdviceLocationEnum.values()) {
            if (type.getId() == id)
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
        return Objects.nonNull(fromName(field.toString()));
    }
}
