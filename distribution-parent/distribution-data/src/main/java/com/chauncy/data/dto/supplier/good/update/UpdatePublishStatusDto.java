package com.chauncy.data.dto.supplier.good.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-21 14:07
 *
 * 上下架商品需要的条件
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "上下架商品需要的条件")
public class UpdatePublishStatusDto {

    @ApiModelProperty("商品ID集合，以逗号隔开：1，2，3")
    @NeedExistConstraint(tableName = "pm_goods")
    @NotNull(message = "商品ID不能为空")
    private Long[] goodIds;

    @ApiModelProperty("上下架状态: 0->下架，1->上架")
    @NotNull(message = "上下架状态不能为空")
    private Boolean pulishStatus;
}
