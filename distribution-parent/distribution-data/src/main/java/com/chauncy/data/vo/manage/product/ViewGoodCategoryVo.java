package com.chauncy.data.vo.manage.product;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**查看商品分类详情
 * @Author zhangrt
 * @Date 2019/6/14 17:07
 **/
@Data
public class ViewGoodCategoryVo {

    @ApiModelProperty(value = "id,当新增时为空")
    private Long id;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    @NotNull(message = "启用状态不能为空!")
    private Boolean enabled;

    @ApiModelProperty(value = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "分类缩略图")
    @NotBlank(message = "分类缩略图不能为空")
    private String icon;

    @ApiModelProperty(value = "排序数字")
    @Min(value = 0,message = "排序数字必须大于0")
    private BigDecimal sort;



    @ApiModelProperty(value = "税率")
    @Min(value = 0,message = "税率必须大于0")
    private BigDecimal taxRate;

    @ApiModelProperty(value = "父分类ID,一级分类时这个字段不传或为空")
    @NeedExistConstraint(tableName = "pm_goods_category")
    private Long parentId;

    @ApiModelProperty(value = "分类总共三级")
    @NotNull(message = "分类级别不能为空")
    @Min(value = 1,message = "分类级别最小为1")
    @Max(value = 3,message = "分类级别最多为3级")
    private Integer level;

    @ApiModelProperty(value = "规格、参数、服务说明、活动说明的id集合")
    @NeedExistConstraint(tableName = "pm_goods_attribute",message = "goodAttributeIds中存在数据库没有的id")
    List<Long> goodAttributeIds;
}
