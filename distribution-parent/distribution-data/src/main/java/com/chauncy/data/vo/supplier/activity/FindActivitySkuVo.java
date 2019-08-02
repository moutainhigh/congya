package com.chauncy.data.vo.supplier.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-26 15:17
 *
 * 查找对应的sku信息
 */
@Data
@ApiModel(description = "查找对应的sku信息")
@Accessors(chain = true)
public class FindActivitySkuVo {

    @ApiModelProperty(value = "skuId")
    @JSONField(ordinal = 13)
    private Long skuId;

//    @ApiModelProperty(value = "商品id")
//    private Long goodsId;
//
//    @ApiModelProperty(value = "图片")
//    private String picture;

    @ApiModelProperty(value = "供货价",hidden = true)
    @JSONField(ordinal = 24)
    private BigDecimal supplierPrice;

    @ApiModelProperty(value = "利润比例",hidden = true)
    @JSONField(ordinal = 23)
    private BigDecimal profitRate;

    @ApiModelProperty(value = "运营成本比例",hidden = true)
    @JSONField(ordinal = 22)
    private BigDecimal operationCost;

//    @ApiModelProperty(value = "活动成本比例",hidden = true)
//    private BigDecimal activityCostRate;

    @ApiModelProperty(value = "销售价格",hidden = true)
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "库存数量")
    @JSONField(ordinal = 14)
    private Integer stock;

    @ApiModelProperty(value = "活动库存数量")
    @JSONField(ordinal = 15)
    private Integer activityStock;

    @ApiModelProperty(value = "活动价格")
    @JSONField(ordinal = 16)
    private BigDecimal activityPrice;
//
//    @ApiModelProperty(value = "活动图片")
//    private String activityPicture;

//    @ApiModelProperty(value = "购买上限")
//    private Integer limitNum;

//    @ApiModelProperty(value = "备注")
//    private String remark;
//
//    @ApiModelProperty("审核状态:2-待审核 3-已通过 4-已拒绝 5-返回修改 ")
//    private String status;
//
//    @ApiModelProperty(value = "活动类型")
//    private Integer activityType;
//
//    @ApiModelProperty(value = "店铺ID")
//    private Long storeId;
//
//    @ApiModelProperty(value = "活动ID")
//    private Long activityId;
//
//    @ApiModelProperty(value = "审核时间")
//    private LocalDateTime verifyTime;
//
//    @ApiModelProperty(value = "审核人")
//    private String verifier;
//
//    @ApiModelProperty(value = "购买上限")
//    private Integer buyLimit;

    @ApiModelProperty("推荐活动价")
    @JSONField(ordinal = 17)
    private String recommendedActivityPrice;

    @ApiModelProperty(value = "平台活动的商品与sku关联的ID",hidden = true)
    @JSONField(ordinal = 21)
    private Long goodsSkuRelId;

//    @ApiModelProperty(value = "平台活动与商品关联的ID",hidden = true)
//    private Long activityGoodsRelId;

    @ApiModelProperty("推荐活动最低价")
    @JSONField(ordinal = 18)
    private BigDecimal lowActivityPrice;

    @ApiModelProperty("推荐活动最高价")
    @JSONField(ordinal = 19)
    private BigDecimal highActivityPrice;

}
