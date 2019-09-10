package com.chauncy.data.dto.manage.good.add;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/6/25 15:25
 **/

@ApiModel(description ="添加或修改会员等级")
@Data
@Accessors(chain=true)
public class AddMemberLevelDto {

    @ApiModelProperty(value = "会员ID")
    @NotNull(groups = IUpdateGroup.class,message = "会员id不能为空！")
    private Long id;

    @ApiModelProperty(value = "头衔名称")
    @NotBlank(message = "头衔名称不能为空！")
    private String actor;

    @ApiModelProperty(value = "等级名称")
    @NotBlank(message = "等级名称不能为空！")
    private String levelName;

    @ApiModelProperty(value = "购物赠送比例")
    @NotNull(message = "购物赠送比例不能为空！")
    @Min(message = "购物赠送比例必须大于0", value = 0)
    private BigDecimal purchasePresent;

    @ApiModelProperty(value = "会员等级经验值")
    @NotNull(message = "会员等级经验值不能为空！")
    @Min(message = "会员等级经验值必须大于0", value = 0)
    private BigDecimal levelExperience;

    @ApiModelProperty(value = "红包赠送比例")
    @NotNull(message = "红包赠送比例不能为空！")
    @Min(message = "红包赠送比例必须大于0", value = 0)
    private BigDecimal packetPresent;

    @ApiModelProperty(value = "会员头衔图标")
    @NotBlank(message = "会员头衔图标不能为空！")
    private String actorImage;

    @ApiModelProperty(value = "备注")
    private String remark;
}
