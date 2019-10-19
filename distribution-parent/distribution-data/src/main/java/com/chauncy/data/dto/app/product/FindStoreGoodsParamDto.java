package com.chauncy.data.dto.app.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.goods.StoreGoodsListTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @description: 查询店铺下的商品列表筛选参数
 * @since 2019/10/17 14:25
 */
@Data
@ApiModel(value = "GetStoreGoodsParamDto对象", description = "查询店铺下的商品列表筛选参数")
public class FindStoreGoodsParamDto implements Serializable {

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

    /**
     * 新品的评判标准  上架几天内为新品
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private Integer newGoodsDays;

}
