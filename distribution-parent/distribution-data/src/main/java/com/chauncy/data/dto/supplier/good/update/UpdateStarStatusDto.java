package com.chauncy.data.dto.supplier.good.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-21 14:26
 *
 * 修改应用标签
 */
@Data
@ApiModel(description = "修改应用标签")
public class UpdateStarStatusDto {

    @ApiModelProperty("商品ID集合")
    @NotNull(message = "商品ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long[] goodIds;

    @ApiModelProperty("是否是明星单品")
    @NotNull(message = "明星单品不能为空")
    private Boolean starStatus;
}
