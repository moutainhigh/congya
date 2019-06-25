package com.chauncy.data.dto.manage.ship.add;

import com.chauncy.common.enums.ship.ShipCalculateWayEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-24 12:12
 *
 * 添加按金额计算运费模版
 */
@ApiModel("添加按金额计算运费模版")
@Data
@Accessors(chain = true)
public class AddShipTemplateDto {

    @ApiModelProperty("运费模版ID")
//    @NotNull(message = "运费模版ID不能为空",groups = IUpdateGroup.class)
//    @NeedExistConstraint(tableName = "pm_shipping_template",groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty("运费模版名称")
    @NotNull(message = "运费模版名称不能为空")
    private String name;

    @ApiModelProperty("运费计算方式 1->按金额。2->按件数")
    @NotNull(message = "运费计算方式不能为空")
    @EnumConstraint(target = ShipCalculateWayEnum.class)
    private Integer calculateWay;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

//    @ApiModelProperty("模版类型 1--平台后台 2--商家端")
//    private Integer type;

    @ApiModelProperty(value = "默认(基础)运费")
    @NotNull(message = "默认(基础)运费不能为空")
    private BigDecimal defaultFreight;

    @ApiModelProperty(value = "默认满金额(满足条件金额)")
    @NotNull(message = "默认满金额(满足条件金额不能为空")
    private BigDecimal defaultFullMoney;

    @ApiModelProperty(value = "默认满足金额条件后的运费默认满足金额条件后的运费默认满足金额条件后的运费")
    @NotNull(message = "默认满足金额条件后的运费默认满足金额条件后的运费默认满足金额条件后的运费不能为空")
    private BigDecimal defaultPostMoney;

    @ApiModelProperty(value = "商品地址")
    @NeedExistConstraint(tableName = "area_region")
    private Long productAddressId;

    @ApiModelProperty(value = "按金额计算条件对象")
    @Valid
    private List<AddAmountDto> amountDtos;

    @ApiModelProperty(value = "按金额计算条件对象")
    @Valid
    private List<AddNumDto> numDtos;

}
