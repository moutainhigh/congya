package com.chauncy.data.dto.manage.message.advice.tab.association.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-14 16:07
 *
 * 分页查询店铺分类条件参数
 */
@Data
@ApiModel(description = "分页查询店铺分类条件参数")
@Accessors(chain = true)
public class SearchStoreClassificationDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "店铺分类名称")
    private String name;

    @ApiModelProperty(value = "广告ID,当新增时广告ID传0")
    @NotNull(message = "广告ID不能为空")
    private Long adviceId;
}
