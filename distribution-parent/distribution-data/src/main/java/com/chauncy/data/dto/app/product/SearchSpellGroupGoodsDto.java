package com.chauncy.data.dto.app.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/2 21:31
 */
@Data
@ApiModel(value = "SearchSpellGroupGoodsDto对象", description = "拼团活动商品列表参数")
public class SearchSpellGroupGoodsDto   extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "categoryId参数错误，不能为0")
    @ApiModelProperty(value = "商品分类id   \n")
    private Long categoryId;

    @JsonIgnore
    @JSONField(serialize = false)
    @ApiModelProperty(value = "活动id   \n")
    private Long activityId;

    @ApiModelProperty(value = "排序方式 默认降序（desc）   \n")
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）   \nCOMPREHENSIVE_SORT：综合排序   \n" +
            "SALES_SORT：销量排序   \n PRICE_SORT：价格排序   \n")
    private SortFileEnum sortFile;

}