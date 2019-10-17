package com.chauncy.data.dto.app.product;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yeJH
 * @description: 查找商品分类
 * @since 2019/10/17 12:17
 */
@Data
@ApiModel(value = "FindGoodsCategoryDto对象", description = "查找商品分类参数")
public class FindGoodsCategoryDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "分类id")
    @NeedExistConstraint(tableName = "pm_goods_category",
            concatWhereSql = "and level = 1", message = "分类不存在或不是一级分类")
    @Min(value = 1, message = "分类id不能为0")
    private Long categoryId;

}
