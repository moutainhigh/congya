package com.chauncy.data.vo.app.goods;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-07-10 11:11
 *
 * 用户点击商品查看商品具体信息
 *
 * skuDetail:
 * {
 *     ";规格名称1ID:规格值1ID;规格名称2ID:规格值2ID;规格名称3ID:规格值3ID;":{
 *         "holdQuantity":限购数量,
 *         "overSold":是否售空,
 *         "sellAbleQuantity"：可售数量,
 *         "stock":库存
 *     }
 * }
 *
 * goodsStandardVoList:{[
 *     "attributeId":规格名称id,
 *     "attributeName":规格名称,
 *     "attributeValueInfos":[{
 *         "attributeValueId":attributeValueId,
 *         "attributeValue":attributeValue
 *     },
 *     .
 *     .
 *     .
 *     ]
 * },
 * .
 * .
 * .
 * .
 * ]
 */
@Data
@ApiModel(description = "商品具体信息Vo")
@Accessors(chain = true)
public class SpecifiedGoodsVo {

    @ApiModelProperty("商品名称")
    @JSONField(ordinal = 0)
    private String goodsName;

    @ApiModelProperty("商品轮播图")
    @JSONField(ordinal = 1)
    private List<String> carouselImages;

    @ApiModelProperty("发货地")
    @JSONField(ordinal = 2)
    private String originPlace;

    @ApiModelProperty("商品标题")
    @JSONField(ordinal = 3)
    private String subtitle;

    @ApiModelProperty("是否包邮")
    @JSONField(ordinal = 4)
    private Boolean isFreePostage;

    @ApiModelProperty("显示的商品价格,只是显示作用")
    @JSONField(ordinal = 5)
    private String displayPrice;

    @ApiModelProperty("销量")
    @JSONField(ordinal = 6)
    private Integer salesVolume;

    @ApiModelProperty("收藏数量")
    @JSONField(ordinal = 7)
    private Integer collectionNum;

    @ApiModelProperty("用户是否已经收藏 0- 否 1-是")
    @JSONField(ordinal = 8)
    private Boolean isFavorites;

    @ApiModelProperty("商品ID")
    @JSONField(ordinal = 9)
    private Long goodsId;

    @ApiModelProperty(value = "税率类型 1--平台税率 2--自定义税率 3—无税率")
    @JSONField(ordinal = 10)
    private Integer taxRateType;

    @ApiModelProperty(value = "税率")
    @JSONField(ordinal = 11)
    private BigDecimal taxRate;

    @ApiModelProperty(value = "自定义税率")
    @JSONField(serialize = false,ordinal = 12)
    private BigDecimal customTaxRate;

    @ApiModelProperty(value = "税费")
    @JSONField(ordinal = 13)
    private BigDecimal taxCost;

    @ApiModelProperty(value = "类目ID")
    @JSONField(ordinal = 14)
    private Long categoryId;

    @ApiModelProperty(value = "店铺IM账号")
    @JSONField(ordinal = 15)
    private String storeImId;

    @ApiModelProperty(value = "平台IM账号")
    @JSONField(ordinal = 16)
    private String platImId;

    @ApiModelProperty("运费信息")
    @JSONField(ordinal = 17)
    private ShipFreightInfoVo shipFreightInfoVo;

    @ApiModelProperty("店铺信息")
    @JSONField(ordinal = 18)
    private StoreVo storeVo;

    @ApiModelProperty("活动")
    @JSONField(ordinal = 19)
    private List<AttributeVo> activityVoList;

    @ApiModelProperty("服务")
    @JSONField(ordinal = 20)
    private List<AttributeVo> serviceList;

    @ApiModelProperty("参数")
    @JSONField(ordinal = 21)
    private List<AttributeVo> paramList;

    @ApiModelProperty("商品对应的所有规格信息")
    @JSONField(ordinal = 22)
    private List<GoodsStandardVo> goodsStandardVoList;

    @ApiModelProperty("每个sku的详情")
    @JSONField(ordinal = 23)
    private Map<String,SpecifiedSkuVo> skuDetail;

    @ApiModelProperty("商品图文详情")
    @JSONField(ordinal = 24)
    private String detailHtml;

}