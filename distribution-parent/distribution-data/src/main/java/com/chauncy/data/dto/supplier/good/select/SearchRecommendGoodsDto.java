package com.chauncy.data.dto.supplier.good.select;

import com.chauncy.common.enums.goods.StoreGoodsTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/8/10 19:42
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "查找店铺推荐商品")
public class SearchRecommendGoodsDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品分类id")
    @NeedExistConstraint(tableName = "pm_goods_category")
    @Min(value = 1, message = "商品分类id参数错误")
    public Long goodsCategoryId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "资讯id，编辑资讯时查找店铺推荐商品必传")
    @NeedExistConstraint(tableName = "mm_information")
    @Min(value = 1, message = "资讯id参数错误")
    private Long informationId;

    @JsonIgnore
    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
