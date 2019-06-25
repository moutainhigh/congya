package com.chauncy.data.dto.manage.ship.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-24 12:12
 *
 * 添加按件数计算运费模版
 */
@ApiModel("添加按件数计算运费模版")
@Data
@Accessors(chain = true)
public class AddNumTemplateDto {

    @ApiModelProperty("运费模版ID")
    @NotNull(message = "运费模版ID不能为空",groups = IUpdateGroup.class)
    @NeedExistConstraint(tableName = "pm_shipping_template")
    private Long id;

    @ApiModelProperty("运费模版名称")
    @NotNull(message = "运费模版名称不能为空")
    private String name;

    @ApiModelProperty("运费计算方式 1->按金额。2->按件数")
    @NotNull(message = "运费计算方式不能为空")
    private Integer calculateWay;

    @ApiModelProperty(value = "商品地址")
    @NeedExistConstraint(tableName = "area_region")
    private Long productAddressId;

    @ApiModelProperty(value = "按金额计算条件对象")
    private List<AddAmountDto> amount;

    @ApiModelProperty(value = "默认运费的最大件数")
    private Integer defaultMaxNumber;

    @ApiModelProperty(value = "默认最大件数内的运费")
    private BigDecimal defaultMaxNumberMoney;

    @ApiModelProperty(value = "默认超过最大件数每增加件数")
    private Integer defaultAddtionalNumber;

    @ApiModelProperty(value = "默认每增加件数就增加的运费")
    private BigDecimal defaultAddtionalFreight;

    @ApiModelProperty(value = "按件数计算条件对象")
    private List<AddNumDto> numDtos;

}
