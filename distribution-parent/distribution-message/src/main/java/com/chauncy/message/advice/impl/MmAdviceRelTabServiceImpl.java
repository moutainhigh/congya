package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.app.advice.AssociationTypeEnum;
import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.message.advice.*;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.message.advice.tab.tab.add.SaveRelTabDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchAdviceGoodsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedGoodsDto;
import com.chauncy.data.mapper.message.advice.*;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.GoodsVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo;
import com.chauncy.message.advice.IMmAdviceRelTabService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 广告与广告选项卡表关联表，广告位置为除了具体店铺分类的所有有选项卡的广告 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MmAdviceRelTabServiceImpl extends AbstractService<MmAdviceRelTabMapper, MmAdviceRelTabPo> implements IMmAdviceRelTabService {

    @Autowired
    private MmAdviceRelTabMapper relTabMapper;

    @Autowired
    private MmAdviceRelTabThingsMapper relTabThingsMapper;

    @Autowired
    private PmGoodsAttributeMapper brandMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private MmAdviceMapper adviceMapper;

    @Autowired
    private MmAdviceTabMapper adviceTabMapper;

    @Autowired
    private MmAdviceRelShufflingMapper relShufflingMapper;

    @Autowired
    private MmInformationMapper informationMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 条件分页查询需要被关联的品牌
     *
     * @param searchBrandsDto
     * @return
     */
    @Override
    public PageInfo<BaseVo> searchBrands(SearchBrandsDto searchBrandsDto) {

        //获取全部启用的品牌
        List<Long> allBrandIds = brandMapper.selectList(new QueryWrapper<PmGoodsAttributePo>().lambda().and(obj -> obj
                .eq(PmGoodsAttributePo::getEnabled, true)
                .eq(PmGoodsAttributePo::getType, GoodsAttributeTypeEnum.BRAND.getId()))).stream()
                .map(a -> a.getId()).collect(Collectors.toList());

        //获取该广告关联的选项卡
        List<Long> tabIdList = relTabMapper.selectList(new QueryWrapper<MmAdviceRelTabPo>().lambda()
                .eq(MmAdviceRelTabPo::getTabId, searchBrandsDto.getAdviceId())).stream()
                .map(a -> a.getTabId()).collect(Collectors.toList());
        //保存已经关联的品牌List
        List<Long> associatedBrandIds = Lists.newArrayList();
        tabIdList.forEach(a -> {
            List<Long> storeIds = relTabThingsMapper.selectList(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                    .eq(MmAdviceRelTabThingsPo::getTabId, a)).stream()
                    .map(b -> b.getAssociationId()).collect(Collectors.toList());
            storeIds.forEach(c -> {
                associatedBrandIds.add(c);
            });
        });
        List<Long> associatedBrandIdList = associatedBrandIds;
        //排除已经关联的品牌ID
        List<Long> idLists = Lists.newArrayList();
        if (ListUtil.isListNullAndEmpty(allBrandIds)) {
            return new PageInfo<>();
        }
        if (ListUtil.isListNullAndEmpty(associatedBrandIdList)) {
            idLists = allBrandIds;
            associatedBrandIdList = null;
        } else {

            List<Long> finalAssociatedBrandIdList = associatedBrandIdList;
            idLists = allBrandIds.stream().filter(d -> !finalAssociatedBrandIdList.contains(d)).collect(Collectors.toList());

        }
        Integer pageNo = searchBrandsDto.getPageNo() == null ? defaultPageNo : searchBrandsDto.getPageNo();
        Integer pageSize = searchBrandsDto.getPageSize() == null ? defaultPageSize : searchBrandsDto.getPageSize();
//        List<Long> finalIdLists = idLists;
        List<Long> finalAssociatedBrandIdList1 = associatedBrandIdList;
        PageInfo<BaseVo> BrandList = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> brandMapper.searchBrands(searchBrandsDto.getName(), finalAssociatedBrandIdList1));

        return BrandList;
    }


    /**
     * 条件分页查询需要被关联的商品(考虑数据量大，就不排除已经关联的了，直接查找全部的商品)
     *
     * @param searchAdviceGoodsDto
     * @return
     */
    @Override
    public PageInfo<SearchAdviceGoodsVo> searchAdviceGoods(SearchAdviceGoodsDto searchAdviceGoodsDto) {

        Integer pageNo = searchAdviceGoodsDto.getPageNo() == null ? defaultPageNo : searchAdviceGoodsDto.getPageNo();
        Integer pageSize = searchAdviceGoodsDto.getPageSize() == null ? defaultPageSize : searchAdviceGoodsDto.getPageSize();
        PageInfo<SearchAdviceGoodsVo> goodsList = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> goodsMapper.searchTabNeedGoods(searchAdviceGoodsDto));

        //获取三级类目名称
        AtomicReference<String> level3 = new AtomicReference<>("");
        AtomicReference<String> level2 = new AtomicReference<>("");
        AtomicReference<String> level1 = new AtomicReference<>("");
        goodsList.getList().forEach(a -> {
            PmGoodsCategoryPo goodsCategoryPo3 = goodsCategoryMapper.selectById(a.getGoodsCategoryId());
            if (goodsCategoryPo3 != null) {
                level3.set(goodsCategoryPo3.getName());
                PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo3.getParentId());
                if (goodsCategoryPo2 != null) {
                    level2.set(goodsCategoryPo2.getName());
                    PmGoodsCategoryPo goodsCategoryPo1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId());
                    if (goodsCategoryPo1 != null) {
                        level1.set(goodsCategoryPo1.getName());
                    }
                    String categoryName = level1 + "/" + level2 + "/" + level3;
                    a.setCategory(categoryName);
                }
            }
        });

        return goodsList;
    }

    /**
     * 保存特卖、有品、主题、优选等广告信息
     *
     * @param saveRelTabDto
     * @return
     */
    @Override
    public void saveRelTab(SaveRelTabDto saveRelTabDto) {

        SysUserPo sysUser = securityUtil.getCurrUser();
        MmAdvicePo advicePo = new MmAdvicePo();

        //新增操作
        if (saveRelTabDto.getAdviceId() == 0) {
            //判断一: 广告名称不能相同
            List<String> nameList = adviceMapper.selectList(null).stream().map(a -> a.getName()).collect(Collectors.toList());
            if (nameList.contains(saveRelTabDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveRelTabDto.getName()));
            }

            //判断二:同一个广告下的选项卡不能重复
            List<String> tabNameList = saveRelTabDto.getTabInfos().stream().map(a -> a.getTabName()).collect(Collectors.toList());
            boolean tabNamesIsRepeat = tabNameList.size() != new HashSet<String>(tabNameList).size();
            if (tabNamesIsRepeat) {
                List<String> repeatNames = Lists.newArrayList();
                //查找重复的数据
                Map<String, Integer> repeatMap = Maps.newHashMap();
                tabNameList.forEach(str -> {
                    Integer i = 1;
                    if (repeatMap.get(str) != null) {
                        i = repeatMap.get(str) + 1;
                    }
                    repeatMap.put(str, i);
                });
                for (String s : repeatMap.keySet()) {
                    if (repeatMap.get(s) > 1) {

                        repeatNames.add(s);
                    }
                }
                log.info("重复数据为：" + repeatNames.toString());
                throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复选项卡名称：%s,请检查!", repeatNames.toString()));
            }

            //1、保存广告信息到广告表——mm_advice
            BeanUtils.copyProperties(saveRelTabDto, advicePo);
            advicePo.setCreateBy(sysUser.getUsername());
            advicePo.setId(null);
            adviceMapper.insert(advicePo);

            List<Long> brandIds = Lists.newArrayList();
            List<Long> goodsIds = Lists.newArrayList();
            AdviceLocationEnum adviceLocationEnum = AdviceLocationEnum.fromEnumName(saveRelTabDto.getLocation());
            switch (adviceLocationEnum) {
                case SHOUYE_YOUPIN:
                    //判断三:同一个广告的关联的品牌不能重复
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        a.getTabRelAssociatedDtos().forEach(b -> {
                            brandIds.add(b.getAssociatedId());
                        });
                    });
                    boolean brandNamesIsRepeat = brandIds.size() != new HashSet<Long>(brandIds).size();
                    if (brandNamesIsRepeat) {
                        List<String> repeatNames = Lists.newArrayList();
                        //查找重复的数据
                        Map<Long, Integer> repeatMap = Maps.newHashMap();
                        brandIds.forEach(str -> {
                            Integer i = 1;
                            if (repeatMap.get(str) != null) {
                                i = repeatMap.get(str) + 1;
                            }
                            repeatMap.put(str, i);
                        });
                        for (Long s : repeatMap.keySet()) {
                            if (repeatMap.get(s) > 1) {

                                repeatNames.add(brandMapper.selectById(s).getName());
                            }
                        }
                        log.info("重复数据为：" + repeatNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复品牌：%s,请检查!", repeatNames.toString()));
                    }
                    //2、保存选项卡
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                        adviceTabPo.setId(null)
                                .setCreateBy(sysUser.getUsername())
                                .setName(a.getTabName());
                        adviceTabMapper.insert(adviceTabPo);
                        //3、保存选项卡与广告id到关联表mm_advice_rel_tab
                        MmAdviceRelTabPo adviceRelTabPo = new MmAdviceRelTabPo();
                        adviceRelTabPo.setId(null)
                                .setAdviceId(advicePo.getId())
                                .setTabId(adviceTabPo.getId())
                                .setCreateBy(sysUser.getUsername());
                        relTabMapper.insert(adviceRelTabPo);
                        a.getTabRelAssociatedDtos().forEach(b -> {
                            if (brandMapper.selectById(b.getAssociatedId()) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在id为[%s]的品牌,请检查", b));
                            }
                            //4、保存该选项卡下的品牌到mm_advice_rel_tab_things
                            MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                            relTabThingsPo.setId(null)
                                    .setType(AssociationTypeEnum.BRAND.getId())
                                    .setCreateBy(sysUser.getUsername())
                                    .setTabId(adviceTabPo.getId())
                                    .setAssociationId(b.getAssociatedId());
                            relTabThingsMapper.insert(relTabThingsPo);

                            //判断四: 同一个品牌关联的ID（资讯、商品、店铺）不能一样
                            List<Long> brandRelIds = b.getBrandShufflings().stream().map(rel -> rel.getDetailId()).collect(Collectors.toList());
                            if (!ListUtil.isListNullAndEmpty(brandRelIds)) {
                                boolean relIdIsrepeat = brandRelIds.size() != new HashSet<Long>(brandRelIds).size();
                                if (relIdIsrepeat) {
                                    List<String> repeatNames = Lists.newArrayList();
                                    //查找重复的数据
                                    Map<Long, Integer> repeatMap = Maps.newHashMap();
                                    brandRelIds.forEach(str -> {
                                        Integer i = 1;
                                        if (repeatMap.get(str) != null) {

                                            i = repeatMap.get(str) + 1;
                                        }
                                        repeatMap.put(str, i);
                                    });
                                    for (Long s : repeatMap.keySet()) {
                                        if (repeatMap.get(s) > 1) {
                                            repeatNames.add(brandMapper.selectById(s).getName());
                                        }
                                    }
                                    throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复数据,请检查!"));
                                }
                            }

                            //5、保存该选项卡下品牌下的轮播图广告
                            b.getBrandShufflings().forEach(c -> {
                                //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                                if (c.getEndTime().isBefore(c.getStartTime()) || c.getEndTime().equals(c.getStartTime())) {
                                    throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                                }
                                if (c.getStartTime().isBefore(LocalDateTime.now())) {
                                    throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                                }
                                if (c.getEndTime().isBefore(LocalDateTime.now())) {
                                    throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                                }

                                MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                                //获取广告类型
                                AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(c.getAdviceType());
                                switch (adviceTypeEnum) {
                                    case HTML_DETAIL:
                                        relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(advicePo.getId())
                                                .setAdviceType(adviceTypeEnum).setHtmlDetail(c.getHtmlDetail()).setRelTabBrandId(relTabThingsPo.getId())
                                                .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                .setCoverPhoto(c.getCoverPhoto()).setDetailId(null).setRelCategoryId(null).setFirstCategoryId(null);
                                        relShufflingMapper.insert(relShufflingPo);
                                        break;
                                    case INFORMATION:
                                    case STROE:
                                    case GOODS:
                                        if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                            if (goodsMapper.selectById(c.getDetailId()) == null) {
                                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", c.getDetailId()));
                                            }
                                        } else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                            if (informationMapper.selectById(c.getDetailId()) == null) {
                                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", c.getDetailId()));
                                            }
                                        } else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                            if (storeMapper.selectById(c.getDetailId()) == null) {
                                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", c.getDetailId()));
                                            }
                                        }
                                        relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(advicePo.getId())
                                                .setAdviceType(adviceTypeEnum).setDetailId(c.getDetailId()).setRelTabBrandId(relTabThingsPo.getId())
                                                .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                .setCoverPhoto(c.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null).setFirstCategoryId(null);
                                        relShufflingMapper.insert(relShufflingPo);
                                        break;
                                }
                            });
                        });

                    });

                    break;
                case SHOUYE_ZHUTI:
                case SALE:
                case YOUXUAN:
                    //判断三:同一个广告关联的商品不能重复
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        a.getTabRelAssociatedDtos().forEach(b -> {
                            goodsIds.add(b.getAssociatedId());
                        });
                    });
                    boolean goodsNamesIsRepeat = goodsIds.size() != new HashSet<Long>(goodsIds).size();
                    if (goodsNamesIsRepeat) {
                        List<String> repeatNames = Lists.newArrayList();
                        //查找重复的数据
                        Map<Long, Integer> repeatMap = Maps.newHashMap();
                        goodsIds.forEach(str -> {
                            Integer i = 1;
                            if (repeatMap.get(str) != null) {
                                i = repeatMap.get(str) + 1;
                            }
                            repeatMap.put(str, i);
                        });
                        for (Long s : repeatMap.keySet()) {
                            if (repeatMap.get(s) > 1) {
                                System.out.println(s);
                                repeatNames.add(goodsMapper.selectById(s).getName());
                            }
                        }
                        log.info("重复数据为：" + repeatNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复商品：%s,请检查!", repeatNames.toString()));
                    }
                    //2、保存选项卡以及对应的品商品mm_advice_tab、mm_advice_rel_tab、mm_advice_rel_tab_things
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                        adviceTabPo.setId(null)
                                .setCreateBy(sysUser.getUsername())
                                .setName(a.getTabName());
                        adviceTabMapper.insert(adviceTabPo);
                        //3、保存选项卡与广告id到关联表mm_advice_rel_tab
                        MmAdviceRelTabPo adviceRelTabPo = new MmAdviceRelTabPo();
                        adviceRelTabPo.setId(null)
                                .setAdviceId(advicePo.getId())
                                .setTabId(adviceTabPo.getId())
                                .setCreateBy(sysUser.getUsername());
                        relTabMapper.insert(adviceRelTabPo);
                        a.getTabRelAssociatedDtos().forEach(b -> {
                            if (goodsMapper.selectById(b.getAssociatedId()) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在id为[%s]的商品,请检查", b));
                            }
                            //4、保存该选项卡下的商品到mm_advice_rel_tab_things
                            MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                            relTabThingsPo.setId(null)
                                    .setType(AssociationTypeEnum.Goods.getId())
                                    .setCreateBy(sysUser.getUsername())
                                    .setTabId(adviceTabPo.getId())
                                    .setAssociationId(b.getAssociatedId());
                            relTabThingsMapper.insert(relTabThingsPo);
                        });

                    });
                    break;
            }

        }

        //编辑操作(包括修改和删除)
        else {
            /*****************删除选项卡 start***************/
            //查找已经删除了的tab以及执行删除操作，首先获取该广告下全部的选项卡Tabs
            List<Long> allTabIds = relTabMapper.selectList(new QueryWrapper<MmAdviceRelTabPo>().lambda()
                    .eq(MmAdviceRelTabPo::getAdviceId, saveRelTabDto.getAdviceId())).stream()
                    .map(a -> a.getTabId()).collect(Collectors.toList());
            //获取前端传过来除了新增(即tabId=0)的数据
            List<Long> remainTabIds = saveRelTabDto.getTabInfos().stream().filter(a -> a.getTabId() != 0)
                    .map(b -> b.getTabId()).collect(Collectors.toList());
            //获取需要删除的数据(去集合allTabIds和集合remainTabIds的交集)
            List<Long> delIds = Lists.newArrayList(allTabIds);
            delIds.removeAll(remainTabIds);
            if (!ListUtil.isListNullAndEmpty(delIds)) {
                delIds.forEach(a -> {
                    if (saveRelTabDto.getLocation().equals(AdviceLocationEnum.SHOUYE_YOUPIN.getName())) {
                        //获取该选项卡下绑定的品牌
                        List<Long> tabBrandIdList = relTabThingsMapper.selectList(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                .eq(MmAdviceRelTabThingsPo::getTabId, a)).stream().map(b -> b.getAssociationId()).collect(Collectors.toList());

                        //获取该选项卡下的品牌下的关联id
                        tabBrandIdList.forEach(b -> {
                            Long brandRelId = relTabThingsMapper.selectOne(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getAssociationId, b)
                                    .eq(MmAdviceRelTabThingsPo::getTabId, a)).getId();
                            //删除该选项卡下的品牌下的轮播图广告
                            relShufflingMapper.delete(new QueryWrapper<MmAdviceRelShufflingPo>().lambda().and(obj -> obj
                                    .eq(MmAdviceRelShufflingPo::getRelTabBrandId, brandRelId)));
                        });

                    }

                    //删除该选项卡关联的商品/品牌
                    relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                            .eq(MmAdviceRelTabThingsPo::getTabId, a));
                    //删除广告与选项卡关联记录
                    relTabMapper.delete(new QueryWrapper<MmAdviceRelTabPo>().lambda()
                            .eq(MmAdviceRelTabPo::getTabId, a));
                });
                //删除选项卡
                adviceTabMapper.deleteBatchIds(delIds);
            }
            /*****************删除 end***************/

            //判断一: 广告名称不能相同
            //获取该广告名称
            String adviceName = adviceMapper.selectById(saveRelTabDto.getAdviceId()).getName();
            //获取除该广告名称之外的所有广告名称
            List<String> nameList = adviceMapper.selectList(null).stream().filter(name -> !name.getName().equals(adviceName)).map(a -> a.getName()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(nameList) && nameList.contains(saveRelTabDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveRelTabDto.getName()));
            }

            //判断二:同一个广告下的选项卡不能重复
            List<String> tabNameList = saveRelTabDto.getTabInfos().stream().map(a -> a.getTabName()).collect(Collectors.toList());
            boolean tabNamesIsRepeat = tabNameList.size() != new HashSet<String>(tabNameList).size();
            if (tabNamesIsRepeat) {
                List<String> repeatNames = Lists.newArrayList();
                //查找重复的数据
                Map<String, Integer> repeatMap = Maps.newHashMap();
                tabNameList.forEach(str -> {
                    Integer i = 1;
                    if (repeatMap.get(str) != null) {
                        i = repeatMap.get(str) + 1;
                    }
                    repeatMap.put(str, i);
                });
                for (String s : repeatMap.keySet()) {
                    if (repeatMap.get(s) > 1) {

                        repeatNames.add(s);
                    }
                }
                log.info("重复数据为：" + repeatNames.toString());
                throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复选项卡名称：%s,请检查!", repeatNames.toString()));
            }

            //1、更改广告信息到广告表——mm_advice
            MmAdvicePo mmAdvicePo = adviceMapper.selectById(saveRelTabDto.getAdviceId());
            BeanUtils.copyProperties(saveRelTabDto, mmAdvicePo);
            mmAdvicePo.setUpdateBy(sysUser.getUsername());
            adviceMapper.updateById(mmAdvicePo);

            List<Long> brandIds = Lists.newArrayList();
            List<Long> goodsIds = Lists.newArrayList();
            AdviceLocationEnum adviceLocationEnum = AdviceLocationEnum.fromEnumName(saveRelTabDto.getLocation());
            switch (adviceLocationEnum) {
                case SHOUYE_YOUPIN:
                    //判断三:同一个广告的关联的品牌不能重复
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        a.getTabRelAssociatedDtos().forEach(b -> {
                            brandIds.add(b.getAssociatedId());
                        });
                    });
                    boolean brandNamesIsRepeat = brandIds.size() != new HashSet<Long>(brandIds).size();
                    if (brandNamesIsRepeat) {
                        List<String> repeatNames = Lists.newArrayList();
                        //查找重复的数据
                        Map<Long, Integer> repeatMap = Maps.newHashMap();
                        brandIds.forEach(str -> {
                            Integer i = 1;
                            if (repeatMap.get(str) != null) {
                                i = repeatMap.get(str) + 1;
                            }
                            repeatMap.put(str, i);
                        });
                        for (Long s : repeatMap.keySet()) {
                            if (repeatMap.get(s) > 1) {

                                repeatNames.add(brandMapper.selectById(s).getName());
                            }
                        }
                        log.info("重复数据为：" + repeatNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复品牌：%s,请检查!", repeatNames.toString()));
                    }

                    saveRelTabDto.getTabInfos().forEach(a -> {
                        //新增选项卡
                        if (a.getTabId() == 0) {
                            //2、保存选项卡信息
                            MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                            adviceTabPo.setId(null)
                                    .setCreateBy(sysUser.getUsername())
                                    .setName(a.getTabName());
                            adviceTabMapper.insert(adviceTabPo);
                            //3、保存选项卡与广告id到关联表mm_advice_rel_tab
                            MmAdviceRelTabPo adviceRelTabPo = new MmAdviceRelTabPo();
                            adviceRelTabPo.setId(null)
                                    .setAdviceId(saveRelTabDto.getAdviceId())
                                    .setTabId(adviceTabPo.getId())
                                    .setCreateBy(sysUser.getUsername());
                            relTabMapper.insert(adviceRelTabPo);
                            a.getTabRelAssociatedDtos().forEach(b -> {
                                if (brandMapper.selectById(b.getAssociatedId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在id为[%s]的品牌,请检查", b));
                                }
                                //4、保存该选项卡下的品牌到mm_advice_rel_tab_things
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setId(null)
                                        .setType(AssociationTypeEnum.Goods.getId())
                                        .setCreateBy(sysUser.getUsername())
                                        .setTabId(adviceTabPo.getId())
                                        .setAssociationId(b.getAssociatedId());
                                relTabThingsMapper.insert(relTabThingsPo);

                                //5、保存该选项卡下品牌下的轮播图广告
                                b.getBrandShufflings().forEach(c -> {
                                    //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                                    if (c.getEndTime().isBefore(c.getStartTime()) || c.getEndTime().equals(c.getStartTime())) {
                                        throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                                    }
                                    if (c.getStartTime().isBefore(LocalDateTime.now())) {
                                        throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                                    }
                                    if (c.getEndTime().isBefore(LocalDateTime.now())) {
                                        throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                                    }

                                    MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                                    //获取广告类型
                                    AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(c.getAdviceType());
                                    switch (adviceTypeEnum) {
                                        case HTML_DETAIL:
                                            relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveRelTabDto.getAdviceId())
                                                    .setAdviceType(adviceTypeEnum).setHtmlDetail(c.getHtmlDetail()).setRelTabBrandId(relTabThingsPo.getId())
                                                    .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                    .setCoverPhoto(c.getCoverPhoto()).setDetailId(null).setRelCategoryId(null).setFirstCategoryId(null);
                                            relShufflingMapper.insert(relShufflingPo);
                                            break;
                                        case INFORMATION:
                                            if (informationMapper.selectById(c.getDetailId()) == null) {
                                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", c.getDetailId()));
                                            }
                                        case STROE:
                                            if (storeMapper.selectById(c.getDetailId()) == null) {
                                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", c.getDetailId()));
                                            }
                                        case GOODS:
                                            if (goodsMapper.selectById(c.getDetailId()) == null) {
                                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", c.getDetailId()));
                                            }
                                            relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveRelTabDto.getAdviceId())
                                                    .setAdviceType(adviceTypeEnum).setDetailId(c.getDetailId()).setRelTabBrandId(relTabThingsPo.getId())
                                                    .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                    .setCoverPhoto(c.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null).setFirstCategoryId(null);
                                            relShufflingMapper.insert(relShufflingPo);
                                            break;
                                    }
                                });

                            });
                        }
                        //修改操作
                        else {
                            if (relTabMapper.selectOne(new QueryWrapper<MmAdviceRelTabPo>().lambda()
                                    .eq(MmAdviceRelTabPo::getTabId, a.getTabId())
                                    .eq(MmAdviceRelTabPo::getAdviceId, saveRelTabDto.getAdviceId())) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该广告【%s】不存在选项卡【%s】,请检查",
                                        saveRelTabDto.getName(), a.getTabName()));
                            }
                            //2、修改选项卡信息
                            MmAdviceTabPo adviceTabPo = adviceTabMapper.selectById(a.getTabId());
                            adviceTabPo.setUpdateBy(sysUser.getUsername())
                                    .setName(a.getTabName());
                            adviceTabMapper.updateById(adviceTabPo);

                            //4、获取该选项卡下绑定的品牌
                            List<Long> tabBrandIdList = relTabThingsMapper.selectList(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, a.getTabId())).stream().map(b -> b.getAssociationId()).collect(Collectors.toList());
                            if (!ListUtil.isListNullAndEmpty(tabBrandIdList)) {
                                List<Long> delBrandIds = new ArrayList<>(tabBrandIdList);
                                //前端传的值
                                List<Long> newBrandIds = new ArrayList<>(a.getTabRelAssociatedDtos().stream().map(r -> r.getAssociatedId()).collect(Collectors.toList()));
                                //获取需要删除的品牌并删除
                                delBrandIds.removeAll(a.getTabRelAssociatedDtos().stream().map(r -> r.getAssociatedId()).collect(Collectors.toList()));
                                //获取新增的品牌并保存到数据库中
                                newBrandIds.removeAll(tabBrandIdList);
                                if (!ListUtil.isListNullAndEmpty(delBrandIds)) {
                                    delBrandIds.forEach(c -> {
                                        //获取该选项卡下的品牌下的关联id
                                        Long brandRelId = relTabThingsMapper.selectOne(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                                .eq(MmAdviceRelTabThingsPo::getAssociationId, c)
                                                .eq(MmAdviceRelTabThingsPo::getTabId, a.getTabId())).getId();

                                        //删除该选项卡下的品牌下的轮播图广告
                                        relShufflingMapper.delete(new QueryWrapper<MmAdviceRelShufflingPo>().lambda().and(obj -> obj
                                                .eq(MmAdviceRelShufflingPo::getRelTabBrandId, brandRelId)));

                                        //删除该选项卡下的品牌
                                        relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                                .eq(MmAdviceRelTabThingsPo::getAssociationId, c)
                                                .eq(MmAdviceRelTabThingsPo::getTabId, a.getTabId()));
                                    });
                                }

                                if (!ListUtil.isListNullAndEmpty(newBrandIds)) {
                                    //保存该选项卡下的品牌到mm_advice_rel_tab_things
                                    newBrandIds.forEach(c -> {
                                        MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                        relTabThingsPo.setId(null)
                                                .setType(AssociationTypeEnum.BRAND.getId())
                                                .setCreateBy(sysUser.getUsername())
                                                .setTabId(a.getTabId())
                                                .setAssociationId(c);
                                        relTabThingsMapper.insert(relTabThingsPo);
                                    });
                                }
                            }

                            //5、保存该选项卡下品牌下的轮播图广告，遍历每一个品牌
                            a.getTabRelAssociatedDtos().forEach(b -> {
                                /******************************************* 删除 轮播图 start**********************************/
                                //获取品牌和选项卡关联表的ID
                                Long relThingsId = relTabThingsMapper.selectOne(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                        .eq(MmAdviceRelTabThingsPo::getTabId, a.getTabId())
                                        .eq(MmAdviceRelTabThingsPo::getAssociationId, b.getAssociatedId())).getId();
                                //获取原来有的轮播图
                                List<Long> allShufflingIds = relShufflingMapper.selectList(new QueryWrapper<MmAdviceRelShufflingPo>().lambda()
                                        .eq(MmAdviceRelShufflingPo::getRelTabBrandId, relThingsId)).stream()
                                        .map(j -> j.getId()).collect(Collectors.toList());
                                //获取前端传来的(除新增shufflingId = 0)的轮播图数据
                                List<Long> remainShufflingIds = b.getBrandShufflings().stream().filter(del -> del.getShufflingId() != 0)
                                        .map(v -> v.getShufflingId()).collect(Collectors.toList());
                                //获取需要删除的轮播图
                                List<Long> delShufflingIds = Lists.newArrayList(allShufflingIds);
                                delShufflingIds.removeAll(remainShufflingIds);
                                if (!ListUtil.isListNullAndEmpty(delShufflingIds)) {
                                    //批量删除轮播图
                                    relShufflingMapper.deleteBatchIds(delShufflingIds);
                                }
                                /******************************************* 删除 轮播图 end******************************************/

                                //遍历每一个品牌每一个广告轮播图
                                b.getBrandShufflings().forEach(c -> {
                                    /******************************************* 修改选项卡信息时 新增轮播图 start******************************************/
                                    //新增广告轮播图
                                    if (c.getShufflingId() == 0) {
                                        //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                                        if (c.getEndTime().isBefore(c.getStartTime()) || c.getEndTime().equals(c.getStartTime())) {
                                            throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                                        }
                                        if (c.getStartTime().isBefore(LocalDateTime.now())) {
                                            throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                                        }
                                        if (c.getEndTime().isBefore(LocalDateTime.now())) {
                                            throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                                        }

                                        MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                                        //获取广告类型
                                        AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(c.getAdviceType());
                                        switch (adviceTypeEnum) {
                                            case HTML_DETAIL:
                                                relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveRelTabDto.getAdviceId())
                                                        .setAdviceType(adviceTypeEnum).setHtmlDetail(c.getHtmlDetail()).setRelTabBrandId(relThingsId)
                                                        .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                        .setCoverPhoto(c.getCoverPhoto()).setDetailId(null).setRelCategoryId(null).setFirstCategoryId(null);
                                                relShufflingMapper.insert(relShufflingPo);
                                                break;
                                            case INFORMATION:
                                            case STROE:
                                            case GOODS:
                                                if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                                    if (goodsMapper.selectById(c.getDetailId()) == null) {
                                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", c.getDetailId()));
                                                    }
                                                } else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                                    if (informationMapper.selectById(c.getDetailId()) == null) {
                                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", c.getDetailId()));
                                                    }
                                                } else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                                    if (storeMapper.selectById(c.getDetailId()) == null) {
                                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", c.getDetailId()));
                                                    }
                                                }
                                                relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveRelTabDto.getAdviceId())
                                                        .setAdviceType(adviceTypeEnum).setDetailId(c.getDetailId()).setRelTabBrandId(relThingsId)
                                                        .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                        .setCoverPhoto(c.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null).setFirstCategoryId(null);
                                                relShufflingMapper.insert(relShufflingPo);
                                                break;
                                        }
                                    }
                                    /******************************************* 修改选项卡信息时 新增轮播图 end******************************************/

                                    /******************************************* 修改选项卡信息时 修改轮播图 start******************************************/
                                    //修改轮播图
                                    else {
                                        //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                                        if (c.getEndTime().isBefore(c.getStartTime()) || c.getEndTime().equals(c.getStartTime())) {
                                            throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                                        }
                                        //判断是否修改开始时间和结束时间
                                        LocalDateTime startTime = relShufflingMapper.selectById(c.getShufflingId()).getStartTime();
                                        LocalDateTime endTime = relShufflingMapper.selectById(c.getShufflingId()).getEndTime();
                                        if (!startTime.equals(c.getStartTime())) {
                                            //已经开始但还没结束，不能修改开始时间
                                            if (startTime.isBefore(LocalDateTime.now()) && endTime.isAfter(LocalDateTime.now())) {
                                                throw new ServiceException(ResultCode.FAIL, String.format("存在轮播图广告已经开始,不能修改开始时间,请检查"));
                                            }
                                            if (c.getStartTime().isBefore(LocalDateTime.now())) {
                                                throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                                            }
                                        }
                                        if (!endTime.equals(c.getEndTime())) {
                                            if (c.getEndTime().isBefore(LocalDateTime.now())) {
                                                throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                                            }
                                        }

                                        MmAdviceRelShufflingPo relShufflingPo = relShufflingMapper.selectById(c.getShufflingId());
                                        //获取广告类型
                                        AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(c.getAdviceType());
                                        switch (adviceTypeEnum) {
                                            case HTML_DETAIL:
                                                relShufflingPo.setUpdateBy(sysUser.getUsername()).setAdviceId(saveRelTabDto.getAdviceId())
                                                        .setAdviceType(adviceTypeEnum).setHtmlDetail(c.getHtmlDetail()).setRelTabBrandId(relThingsId)
                                                        .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                        .setCoverPhoto(c.getCoverPhoto()).setDetailId(null).setRelCategoryId(null).setFirstCategoryId(null);
                                                relShufflingMapper.updateById(relShufflingPo);
                                                break;
                                            case INFORMATION:
                                            case STROE:
                                            case GOODS:
                                                if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                                    if (goodsMapper.selectById(c.getDetailId()) == null) {
                                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", c.getDetailId()));
                                                    }
                                                } else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                                    if (informationMapper.selectById(c.getDetailId()) == null) {
                                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", c.getDetailId()));
                                                    }
                                                } else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                                    if (storeMapper.selectById(c.getDetailId()) == null) {
                                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", c.getDetailId()));
                                                    }
                                                }
                                                relShufflingPo.setUpdateBy(sysUser.getUsername()).setAdviceId(saveRelTabDto.getAdviceId())
                                                        .setAdviceType(adviceTypeEnum).setDetailId(c.getDetailId()).setRelTabBrandId(relThingsId)
                                                        .setBrandId(b.getAssociatedId()).setStartTime(c.getStartTime()).setEndTime(c.getEndTime())
                                                        .setCoverPhoto(c.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null).setFirstCategoryId(null);
                                                relShufflingMapper.updateById(relShufflingPo);
                                                break;
                                        }
                                    }
                                    /******************************************* 修改选项卡信息时 修改轮播图 end******************************************/
                                });


                            });


                            //4、删除选项卡与品牌关联表
