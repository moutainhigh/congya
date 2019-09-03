package com.chauncy.data.dto.manage.activity.gift.add;

import com.chauncy.common.enums.app.gift.GiftTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-22 14:39
 *
 * 保存礼包信息
 */
@ApiModel(description = "保存礼包信息")
@Data
@Accessors(chain = true)
public class SaveGiftDto {

    @ApiModelProperty(value = "礼包ID,新增时传id = 0")
    private Long id;

    @ApiModelProperty(value = "礼包类型 1-充值礼包 2-新人礼包")
    @EnumConstraint(target = GiftTypeEnum.class)
    @NotNull(message = "礼包类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "是否启用 1-启用，0-禁用")
    @NotNull(message = "状态不能为空")
    private Boolean enable;

    @ApiModelProperty(value = "礼包名称")
    @NotNull(message = "礼包名称不能为空")
    private String name;

    @ApiModelProperty(value = "经验值")
    @NotNull(message = "经验值不能为空")
    private BigDecimal experience;

    @ApiModelProperty(value = "购物券")
    @NotNull(message = "购物券不能为空")
    private BigDecimal vouchers;

    @ApiModelProperty(value = "积分")
    @NotNull(message = "积分不能为空")
    private BigDecimal integrals;

    @ApiModelProperty(value = "购买金额,添加新人礼包时不用赋值")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "图片,添加新人礼包时不用赋值")
    private String picture;

    @ApiModelProperty(value = "图文详情,添加新人礼包时不用赋值")
    private String detailHtml;

    @ApiModelProperty(value = "优惠券id集合")
    @NeedExistConstraint(tableName = "am_coupon")
    private List<Long> couponIdList;
}
