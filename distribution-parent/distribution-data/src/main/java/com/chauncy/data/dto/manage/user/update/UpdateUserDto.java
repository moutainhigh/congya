package com.chauncy.data.dto.manage.user.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/7 18:39
 **/
@ApiModel(description = "后端修改用户信息")
@Data
@Accessors(chain = true)
public class UpdateUserDto {

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空！")
    private Long id;

    @ApiModelProperty(value = "会员等级")
    @NotNull(message = "会员等级不能为空！")
    private Long memberLevelId;

    @ApiModelProperty(value = "节点用户")
    @NotNull(message = "节点用户不能为空！")
    private Long parentId;

    @ApiModelProperty(value = "用户标签")
    private List<Long> labelIds;

    @ApiModelProperty(value = "所属商家id")
    @NotNull(message = "所属商家不能为空！")
    private Long storeId;

    @ApiModelProperty(value = "佣金判断资格 0-关闭  1-开始")
    @NotNull(message = "佣金判断资格不能为空！")
    private Boolean commissionStatus;

    @ApiModelProperty(value = "红包数值")
    @NotNull(message = "红包的数值不能为空！")
    private BigDecimal currentRedEnvelops;

    @ApiModelProperty(value = "积分数值")
    @NotNull(message = "当前积分的数值不能为空！")
    private BigDecimal currentIntegral;

    @ApiModelProperty(value = "购物券数值")
    @NotNull(message = "当前购物券的数值不能为空！")
    private BigDecimal currentShopTicket;

    @ApiModelProperty(hidden = true)
    private BigDecimal totalAddRedEnvelops;

    @ApiModelProperty(hidden = true)
    private BigDecimal totalAddIntegral;

    @ApiModelProperty(hidden = true)
    private BigDecimal totalAddShopTicket;
}
