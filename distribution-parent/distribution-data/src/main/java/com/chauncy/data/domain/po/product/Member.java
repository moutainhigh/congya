package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-20 00:29
 *
 * 会员表
 *
 */
@TableName(value = "tb_member")
@Data
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "头衔名称")
    private String actor;

    @ApiModelProperty(value = "等级名称")
    private String level;

    @ApiModelProperty(value = "购物赠送比例")
    private String purchasePresent;

    @ApiModelProperty(value = "会员等级经验值")
    private String levelExperience;

    @ApiModelProperty(value = "红包赠送比例")
    private String packetPresent;

    @ApiModelProperty(value = "会员头衔图标")
    private String actorImage;

    @ApiModelProperty(value = "备注")
    private String remark;


}
