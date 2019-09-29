package com.chauncy.data.vo.app.goods;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJ
 * @since 2019/9/24 17:46
 */
@Data
@ApiModel(description = "积分/满减活动商品信息")
public class ActivityGoodsVo extends GoodsBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品副标题")
    private String subtitle;

    @ApiModelProperty(value = "积分抵扣金额")
    private BigDecimal deductibleAmount ;

    @ApiModelProperty(value = "满减活动满额条件")
    private BigDecimal reductionFullMoney ;

    @ApiModelProperty(value = "满减活动优惠金额")
    private BigDecimal reductionPostMoney ;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "商品标签")
    private String labels;

    @ApiModelProperty(value = "商品标签")
    private List<String> labelList;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "积分活动抵扣比例")
    private BigDecimal discountPriceRatio ;


}