//                            Map<String, Object> map = Maps.newHashMap();
//                            map.put("tab_id", a.getTabId());
//                            relTabThingsMapper.deleteByMap(map);
                            //遍历该选项卡下的品牌
//                            a.getAssociatedIds().forEach(c -> {
//                                if (brandMapper.selectById(c) == null){
//                                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为[%s]的品牌,请检查",c));
//                                }
//                                //保存该选项卡下的品牌到mm_advice_rel_tab_things
//                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
//                                relTabThingsPo.setId(null)
//                                        .setType(AssociationTypeEnum.BRAND.getId())
//                                        .setCreateBy(sysUser.getUsername())
//                                        .setTabId(a.getTabId())
//                                        .setAssociationId(c);
//                                relTabThingsMapper.insert(relTabThingsPo);
//                            });
                        }

                    });

                    break;
                case SHOUYE_ZHUTI:
                case SALE:
                case YOUXUAN:
                    //判断三:同一个广告关联的商品不能重复
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        a.getTabRelAssociatedDtos().forEach(b -> {
                            goodsIds.add(b.getAssociatedId());
                        });
                    });
                    boolean goodsNamesIsRepeat = brandIds.size() != new HashSet<Long>(brandIds).size();
                    if (goodsNamesIsRepeat) {
                        List<String> repeatNames = Lists.newArrayList();
                        //查找重复的数据
                        Map<Long, Integer> repeatMap = Maps.newHashMap();
                        goodsIds.forEach(str -> {
                            Integer i = 1;
                            if (repeatMap.get(str) != null) {
                                i = repeatMap.get(str) + 1;
                            }
                            repeatMap.put(str, i);
                        });
                        for (Long s : repeatMap.keySet()) {
                            if (repeatMap.get(s) > 1) {

                                repeatNames.add(goodsMapper.selectById(s).getName());
                            }
                        }
                        log.info("重复数据为：" + repeatNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复商品：%s,请检查!", repeatNames.toString()));
                    }

                    saveRelTabDto.getTabInfos().forEach(a -> {
                        //新增选项卡
                        if (a.getTabId() == 0) {
                            //2、保存选项卡信息
                            MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                            adviceTabPo.setId(null)
                                    .setCreateBy(sysUser.getUsername())
                                    .setName(a.getTabName());
                            adviceTabMapper.insert(adviceTabPo);
                            //3、保存选项卡与广告id到关联表mm_advice_rel_tab
                            MmAdviceRelTabPo adviceRelTabPo = new MmAdviceRelTabPo();
                            adviceRelTabPo.setId(null)
                                    .setAdviceId(saveRelTabDto.getAdviceId())
                                    .setTabId(adviceTabPo.getId())
                                    .setCreateBy(sysUser.getUsername());
                            relTabMapper.insert(adviceRelTabPo);
                            a.getTabRelAssociatedDtos().forEach(b -> {
                                if (goodsMapper.selectById(b.getAssociatedId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在id为[%s]的商品,请检查", b.getAssociatedId()));
                                }
                                //4、保存该选项卡下的商品到mm_advice_rel_tab_things
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setId(null)
                                        .setType(AssociationTypeEnum.Goods.getId())
                                        .setCreateBy(sysUser.getUsername())
                                        .setTabId(adviceTabPo.getId())
                                        .setAssociationId(b.getAssociatedId());
                                relTabThingsMapper.insert(relTabThingsPo);
                            });
                        }
                        //修改操作
                        else {
                            if (relTabMapper.selectOne(new QueryWrapper<MmAdviceRelTabPo>().lambda()
                                    .eq(MmAdviceRelTabPo::getTabId, a.getTabId())
                                    .eq(MmAdviceRelTabPo::getAdviceId, saveRelTabDto.getAdviceId())) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该广告【%s】不存在选项卡【%s】,请检查",
                                        saveRelTabDto.getName(), a.getTabName()));
                            }
                            //2、修改选项卡信息
                            MmAdviceTabPo adviceTabPo = adviceTabMapper.selectById(a.getTabId());
                            adviceTabPo.setUpdateBy(sysUser.getUsername())
                                    .setName(a.getTabName());
                            adviceTabMapper.updateById(adviceTabPo);

                            //4、更新选项卡与商品关联表，获取该选项卡下绑定的商品
                            List<Long> tabGoodsIdList = relTabThingsMapper.selectList(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, a.getTabId())).stream().map(b -> b.getAssociationId()).collect(Collectors.toList());
                            if (!ListUtil.isListNullAndEmpty(tabGoodsIdList)) {
                                List<Long> delGoodsIds = new ArrayList<>(tabGoodsIdList);
                                //前端传的值
                                List<Long> newGoodsIds = new ArrayList<>(a.getTabRelAssociatedDtos().stream().map(r -> r.getAssociatedId()).collect(Collectors.toList()));
                                //获取需要删除的商品并删除
                                delGoodsIds.removeAll(a.getTabRelAssociatedDtos().stream().map(r -> r.getAssociatedId()).collect(Collectors.toList()));
                                //获取新增的商品并保存到数据库中
                                newGoodsIds.removeAll(tabGoodsIdList);
                                if (!ListUtil.isListNullAndEmpty(delGoodsIds)) {
                                    delGoodsIds.forEach(c -> {
                                        relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                                .eq(MmAdviceRelTabThingsPo::getAssociationId, c)
                                                .eq(MmAdviceRelTabThingsPo::getTabId, a.getTabId()));
                                    });
                                }

                                if (!ListUtil.isListNullAndEmpty(newGoodsIds)) {
                                    //保存该选项卡下的商品到mm_advice_rel_tab_things
                                    newGoodsIds.forEach(c -> {
                                        MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                        relTabThingsPo.setId(null)
                                                .setType(AssociationTypeEnum.BRAND.getId())
                                                .setCreateBy(sysUser.getUsername())
                                                .setTabId(a.getTabId())
                                                .setAssociationId(c);
                                        relTabThingsMapper.insert(relTabThingsPo);
                                    });
                                }
                            }

//                            //4、更新选项卡与商品关联表
//                            Map<String, Object> map = Maps.newHashMap();
//                            map.put("tab_id", a.getTabId());
//                            relTabThingsMapper.deleteByMap(map);
//                            //遍历该选项卡下的商品
//                            a.getAssociatedIds().forEach(c -> {
//                                if (goodsMapper.selectById(c) == null) {
//                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在id为[%s]的商品,请检查", c));
//                                }
//                                //保存该选项卡下的品牌到mm_advice_rel_tab_things
//                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
//                                relTabThingsPo.setId(null)
//                                        .setType(AssociationTypeEnum.BRAND.getId())
//                                        .setCreateBy(sysUser.getUsername())
//                                        .setTabId(a.getTabId())
//                                        .setAssociationId(c);
//                                relTabThingsMapper.insert(relTabThingsPo);
//                            });
                        }

                    });

                    break;

            }
        }
    }

    /**
     * 条件分页查询选项卡已经关联的商品
     *
     * @param searchTabAssociatedGoodsDto
     * @return
     */
    @Override
    public PageInfo<GoodsVo> searchTabAssociatedGoods(SearchTabAssociatedGoodsDto searchTabAssociatedGoodsDto) {
        Integer PageNo = searchTabAssociatedGoodsDto.getPageNo() == null ? defaultPageNo : searchTabAssociatedGoodsDto.getPageNo();
        Integer pageSize = searchTabAssociatedGoodsDto.getPageSize() == null ? defaultPageSize : searchTabAssociatedGoodsDto.getPageSize();
        PageInfo<GoodsVo> goodsVoPageInfo = PageHelper.startPage(PageNo, pageSize)
                .doSelectPageInfo(() -> relTabMapper.searchTabAssociatedGoods(searchTabAssociatedGoodsDto));

        return goodsVoPageInfo;
    }

    /**
     * 条件分页查询选项卡已经关联的品牌
     *
     * @param searchTabAssociatedBrandsDto
     * @return
     */
    @Override
    public PageInfo<BrandVo> searchTabAssociatedBrands(SearchTabAssociatedBrandsDto searchTabAssociatedBrandsDto) {
        Integer PageNo = searchTabAssociatedBrandsDto.getPageNo() == null ? defaultPageNo : searchTabAssociatedBrandsDto.getPageNo();
        Integer pageSize = searchTabAssociatedBrandsDto.getPageSize() == null ? defaultPageSize : searchTabAssociatedBrandsDto.getPageSize();
        PageInfo<BrandVo> brandVoPageInfo = PageHelper.startPage(PageNo, pageSize)
                .doSelectPageInfo(() -> relTabMapper.searchTabAssociatedBrands(searchTabAssociatedBrandsDto));

        return brandVoPageInfo;
    }
}
