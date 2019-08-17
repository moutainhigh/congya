package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.app.advice.AssociationTypeEnum;
import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabPo;
import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabThingsPo;
import com.chauncy.data.domain.po.message.advice.MmAdviceTabPo;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.message.advice.tab.tab.add.SaveRelTabDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchAdviceGoodsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedGoodsDto;
import com.chauncy.data.mapper.message.advice.MmAdviceMapper;
import com.chauncy.data.mapper.message.advice.MmAdviceRelTabMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.message.advice.MmAdviceRelTabThingsMapper;
import com.chauncy.data.mapper.message.advice.MmAdviceTabMapper;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.GoodsVo;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.chauncy.common.enums.app.advice.AdviceLocationEnum.SHOUYE_ZHUTI;

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
    private MmAdviceMapper adviceMapper;

    @Autowired
    private MmAdviceTabMapper adviceTabMapper;

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
    public PageInfo<BaseVo> searchAdviceGoods(SearchAdviceGoodsDto searchAdviceGoodsDto) {

        Integer pageNo = searchAdviceGoodsDto.getPageNo() == null ? defaultPageNo : searchAdviceGoodsDto.getPageNo();
        Integer pageSize = searchAdviceGoodsDto.getPageSize() == null ? defaultPageSize : searchAdviceGoodsDto.getPageSize();
        PageInfo<BaseVo> goodsList = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> goodsMapper.searchTabNeedGoods(searchAdviceGoodsDto.getName()));

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
                        a.getAssociatedIds().forEach(b -> {
                            brandIds.add(b);
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
                        a.getAssociatedIds().forEach(b -> {
                            if (brandMapper.selectById(b) == null){
                                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为[%s]的品牌,请检查",b));
                            }
                            //4、保存该选项卡下的品牌到mm_advice_rel_tab_things
                            MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                            relTabThingsPo.setId(null)
                                    .setType(AssociationTypeEnum.BRAND.getId())
                                    .setCreateBy(sysUser.getUsername())
                                    .setTabId(adviceTabPo.getId())
                                    .setAssociationId(b);
                            relTabThingsMapper.insert(relTabThingsPo);
                        });

                    });

                    break;
                case SHOUYE_ZHUTI:
                case SALE:
                case YOUXUAN:
                    //判断三:同一个广告关联的商品不能重复
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        a.getAssociatedIds().forEach(b -> {
                            goodsIds.add(b);
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
                        a.getAssociatedIds().forEach(b -> {
                            if (goodsMapper.selectById(b) == null){
                                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为[%s]的商品,请检查",b));
                            }
                            //4、保存该选项卡下的商品到mm_advice_rel_tab_things
                            MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                            relTabThingsPo.setId(null)
                                    .setType(AssociationTypeEnum.Goods.getId())
                                    .setCreateBy(sysUser.getUsername())
                                    .setTabId(adviceTabPo.getId())
                                    .setAssociationId(b);
                            relTabThingsMapper.insert(relTabThingsPo);
                        });

                    });
                    break;
            }

        }

        //编辑操作(包括修改和删除)
        else {
            /*****************删除 start***************/
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
                    //删除该选项卡关联的商品
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
                        a.getAssociatedIds().forEach(b -> {
                            brandIds.add(b);
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
                            a.getAssociatedIds().forEach(b -> {
                                if (brandMapper.selectById(b) == null){
                                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为[%s]的品牌,请检查",b));
                                }
                                //4、保存该选项卡下的品牌到mm_advice_rel_tab_things
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setId(null)
                                        .setType(AssociationTypeEnum.Goods.getId())
                                        .setCreateBy(sysUser.getUsername())
                                        .setTabId(adviceTabPo.getId())
                                        .setAssociationId(b);
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
                            //4、更新选项卡与品牌/商品关联表
                            Map<String, Object> map = Maps.newHashMap();
                            map.put("tab_id", a.getTabId());
                            relTabThingsMapper.deleteByMap(map);
                            //遍历该选项卡下的品牌
                            a.getAssociatedIds().forEach(c -> {
                                if (brandMapper.selectById(c) == null){
                                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为[%s]的品牌,请检查",c));
                                }
                                //保存该选项卡下的品牌到mm_advice_rel_tab_things
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setId(null)
                                        .setType(AssociationTypeEnum.BRAND.getId())
                                        .setCreateBy(sysUser.getUsername())
                                        .setTabId(a.getTabId())
                                        .setAssociationId(c);
                                relTabThingsMapper.insert(relTabThingsPo);
                            });
                        }

                    });

                    break;
                case SHOUYE_ZHUTI:
                case SALE:
                case YOUXUAN:
                    //判断三:同一个广告关联的商品不能重复
                    saveRelTabDto.getTabInfos().forEach(a -> {
                        a.getAssociatedIds().forEach(b -> {
                            goodsIds.add(b);
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
                            a.getAssociatedIds().forEach(b -> {
                                if (goodsMapper.selectById(b) == null){
                                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为[%s]的商品,请检查",b));
                                }
                                //4、保存该选项卡下的商品到mm_advice_rel_tab_things
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setId(null)
                                        .setType(AssociationTypeEnum.Goods.getId())
                                        .setCreateBy(sysUser.getUsername())
                                        .setTabId(adviceTabPo.getId())
                                        .setAssociationId(b);
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
                            //4、更新选项卡与商品关联表
                            Map<String, Object> map = Maps.newHashMap();
                            map.put("tab_id", a.getTabId());
                            relTabThingsMapper.deleteByMap(map);
                            //遍历该选项卡下的商品
                            a.getAssociatedIds().forEach(c -> {
                                if (goodsMapper.selectById(c) == null){
                                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为[%s]的商品,请检查",c));
                                }
                                //保存该选项卡下的品牌到mm_advice_rel_tab_things
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setId(null)
                                        .setType(AssociationTypeEnum.BRAND.getId())
                                        .setCreateBy(sysUser.getUsername())
                                        .setTabId(a.getTabId())
                                        .setAssociationId(c);
                                relTabThingsMapper.insert(relTabThingsPo);
                            });
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
