package com.chauncy.data.vo.app.activity.spell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/8 0:12
 */
@Data
@ApiModel(description = "订单收货地址信息")
public class SpellGroupAreaVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货人电话")
    private String phone;

    @ApiModelProperty(value = "详细地址")
    private String shipAddress;

    @ApiModelProperty(value = "省市区")
    private String areaName;

}

