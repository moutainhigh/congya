package com.chauncy.data.vo.manage.activity.gift;

import com.chauncy.common.enums.app.gift.GiftTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-22 16:41
 *
 * FindGiftVo
 */
@Data
@ApiModel(description = "FindGiftVo")
@Accessors(chain = true)
@ToString
public class FindGiftVo {

    @ApiModelProperty(value = "礼包类型 1-充值礼包 2-新人礼包")
    private Integer type;

    @ApiModelProperty(value = "是否启用 1-启用，0-禁用")
    private Boolean enable;

    @ApiModelProperty(value = "礼包名称")
    private String name;

    @ApiModelProperty(value = "经验值")
    private Integer experience;

    @ApiModelProperty(value = "购物券")
    private Integer vouchers;

    @ApiModelProperty(value = "积分")
    private Integer integrals;

    @ApiModelProperty(value = "购买金额,新人礼包不显示")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "图片,新人礼包不显示")
    private String picture;

    @ApiModelProperty(value = "图文详情,新人礼包不显示")
    private String detailHtml;

    @ApiModelProperty("已经关联的优惠券集合")
    private List<BaseVo> associationedList;

    @ApiModelProperty("未关联的优惠券集合")
    private List<BaseVo> associationList;
}
