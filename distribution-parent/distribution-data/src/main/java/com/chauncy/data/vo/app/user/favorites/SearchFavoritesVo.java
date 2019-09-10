package com.chauncy.data.vo.app.user.favorites;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/13  16:35
 * @Version 1.0
 *
 * 条件查询收藏夹Vo
 */
@Data
@ApiModel (description = "条件查询收藏商品Vo")
public class SearchFavoritesVo {

    @ApiModelProperty("收藏商品信息")
    private PageInfo<FavoritesGoosVo> favoritesGoosVo;

    @ApiModelProperty("收藏资讯信息")
    private PageInfo<FavoritesInformationVo> favoritesInormationVo;

    @ApiModelProperty("收藏店铺信息")
    private PageInfo<FavoritesStoreVo> favoritesStoreVo;

}
