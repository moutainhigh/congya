package com.chauncy.data.domain.po.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户收藏夹
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode (callSuper = false)
@Accessors (chain = true)
@TableName ("um_user_favorites")
@ApiModel (value = "UmUserFavoritesPo对象", description = "用户收藏夹")
public class UmUserFavoritesPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty (value = "收藏id")
    @TableId (value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty (value = "创建者")
    private String createBy;

    @ApiModelProperty (value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty (value = "收藏宝贝的id")
    private Long favoritesId;

    @ApiModelProperty (value = "收藏类型 商品 店铺 资讯")
    private String type;

    @ApiModelProperty (value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    private Boolean delFlag;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    private Boolean isFavorites;

}
