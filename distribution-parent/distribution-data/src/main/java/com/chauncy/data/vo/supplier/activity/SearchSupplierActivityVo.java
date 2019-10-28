package com.chauncy.data.vo.supplier.activity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-29 12:48
 *
 * 条件分页查找商家参与活动列表
 */
@Data
@ApiModel(description = "条件分页查找商家参与活动列表")
@Accessors(chain = true)
public class SearchSupplierActivityVo {

    @ApiModelProperty(value = "平台活动与商品关联的ID")
    @JSONField(ordinal = 0)
    private Long activityGoodsRelId;

//    @ApiModelProperty(value = "skuIds")
//    @JSONField(ordinal = 1)
//    private List<Long> skuIds;

    @ApiModelProperty(value = "goodsSkuRelIds")
    @JSONField(ordinal = 1)
    private List<Long> goodsSkuRelIds;

    @ApiModelProperty("商品ID")
    @JSONField(ordinal = 2)
    private Long goodsId;

    @ApiModelProperty("商品名称")
    @JSONField(ordinal = 3)
    private String goodsName;

    @ApiModelProperty("活动ID")
    @JSONField(ordinal = 4)
    private Long activityId;

    @ApiModelProperty("活动名称")
    @JSONField(ordinal = 5)
    private String activityName;

    @ApiModelProperty(value = "活动类型",hidden = true)
    @JSONField(ordinal = 6)
    private Integer activityType;

    @ApiModelProperty("品牌")
    @JSONField(ordinal = 7)
    private String brand;

    @ApiModelProperty("审核状态 2-待审核 3-已通过 4-已拒绝 5-返回修改")
    @JSONField(ordinal = 8)
    private Integer verifyStatus;

    @ApiModelProperty("参与活动的商品详细信息")
    @JSONField(ordinal = 9)
    private GetActivitySkuInfoVo goodsDetail;

    @ApiModelProperty(value = "返回修改原因",hidden = true)
    @JSONField(ordinal = 10,serialize = false)
    private String modifyCause;

    @ApiModelProperty(value = "拒绝原因",hidden = true)
    @JSONField(ordinal = 11,serialize = false)
    private String refuseCase;

    @ApiModelProperty(value = "原因")
    @JSONField(ordinal = 12)
    private String cause;

    @ApiModelProperty("店铺ID")
    @JSONField(ordinal = 13)
    private Long storeId;

    @ApiModelProperty("店铺名称")
    @JSONField(ordinal = 14)
    private String storeName;
}
