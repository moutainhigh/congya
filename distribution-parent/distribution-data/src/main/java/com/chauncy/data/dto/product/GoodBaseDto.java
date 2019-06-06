package com.chauncy.data.dto.product;

import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author huangwancheng
 * @create 2019-06-04 11:33
 */
@Data
@ApiModel(value = "GoodBaseDtoo对象", description = "商品基本信息")
public class GoodBaseDto {

    @ApiModelProperty(value = "商品类型")
    @NotBlank(message = "商品类型图不能为空")
    private String goodsType;

    @ApiModelProperty(value = "分类ID")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long goodsCategoryId;

    @ApiModelProperty(value = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "spu编码")
    private String spu;

    @ApiModelProperty(value = "是否是店铺推荐；0->不推荐；1->推荐")
    private Boolean recommandStatus;

    @ApiModelProperty(value = "是否是明星单品；0->否；1->是")
    private Boolean starStatus;

    @ApiModelProperty(value = "商品缩略图")
    @NotBlank(message = "商品缩略图不能为空")
    private String icon;

    @ApiModelProperty(value = "轮播图")
    @NotBlank(message = "轮播图不能为空")
    private String carouselImage;

    @ApiModelProperty(value = "产品详情网页内容")
    private String detailHtml;

    @ApiModelProperty(value = "属性IDs集合")
    private Long[] attributeIds;

    @ApiModelProperty(value = "运费说明id")
    private Long shippingId;
}
