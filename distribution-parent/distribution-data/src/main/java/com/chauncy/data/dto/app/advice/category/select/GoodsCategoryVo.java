package com.chauncy.data.dto.app.advice.category.select;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-05 21:02
 *
 * 葱鸭百货下关联的商品分类
 */
@Data
@ApiModel(description = "葱鸭百货下关联的商品分类")
@Accessors(chain = true)
public class GoodsCategoryVo {

    @ApiModelProperty("分类ID")
    @JSONField(ordinal = 0)
    private Long categoryId;

    @ApiModelProperty("分类名称")
    @JSONField(ordinal = 1)
    private String categoryName;

    @ApiModelProperty("分类缩略图")
    @JSONField(ordinal = 2)
    private String categoryIcon;

    @ApiModelProperty("父级ID")
    @JSONField(ordinal = 3)
    private Long parentId;

    @ApiModelProperty("子级")
    @JSONField(ordinal = 4)
    private List<GoodsCategoryVo> children;
}
