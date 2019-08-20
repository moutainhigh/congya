package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.advice.*;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.mapper.message.advice.*;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.chauncy.data.vo.manage.message.advice.shuffling.FindShufflingVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreTabsVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.TabInfosVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.*;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 广告基本信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceServiceImpl extends AbstractService<MmAdviceMapper, MmAdvicePo> implements IMmAdviceService {

    @Autowired
    private MmAdviceMapper mapper;

    @Autowired
    private MmAdviceRelAssociaitonMapper relAssociaitonMapper;

    @Autowired
    private MmAdviceRelTabAssociationMapper relTabAssociationMapper;

    @Autowired
    private MmAdviceRelTabThingsMapper relTabThingsMapper;

    @Autowired
    private MmAdviceTabMapper tabMapper;

    @Autowired
    private MmAdviceRelTabMapper relTabMapper;

    @Autowired
    private MmAdviceRelShufflingMapper relShufflingMapper;

    @Autowired
    private MmInformationMapper informationMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 获取广告位置
     *
     * @return
     */
    @Override
    public Object findAdviceLocation() {

        //存储广告位置
        Map<String, String> locations = Maps.newHashMap();
        List<AdviceLocationEnum> adviceLocationEnumList = Arrays.stream(AdviceLocationEnum.values()).collect(Collectors.toList());
        adviceLocationEnumList.forEach(a -> {
            locations.put(a.name(), a.getName());
        });
        return locations;
    }

    /**
     * 条件分页获取广告信息及其对应的详情
     *
     * @param searchAdvicesDto
     * @return
     */
    @Override
    public PageInfo<SearchAdvicesVo> searchAdvices(SearchAdvicesDto searchAdvicesDto) {

        Integer PageNo = searchAdvicesDto.getPageNo() == null ? defaultPageNo : searchAdvicesDto.getPageNo();
        Integer pageSize = searchAdvicesDto.getPageSize() == null ? defaultPageSize : searchAdvicesDto.getPageSize();
        PageInfo<SearchAdvicesVo> advicesVoPageInfo = PageHelper.startPage(PageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchAdvices(searchAdvicesDto));
        advicesVoPageInfo.getList().forEach(a -> {
            AdviceLocationEnum adviceLocationEnum = AdviceLocationEnum.fromEnumName(a.getLocation());
            switch (adviceLocationEnum) {

                /******************* Tab start ****************/
                //首页有店+店铺分类详情
                case STORE_DETAIL:
                    //获取该广告分类下的店铺分类信息以及该广告与店铺分类关联的ID
                    List<StoreTabsVo> storeClassificationList = relAssociaitonMapper.findStoreClassificationList(a.getAdviceId());
                    storeClassificationList.forEach(b -> {
                        //获取该广告的该店铺下的选项卡
                        List<TabInfosVo> tabInfosVoList = relTabAssociationMapper.findTabInfos(b.getAdviceAssociationId());
                        tabInfosVoList.forEach(c -> {
                            //获取该广告的该店铺下的选项卡下的关联店铺
                            PageInfo<StoreVo> storeList = PageHelper.startPage(defaultPageNo, defaultPageSize).
                                    doSelectPageInfo(() -> relTabThingsMapper.findStoreList(c.getTabId()));

                            c.setStoreList(storeList);
                        });
                        b.setTabInfos(tabInfosVoList);
                    });
                    a.setDetail(storeClassificationList);
                    break;

                //首页有品------》》》品牌
                case SHOUYE_YOUPIN:
                    //获取该广告下的所有选项卡信息
                    List<BrandTabInfosVo> brandTabInfosVos = relTabMapper.findBrandTabInfosVos(a.getAdviceId());
                    brandTabInfosVos.forEach(b -> {
                        //分页获取品牌选项卡关联的品牌
                        PageInfo<BrandVo> brandList = PageHelper.startPage(defaultPageNo, defaultPageSize)
                                .doSelectPageInfo(() -> relTabThingsMapper.findBrandList(b.getTabId()));
                        brandList.getList().forEach(c -> {
                            Long relTabBrandId = relTabThingsMapper.selectOne(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, b.getTabId())
                                    .eq(MmAdviceRelTabThingsPo::getAssociationId, c.getBrandId())).getId();
                            List<BrandShufflingVo> brandShufflingVos = relShufflingMapper.findShufflingList(relTabBrandId);
                            brandShufflingVos.forEach(d -> {
                                AdviceTypeEnum adviceTypeEnum = d.getAdviceType();
                                switch (adviceTypeEnum) {
                                    case HTML_DETAIL:
                                        break;
                                    case INFORMATION:
                                        d.setDetailName(informationMapper.selectById(d.getDetailId()).getTitle());
                                        break;
                                    case STROE:
                                        d.setDetailName(storeMapper.selectById(d.getDetailId()).getName());
                                        break;
                                    case GOODS:
                                        d.setDetailName(goodsMapper.selectById(d.getDetailId()).getName());
                                        break;
                                }
                            });
                            c.setBrandShufflingVos(brandShufflingVos);
                        });
                        b.setBrandList(brandList);

                    });
                    a.setDetail(brandTabInfosVos);
                    break;

                //特卖、主题、优选-----》》》商品
                case SHOUYE_ZHUTI:
                case SALE:
                case YOUXUAN:
                    //获取该选项卡信息
                    List<GoodsTabInfosVo> goodsTabInfosVos = relTabMapper.findGoodsTabInfosVos(a.getAdviceId());
                    goodsTabInfosVos.forEach(b -> {
                        //分页获取品牌选项卡关联的商品
                        PageInfo<GoodsVo> goodsList = PageHelper.startPage(defaultPageNo, defaultPageSize)
                                .doSelectPageInfo(() -> relTabThingsMapper.findGoodsList(b.getTabId()));
                        b.setGoodsList(goodsList);
                    });
                    a.setDetail(goodsTabInfosVos);
                    break;
                /******************* Tab end ****************/
                /******************* 无关联广告轮播图 start **********/
                case BOTTOM_SHUFFLING:
                case LEFT_UP_CORNER_SHUFFLING:
                case MIDDLE_ONE_SHUFFLING:
                case MIDDLE_TWO_SHUFFLING:
                case MIDDLE_THREE_SHUFFLING:
                case YOUPIN_INSIDE_SHUFFLING:
                case YOUDIAN_INSIDE_SHUFFLING:
                case SALE_INSIDE_SHUFFLING:
                case YOUXUAN_INSIDE_SHUFFLING:
                case BAIHUO_INSIDE_SHUFFLING:
                case PERSONAL_CENTER:
                    List<FindShufflingVo> shufflingVoList = relShufflingMapper.findShuffling(a.getAdviceId());
                    shufflingVoList.forEach(b -> {
                        AdviceTypeEnum adviceTypeEnum = b.getAdviceType();
                        switch (adviceTypeEnum) {
                            case HTML_DETAIL:
                                break;
                            case INFORMATION:
                                b.setDetailName(informationMapper.selectById(b.getDetailId()).getTitle());
                                break;
                            case STROE:
                                b.setDetailName(storeMapper.selectById(b.getDetailId()).getName());
                                break;
                            case GOODS:
                                b.setDetailName(goodsMapper.selectById(b.getDetailId()).getName());
                                break;
                        }
                    });
                    a.setDetail(shufflingVoList);
                    break;
                /******************* 无关联广告轮播图 end **********/
//                case YOUPIN_DETAIL:
//                    break;
                case FIRST_CATEGORY_DETAIL:
                    break;
                case BAIHUO:
                    break;
                case information_recommended:
                    break;

                /*******************充值入口+拼团鸭*********************/
                case TOP_UP_ENTRY:
                case SPELL_GROUP:
                    break;
                /*******************充值入口+拼团鸭*********************/
            }
        });

        return advicesVoPageInfo;
    }

    /**
     * 批量删除广告
     *
     * @param idList
     */
    @Override
    public void deleteAdvices(List<Long> idList) {

        idList.forEach(a -> {
            MmAdvicePo advicePo = mapper.selectById(a);
            if (advicePo == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在该广告,请检查");
            }
            AdviceLocationEnum adviceLocationEnum = AdviceLocationEnum.fromEnumName(advicePo.getLocation());
            switch (adviceLocationEnum) {
                /******************* Tab start ****************/

                //有店+店铺分类详情
                case STORE_DETAIL:
                    List<Long> relId = relAssociaitonMapper.selectList(new QueryWrapper<MmAdviceRelAssociaitonPo>().lambda()
                            .eq(MmAdviceRelAssociaitonPo::getAdviceId, a)).stream().map(b -> b.getId()).collect(Collectors.toList());
                    relId.forEach(c -> {
                        List<Long> tabIds = relTabAssociationMapper.selectList(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda()
                                .eq(MmAdviceRelTabAssociationPo::getRelId, c)).stream().map(d -> d.getTabId()).collect(Collectors.toList());
                        tabIds.forEach(e -> {
                            relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, e));
                            tabMapper.deleteById(e);
                        });
                        relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda()
                                .eq(MmAdviceRelTabAssociationPo::getRelId, c));
                    });
                    relAssociaitonMapper.delete(new QueryWrapper<MmAdviceRelAssociaitonPo>().lambda().and(obj -> obj
                            .eq(MmAdviceRelAssociaitonPo::getAdviceId, a)));
//                    mapper.deleteById(a);
                    break;
                //首页有品+品牌详情------》》》品牌
                //特卖、主题、优选-----》》》商品
                case SHOUYE_YOUPIN:
                case SHOUYE_ZHUTI:
                case SALE:
                case YOUXUAN:
                    //获取该广告下的所有选项卡
                    List<Long> tabIds = relTabMapper.selectList(new QueryWrapper<MmAdviceRelTabPo>().lambda()
                            .eq(MmAdviceRelTabPo::getAdviceId, a)).stream().map(b -> b.getTabId()).collect(Collectors.toList());
                    //删除选项卡关联的商品/品牌
                    tabIds.forEach(b -> {
                        if (adviceLocationEnum.equals(AdviceLocationEnum.SHOUYE_YOUPIN)) {
                            //获取该选项卡下绑定的品牌
                            List<Long> tabBrandIdList = relTabThingsMapper.selectList(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, b)).stream().map(c -> c.getAssociationId()).collect(Collectors.toList());

                            //获取该选项卡下的品牌下的关联id
                            tabBrandIdList.forEach(c -> {
                                Long brandRelId = relTabThingsMapper.selectOne(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                        .eq(MmAdviceRelTabThingsPo::getAssociationId, c)
                                        .eq(MmAdviceRelTabThingsPo::getTabId, b)).getId();
                                //删除该选项卡下的品牌下的轮播图广告
                                relShufflingMapper.delete(new QueryWrapper<MmAdviceRelShufflingPo>().lambda().and(obj -> obj
                                        .eq(MmAdviceRelShufflingPo::getRelTabBrandId, brandRelId)));
                            });
                            relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, b));
                        }
                    });

                    //删除广告与选项卡关联的记录
                    relTabMapper.delete(new QueryWrapper<MmAdviceRelTabPo>().lambda()
                            .eq(MmAdviceRelTabPo::getAdviceId, a));
                    //删除对应的选项卡
                    tabMapper.deleteBatchIds(tabIds);
                    //删除对应的广告
