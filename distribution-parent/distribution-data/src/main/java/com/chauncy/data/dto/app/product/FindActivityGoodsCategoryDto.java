package com.chauncy.data.dto.app.product;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/28 17:41
 */
@Data
@ApiModel(value = "FindActivityGoodsCategoryDto对象", description = "获取活动商品列表商品一级分类")
public class FindActivityGoodsCategoryDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "groupId参数错误，不能为0")
    @ApiModelProperty(value = "活动分组id   \n")
    private Long groupId;

    @Min(value = 1, message = "goodsId参数错误，不能为0")
    @ApiModelProperty(value = "商品id   \n")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

}
