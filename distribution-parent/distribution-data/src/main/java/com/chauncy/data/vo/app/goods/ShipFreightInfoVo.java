package com.chauncy.data.vo.app.goods;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-31 16:14
 *
 * 运费详细信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "运费详细信息")
public class ShipFreightInfoVo {

    @ApiModelProperty("运费模版ID")
    @JSONField(ordinal = 0)
    private Long templateId;

    @ApiModelProperty("计算方式 1->按金额，numberShippingVo为空 2->按件数，moneyShippingVo为空")
    @JSONField(ordinal = 1)
    private Integer calculateWay;

    @ApiModelProperty(value = "默认(基础)运费")
    @JSONField(ordinal = 2)
    private BigDecimal defaultFreight;

    @ApiModelProperty(value = "默认满金额(满足条件金额)")
    @JSONField(ordinal = 3)
    private BigDecimal defaultFullMoney;

    @ApiModelProperty(value = "默认满足金额条件后的运费默认满足金额条件后的运费默认满足金额条件后的运费")
    @JSONField(ordinal = 4)
    private BigDecimal defaultPostMoney;

    @ApiModelProperty(value = "默认运费的最大件数")
    @JSONField(ordinal = 5)
    private Integer defaultMaxNumber;

    @ApiModelProperty(value = "默认最大件数内的运费")
    @JSONField(ordinal = 6)
    private BigDecimal defaultMaxNumberMoney;

    @ApiModelProperty(value = "默认超过最大件数每增加件数")
    @JSONField(ordinal = 7)
    private Integer defaultAddtionalNumber;

    @ApiModelProperty(value = "默认每增加件数就增加的运费")
    @JSONField(ordinal = 8)
    private BigDecimal defaultAddtionalFreight;

    @ApiModelProperty("按金额计算运费Vo")
    @JSONField(ordinal = 10)
    private List<MoneyShippingVo> moneyShippingVos;

    @ApiModelProperty("按金额计算运费Vo")
    @JSONField(ordinal = 11)
    private List<NumberShippingVo> numberShippingVos;
}
