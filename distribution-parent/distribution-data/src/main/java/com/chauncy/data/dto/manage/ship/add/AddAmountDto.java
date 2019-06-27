package com.chauncy.data.dto.manage.ship.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-24 13:06
 *
 * 添加按金额计算运费信息列表
 */
@Data
@ApiModel(description = "添加按金额计算运费信息列表")
@Accessors(chain = true)
public class AddAmountDto {

    @ApiModelProperty("运费ID")
//    @NotNull(content = "运费ID不能为空",groups = IUpdateGroup.class)
//    @NeedExistConstraint(tableName = "pm_money_shipping")
    private Long id;

    @ApiModelProperty(value = "指定地区")
    @NotNull(message = "指定地区不能为空")
    @NeedExistConstraint(tableName = "area_region",groups = IUpdateGroup.class)
    private Long destinationId;

    @ApiModelProperty(value = "指定地区基础运费")
    @NotNull(message = "指定地区基础运费不能为空")
    private BigDecimal destinationBasisFreight;

    @ApiModelProperty(value = "指定地区满金额条件")
    @NotNull(message = "指定地区满金额条件不能为空")
    private BigDecimal destinationFullMoney;

    @ApiModelProperty(value = "指定地区满足金额条件后的运费")
    @NotNull(message = "指定地区满足金额条件后的运费不能为空")
    private BigDecimal destinationPostMoney;

}
