package com.chauncy.user.service;

import com.chauncy.data.domain.po.user.UmUserFavoritesPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.favorites.add.AddFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.select.SelectFavoritesDto;
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
     * 添加用户收藏信息
     * @param addFavoritesDto
     * @return
     */
    void addFavorites (AddFavoritesDto addFavoritesDto, UmUserPo userPo);

    /**
     * 批量删除收藏
     * @param ids
     * @return
     */
    void delFavoritesByIds (Long[] ids);

    /**
     * 条件查询收藏信息
     *
     * @param selectFavoritesDto
     * @return
     */
    PageInfo<SearchFavoritesVo> searchFavorites (SelectFavoritesDto selectFavoritesDto, UmUserPo userPo);
}
