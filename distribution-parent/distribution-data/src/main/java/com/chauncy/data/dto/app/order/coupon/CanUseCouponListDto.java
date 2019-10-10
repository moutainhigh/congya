package com.chauncy.data.dto.app.order.coupon;

import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/10/10 12:07
 **/
@Data
@ApiModel(description = "查出能使用的优惠券")
@Accessors(chain = true)
public class CanUseCouponListDto  {

    @ApiModelProperty("商品skuID")
    @NotNull(message = "商品skuID不能为空")
    @NeedExistConstraint(tableName = "pm_goods_sku")
    private Long skuId;

    @ApiModelProperty("商品数量")
    @NotNull(message = "商品数量不能为空")
    private Integer number;
}
