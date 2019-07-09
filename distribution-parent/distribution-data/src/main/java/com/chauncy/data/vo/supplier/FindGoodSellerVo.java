package com.chauncy.data.vo.supplier;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-20 19:17
 *
 * 查找销售角色需要的商品信息
 */
@Data
@ApiModel(description = "查找销售角色需要的商品信息")
@Accessors(chain = true)
public class FindGoodSellerVo {

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "发货地")
    private String location;

    @ApiModelProperty(value = "限购数量")
    private Integer purchaseLimit;

//    @ApiModelProperty(value = "发货地，返回省市的id")
//    private String[] locationCodes;
}
