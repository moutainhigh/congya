package com.chauncy.data.dto.supplier.good.update;

import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-16 12:12
 *
 * 运营角色添加商品信息
 */
@Data
@ApiModel(value = "UpdateGoodOperationDto", description = "运营角色添加商品信息")
public class UpdateGoodOperationDto {

    @ApiModelProperty(value = "商品ID")
    @NeedExistConstraint(tableName = "pm_goods")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "活动成本比例")
    @NotNull(message = "活动成本比例不能为空")
    private BigDecimal activityCostRate;

    @ApiModelProperty(value = "让利成本比例")
    @NotNull(message = "让利成本比例不能为空")
    private BigDecimal profitsRate;

    @ApiModelProperty(value = "推广成本比例")
    @NotNull(message = "推广成本比例不能为空")
    private BigDecimal generalizeCostRate;

    @ApiModelProperty(value = "限定会员等级ID")
    @NotNull(message = "限定会员等级ID不能为空")
    @NeedExistConstraint(tableName = "pm_member_level")
    private Long memberLevelId;

    @ApiModelProperty(value = "商品排序数字")
    @NotNull(message = "商品排序数字不能为空")
    private BigDecimal sort;

    @ApiModelProperty(value="税率选择，即税率类型1--平台税率 2--自定义税率,只有在商品类型goodsType为保税仓或海外直邮才显示")
    private Integer taxRateType;

    @ApiModelProperty(value="税率")
    private BigDecimal customTaxRate;

    @ApiModelProperty(value = "是否包邮 默认为0不包邮")
    @NotNull(message = "是否包邮不能为空")
    private Boolean isFreePostage;

    @ApiModelProperty(value = "审核状态 1->未审核 2->审核通过 3->未通过")
    @EnumConstraint(target = VerifyStatusEnum.class)
    private Integer verifyStatus;
}