//                    mapper.deleteById(a);
                    break;
                /******************* Tab end ****************/

                /*******************首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式*********************/
                case BOTTOM_SHUFFLING:
                case LEFT_UP_CORNER_SHUFFLING:
                case MIDDLE_ONE_SHUFFLING:
                case MIDDLE_TWO_SHUFFLING:
                case MIDDLE_THREE_SHUFFLING:
                case YOUPIN_INSIDE_SHUFFLING:
                case YOUDIAN_INSIDE_SHUFFLING:
                case SALE_INSIDE_SHUFFLING:
                case YOUXUAN_INSIDE_SHUFFLING:
                case BAIHUO_INSIDE_SHUFFLING:
                case PERSONAL_CENTER:
                    //删除该广告对应的轮播图
                    relShufflingMapper.delete(new QueryWrapper<MmAdviceRelShufflingPo>().lambda()
                            .eq(MmAdviceRelShufflingPo::getAdviceId,a));
                    break;
                /*******************首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式*********************/

//                case YOUPIN_DETAIL:
//                    break;
                case FIRST_CATEGORY_DETAIL:
                    break;
                case BAIHUO:
                    break;
                case information_recommended:
                    break;
                /*******************充值入口+拼团鸭*********************/
                case TOP_UP_ENTRY:
                case SPELL_GROUP:
                    break;
            }

        });
        //最后一步，删除对应的广告
        mapper.deleteBatchIds(idList);

    }

    /**
     * 保存充值入口/拼团鸭广告
     *
     * @param saveOtherAdviceDto
     * @return
     */
    @Override
    public void saveOtherAdvice(SaveOtherAdviceDto saveOtherAdviceDto) {

        SysUserPo user = securityUtil.getCurrUser();

        //新增
        if (saveOtherAdviceDto.getAdviceId() == 0) {

            //判断一: 广告名称不能相同
            List<String> nameList = mapper.selectList(null).stream().map(a -> a.getName()).collect(Collectors.toList());
            if (nameList.contains(saveOtherAdviceDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveOtherAdviceDto.getName()));
            }
            MmAdvicePo advicePo = new MmAdvicePo();
            BeanUtils.copyProperties(saveOtherAdviceDto, advicePo);
            advicePo.setId(null).setCreateBy(user.getUsername());
            mapper.insert(advicePo);
        }
        //编辑
        else {
            //获取该广告名称
            String adviceName = mapper.selectById(saveOtherAdviceDto.getAdviceId()).getName();
            //获取除该广告名称之外的所有广告名称
            List<String> nameList = mapper.selectList(null).stream().filter(name -> !name.getName().equals(adviceName)).map(a -> a.getName()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(nameList) && nameList.contains(saveOtherAdviceDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveOtherAdviceDto
                        .getName()));
            }
            MmAdvicePo advicePo = mapper.selectById(saveOtherAdviceDto.getAdviceId());
            BeanUtils.copyProperties(saveOtherAdviceDto, advicePo);
            advicePo.setUpdateBy(user.getUsername());
            mapper.updateById(advicePo);
        }
    }

}
