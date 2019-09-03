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
 * @create 2019-09-03 16:03
 *
 * 根据选项卡ID获取品牌及其对应的商品基本信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "根据选项卡ID获取品牌及其对应的商品基本信息")
public class SearchBrandAndSkuBaseVo {

    @ApiModelProperty("品牌Id")
    @JSONField(ordinal = 0)
    private Long brandId;

    @ApiModelProperty("品牌名称")
    @JSONField(ordinal = 1)
    private String brandName;

    @ApiModelProperty("品牌logo")
    @JSONField(ordinal = 2)
    private String brandLogo;

    @ApiModelProperty("品牌副标题")
    @JSONField(ordinal = 3)
    private String subtitle;

    @ApiModelProperty("该品牌销量前6的商品基本信息")
    @JSONField(ordinal = 4)
    private List<BrandGoodsVo> brandGoodsVos;

}
