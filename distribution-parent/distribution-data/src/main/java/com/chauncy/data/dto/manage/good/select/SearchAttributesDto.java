package com.chauncy.data.dto.manage.good.select;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yeJH
 * @Description 编辑新增商品时根据商品分类id以及正在编辑的商品id获取商品属性信息
 * @since 2019/11/26 10:40
 */
@Data
@ApiModel(value = "查找商品分类列表")
public class SearchAttributesDto {

    @ApiModelProperty("分类id")
    @NotNull(message = "分类id不能为空")
    private Long categoryId;

    @ApiModelProperty("商品id")
    private Long goodsId;

}

