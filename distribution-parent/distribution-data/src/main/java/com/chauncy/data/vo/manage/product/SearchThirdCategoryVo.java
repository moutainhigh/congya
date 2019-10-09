package com.chauncy.data.vo.manage.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-07 15:53
 *
 * 条件分页查询所有第三级分类信息
 */
@Data
@ApiModel(description = "条件分页查询所有第三级分类信息")
@Accessors(chain = true)
public class SearchThirdCategoryVo {

    @ApiModelProperty(value = "分类ID")
    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "所属分类(只展示到二级分类：A/B)")
    private String categoryName;
}
