package com.chauncy.data.dto.app.user.favorites.update;

import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/13  23:47
 * @Version 1.0
 */
@Data
@ApiModel(description = "批量删除收藏信息")
@Accessors(chain = true)
public class DelFavaritesDto {

    @ApiModelProperty("收藏id")
    private List<Long> ids;

    @ApiModelProperty (value = "收藏类型 商品 店铺 资讯")
    @EnumConstraint (target = KeyWordTypeEnum.class)
    private String type;
}
