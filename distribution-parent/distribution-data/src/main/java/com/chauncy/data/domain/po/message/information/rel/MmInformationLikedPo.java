package com.chauncy.data.domain.po.message.information.rel;

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
 * 用户资讯点赞表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_information_liked")
@ApiModel(value = "MmInformationLikedPo对象", description = "用户资讯点赞表")
public class MmInformationLikedPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "资讯id")
    private Long infoId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "删除标志")
    @TableLogic
    private Boolean delFlag;


}
