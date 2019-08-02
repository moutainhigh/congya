package com.chauncy.data.domain.po.activity.group;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 活动分组管理
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_activity_group")
@ApiModel(value = "AmActivityGroupPo对象", description = "活动分组管理")
public class AmActivityGroupPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "分组名称")
    private String name;

    @ApiModelProperty(value = "分组图片")
    private String picture;

    @ApiModelProperty(value = "类型 1-满减，2-积分")
    private Integer type;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enable;

    @ApiModelProperty(value = "分组说明")
    private String description;

}
