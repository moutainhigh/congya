package com.chauncy.data.dto.manage.message.advice.tab.association.search;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-16 16:32
 * <p>
 * 条件分页查询店铺基础信息
 */
@Data
@ApiModel(description = "条件分页查询店铺基础信息")
@Accessors(chain = true)
public class SearchStoresDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "选项卡ID")
    @NotNull(message = "选项卡ID不能为空")
    @NeedExistConstraint(tableName = "mm_advice_tab")
    private Long tabId;
}
