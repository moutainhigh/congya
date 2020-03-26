package com.chauncy.data.vo.app.advice.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-09-27 17:44
 *
 * 商品详情页优惠券列表
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "领取优惠券")
public class FindCouponListVo {

    @ApiModelProperty(value = "优惠券Id")
    @JSONField(ordinal = 0)
    private Long couponId;

    @ApiModelProperty(value = "优惠券名称")
    @JSONField(ordinal = 1)
    private String name;

    @ApiModelProperty(value = "是否已领取")
    @JSONField(ordinal = 2)
    private Boolean isReceive = false;

    @ApiModelProperty(value = "是否已抢光",hidden = true)
    @JSONField(ordinal = 3,serialize = false)
    private Boolean isSnatchedOut = false;

    @ApiModelProperty(value = "发放总数",hidden = true)
    @JSONField(ordinal = 4,serialize = false)
    private Integer totalNum;

    @ApiModelProperty(value = "优惠券库存",hidden = true)
    @JSONField(ordinal = 5,serialize = false)
    private Integer stock;

    @ApiModelProperty(value = "优惠形式 1-满减 2-固定折扣 3-包邮")
    @JSONField(ordinal = 6)
    private Integer type;

    @ApiModelProperty(value = "折扣比例")
    @JSONField(ordinal = 7)
    private BigDecimal discount;

    @ApiModelProperty(value = "折扣满金额")
    @JSONField(ordinal = 8)
    private BigDecimal discountFullMoney;

    @ApiModelProperty(value = "满减满金额条件")
    @JSONField(ordinal = 9)
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减金额")
    @JSONField(ordinal = 10)
    private BigDecimal reductionPostMoney;

    @ApiModelProperty("使用期限--领取日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(ordinal = 11)
    private LocalDate receiveTime;

    @ApiModelProperty("使用期限--截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 12)
    private LocalDate deadLine;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "每人限领数量")
    private Integer everyLimitNum;

}
