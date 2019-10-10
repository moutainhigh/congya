package com.chauncy.data.dto.manage.message.advice.select;

import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-10-09 20:41
 *
 * 条件分页查询今日必拼广告需要绑定的参加拼团的商品信息
 */
@Data
@ApiModel(description = "条件分页查询今日必拼广告需要绑定的参加拼团的商品信息")
@Accessors(chain = true)
public class SearchSpellAdviceGoodsDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "三级分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "排序方式 默认降序（desc）   \n",hidden = true)
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）   \nCOMPREHENSIVE_SORT：综合排序   \n" +
            "SALES_SORT：销量排序   \n PRICE_SORT：价格排序   \n",hidden = true)
    private SortFileEnum sortFile;
}
