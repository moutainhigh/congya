package com.chauncy.data.dto.app.car;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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

    @ApiModelProperty(value = "收货地区id，第一次请求时为空，采用默认地址，之后修改收货地址触发")
    private Long areaShipId;

    @ApiModelProperty(value = "结算详情")
    private List<SettleAccountsDto> settleAccountsDtos;
}
