package com.chauncy.user.service;

import com.chauncy.data.domain.po.user.UmUserFavoritesPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.favorites.add.UpdateFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.select.SelectFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.update.DelFavaritesDto;
import com.chauncy.data.vo.app.user.favorites.SearchFavoritesVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 用户收藏夹 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-13
 */
public interface IUmUserFavoritesService extends Service<UmUserFavoritesPo> {

    /**
     * 更新用户收藏信息
     * @param updateFavoritesDto
     * @return
     */
    Integer updateFavorites (UpdateFavoritesDto updateFavoritesDto, UmUserPo userPo);

    /**
     * 批量删除收藏
     * @param delFavaritesDto
     * @return
     */
    void delFavoritesByIds (DelFavaritesDto delFavaritesDto);

    /**
     * 条件查询收藏信息
     *
     * @param selectFavoritesDto
     * @return
     */
    SearchFavoritesVo searchFavorites (SelectFavoritesDto selectFavoritesDto, UmUserPo userPo);
}
