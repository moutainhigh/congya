package com.chauncy.data.dto;

import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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
    private Long parentId;

    @ApiModelProperty(value = "规格、参数、服务说明、活动说明的id集合")
    @Valid
    List<Long> goodAttributeIds;






}
