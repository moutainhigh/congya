package com.chauncy.data.dto.app.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/28 15:45
 */
@Data
@ApiModel(value = "SearchReducedGoodsDto对象", description = "满减活动获取去凑单商品列表参数")
public class SearchReducedGoodsDto  extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "categoryId参数错误，不能为0")
    @ApiModelProperty(value = "商品分类id   \n")
    private Long categoryId;

    @Min(value = 1, message = "goodsId参数错误，不能为0")
    @NotNull(message = "商品id参数不能为空")
    @ApiModelProperty(value = "商品id   \n")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "活动id   \n")
    private Long activityId;

    @ApiModelProperty(value = "排序方式 默认降序（desc）   \n")
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）   \nCOMPREHENSIVE_SORT：综合排序   \n" +
            "SALES_SORT：销量排序   \n PRICE_SORT：价格排序   \n")
    private SortFileEnum sortFile;

}