package com.chauncy.data.dto.app.user.favorites.add;

import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/13  15:30
 * @Version 1.0
 *
 * 用户添加收藏信息
 */
@Data
@ApiModel(description = "用户添加收藏信息")
@Accessors(chain = true)
public class AddFavoritesDto {

    @ApiModelProperty (value = "收藏宝贝的id")
    @NeedExistConstraint (tableName = "um_user_favorites",isNeedExists = false,message = "已收藏")
    private Long favoritesId;

    @ApiModelProperty (value = "收藏类型 商品 店铺 资讯")
    @EnumConstraint (target = KeyWordTypeEnum.class)
    private String type;

    /*@ApiModelProperty (value = "用户id")
    @NeedExistConstraint (tableName = "um_user")
    @NotNull(message = "用户id不能为空")
    private Long userId;*/
}
