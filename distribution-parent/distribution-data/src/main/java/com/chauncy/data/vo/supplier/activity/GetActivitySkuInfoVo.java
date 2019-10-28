package com.chauncy.data.vo.supplier.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-06-18 23:39
 * <p>
 * 商品属性页面
 * <p>
 * 获取商家报名活动需要的商品sku信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "获取商家报名活动需要的商品sku信息")
public class GetActivitySkuInfoVo {

    @ApiModelProperty("商品规格信息")
    @Valid
    @NotNull(message = "商品规格信息不能为空")
    @JSONField(ordinal = 11)
    private List<GoodsStandardVo> goodsStandardVo;

    @ApiModelProperty("具体sku列表信息")
    @JSONField(ordinal = 12)
    private List<Map<String,Object>> skuList;

    @ApiModelProperty("积分抵扣比例")
    @JSONField(ordinal = 13)
    private BigDecimal discountPriceRatio;

    @ApiModelProperty("所有sku中最小活动库存")
    @JSONField(ordinal = 14)
    private Integer lowestStock;

    @ApiModelProperty(value = "备注")
    @JSONField(ordinal = 4)
    private String remark;

    @ApiModelProperty("审核状态:2-待审核 3-已通过 4-已拒绝 5-返回修改 ")
    @JSONField(ordinal = 10)
    private String status;

    @ApiModelProperty(value = "活动类型")
    @JSONField(ordinal = 8)
    private Integer activityType;

    @ApiModelProperty(value = "店铺ID")
    @JSONField(ordinal = 9)
    private Long storeId;

    @ApiModelProperty(value = "活动ID")
    @JSONField(ordinal = 7)
    private Long activityId;

    @ApiModelProperty(value = "审核时间")
    @JSONField(ordinal = 6)
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核人")
    @JSONField(ordinal = 5)
    private String verifier;

    @ApiModelProperty(value = "购买上限")
    @JSONField(ordinal = 4)
    private Integer buyLimit;

    @ApiModelProperty(value = "平台活动与商品关联的ID",hidden = true)
    @JSONField(ordinal = 0)
    private Long activityGoodsRelId;

    @ApiModelProperty(value = "商品id")
    @JSONField(ordinal = 1)
    private Long goodsId;

    @ApiModelProperty(value = "图片")
    @JSONField(ordinal = 2)
    private String picture;

    @ApiModelProperty(value = "活动成本比例",hidden = true)
    @JSONField(ordinal = 3)
    private BigDecimal activityCostRate;

}
