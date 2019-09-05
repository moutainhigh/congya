package com.chauncy.data.dto.app.advice.brand.select;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-09-05 12:15
 *
 * 获取不同品牌不同广告轮播图条件
 */
@Data
@ApiModel(description = "获取不同品牌不同广告轮播图条件")
@Accessors(chain = true)
public class FindBrandShufflingDto {

    @ApiModelProperty("选项卡ID")
    @NeedExistConstraint(tableName = "mm_advice_rel_tab_things",field = "tab_id")
    @NotNull(message = "选项卡ID不能为空")
    private Long tabId;

    @ApiModelProperty("品牌ID")
    @NeedExistConstraint(tableName = "mm_advice_rel_tab_things",field = "association_id")
    @NotNull(message = "品牌ID不能为空")
    private Long brandId;

}
