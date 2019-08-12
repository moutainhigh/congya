package com.chauncy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.user.UmUserFavoritesPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.favorites.add.UpdateFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.select.SelectFavoritesDto;
import com.chauncy.data.dto.app.user.favorites.update.DelFavaritesDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.user.UmUserFavoritesMapper;
import com.chauncy.data.vo.app.user.favorites.SearchFavoritesVo;
import com.chauncy.user.service.IUmUserFavoritesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PmGoodsAttributeMapper attributeMapper;

    /**
     * 更新用户收藏
     * @param updateFavoritesDto
     * @return
     */
    @Override
    public void updateFavorites (UpdateFavoritesDto updateFavoritesDto, UmUserPo userPo) {

        if (userPo==null){
            throw new ServiceException (ResultCode.FAIL,"您不是app用户！");
        }
        Map<String,Object> query = Maps.newHashMap();
        query.put("user_id",userPo.getId());
        query.put("favorites_id",updateFavoritesDto.getFavoritesId());
        List<UmUserFavoritesPo> favoritesPoList = mapper.selectByMap(query);
        KeyWordTypeEnum typeEnum = KeyWordTypeEnum.fromName (updateFavoritesDto.getType ());
        assert typeEnum != null;
        switch (typeEnum) {
            case GOODS:
                if (goodsMapper.selectById (updateFavoritesDto.getFavoritesId ())==null){
                    throw new ServiceException (ResultCode.FAIL,"不存在该商品");
                }else{
                    PmGoodsPo goodsPo = new PmGoodsPo ();
                    goodsPo = goodsMapper.selectById (updateFavoritesDto.getFavoritesId ());
                    if (updateFavoritesDto.getOperation()) {
                        if (mapper.selectByMap(query).size()!=0 && mapper.selectByMap(query)!=null){
                            throw new ServiceException(ResultCode.FAIL,"不能重复收藏该宝贝");
                        }
                        goodsPo.setCollectionNum(goodsPo.getCollectionNum() + 1);
                    }else{
                        if (mapper.selectByMap(query).size()==0 && mapper.selectByMap(query)==null){
                            throw new ServiceException(ResultCode.FAIL,"您暂时没有收藏该宝贝，不能执行取消操作");
                        }
                        goodsPo.setCollectionNum(goodsPo.getCollectionNum() - 1);
                    }
                    goodsMapper.updateById (goodsPo);
                }
                break;
            case MERCHANT:
                if (smStoreMapper.selectById (updateFavoritesDto.getFavoritesId ())==null){
                    throw new ServiceException (ResultCode.FAIL,"不存在该店铺");
                }else {
                    SmStorePo storePo = smStoreMapper.selectById (updateFavoritesDto.getFavoritesId ());
                    if (updateFavoritesDto.getOperation()) {
                        if (mapper.selectByMap(query).size()!=0 && mapper.selectByMap(query)!=null){
                            throw new ServiceException(ResultCode.FAIL,"不能重复收藏该宝贝");
                        }
                        storePo.setCollectionNum(storePo.getCollectionNum() + 1);
                    }else{
                        if (mapper.selectByMap(query).size()==0 && mapper.selectByMap(query)==null){
                            throw new ServiceException(ResultCode.FAIL,"您暂时没有收藏该宝贝，不能执行取消操作");
                        }
                        storePo.setCollectionNum(storePo.getCollectionNum() - 1);
                    }
                    smStoreMapper.updateById (storePo);
                }
                break;
            case INFORMATION:
                if (informationMapper.selectById (updateFavoritesDto.getFavoritesId ())==null){
                    throw new ServiceException (ResultCode.FAIL,"不存在该资讯");
                }else{
                    MmInformationPo informationPo = informationMapper.selectById (updateFavoritesDto.getFavoritesId ());
                    if (updateFavoritesDto.getOperation()) {
                        if (mapper.selectByMap(query).size()!=0 && mapper.selectByMap(query)!=null){
                            throw new ServiceException(ResultCode.FAIL,"不能重复收藏该宝贝");
                        }
                        informationPo.setCollectionNum(informationPo.getCollectionNum() + 1);
                    }else {
                        if (mapper.selectByMap(query).size()==0 && mapper.selectByMap(query)==null){
                            throw new ServiceException(ResultCode.FAIL,"您暂时没有收藏该宝贝，不能执行取消操作");
                        }
                        informationPo.setCollectionNum(informationPo.getCollectionNum() - 1);
                    }
                    informationMapper.updateById(informationPo);
                }
                break;
            case BRAND:
                if (attributeMapper.selectById (updateFavoritesDto.getFavoritesId ())==null){
                    throw new ServiceException (ResultCode.FAIL,"不存在该品牌");
                }else{
                    PmGoodsAttributePo brandVo = attributeMapper.selectById (updateFavoritesDto.getFavoritesId ());
                    if (updateFavoritesDto.getOperation()) {
                        if (mapper.selectByMap(query).size()!=0 && mapper.selectByMap(query)!=null){
                            throw new ServiceException(ResultCode.FAIL,"不能重复收藏该宝贝");
                        }
                        brandVo.setCollectionNum(brandVo.getCollectionNum() + 1);
                    }else {
                        if (ListUtil.isListNullAndEmpty(favoritesPoList)){
                            throw new ServiceException(ResultCode.FAIL,"您暂时没有收藏该宝贝，不能执行取消操作");
                        }
                        brandVo.setCollectionNum(brandVo.getCollectionNum() - 1);
                    }
                    attributeMapper.updateById(brandVo);
                }
                break;
        }
        if (updateFavoritesDto.getOperation()) {
            UmUserFavoritesPo favoritesPo = new UmUserFavoritesPo();
            BeanUtils.copyProperties(updateFavoritesDto, favoritesPo);
            favoritesPo.setId(null);
            favoritesPo.setCreateBy(userPo.getTrueName());
            favoritesPo.setUserId(userPo.getId());
            favoritesPo.setUpdateTime(LocalDateTime.now());
            mapper.insert(favoritesPo);
        }else{
            QueryWrapper<UmUserFavoritesPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(obj->obj.eq(UmUserFavoritesPo::getFavoritesId,updateFavoritesDto.getFavoritesId())
                    .eq(UmUserFavoritesPo::getUserId,userPo.getId()));
            mapper.delete(queryWrapper);
        }
    }

    /**
     * 批量删除收藏
     * @param delFavaritesDto
     * @return
     */
    @Override
    public void delFavoritesByIds (DelFavaritesDto delFavaritesDto) {

        if (delFavaritesDto.getIds ().size()==0 && delFavaritesDto.getIds ()==null){
            throw new ServiceException (ResultCode.FAIL,"请选择宝贝");
        }
        delFavaritesDto.getIds ().forEach (a->{
            Long favoritesId = mapper.selectById(a).getFavoritesId();
            if (mapper.selectById (favoritesId)==null){
                throw new ServiceException (ResultCode.FAIL,"出错了，宝贝不存在");
            }
            KeyWordTypeEnum typeEnum = KeyWordTypeEnum.fromName (delFavaritesDto.getType ());
            assert typeEnum != null;
            switch (typeEnum) {
                case GOODS:
                    if (goodsMapper.selectById (favoritesId)==null){
                        throw new ServiceException (ResultCode.FAIL,"不存在该商品");
                    }else{
                        PmGoodsPo goodsPo = new PmGoodsPo ();
                        goodsPo = goodsMapper.selectById (favoritesId);
                        goodsPo.setCollectionNum (goodsPo.getCollectionNum ()-1);
                        goodsMapper.updateById (goodsPo);
                    }
                    break;
                case MERCHANT:
                    if (smStoreMapper.selectById (favoritesId)==null){
                        throw new ServiceException (ResultCode.FAIL,"不存在该店铺");
                    }else {
                        SmStorePo storePo = smStoreMapper.selectById (favoritesId);
                        storePo.setCollectionNum(storePo.getCollectionNum()-1);
                        smStoreMapper.updateById (storePo);
                    }
                    break;
                case INFORMATION:
                    if (informationMapper.selectById (favoritesId)==null){
                        throw new ServiceException (ResultCode.FAIL,"不存在该资讯");
                    }else{
                        MmInformationPo informationPo = informationMapper.selectById (favoritesId);
                        informationPo.setCollectionNum (informationPo.getCollectionNum ()-1);
                        informationMapper.updateById(informationPo);
                    }
                    break;
            }

        });
        mapper.deleteBatchIds (delFavaritesDto.getIds ());
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
