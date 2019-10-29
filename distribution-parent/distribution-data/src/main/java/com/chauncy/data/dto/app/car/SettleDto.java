package com.chauncy.data.dto.app.car;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/16 15:48
 **/

@Data
@ApiModel(description = "购物车点击结算")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SettleDto {

    @ApiModelProperty(value = "收货地址id，第一次请求时为空，采用默认地址，之后修改收货地址触发;提交订单时必传")
    private Long areaShipId;

    @ApiModelProperty(value = "结算详情")
    private List<SettleAccountsDto> settleAccountsDtos;

    //第二版本
    @ApiModelProperty(value = "结算时使用用户哪张优惠券")
    private Long couponRelUserId;

    @ApiModelProperty(value = "是否拼团")
    private Boolean isGroup;

    @ApiModelProperty(value = "参加拼团id,自己发起新团时该字段为空")
    @NeedExistConstraint(tableName = "am_spell_group_main")
    private Long mainId;
}
