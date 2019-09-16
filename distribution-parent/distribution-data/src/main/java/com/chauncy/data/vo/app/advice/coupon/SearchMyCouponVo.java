package com.chauncy.data.vo.app.advice.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-09-16 11:01
 *
 * APP查找我的优惠券
 */
@Data
@ApiModel(description = "APP查找我的优惠券")
@Accessors(chain = true)
public class SearchMyCouponVo {

    @ApiModelProperty("优惠券ID")
    @JSONField(ordinal = 0)
    private Long couponId;

    @ApiModelProperty("优惠券领取记录ID(列表id)")
    @JSONField(ordinal = 1)
    private Long receiveId;

    @ApiModelProperty("优惠券名称")
    @JSONField(ordinal = 2)
    private String name;

    @ApiModelProperty("满减满金额")
    @JSONField(ordinal = 3)
    private BigDecimal reductionFullMoney;

    @ApiModelProperty("满减减金额")
    @JSONField(ordinal = 4)
    private BigDecimal reductionPostMoney;

    @ApiModelProperty("打折金额条件")
    @JSONField(ordinal = 5)
    private BigDecimal discountFullMoney;

    @ApiModelProperty("折扣")
    @JSONField(ordinal = 6)
    private BigDecimal discount;

    @ApiModelProperty("使用期限--领取日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(ordinal = 7)
    private LocalDate receiveTime;

    @ApiModelProperty("使用期限--截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 8)
    private LocalDate deadLine;

    @ApiModelProperty("优惠类型 1-满减 2-固定折扣 3-包邮")
    @JSONField(ordinal = 9)
    private Integer type;

    @ApiModelProperty("优惠券使用状态:1-已使用 2-未使用  3-失效")
    @JSONField(ordinal = 10)
    private Integer useStatus;

}
