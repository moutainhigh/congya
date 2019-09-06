package com.chauncy.web.api.app.user;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.favorites.add.UpdateFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.select.SelectFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.update.DelFavaritesDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.user.favorites.SearchFavoritesVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.user.service.IUmUserFavoritesService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户收藏夹 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-13
 */
@RestController
@RequestMapping ("/app/user/favorites")
@Api (tags = "app_用户收藏夹")
public class UmUserFavoritesApi {

    @Autowired
    private IUmUserFavoritesService service;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 更新用户收藏信息
     * @param updateFavoritesDto
     * @return
     */
    @PostMapping("/updateFavorites")
    @ApiOperation ("更新用户收藏信息，收藏或取消收藏")
    public JsonViewData<Integer> updateFavorites(@RequestBody @ApiParam(required = true,name = "updateFavoritesDto",value = "用户添加或取消收藏")
                                     @Validated UpdateFavoritesDto updateFavoritesDto){

        UmUserPo userPo = securityUtil.getAppCurrUser ();
        return new JsonViewData (service.updateFavorites(updateFavoritesDto,userPo));
    }

    /**
     * 批量删除收藏
     * @param delFavaritesDto
     * @return
     */
    @PostMapping("/delFavoritesByIds")
    @ApiOperation ("批量删除收藏")
    public JsonViewData delFavoritesByIds(@RequestBody @ApiParam(required = true,name = "ids",value="收藏ids")
                                          @Validated DelFavaritesDto delFavaritesDto){
        service.delFavoritesByIds(delFavaritesDto);
        return new JsonViewData (ResultCode.SUCCESS);
    }

    /**
     * 条件查询收藏信息
     *
     * @param selectFavoritesDto
     * @return
     */
    @ApiOperation ("条件查询收藏信息")
    @PostMapping("/searchFavorites")
    public JsonViewData<PageInfo<SearchFavoritesVo>> searchFavorites(@RequestBody @ApiParam(required = true,name = "selectFavoritesDto",value="条件查询收藏商品信息")
                                                                     @Validated SelectFavoritesDto selectFavoritesDto){

        UmUserPo userPo = securityUtil.getAppCurrUser ();
        return new JsonViewData(service.searchFavorites(selectFavoritesDto,userPo));
    }
}
