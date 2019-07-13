package com.chauncy.user.service.impl;

import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.user.UmUserFavoritesPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.favorites.add.AddFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.select.SelectFavoritesDto;
import com.chauncy.data.dto.supplier.good.add.AddStandardToGoodDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.user.UmUserFavoritesMapper;
import com.chauncy.data.vo.app.user.favorites.SearchFavoritesVo;
import com.chauncy.user.service.IUmUserFavoritesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户收藏夹 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-13
 */
@Service
@Transactional (rollbackFor = Exception.class)
public class UmUserFavoritesServiceImpl extends AbstractService<UmUserFavoritesMapper, UmUserFavoritesPo> implements IUmUserFavoritesService {

    @Autowired
    private UmUserFavoritesMapper mapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private MmInformationMapper informationMapper;

    @Autowired
    private PmGoodsSkuMapper goodsSkuMapper;

    /**
     * 添加用户收藏信息
     * @param addFavoritesDto
     * @return
     */
    @Override
    public void addFavorites (AddFavoritesDto addFavoritesDto,UmUserPo userPo) {

        if (userPo==null){
            throw new ServiceException (ResultCode.FAIL,"您不是app用户！");
        }
        KeyWordTypeEnum typeEnum = KeyWordTypeEnum.fromName (addFavoritesDto.getType ());
        assert typeEnum != null;
        switch (typeEnum) {
            case GOODS:
                if (goodsMapper.selectById (addFavoritesDto.getFavoritesId ())==null){
                    throw new ServiceException (ResultCode.FAIL,"不存在该商品");
                }else{
                    PmGoodsPo goodsPo = new PmGoodsPo ();
                    goodsPo = goodsMapper.selectById (addFavoritesDto.getFavoritesId ());
                    goodsPo.setCollectionNum (goodsPo.getCollectionNum ()+1);
                    goodsMapper.updateById (goodsPo);
                }
                break;
            case MERCHANT:
                if (smStoreMapper.selectById (addFavoritesDto.getFavoritesId ())==null){
                    throw new ServiceException (ResultCode.FAIL,"不存在该店铺");
                }else {
                    SmStorePo storePo = smStoreMapper.selectById (addFavoritesDto.getFavoritesId ());
//                    storePo.setCollectionNum(storePo.getCollectionNum()+1);
                    smStoreMapper.updateById (storePo);
                }
                break;
            case INFORMATION:
                if (informationMapper.selectById (addFavoritesDto.getFavoritesId ())==null){
                    throw new ServiceException (ResultCode.FAIL,"不存在该资讯");
                }else{
                    MmInformationPo informationPo = informationMapper.selectById (addFavoritesDto.getFavoritesId ());
                    informationPo.setCollectionNum (informationPo.getCommentNum ()+1);
                    informationMapper.updateById(informationPo);
                }
                break;
        }
        UmUserFavoritesPo favoritesPo = new UmUserFavoritesPo ();
        BeanUtils.copyProperties (addFavoritesDto, favoritesPo);
        favoritesPo.setId (null);
        favoritesPo.setCreateBy(userPo.getTrueName ());
        favoritesPo.setUserId (userPo.getId ());
        favoritesPo.setUpdateTime (LocalDateTime.now ());
        mapper.insert (favoritesPo);
    }

    /**
     * 批量删除收藏
     * @param ids
     * @return
     */
    @Override
    public void delFavoritesByIds (Long[] ids) {

        List<Long> idList = Arrays.asList (ids);
        if (idList.size()==0 && idList==null){
            throw new ServiceException (ResultCode.FAIL,"请选择宝贝");
        }
        idList.forEach (a->{
            if (mapper.selectById (a)==null){
                throw new ServiceException (ResultCode.FAIL,"出错了，宝贝不存在");
            }
        });
        mapper.deleteBatchIds (idList);
    }

    /**
     * 条件查询收藏信息
     *
     * @param selectFavoritesDto
     * @return
     */
    @Override
    public PageInfo<SearchFavoritesVo> searchFavorites (SelectFavoritesDto selectFavoritesDto, UmUserPo userPo) {
        if (userPo == null){
            throw new ServiceException (ResultCode.FAIL,"您不是App用户！");
        }
        Integer pageNo = selectFavoritesDto.getPageNo() == null ? defaultPageNo : selectFavoritesDto.getPageNo();
        Integer pageSize = selectFavoritesDto.getPageSize() == null ? defaultPageSize : selectFavoritesDto.getPageSize();
        PageInfo<SearchFavoritesVo> searchFavoritesVoPageInfo;
        KeyWordTypeEnum typeEnum = KeyWordTypeEnum.fromName (selectFavoritesDto.getType ());
        assert typeEnum != null;
        switch (typeEnum) {
            case GOODS:
                searchFavoritesVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchGoodsFavorites(selectFavoritesDto,userPo.getId ()));
                if (searchFavoritesVoPageInfo.getList ().size ()==0 || searchFavoritesVoPageInfo.getList ()==null){
                    return new PageInfo<> ();
                }
                searchFavoritesVoPageInfo.getList().forEach (a->{
                    String sellPrice ="";
                    BigDecimal lowestSellPrice = goodsSkuMapper.getLowestPrice (a.getGoodsId ());
                    BigDecimal highestSellPrice = goodsSkuMapper.getHighestPrice (a.getGoodsId ());
                    if (lowestSellPrice==null && highestSellPrice == null){
                        a.setPrice (null);
                    }else if (lowestSellPrice.equals (highestSellPrice)){
                        sellPrice=lowestSellPrice.toString ();
                    }else{
                        sellPrice = lowestSellPrice.toString ()+"-"+highestSellPrice;
                    }
                    a.setPrice (sellPrice);
                });
                return searchFavoritesVoPageInfo;
            case MERCHANT:
                searchFavoritesVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchMerchantFavorites(selectFavoritesDto,userPo.getId ()));
                if (searchFavoritesVoPageInfo.getList ().size ()==0 || searchFavoritesVoPageInfo.getList ()==null){
                    return new PageInfo<> ();
                }
                return searchFavoritesVoPageInfo;
            case INFORMATION:
                searchFavoritesVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchInformationFavorites(selectFavoritesDto,userPo.getId ()));
                if (searchFavoritesVoPageInfo.getList ().size ()==0 || searchFavoritesVoPageInfo.getList ()==null){
                    return new PageInfo<> ();
                }
                return searchFavoritesVoPageInfo;
        }
        return new PageInfo<> ();
    }
}
