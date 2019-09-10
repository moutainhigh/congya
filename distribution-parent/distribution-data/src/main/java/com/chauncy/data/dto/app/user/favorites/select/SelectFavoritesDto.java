package com.chauncy.data.dto.app.user.favorites.select;

import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/13  16:26
 * @Version 1.0
 */
@Data
@ApiModel(description = "条件查询收藏夹")
@Accessors(chain = true)
public class SelectFavoritesDto {

    @ApiModelProperty ("收藏夹类型 收藏类型 商品 店铺 资讯")
    @NotNull (message = "搜索收藏类型不能为空")
    @EnumConstraint (target = KeyWordTypeEnum.class)
    private String type;

    @ApiModelProperty(value = "类目id",hidden = false)
    private Long categoryId;

    @ApiModelProperty(value = "模糊查询名称")
    private String name;

    @Min (1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
