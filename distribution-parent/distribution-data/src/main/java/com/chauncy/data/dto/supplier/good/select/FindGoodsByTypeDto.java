package com.chauncy.data.dto.supplier.good.select;

import com.chauncy.common.enums.goods.StoreGoodsTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/8 21:27
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "根据类型（自有商品，分配商品）查找店铺商品")
public class FindGoodsByTypeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺id")
    @NotNull(message = "店铺id不能为空")
    @NeedExistConstraint(tableName = "sm_store")
    public Long storeId;

    @ApiModelProperty(value = "商品类型： 自有商品  分配商品")
    @EnumConstraint(target = StoreGoodsTypeEnum.class)
    private StoreGoodsTypeEnum storeGoodsTypeEnum;
}
