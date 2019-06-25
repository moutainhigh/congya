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
 * 添加按件数计算运费信息列表
 */
@Data
@ApiModel(description = "添加按件数计算运费信息列表")
@Accessors(chain = true)
public class AddNumDto {

    @ApiModelProperty("运费ID")
//    @NotNull(message = "运费ID不能为空",groups = IUpdateGroup.class)
//    @NeedExistConstraint(tableName = "pm_money_shipping",groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "指定地区")
    @NotNull(message = "指定地区不能为空")
    @NeedExistConstraint(tableName = "area_region")
    private Long destinationId;

    @ApiModelProperty(value = "指定地区运费的最大件数")
    private Integer destinationMaxNumber;

    @ApiModelProperty(value = "指定地区最大件数内的运费")
    private BigDecimal destinationMaxNumberMoney;

    @ApiModelProperty(value = "指定地区超过最大件数每增加件数")
    private Integer destinationAddtionalNumber;

    @ApiModelProperty(value = "指定地区每增加件数就增加的运费")
    private BigDecimal destinationAddtionalFreight;

}
