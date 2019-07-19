package com.chauncy.data.dto.app.store;

import com.chauncy.common.enums.goods.GoodsCategoryLevelEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/16 11:45
 */
@Data
@ApiModel(value = "FindStoreCategoryDto对象", description = "查询店铺下的商品分类")
public class FindStoreCategoryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺id")
    @Min(value = 1, message = "店铺ID参数错误")
    @NeedExistConstraint(tableName = "sm_store",  concatWhereSql = "and enabled = 1")
    private Long storeId;

    @ApiModelProperty(value = "分类id")
    @NeedExistConstraint(tableName = "pm_goods_category")
    private Long goodsCategoryId;

}
