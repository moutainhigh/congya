package com.chauncy.data.dto.good;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019-06-03 11:34
 **/
@Data
@ApiModel(value = "查找商品分类对象")
public class SearchGoodCategoryDto {

    @ApiModelProperty(value = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "分类缩略图")
    @NotBlank(message = "分类缩略图不能为空")
    private String icon;

    @ApiModelProperty(value = "排序数字")
    @Min(value = 0,message = "排序数字必须大于0")
    private BigDecimal sort;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    @NotNull(message = "启用状态不能为空!")
    private Boolean enabled;

    @ApiModelProperty(value = "税率")
    @Min(value = 0,message = "税率必须大于0")
    private BigDecimal taxRate;

    @ApiModelProperty(value = "父分类ID")
    @NeedExistConstraint(tableName = "pm_goods_category")
    private Long parentId;

    @ApiModelProperty(value = "规格、参数、服务说明、活动说明的id集合")
    @NotNull
    @NeedExistConstraint(tableName = "pm_goods_attribute",message = "goodAttributeIds中存在数据库没有的id")
    List<Long> goodAttributeIds;






}
