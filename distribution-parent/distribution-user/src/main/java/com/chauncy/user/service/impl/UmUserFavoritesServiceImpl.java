package com.chauncy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.GuavaUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.rel.SmStoreRelLabelPo;
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
import com.chauncy.data.mapper.store.label.SmStoreLabelMapper;
import com.chauncy.data.mapper.store.rel.SmStoreRelLabelMapper;
import com.chauncy.data.mapper.user.UmUserFavoritesMapper;
import com.chauncy.data.vo.app.user.favorites.FavoritesGoosVo;
import com.chauncy.data.vo.app.user.favorites.FavoritesInformationVo;
import com.chauncy.data.vo.app.user.favorites.FavoritesStoreVo;
import com.chauncy.data.vo.app.user.favorites.SearchFavoritesVo;
import com.chauncy.user.service.IUmUserFavoritesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户收藏夹 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UmUserFavoritesServiceImpl extends AbstractService<UmUserFavoritesMapper, UmUserFavoritesPo> implements IUmUserFavoritesService {

    @Autowired
    private UmUserFavoritesMapper mapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private SmStoreLabelMapper storeLabelMapper;

    @Autowired
    private SmStoreRelLabelMapper storeRelLabelMapper;

    @Autowired
    private MmInformationMapper informationMapper;

    @Autowired
    private PmGoodsSkuMapper goodsSkuMapper;

    @Autowired
    private PmGoodsAttributeMapper attributeMapper;

    /**
     * 更新用户收藏
     *
     * @param updateFavoritesDto
     * @return
     */
    @Override
    public Integer updateFavorites(UpdateFavoritesDto updateFavoritesDto, UmUserPo userPo) {
        if (userPo == null) {
            throw new ServiceException(ResultCode.FAIL, "您不是app用户！");
        }

        //查询是否收藏过,并发情况，锁住行
        UmUserFavoritesPo userFavoritesPo = mapper.selectOne(new QueryWrapper<UmUserFavoritesPo>().lambda()
                .eq(UmUserFavoritesPo::getUserId, userPo.getId())
                .eq(UmUserFavoritesPo::getFavoritesId, updateFavoritesDto.getFavoritesId())
                .last("for update"));

        Integer num = 0;

        KeyWordTypeEnum typeEnum = KeyWordTypeEnum.fromName(updateFavoritesDto.getType());
        assert typeEnum != null;
        switch (typeEnum) {
            case GOODS:
                //判断商品是否存在
                if (goodsMapper.selectById(updateFavoritesDto.getFavoritesId()) == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在该商品，请检查");
                }
//                //并发情况，锁住行
//                PmGoodsPo goodsPo = goodsMapper.selectOne(new QueryWrapper<PmGoodsPo>().lambda()
//                        .eq(PmGoodsPo::getId,updateFavoritesDto.getFavoritesId()).last("for update"));

                if (userFavoritesPo == null) { //未收藏过

                    userFavoritesPo = new UmUserFavoritesPo();
                    userFavoritesPo.setCreateBy(userPo.getTrueName()).setUserId(userPo.getId())
                            .setId(null).setFavoritesId(updateFavoritesDto.getFavoritesId())
                            .setType(typeEnum.getName()).setIsFavorites(true);
                    mapper.insert(userFavoritesPo);
                    //不用updateById  update a=a+1
                    goodsMapper.addFavorites(updateFavoritesDto.getFavoritesId());

                } else if (userFavoritesPo.getIsFavorites()) { //取消收藏

                    userFavoritesPo.setIsFavorites(false);
                    mapper.updateById(userFavoritesPo);
                    //不用updateById  update a=a-1
                    goodsMapper.delFavorites(updateFavoritesDto.getFavoritesId());

                } else if (!userFavoritesPo.getIsFavorites()) { //收藏过且取消收藏再次收藏
                    userFavoritesPo.setIsFavorites(true);
                    mapper.updateById(userFavoritesPo);
                    //不用updateById  update a=a+1
                    goodsMapper.addFavorites(updateFavoritesDto.getFavoritesId());

                }
                num = goodsMapper.selectById(updateFavoritesDto.getFavoritesId()).getCollectionNum();
                break;
            case MERCHANT:
                if (smStoreMapper.selectById(updateFavoritesDto.getFavoritesId()) == null) {
                    throw new ServiceException(ResultCode.FAIL, "不存在该店铺");
                } else {

                    if (userFavoritesPo == null) { //未收藏过

                        userFavoritesPo = new UmUserFavoritesPo();
                        userFavoritesPo.setCreateBy(userPo.getTrueName()).setUserId(userPo.getId())
                                .setId(null).setFavoritesId(updateFavoritesDto.getFavoritesId())
                                .setType(typeEnum.getName()).setIsFavorites(true);
                        mapper.insert(userFavoritesPo);
                        //不用updateById  update a=a+1
                        smStoreMapper.addFavorites(updateFavoritesDto.getFavoritesId());

                    } else if (userFavoritesPo.getIsFavorites()) { //取消收藏

                        userFavoritesPo.setIsFavorites(false);
                        mapper.updateById(userFavoritesPo);
                        //不用updateById  update a=a-1
                        smStoreMapper.delFavorites(updateFavoritesDto.getFavoritesId());
                    } else if (!userFavoritesPo.getIsFavorites()) { //收藏过且取消收藏再次收藏
                        userFavoritesPo.setIsFavorites(true);
                        mapper.updateById(userFavoritesPo);
                        //不用updateById  update a=a+1
                        smStoreMapper.addFavorites(updateFavoritesDto.getFavoritesId());
                    }
                }
                num = smStoreMapper.selectById(updateFavoritesDto.getFavoritesId()).getCollectionNum();
                break;
            case INFORMATION:
                if (informationMapper.selectById(updateFavoritesDto.getFavoritesId()) == null) {
                    throw new ServiceException(ResultCode.FAIL, "不存在该资讯");
                } else {
                    if (userFavoritesPo == null) { //未收藏过

                        userFavoritesPo = new UmUserFavoritesPo();
                        userFavoritesPo.setCreateBy(userPo.getTrueName()).setUserId(userPo.getId())
                                .setId(null).setFavoritesId(updateFavoritesDto.getFavoritesId())
                                .setType(typeEnum.getName()).setIsFavorites(true);
                        mapper.insert(userFavoritesPo);
                        //不用updateById  update a=a+1
                        informationMapper.addFavorites(updateFavoritesDto.getFavoritesId());

                    } else if (userFavoritesPo.getIsFavorites()) { //取消收藏

                        userFavoritesPo.setIsFavorites(false);
                        mapper.updateById(userFavoritesPo);
                        //不用updateById  update a=a-1
                        informationMapper.delFavorites(updateFavoritesDto.getFavoritesId());
                    } else if (!userFavoritesPo.getIsFavorites()) { //收藏过且取消收藏再次收藏
                        userFavoritesPo.setIsFavorites(true);
                        mapper.updateById(userFavoritesPo);
                        //不用updateById  update a=a+1
                        informationMapper.addFavorites(updateFavoritesDto.getFavoritesId());
                    }
                }
                num = informationMapper.selectById(updateFavoritesDto.getFavoritesId()).getCollectionNum();
                break;
            case BRAND:
                if (attributeMapper.selectById(updateFavoritesDto.getFavoritesId()) == null) {
                    throw new ServiceException(ResultCode.FAIL, "不存在该品牌");
                } else {
                    if (userFavoritesPo == null) { //未收藏过

                        userFavoritesPo = new UmUserFavoritesPo();
                        userFavoritesPo.setCreateBy(userPo.getTrueName()).setUserId(userPo.getId())
                                .setId(null).setFavoritesId(updateFavoritesDto.getFavoritesId())
                                .setType(typeEnum.getName()).setIsFavorites(true);
                        mapper.insert(userFavoritesPo);
                        //不用updateById  update a=a+1
                        attributeMapper.addFavorites(updateFavoritesDto.getFavoritesId());

                    } else if (userFavoritesPo.getIsFavorites()) { //取消收藏

                        userFavoritesPo.setIsFavorites(false);
                        mapper.updateById(userFavoritesPo);
                        //不用updateById  update a=a-1
                        attributeMapper.delFavorites(updateFavoritesDto.getFavoritesId());
                    } else if (!userFavoritesPo.getIsFavorites()) { //收藏过且取消收藏再次收藏
                        userFavoritesPo.setIsFavorites(true);
                        mapper.updateById(userFavoritesPo);
                        //不用updateById  update a=a+1
                        attributeMapper.addFavorites(updateFavoritesDto.getFavoritesId());
                    }
                    num = attributeMapper.selectById(updateFavoritesDto.getFavoritesId()).getCollectionNum();
                    break;
                }
        }
        if (num < 0){
            num = 0;
        }
        return num;
    }

    /**
     * 批量删除收藏
     *
     * @param delFavaritesDto
     * @return
     */
    @Override
    public void delFavoritesByIds(DelFavaritesDto delFavaritesDto) {

        if (delFavaritesDto.getIds().size() == 0 && delFavaritesDto.getIds() == null) {
            throw new ServiceException(ResultCode.FAIL, "请选择宝贝");
        }
        delFavaritesDto.getIds().forEach(a -> {
            Long favoritesId = mapper.selectById(a).getFavoritesId();
            if (mapper.selectById(favoritesId) == null) {
                throw new ServiceException(ResultCode.FAIL, "出错了，宝贝不存在");
            }
            KeyWordTypeEnum typeEnum = KeyWordTypeEnum.fromName(delFavaritesDto.getType());
            assert typeEnum != null;
            switch (typeEnum) {
                case GOODS:
                    if (goodsMapper.selectById(favoritesId) == null) {
                        throw new ServiceException(ResultCode.FAIL, "不存在该商品");
                    } else {
                        PmGoodsPo goodsPo = new PmGoodsPo();
                        goodsPo = goodsMapper.selectById(favoritesId);
                        goodsPo.setCollectionNum(goodsPo.getCollectionNum() - 1);
                        goodsMapper.updateById(goodsPo);
                    }
                    break;
                case MERCHANT:
                    if (smStoreMapper.selectById(favoritesId) == null) {
                        throw new ServiceException(ResultCode.FAIL, "不存在该店铺");
                    } else {
                        SmStorePo storePo = smStoreMapper.selectById(favoritesId);
                        storePo.setCollectionNum(storePo.getCollectionNum() - 1);
                        smStoreMapper.updateById(storePo);
                    }
                    break;
                case INFORMATION:
                    if (informationMapper.selectById(favoritesId) == null) {
                        throw new ServiceException(ResultCode.FAIL, "不存在该资讯");
                    } else {
                        MmInformationPo informationPo = informationMapper.selectById(favoritesId);
                        informationPo.setCollectionNum(informationPo.getCollectionNum() - 1);
                        informationMapper.updateById(informationPo);
                    }
                    break;
            }

        });
        mapper.deleteBatchIds(delFavaritesDto.getIds());
    }

    /**
     * 条件查询收藏信息
     *
     * @param selectFavoritesDto
     * @return
     */
    @Override
    public SearchFavoritesVo searchFavorites(SelectFavoritesDto selectFavoritesDto, UmUserPo userPo) {
        if (userPo == null) {
            throw new ServiceException(ResultCode.FAIL, "您不是App用户！");
        }
        Integer pageNo = selectFavoritesDto.getPageNo() == null ? defaultPageNo : selectFavoritesDto.getPageNo();
        Integer pageSize = selectFavoritesDto.getPageSize() == null ? defaultPageSize : selectFavoritesDto.getPageSize();

        SearchFavoritesVo searchFavoritesVo = new SearchFavoritesVo();

        KeyWordTypeEnum typeEnum = KeyWordTypeEnum.fromName(selectFavoritesDto.getType());
        assert typeEnum != null;
        switch (typeEnum) {
            case GOODS:
                PageInfo<FavoritesGoosVo> favoritesGoosVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchGoodsFavorites(selectFavoritesDto, userPo.getId()));
                if (ListUtil.isListNullAndEmpty(favoritesGoosVoPageInfo.getList())) {
                    favoritesGoosVoPageInfo = new PageInfo<>();

                } else {
                    favoritesGoosVoPageInfo.getList().forEach(a -> {
                        String sellPrice = "";
                        BigDecimal lowestSellPrice = goodsSkuMapper.getLowestPrice(a.getGoodsId());
                        BigDecimal highestSellPrice = goodsSkuMapper.getHighestPrice(a.getGoodsId());
                        if (lowestSellPrice == null && highestSellPrice == null) {
                            a.setPrice(null);
                        } else if (lowestSellPrice.equals(highestSellPrice)) {
                            sellPrice = lowestSellPrice.toString();
                        } else {
                            sellPrice = lowestSellPrice.toString() + "-" + highestSellPrice;
                        }
                        a.setPrice(lowestSellPrice);
                    });
                }
                searchFavoritesVo.setFavoritesGoosVo(favoritesGoosVoPageInfo);

                break;
            case MERCHANT:
                PageInfo<FavoritesStoreVo> favoritesStoreVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchMerchantFavorites(selectFavoritesDto, userPo.getId()));

                if (ListUtil.isListNullAndEmpty(favoritesStoreVoPageInfo.getList())) {
                    searchFavoritesVo = null;
                } else {
                    favoritesStoreVoPageInfo.getList().forEach(a -> {
                        //获取店铺标签
                        List<Long> labelIds = storeRelLabelMapper.selectList(new QueryWrapper<SmStoreRelLabelPo>().lambda()
                                .eq(SmStoreRelLabelPo::getStoreId, a.getStoreId())).stream().map(b -> b.getStoreLabelId()).collect(Collectors.toList());
                        if (!ListUtil.isListNullAndEmpty(labelIds)) {
                            List<String> labelNames = storeLabelMapper.selectBatchIds(labelIds).stream().map(c->c.getName()).collect(Collectors.toList());
                            a.setStoreLabels(labelNames);
                        }
                    });
                    searchFavoritesVo.setFavoritesStoreVo(favoritesStoreVoPageInfo);

                }
                break;

            case INFORMATION:
                PageInfo<FavoritesInformationVo>  favoritesInormationVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchInformationFavorites(selectFavoritesDto, userPo.getId()));
                if (ListUtil.isListNullAndEmpty(favoritesInormationVoPageInfo.getList())) {
                    favoritesInormationVoPageInfo = new PageInfo<>();
                }else {
                    favoritesInormationVoPageInfo.getList().forEach(a->{
                        if (a.getPicture() != null && a.getPicture() != "") {
                            List<String> pictures = GuavaUtil.StringToList(a.getPicture(), String.class, ",");
                            a.setPicture(pictures.get(0));
                        }
                    });
                }
                searchFavoritesVo.setFavoritesInormationVo(favoritesInormationVoPageInfo);
            break;
        }
        return searchFavoritesVo;
    }
}
