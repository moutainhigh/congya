package com.chauncy.data.vo.app.advice.activity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-10-10 21:29
 *
 * 商品详情之积分活动信息
 */
@Data
@ApiModel(description = "商品详情之积分活动信息")
@Accessors(chain = true)
public class IntegralsVo {

    @ApiModelProperty(value = "积分活动-促销金额",hidden = true)
    @JSONField(serialize = false)
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "积分活动-最低促销金额")
    private double lowestActivityPrice;

    @ApiModelProperty(value = "积分活动-最高促销金额")
    private double highestActivityPrice;

    @ApiModelProperty(value = "积分活动-最低积分抵扣")
    private double lowestIntegralsPrice;

    @ApiModelProperty(value = "积分活动-最高积分抵扣")
    private double highestIntegralsPrice;

    @ApiModelProperty(value = "积分抵扣说明")
    private String integralsDescription;

    @ApiModelProperty(value = "积分活动-促销金额范围(未选择sku时显示的数据)")
    private String discountDisplayPrice;

    @ApiModelProperty(value = "积分抵扣范围(未选择sku时显示的数据)")
    private String integralsDispalyPrice;

    @ApiModelProperty(value = "积分抵扣百分比范围(未选择sku时显示的数据)")
    private String integralsDispalyPercentage;
}
