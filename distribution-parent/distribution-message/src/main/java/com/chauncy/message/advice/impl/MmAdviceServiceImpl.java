package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.app.advice.AssociationTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.advice.*;
import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveClassificationAdviceDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAssociatedClassificationDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchInformationCategoryDto;
import com.chauncy.data.mapper.message.advice.*;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.category.MmInformationCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseVo;
import com.chauncy.data.vo.app.advice.home.GetAdviceInfoVo;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.data.vo.manage.message.advice.ClassificationVo;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
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
@Slf4j
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
    private MmInformationCategoryMapper informationCategoryMapper;

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

                //首页有品------》》》品牌+详情
                case SHOUYE_YOUPIN:
                    //获取该广告下的所有选项卡信息
                    List<BrandTabInfosVo> brandTabInfosVos = relTabMapper.findBrandTabInfosVos(a.getAdviceId());
                    brandTabInfosVos.forEach(b -> {
                        //分页获取品牌选项卡关联的品牌
                        PageInfo<BrandVo> brandList = PageHelper.startPage(defaultPageNo, defaultPageSize)
                                .doSelectPageInfo(() -> relTabThingsMapper.findBrandList(b.getTabId()));
                        //品牌详情-品牌绑定的轮播图广告
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
                /*******************推荐分类 葱鸭百货分类推荐/资讯分类推荐*********************/
                case BAIHUO:
                case information_recommended:
                    //分页获取关联的分类
                    PageInfo<ClassificationVo> classificationVoPageInfo = PageHelper.startPage(defaultPageNo, defaultPageSize)
                            .doSelectPageInfo(() -> relAssociaitonMapper.findClassification(a.getAdviceId()));
                    classificationVoPageInfo.getList().forEach(b -> {
                        AdviceLocationEnum associationTypeEnum = AdviceLocationEnum.fromEnumName(a.getLocation());
                        switch (associationTypeEnum) {
                            case BAIHUO:
                                String categoryName = goodsCategoryMapper.selectById(b.getClassificationId()).getName();
                                //分类1/分类2/分类3
                                PmGoodsCategoryPo goodsCategoryPo = goodsCategoryMapper.selectById(b.getClassificationId());
                                String level3 = goodsCategoryPo.getName();
                                PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo.getParentId());
                                String level2 = goodsCategoryPo2.getName();
                                String level1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
                                String name = level1 + "/" + level2 + "/" + level3;

                                b.setClassificationName(categoryName);
                                break;
                            case information_recommended:
                                String classificationName = informationCategoryMapper.selectById(b.getClassificationId()).getName();
                                b.setClassificationName(classificationName);
                                break;
                        }
                        a.setDetail(classificationVoPageInfo);
                    });
                    break;
                /*******************推荐分类 葱鸭百货分类推荐/资讯分类推荐*********************/

                /*******************充值入口+拼团鸭+优惠券+经验包+邀请包*********************/
                case TOP_UP_ENTRY:
                case SPELL_GROUP:
                case COUPON:
                case INVITATION:
                case EXPERIENCE_PACKAGE:
                    break;
                /*******************充值入口+拼团鸭+优惠券+经验包+邀请包*********************/
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
                            .eq(MmAdviceRelShufflingPo::getAdviceId, a));
                    break;
                /*******************首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式*********************/

