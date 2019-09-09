package com.chauncy.data.mapper.user;

import com.chauncy.data.domain.po.user.UmUserFavoritesPo;
import com.chauncy.data.dto.app.user.favorites.select.SelectFavoritesDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.user.favorites.FavoritesGoosVo;
import com.chauncy.data.vo.app.user.favorites.FavoritesInformationVo;
import com.chauncy.data.vo.app.user.favorites.FavoritesStoreVo;
import com.chauncy.data.vo.app.user.favorites.SearchFavoritesVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户收藏夹 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-13
 */
public interface UmUserFavoritesMapper extends IBaseMapper<UmUserFavoritesPo> {

    /**
     * 条件查询收藏商品信息
     *
     * @param selectFavoritesDto
     * @return
     */
    List<FavoritesGoosVo> searchGoodsFavorites (@Param ("selectFavoritesDto") SelectFavoritesDto selectFavoritesDto, @Param ("userId") Long userId);

    /**
     * 条件查询收藏店铺信息
     *
     * @param selectFavoritesDto
     * @return
     */
    List<FavoritesStoreVo> searchMerchantFavorites (@Param ("selectFavoritesDto") SelectFavoritesDto selectFavoritesDto, @Param ("userId") Long userId);

    /**
     * 条件查询收藏资讯信息
     *
     * @param selectFavoritesDto
     * @return
     */
    List<FavoritesInformationVo> searchInformationFavorites (@Param ("selectFavoritesDto") SelectFavoritesDto selectFavoritesDto, @Param ("userId") Long userId);
}
