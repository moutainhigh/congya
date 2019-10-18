package com.chauncy.data.dto.app.advice.goods.select;

import com.chauncy.common.enums.app.advice.ConditionTypeEnum;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

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

    @ApiModelProperty(value = "查询条件类型")
    private ConditionTypeEnum conditionType;

    @ApiModelProperty(value = "只当查询条件类型为BAIHUO_ASSOCIATED——百货关联的所有商品,才传葱鸭百货广告ID,其它类型不传")
    private Long adviceId;

    @ApiModelProperty(value = "查询商品列表前提条件Id(品牌ID/店铺ID/选项卡Id/商品二级/三级分类ID等) \n "
            +"当查询条件类型为BAIHUO_ASSOCIATED——百货关联的所有商品,该值不传")
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


    @ApiModelProperty(value = "筛选条件  品牌id")
    private List<Long> brandIds;

    @ApiModelProperty(value = "筛选条件  分类id")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "筛选条件  标签id")
    private List<Long> labelIds;

}
