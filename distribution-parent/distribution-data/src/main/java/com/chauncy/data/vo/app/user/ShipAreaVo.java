package com.chauncy.data.vo.app.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-30 16:41
 *
 */

@ApiModel(description = "收货地址详情")
@Data
public class ShipAreaVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货手机号码")
    private String mobile;

    @ApiModelProperty(value = "地区ID")
    private Long areaId;

    @ApiModelProperty(value = "省市区聚合")
    private String areaName;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "邮编")
    private Integer postalCode;

    @ApiModelProperty(value = "是否为默认收货地址 1--默认 0--否")
    private Boolean isDefault;

}
