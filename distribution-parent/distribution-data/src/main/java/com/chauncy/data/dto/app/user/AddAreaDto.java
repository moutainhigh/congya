package com.chauncy.data.dto.app.user;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-30 14:58
 *
 * 用户新增收货地址
 */
@Data
@ApiModel(description = "用户新增收货地址")
@Accessors(chain = true)
public class AddAreaDto {

    @ApiModelProperty(value = "收货地址Id")
    @NeedExistConstraint(tableName ="um_area_shipping",groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货手机号码")
    private String mobile;

    @ApiModelProperty(value = "地区ID")
    private Long areaId;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "邮编")
    private Integer postalCode;

    @ApiModelProperty(value = "是否为默认收货地址 1--默认 0--否")
    private Boolean isDefault;

}
