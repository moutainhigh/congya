package com.chauncy.data.dto;

import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.MyForeignKeyConstraint;
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
@ApiModel(value = "PmGoodsCategoryPo对象", description = "商品分类表")
public class GoodCategoryDto {

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
    @MyForeignKeyConstraint(tableName = "pm_goods_category")
    private Long parentId;

    @ApiModelProperty(value = "规格、参数、服务说明、活动说明的id集合")
    @NotNull
    @MyForeignKeyConstraint(tableName = "pm_goods_attribute",message = "goodAttributeIds中存在数据库没有的id")
    List<Long> goodAttributeIds;
    //没用
    @ApiModelProperty(value = "类型 1->平台服务说明管理类型 2->商家服务说明管理类型 3->平台活动说明管理类型  4->商品参数管理类型 5->标签管理类型 6->购买须知管理类型 7->规格管理类型 8->品牌管理")
    @EnumConstraint(target = GoodsAttributeTypeEnum.class)
    private Integer type;






}
