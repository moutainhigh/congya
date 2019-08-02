package com.chauncy.data.vo.app.goods;

import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
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

    @ApiModelProperty("每个sku的详情")
    private Map<String,SpecifiedSkuVo> skuDetail;

    @ApiModelProperty("商品对应的所有规格信息")
    private List<GoodsStandardVo> goodsStandardVoList;

    @ApiModelProperty("商品标题")
    private String goodsName;

    @ApiModelProperty("商品轮播图")
    private String carousel;

    @ApiModelProperty("发货地")
    private String originPlace;

    @ApiModelProperty("默认运费")
    private BigDecimal defaultFreight;

    @ApiModelProperty("月销量")
    private Integer salesVolumeMonthly;

    @ApiModelProperty("显示的商品价格,只是显示作用")
    private String displayPrice;

    @ApiModelProperty("是否包邮")
    private Boolean isPostage;

    @ApiModelProperty("默认邮费")
    private BigDecimal defaultPostage;

    @ApiModelProperty("活动")
    private List<AttributeVo> activityVoList;

    @ApiModelProperty("服务")
    private List<AttributeVo> serviceList;

    @ApiModelProperty("参数")
    private List<AttributeVo> paramList;

    @ApiModelProperty("店铺信息")
    private StoreVo storeVo;
}