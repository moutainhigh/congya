package com.chauncy.data.domain.po.message.interact;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台信息管理
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_interact_push")
@ApiModel(value = "MmInteractPushPo对象", description = "平台信息管理")
public class MmInteractPushPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推送的信息ID")
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

    @ApiModelProperty(value = "推送方式 1-通知栏推送 2-app内消息中心推送")
    private Integer type;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "图文详情")
    @TableField("detailHtml")
    private String detailHtml;

    @ApiModelProperty(value = "推送对象类型")
    private Integer pushType;


}