//                case YOUPIN_DETAIL:
//                    break;
                case FIRST_CATEGORY_DETAIL:
                    break;
                /*******************推荐分类 葱鸭百货分类推荐/资讯分类推荐*********************/
                case BAIHUO:
                case information_recommended:

                    relAssociaitonMapper.delete(new QueryWrapper<MmAdviceRelAssociaitonPo>().lambda()
                            .eq(MmAdviceRelAssociaitonPo::getAdviceId, a));
                    break;
                /*******************推荐分类 葱鸭百货分类推荐/资讯分类推荐*********************/

                /*******************充值入口+拼团鸭+优惠券+经验包+邀请包*********************/
                case TOP_UP_ENTRY:
                case SPELL_GROUP:
                case COUPON:
                case INVITATION:
                case EXPERIENCE_PACKAGE:
                    break;
            }

        });
        //最后一步，删除对应的广告
        mapper.deleteBatchIds(idList);

    }

    /**
     * 保存充值入口+拼团鸭+优惠券+经验包+邀请包
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

    /**
     * 添加推荐的分类:葱鸭百货分类推荐/资讯分类推荐
     *
     * @param saveClassificationAdviceDto
     * @return
     */
    @Override
    public void saveGoodsCategoryAdvice(SaveClassificationAdviceDto saveClassificationAdviceDto) {

        SysUserPo user = securityUtil.getCurrUser();

        //新增
        if (saveClassificationAdviceDto.getAdviceId() == 0) {

            //判断一: 广告名称不能相同
            List<String> nameList = mapper.selectList(null).stream().map(a -> a.getName()).collect(Collectors.toList());
            if (nameList.contains(saveClassificationAdviceDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveClassificationAdviceDto.getName()));
            }
            MmAdvicePo advicePo = new MmAdvicePo();
            BeanUtils.copyProperties(saveClassificationAdviceDto, advicePo);
            advicePo.setId(null).setCreateBy(user.getUsername());
            mapper.insert(advicePo);
            //判断二：同一个广告的分类不能重复
            //获取全部的分类ID
            List<Long> IdList = saveClassificationAdviceDto.getClassificationsDtoList().stream().map(a -> a.getClassificationId()).collect(Collectors.toList());
            //关联的分类不能重复
            Boolean classIsRepeat = IdList.size() != new HashSet<Long>(IdList).size();
            if (classIsRepeat) {
                List<String> names = Lists.newArrayList();
                Map<Long, Integer> repeatMap = Maps.newHashMap();
                IdList.forEach(a -> {
                    Integer i = 1;
                    if (repeatMap.get(a) != null) {
                        i = repeatMap.get(a) + 1;
                    }
                    repeatMap.put(a, i);
                });
                AdviceLocationEnum associationTypeEnum = AdviceLocationEnum.fromEnumName(saveClassificationAdviceDto.getLocation());
                switch (associationTypeEnum) {
                    case BAIHUO:
                        for (Long l : repeatMap.keySet()) {
                            if (repeatMap.get(l) > 1) {
                                names.add(goodsCategoryMapper.selectById(l).getName());
                            }
                        }
                        log.info("重复数据为：" + names.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的商品分类：%s,请检查!", names.toString()));
                    case information_recommended:
                        for (Long l : repeatMap.keySet()) {
                            if (repeatMap.get(l) > 1) {
                                names.add(informationCategoryMapper.selectById(l).getName());
                            }
                        }
                        log.info("重复数据为：" + names.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的资讯分类：%s,请检查!", names.toString()));

                }
            }

            saveClassificationAdviceDto.getClassificationsDtoList().forEach(a -> {

                MmAdviceRelAssociaitonPo relAssociaitonPo = new MmAdviceRelAssociaitonPo();
                relAssociaitonPo.setCreateBy(user.getUsername()).setId(null).setAdviceId(advicePo.getId())
                        .setAssociationId(a.getClassificationId());
                AdviceLocationEnum associationTypeEnum = AdviceLocationEnum.fromEnumName(saveClassificationAdviceDto.getLocation());
                switch (associationTypeEnum) {
                    case BAIHUO:
                        PmGoodsCategoryPo goodsCategoryPo = goodsCategoryMapper.selectById(a.getClassificationId());
                        if (goodsCategoryPo == null) {
                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在id为[%s]的商品分类，请检查", a.getClassificationId()));
                        } else if (goodsCategoryPo.getLevel() != 3) {
                            throw new ServiceException(ResultCode.FAIL, String.format("商品分类[%s]不是三级分类，请重新选择", goodsCategoryPo.getName()));
                        }
                        relAssociaitonPo.setType(AssociationTypeEnum.THIRD_CLASSIFICATION.getId());
                        break;
                    case information_recommended:
                        MmInformationCategoryPo informationCategoryPo = informationCategoryMapper.selectById(a.getClassificationId());
                        if (informationCategoryPo == null) {
                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在id为[%s]的资讯分类，请检查", a.getClassificationId()));
                        }
                        relAssociaitonPo.setType(AssociationTypeEnum.INFORMATION_CLASSIFICATION.getId());
                        break;
                }
                relAssociaitonMapper.insert(relAssociaitonPo);
            });
        }
        //编辑
        else {
            //判断一: 广告名称不能相同
            //获取该广告名称
            String adviceName = mapper.selectById(saveClassificationAdviceDto.getAdviceId()).getName();
            //获取除该广告名称之外的所有广告名称
            List<String> nameList = mapper.selectList(null).stream().filter(name -> !name.getName().equals(adviceName)).map(a -> a.getName()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(nameList) && nameList.contains(saveClassificationAdviceDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveClassificationAdviceDto.getName()));
            }

            //1、更改广告信息到广告表——mm_advice
            MmAdvicePo mmAdvicePo = mapper.selectById(saveClassificationAdviceDto.getAdviceId());
            BeanUtils.copyProperties(saveClassificationAdviceDto, mmAdvicePo);
            mmAdvicePo.setUpdateBy(user.getUsername());
            mapper.updateById(mmAdvicePo);

            /******************************************* 删除分类信息 start *******************************************/
            //获取该广告原有的所有分类列表信息
            List<Long> allRelAssociatedIds = relAssociaitonMapper.selectList(new QueryWrapper<MmAdviceRelAssociaitonPo>()
                    .lambda().eq(MmAdviceRelAssociaitonPo::getAdviceId, saveClassificationAdviceDto.getAdviceId())).stream()
                    .map(a -> a.getId()).collect(Collectors.toList());
            //获取前端除新增(relAssocitedId为0)的数据
            List<Long> remainIds = saveClassificationAdviceDto.getClassificationsDtoList().stream().filter(a -> a.getRelAssociatedId() != 0)
                    .map(b -> b.getRelAssociatedId()).collect(Collectors.toList());
            //获取需要删除的数据
            List<Long> delIds = Lists.newArrayList(allRelAssociatedIds);
            if (!ListUtil.isListNullAndEmpty(allRelAssociatedIds)) {
                delIds.removeAll(remainIds);
                if (!ListUtil.isListNullAndEmpty(delIds)) {
                    relAssociaitonMapper.deleteBatchIds(delIds);
                }
            }
            /******************************************* 删除分类信息 end *******************************************/

            //判断二：同一个广告的分类不能重复
            //获取全部的分类ID
            List<Long> IdList = saveClassificationAdviceDto.getClassificationsDtoList().stream().map(a -> a.getClassificationId()).collect(Collectors.toList());
            //关联的分类不能重复
            Boolean classIsRepeat = IdList.size() != new HashSet<Long>(IdList).size();
            if (classIsRepeat) {
                List<String> names = Lists.newArrayList();
                Map<Long, Integer> repeatMap = Maps.newHashMap();
                IdList.forEach(a -> {
                    Integer i = 1;
                    if (repeatMap.get(a) != null) {
                        i = repeatMap.get(a) + 1;
                    }
                    repeatMap.put(a, i);
                });
                AdviceLocationEnum associationTypeEnum = AdviceLocationEnum.fromEnumName(saveClassificationAdviceDto.getLocation());
                switch (associationTypeEnum) {
                    case BAIHUO:
                        for (Long l : repeatMap.keySet()) {
                            if (repeatMap.get(l) > 1) {
                                names.add(goodsCategoryMapper.selectById(l).getName());
                            }
                        }
                        log.info("重复数据为：" + names.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的商品分类：%s,请检查!", names.toString()));
                    case information_recommended:
                        for (Long l : repeatMap.keySet()) {
                            if (repeatMap.get(l) > 1) {
                                names.add(informationCategoryMapper.selectById(l).getName());
                            }
                        }
                        log.info("重复数据为：" + names.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的资讯分类：%s,请检查!", names.toString()));

                }
            }


            saveClassificationAdviceDto.getClassificationsDtoList().forEach(a -> {
                //新增分类记录
                if (a.getRelAssociatedId() == 0) {
                    /******************************************* 新增分类信息 start *******************************************/
                    MmAdviceRelAssociaitonPo relAssociaitonPo = new MmAdviceRelAssociaitonPo();
                    relAssociaitonPo.setCreateBy(user.getUsername()).setId(null).setAdviceId(saveClassificationAdviceDto.getAdviceId())
                            .setAssociationId(a.getClassificationId());
                    AdviceLocationEnum associationTypeEnum = AdviceLocationEnum.fromEnumName(saveClassificationAdviceDto.getLocation());
                    switch (associationTypeEnum) {
                        case BAIHUO:
                            PmGoodsCategoryPo goodsCategoryPo = goodsCategoryMapper.selectById(a.getClassificationId());
                            if (goodsCategoryPo == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在id为[%s]的商品分类，请检查", a.getClassificationId()));
                            } else if (goodsCategoryPo.getLevel() != 3) {
                                throw new ServiceException(ResultCode.FAIL, String.format("商品分类[%s]不是三级分类，请重新选择", a.getClassificationId()));
                            }
                            relAssociaitonPo.setType(AssociationTypeEnum.THIRD_CLASSIFICATION.getId());
                            break;
                        case information_recommended:
                            MmInformationCategoryPo informationCategoryPo = informationCategoryMapper.selectById(a.getClassificationId());
                            if (informationCategoryPo == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在id为[%s]的资讯分类，请检查", a.getClassificationId()));
                            }
                            relAssociaitonPo.setType(AssociationTypeEnum.INFORMATION_CLASSIFICATION.getId());
                            break;
                    }
                    relAssociaitonMapper.insert(relAssociaitonPo);
                    /******************************************* 新增分类信息 start *******************************************/
                }

            });

        }
    }

    /**
     * 条件分页查询获取广告位置为葱鸭百货分类推荐/资讯分类推荐已经关联的分类信息
     *
     * @param searchAssociatedClassificationDto
     * @return
     */
    @Override
    public PageInfo<ClassificationVo> searchAssociatedClassification(SearchAssociatedClassificationDto searchAssociatedClassificationDto) {

        Integer PageNo = searchAssociatedClassificationDto.getPageNo() == null ? defaultPageNo : searchAssociatedClassificationDto.getPageNo();
        Integer pageSize = searchAssociatedClassificationDto.getPageSize() == null ? defaultPageSize : searchAssociatedClassificationDto.getPageSize();
        PageInfo<ClassificationVo> classificationVoPageInfo = PageHelper.startPage(PageNo, pageSize)
                .doSelectPageInfo(() -> relAssociaitonMapper.searchAssociatedClassification(searchAssociatedClassificationDto));
        String location = mapper.selectById(searchAssociatedClassificationDto.getAdviceId()).getLocation();
        classificationVoPageInfo.getList().forEach(a -> {
            AdviceLocationEnum associationTypeEnum = AdviceLocationEnum.fromEnumName(location);
            switch (associationTypeEnum) {
                case BAIHUO:
                    String categoryName = goodsCategoryMapper.selectById(a.getClassificationId()).getName();
                    //分类1/分类2/分类3
                    PmGoodsCategoryPo goodsCategoryPo = goodsCategoryMapper.selectById(a.getClassificationId());
                    String level3 = goodsCategoryPo.getName();
                    PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo.getParentId());
                    String level2 = goodsCategoryPo2.getName();
                    String level1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
                    String name = level1 + "/" + level2 + "/" + level3;

                    a.setClassificationName(categoryName);
                    break;
                case information_recommended:
                    String classificationName = informationCategoryMapper.selectById(a.getClassificationId()).getName();
                    a.setClassificationName(classificationName);
                    break;
            }
        });

        return classificationVoPageInfo;
    }

    /**
     * 分页查找需要广告位置为资讯分类推荐需要关联的资讯分类
     *
     * @return
     * @param searchInformationCategoryDto
     */
    @Override
    public PageInfo<BaseVo> searchInformationCategory(SearchInformationCategoryDto searchInformationCategoryDto) {
        Integer PageNo = searchInformationCategoryDto.getPageNo() == null ? defaultPageNo : searchInformationCategoryDto.getPageNo();
        Integer pageSize = searchInformationCategoryDto.getPageSize() == null ? defaultPageSize : searchInformationCategoryDto.getPageSize();
        PageInfo<BaseVo> informationCategoryVoPageInfo = PageHelper.startPage(PageNo, pageSize)
                .doSelectPageInfo(()->informationCategoryMapper.searchInformationCategory(searchInformationCategoryDto.getName()));
        return informationCategoryVoPageInfo;
    }

    /**
     * 批量启用或禁用,同一个广告位只能有一个是启用状态
     *
     * @param baseUpdateStatusDto
     * @return
     */
    @Override
    public void editEnabled(BaseUpdateStatusDto baseUpdateStatusDto) {

        SysUserPo user = securityUtil.getCurrUser();
        Long id = Arrays.asList(baseUpdateStatusDto.getId()).get(0);
        if (baseUpdateStatusDto.getEnabled()){
            //判断该广告对应的广告位置是否已有启用广告，若有则置为0
            String location = mapper.selectById(id).getLocation();
            //获取该广告位置下所有启用的广告
            List<MmAdvicePo> advicePos = mapper.selectList(new QueryWrapper<MmAdvicePo>().lambda()
                    .eq(MmAdvicePo::getLocation,location)).stream().filter(a->a.getEnabled().equals(true)).collect(Collectors.toList());
            advicePos.forEach(b->{
                b.setEnabled(false);
                mapper.updateById(b);
            });

            MmAdvicePo advicePo = mapper.selectById(id);
            advicePo.setEnabled(baseUpdateStatusDto.getEnabled()).setUpdateBy(user.getUsername());
            mapper.updateById(advicePo);
        }
    }

    /**
     * 查找广告位为葱鸭百货的所有广告
     *
     * @return
     */
//    @Override
//    public List<FindBaiHuoAdviceVo> findAdvice() {
//        List<FindBaiHuoAdviceVo> advices = mapper.findAdvice(AdviceLocationEnum.BAIHUO.name());
//
//        return advices;
//    }

    /**
     * 获取APP首页葱鸭优选、葱鸭百货等广告位信息
     * @return
     */
    @Override
    public List<GetAdviceInfoVo> getAdviceInfo() {

        List<GetAdviceInfoVo> adviceInfoVos = mapper.getAdviceInfo();
        return adviceInfoVos;
    }

    /**
     * 获取首页有品内部、有店内部、特卖内部、优选内部、葱鸭百货内部轮播图
     *
     * @return
     */
    @Override
    public List<ShufflingVo> getShuffling(String location) {

        List<ShufflingVo> shufflingVos = mapper.getShuffling(location);

        return shufflingVos;
    }

    /**
     * 根据ID获取特卖、有品、主题、优选等广告选项卡
     *
     * @param adviceId
     * @return
     */
    @Override
    public List<BaseVo> getTab(Long adviceId) {

        List<BaseVo> tabs = mapper.getTab(adviceId);

        return tabs;
    }

    /**
     * 根据选项卡分页获取特卖、主题、优选等选项卡关联的商品基本信息
     *
     * @param searchGoodsBaseDto
     * @return
     */
    @Override
    public PageInfo<SearchGoodsBaseVo> searchGoodsBase(SearchGoodsBaseDto searchGoodsBaseDto) {

        Integer pageNo = searchGoodsBaseDto.getPageNo() == null ? defaultPageNo : searchGoodsBaseDto.getPageNo();
        Integer pageSize = searchGoodsBaseDto.getPageSize() == null ? defaultPageSize : searchGoodsBaseDto.getPageSize();
        PageInfo<SearchGoodsBaseVo> goodsBaseVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchGoodsBase(searchGoodsBaseDto));

        return goodsBaseVoPageInfo;
    }

}
