package com.chauncy.data.vo.manage.activity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-23 16:08
 */
@Data
@ApiModel(description = "条件分页查询分类")
public class SearchCategoryByActivityIdVo {

    @ApiModelProperty("分类ID")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty("父级ID")
    @JSONField(ordinal = 1)
    private Long parentId;

    @ApiModelProperty(value = "分类名称")
    @JSONField(ordinal = 2)
    private String name;

//    @ApiModelProperty(value = "排序")
//    private BigDecimal sort;
//
//    @ApiModelProperty(value = "图标")
//    private String icon;

    @ApiModelProperty(value = "是否包含")
    private Boolean isInclude = false;

    @ApiModelProperty(value = "层级")
    @JSONField(ordinal = 3)
    private Integer level;

    @ApiModelProperty("层级关系")
    @JSONField(ordinal = 4)
    private List<SearchCategoryByActivityIdVo> children;
}
