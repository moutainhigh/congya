package com.chauncy.data.vo.app.activity.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/10/9 20:49
 **/
@Data
@ApiModel(description = "选择优惠券列表")
public class SelectCouponVo {

    @ApiModelProperty(value = "skuId",hidden = true)
    @JSONField(serialize = false)
    private Long skuId;

    @ApiModelProperty(value = "销售价",hidden = true)
    @JSONField(serialize = false)
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "数量",hidden = true)
    @JSONField(serialize = false)
    private Integer number;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "生效时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "截止时间")
    private LocalDateTime deadLine;

    @ApiModelProperty(value = "优惠券类型 1：满减优惠；2：固定折扣；3：包邮")
    private Integer type;

    @ApiModelProperty(value = "折扣比例,type=2时这个字段不为空")
    private BigDecimal discount;

    @ApiModelProperty(value = "折扣满金额条件，type=2时这个字段不为空")
    private BigDecimal discountFullMoney;

    @ApiModelProperty(value = "满减满金额条件，type=1时这个字段不为空")
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减金额，type=1时这个字段不为空")
    private BigDecimal reductionPostMoney;


}
