package com.chauncy.data.dto.app.advice.goods.select;

import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-09-03 22:27
 *
 * 分页查询商品列表条件
 */
@Data
@ApiModel(description = "分页查询商品列表条件")
@Accessors(chain = true)
public class SearchGoodsBaseListDto {

    @ApiModelProperty(value = "查询商品列表前提条件Id(品牌ID/店铺ID等)")
    private Long conditionId;

    @ApiModelProperty(value = "排序方式 默认降序（desc）")
//    @EnumConstraint(target = SortWayEnum.class)
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）")
//    @EnumConstraint(target = SortFileEnum.class)
    private SortFileEnum sortFile;

    @ApiModelProperty(value = "最低价")
    private BigDecimal lowestPrice;

    @ApiModelProperty(value = "最高价")
    private BigDecimal highestPrice;

    @Min(1)
    @ApiModelProperty(value = "页码 默认1")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小 默认10")
    private Integer pageSize;


}
