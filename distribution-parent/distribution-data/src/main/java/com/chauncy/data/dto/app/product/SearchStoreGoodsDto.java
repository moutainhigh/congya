package com.chauncy.data.dto.app.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.goods.StoreGoodsListTypeEnum;
import com.chauncy.common.enums.goods.StoreGoodsTypeEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/17 16:10
 */
@Data
@ApiModel(value = "SearchStoreGoodsDto对象", description = "查询店铺下的商品列表")
public class SearchStoreGoodsDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺id")
    @NeedExistConstraint(tableName = "sm_store",  concatWhereSql = "and enabled = 1", message = "店铺记录不存在")
    private Long storeId;

    @ApiModelProperty(value = "店铺商品列表类型")
    @NotNull(message = "店铺商品列表类型不能为空")
    @EnumConstraint(target = StoreGoodsListTypeEnum.class)
    private Integer goodsType;

    @ApiModelProperty(value = "商品三级分类id，goodsType为6时传参")
    private Long goodsCategoryId;

    @ApiModelProperty(value = "商品名称搜索，goodsType为7时传参")
    private String goodsName;

    @ApiModelProperty(value = "排序方式 默认降序（desc）")
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）")
    private SortFileEnum sortFile;

    /**
     * 新品的评判标准  上架几天内为新品
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private Integer newGoodsDays;


    @ApiModelProperty(value = "筛选条件 最低价")
    private BigDecimal lowestPrice;

    @ApiModelProperty(value = "筛选条件 最高价")
    private BigDecimal highestPrice;

    @ApiModelProperty(value = "筛选条件  品牌id")
    private List<Long> brandIds;

    @ApiModelProperty(value = "筛选条件  分类id")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "筛选条件  标签id")
    private List<Long> labelIds;

}
