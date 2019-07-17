package com.chauncy.data.dto.app.brand;

import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/15  22:06
 * @Version 1.0
 * 条件查询分类下的商品信息
 *
 */
@Data
@ApiModel(description = "条件查询分类下的商品信息")
@Accessors(chain = true)
public class SearchGoodsDto {

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "排序字段 COMPREHENSIVE_SORT--综合排序,SALES_SORT--销量排序,PRICE_SORT--价格排序")
    @EnumConstraint(target = SortFileEnum.class)
    private String sortFile;

    @ApiModelProperty(value = "排序方式 desc--降序 asc--升序")
    @EnumConstraint(target = SortWayEnum.class)
    private String sortWay;

    @Min (1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
