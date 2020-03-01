package com.chauncy.data.dto.manage.activity.coupon.add;

import com.chauncy.common.enums.app.coupon.CouponFormEnum;
import com.chauncy.common.enums.app.coupon.CouponScopeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-18 17:10
 *
 * 保存优惠券Dto
 */
@Data
@ApiModel(description = "保存优惠券Dto")
@Accessors(chain = true)
public class SaveCouponRelationDto {

    @ApiModelProperty("优惠券id")
    @NotNull(message = "优惠券id不能为空")
    private Long id;

    @ApiModelProperty("关联ID集合")
    @NotNull(message = "关联ID集合不能为空")
    private List<Long> relationIds;



}
