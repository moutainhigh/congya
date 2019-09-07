package com.chauncy.data.vo.app.advice.goods;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-03 22:26
 *
 * 分页查询商品列表信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "分页查询商品列表信息")
public class SearchGoodsBaseListVo {

    @ApiModelProperty(value = "品牌/选项卡/商品分类名称")
    @JSONField(ordinal = 0)
    private String conditionName;

    @ApiModelProperty(value = "商品Id")
    @JSONField(ordinal = 1)
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    @JSONField(ordinal = 2)
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    @JSONField(ordinal = 3)
    private String picture;

    @ApiModelProperty(value = "商品最高价格",hidden = true)
    @JSONField(serialize = false,ordinal = 4)
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "商品最低价格",hidden = true)
    @JSONField(serialize = false,ordinal = 5)
    private BigDecimal minPrice;

    @ApiModelProperty(value = "价格")
    @JSONField(ordinal = 6)
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "转发量")
    @JSONField(ordinal = 7)
    private Integer forwardNum;

    @ApiModelProperty(value = "商品活动百分比",hidden = true)
    @JSONField(serialize = false,ordinal = 8)
    private BigDecimal activityCostRate;

    @ApiModelProperty(value = "让利成本比例",hidden = true)
    @JSONField(serialize = false,ordinal = 9)
    private BigDecimal profitsRate;

    @ApiModelProperty(value = "最大返券值")
    @JSONField(ordinal = 10)
    private BigDecimal maxRewardShopTicket;

    @ApiModelProperty(value = "销量")
    @JSONField(ordinal = 11)
    private Integer salesVolume;

    @ApiModelProperty("商品标签")
    @JSONField(ordinal = 12)
    private List<String> labelNames;

    @ApiModelProperty("划线价")
    @JSONField(ordinal = 13)
    private BigDecimal linePrice;

}
