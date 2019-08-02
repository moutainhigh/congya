package com.chauncy.data.vo.app.brand;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/15  21:29
 * @Version 1.0
 */
@Data
@ApiModel(description = "条件分页查询品牌商品信息列表")
@Accessors(chain = true)
public class BrandGoodsListVo {

    @ApiModelProperty("品牌id")
    private Long brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("品牌副标题")
    private String brandSubTitle;

    @ApiModelProperty("粉丝、收藏")
    private Integer collectionNum;

    @ApiModelProperty("商品列表")
    private PageInfo<GoodsVo> goodsVos;

}
