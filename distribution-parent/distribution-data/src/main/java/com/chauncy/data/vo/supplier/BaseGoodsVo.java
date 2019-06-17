package com.chauncy.data.vo.supplier;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-17 14:31
 *
 * 通过ID查找商品基本信息Vo
 */
@Data
@ApiModel(description = "基本商品信息Vo")
@Accessors(chain = true)
public class BaseGoodsVo {

    @ApiModelProperty("第三级分类ID")
    private Long goodsCategoryId;

    @ApiModelProperty("类目名称")
    private String[] categoryName;

    @ApiModelProperty("商品类型")
    private String goodsType;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("副标题")
    private String subtitle;

    @ApiModelProperty("spu码")
    private String spu;

    @ApiModelProperty(value = "是否是店铺推荐；0->不推荐；1->推荐")
    private Boolean recommandStatus;

    @ApiModelProperty(value = "是否是明星单品；0->否；1->是")
    private Boolean starStatus;

    @ApiModelProperty(value = "商品缩略图")
    private String icon;

    @ApiModelProperty(value = "轮播图")
    private String carouselImage;

    @ApiModelProperty(value = "产品详情网页内容")
    private String detailHtml;

    @ApiModelProperty(value = "商品品牌")
    private BaseVo brandVo;

    @ApiModelProperty(value = "商品标签")
    private BaseVo labelVo;

    @ApiModelProperty(value = "平台服务说明")
    private BaseVo platformServiceVo;

    @ApiModelProperty(value = "商家服务说明")
    private BaseVo merchantServiceVo;

    @ApiModelProperty(value = "平台运费说明")
    private BaseVo platformShipVo;

    @ApiModelProperty(value = "商家运费说明")
    private BaseVo merchantShipVo;

    @ApiModelProperty(value = "商品参数信息集合")
    private List<PmGoodsParamVo> goodsParams;

}
