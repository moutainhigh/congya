package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.app.advice.AssociationTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.group.AmActivityGroupPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.domain.po.message.advice.*;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.category.SmStoreCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.SaveActivityGroupAdviceDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.SaveStoreClassificationDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.StoreTabsDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.*;
import com.chauncy.data.mapper.activity.group.AmActivityGroupMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelActivityGoodsMapper;
import com.chauncy.data.mapper.message.advice.*;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.store.category.SmStoreCategoryMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo;
import com.chauncy.message.advice.IMmAdviceRelTabAssociationService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表与广告选项卡表关联表，广告位置为具体店铺分类下面不同选项卡+推荐的店铺，多重关联选项卡 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MmAdviceRelTabAssociationServiceImpl extends AbstractService<MmAdviceRelTabAssociationMapper, MmAdviceRelTabAssociationPo> implements IMmAdviceRelTabAssociationService {

    @Autowired
    private MmAdviceRelTabAssociationMapper relTabAssociationMapper;

    @Autowired
    private MmAdviceRelAssociaitonMapper relAssociaitonMapper;

    @Autowired
    private MmAdviceRelShufflingMapper relShufflingMapper;

    @Autowired
    private AmActivityRelActivityGoodsMapper relActivityGoodsMapper;

    @Autowired
    private MmAdviceMapper adviceMapper;

    @Autowired
    private AmActivityGroupMapper activityGroupMapper;

    @Autowired
    private MmAdviceTabMapper adviceTabMapper;

    @Autowired
    private SmStoreCategoryMapper storeCategoryMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private MmInformationMapper informationMapper;

    @Autowired
    private MmAdviceRelTabThingsMapper relTabThingsMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 分页查询店铺分类
     *
     * @param searchStoreClassificationDto
     * @return
     */
    @Override
    public PageInfo<BaseVo> searchStoreClassification(SearchStoreClassificationDto searchStoreClassificationDto) {
//        //获取全部的店铺分类
        List<Long> allStoreCategoryIds = storeCategoryMapper.selectList(new QueryWrapper<SmStoreCategoryPo>().lambda().and(obj -> obj
                .eq(SmStoreCategoryPo::getEnabled, true)
                .eq(SmStoreCategoryPo::getDelFlag, false))).stream().map(a -> a.getId()).collect(Collectors.toList());
        if (ListUtil.isListNullAndEmpty(allStoreCategoryIds)) {
            return new PageInfo<>();
        }
        //获取该广告位已经关联的店铺分类
        List<Long> associatedIds = relAssociaitonMapper.selectList(new QueryWrapper<MmAdviceRelAssociaitonPo>().lambda()
                .eq(MmAdviceRelAssociaitonPo::getAdviceId, searchStoreClassificationDto.getAdviceId())).stream().map(a -> a.getAssociationId()).collect(Collectors.toList());
        //排除已经关联的店铺分类ID
        List<Long> idLists = Lists.newArrayList();
        if (ListUtil.isListNullAndEmpty(associatedIds)) {
            idLists = allStoreCategoryIds;
            associatedIds = null;
        } else {
            List<Long> finalAssociatedIds = associatedIds;
            idLists = allStoreCategoryIds.stream().filter(b -> !finalAssociatedIds.contains(b)).collect(Collectors.toList());
        }
        if (ListUtil.isListNullAndEmpty(idLists)) {
            return new PageInfo<>();
        }
        Integer pageNo = searchStoreClassificationDto.getPageNo() == null ? defaultPageNo : searchStoreClassificationDto.getPageNo();
        Integer pageSize = searchStoreClassificationDto.getPageSize() == null ? defaultPageSize : searchStoreClassificationDto.getPageSize();
        List<Long> finalIdLists = idLists;
        List<Long> finalAssociatedIds1 = associatedIds;
        PageInfo<BaseVo> storeClassificationVo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> storeCategoryMapper.selectIds(searchStoreClassificationDto.getName(), finalAssociatedIds1));

        return storeClassificationVo;
    }


    /**
     * 分页查询店铺分类下店铺信息
     *
     * @param searchClassificationStoreDto
     * @return
     */
    @Override
    public PageInfo<BaseVo> searchClassificationStore(SearchClassificationStoreDto searchClassificationStoreDto) {
        //获取该店铺分类下的所有店铺
        List<Long> allStoreIds = storeMapper.selectList(new QueryWrapper<SmStorePo>().lambda().and(obj ->
                obj.eq(SmStorePo::getStoreCategoryId, searchClassificationStoreDto.getStoreClassificationId())
                        .eq(SmStorePo::getEnabled, true))).stream().map(a -> a.getId()).collect(Collectors.toList());
        if (ListUtil.isListNullAndEmpty(allStoreIds)) {
            return new PageInfo<>();
        }

        //获取已经关联的该广告下的店铺分类下的店铺
        List<Long> associatedIds = relTabThingsMapper.selectList(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda().and(obj -> obj
                .eq(MmAdviceRelTabThingsPo::getTabId, searchClassificationStoreDto.getTabId()))).stream().map(a -> a.getAssociationId()).collect(Collectors.toList());

        //排除已经关联的店铺ID
        List<Long> idList = Lists.newArrayList();
        //关联的店铺ID为空（这个为了防止sql语句 not in 出错）
        if (ListUtil.isListNullAndEmpty(associatedIds)) {
            associatedIds = null;
        } else {
            List<Long> finalAssociatedIds = associatedIds;
            idList = allStoreIds.stream().filter(b -> !finalAssociatedIds.contains(b)).collect(Collectors.toList());
            if (ListUtil.isListNullAndEmpty(idList)) {
                return new PageInfo<>();
            }
        }

        Integer PageNo = searchClassificationStoreDto.getPageNo() == null ? defaultPageNo : searchClassificationStoreDto.getPageNo();
        Integer pageSize = searchClassificationStoreDto.getPageSize() == null ? defaultPageSize : searchClassificationStoreDto.getPageSize();
        List<Long> finalAssociatedIds1 = associatedIds;
        PageInfo<BaseVo> storesVo = PageHelper.startPage(PageNo, pageSize)
                .doSelectPageInfo(() -> storeMapper.searchStores(searchClassificationStoreDto.getStoreClassificationId(), searchClassificationStoreDto.getName(), finalAssociatedIds1));

        return storesVo;
    }

    /**
     * 条件分页查询已经关联的店铺
     *
     * @param searchStoresDto
     * @return
     */
    @Override
    public PageInfo<StoreVo> searchStores(SearchStoresDto searchStoresDto) {

        Integer PageNo = searchStoresDto.getPageNo() == null ? defaultPageNo : searchStoresDto.getPageNo();
        Integer pageSize = searchStoresDto.getPageSize() == null ? defaultPageSize : searchStoresDto.getPageSize();
        PageInfo<StoreVo> storesVo = PageHelper.startPage(PageNo, pageSize)
                .doSelectPageInfo(() -> relTabThingsMapper.searchStores(searchStoresDto));

        return storesVo;
    }

    /**
     * 首页有店+店铺分类详情
     * <p>
     * 保存广告位置为首页有店+店铺分类详情的信息
     *
     * @param saveStoreClassificationDto
     * @return
     */
    @Override
    public void saveStoreClassification(SaveStoreClassificationDto saveStoreClassificationDto) {

        SysUserPo sysUser = securityUtil.getCurrUser();
        MmAdvicePo advicePo = new MmAdvicePo();

        //添加操作
        if (saveStoreClassificationDto.getAdviceId() == 0) {
            //判断一: 广告名称不能相同
            List<String> nameList = adviceMapper.selectList(null).stream().map(a -> a.getName()).collect(Collectors.toList());
            if (nameList.contains(saveStoreClassificationDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveStoreClassificationDto.getName()));
            }
            //1、保存广告信息到广告表——mm_advice
            BeanUtils.copyProperties(saveStoreClassificationDto, advicePo);
            advicePo.setCreateBy(sysUser.getUsername());
            advicePo.setId(null);
            adviceMapper.insert(advicePo);

            //判断二: 不能有相同的推荐分类,判断新增的店铺分类不能相同
            // 通过去重之后的HashSet长度来判断原list是否包含重复元素
            List<Long> storeClassificationIds = saveStoreClassificationDto.getStoreTabsDtoList().stream().map(classification -> classification.getStoreClassificationId()).collect(Collectors.toList());
            boolean isRepeat = storeClassificationIds.size() != new HashSet<Long>(storeClassificationIds).size();
            if (isRepeat) {
                //存放重复的数据
                List<String> names = Lists.newArrayList();
                //获取重复的店铺分类ID
                Map<Long, Integer> repeatMap = Maps.newHashMap();
                storeClassificationIds.forEach(ints -> {
                    Integer i = 1;//定义一个计时器，计算重复的个数
                    if (repeatMap.get(ints) != null) {
                        i = repeatMap.get(ints) + 1;
                    }
                    repeatMap.put(ints, i);
                });
                for (Long l : repeatMap.keySet()) {
                    if (repeatMap.get(l) > 1) {
                        names.add(storeCategoryMapper.selectById(l).getName());
                    }
                }
                log.info("重复数据为：" + names.toString());
                throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的店铺分类：%s,请检查!", names.toString()));
            }
            //获取前端传的店铺分类及其对应tabs选项卡
            List<StoreTabsDto> storeTabsDtoList = saveStoreClassificationDto.getStoreTabsDtoList();
            storeTabsDtoList.stream().forEach(a -> {
                //判断三：同个广告下的同个店铺分类不能有相同名称的选项卡
                List<String> tabNames = a.getTabInfos().stream().map(tabs -> tabs.getTabName()).collect(Collectors.toList());
                boolean tabNamesIsRepeat = tabNames.size() != new HashSet<String>(tabNames).size();
                if (tabNamesIsRepeat) {
                    //存放重复的数据
                    List<String> repeatTabNames = Lists.newArrayList();
                    //获取重复的选项卡名称
                    Map<String, Integer> repeatMap = Maps.newHashMap();
                    tabNames.forEach(str -> {
                        Integer i = 1;//定义一个计时器，计算重复的个数
                        if (repeatMap.get(str) != null) {
                            i = repeatMap.get(str) + 1;
                        }
                        repeatMap.put(str, i);
                    });
                    for (String s : repeatMap.keySet()) {
                        if (repeatMap.get(s) > 1) {
                            repeatTabNames.add(s);
                        }
                    }
                    log.info("重复数据为：" + repeatTabNames.toString());
                    throw new ServiceException(ResultCode.DUPLICATION, String.format("店铺分类:【%s】存在重复选项卡名称：%s,请检查!",
                            storeCategoryMapper.selectById(a.getStoreClassificationId()).getName(), repeatTabNames.toString()));
                }
                //判断四：同个广告下的同个店铺分类不同的选项卡不能有相同店铺
                List<Long> stores = Lists.newArrayList();
                a.getTabInfos().stream().forEach(store -> {
                    store.getStoreIds().forEach(ids -> {
                        stores.add(ids);
                    });
                });

                boolean storeIsRepeat = stores.size() != new HashSet<Long>(stores).size();
                if (storeIsRepeat) {
                    //存放重复的数据
                    List<String> storeNames = Lists.newArrayList();
                    //获取重复的店铺ID
                    Map<Long, Integer> repeatMap = Maps.newHashMap();
                    stores.forEach(storeId -> {
                        Integer i = 1;
                        if (repeatMap.get(storeId) != null) {
                            i = repeatMap.get(storeId) + 1;
                        }
                        repeatMap.put(storeId, i);
                    });
                    for (Long l : repeatMap.keySet()) {
                        if (repeatMap.get(l) > 1) {
                            storeNames.add(storeMapper.selectById(l).getName());
                        }
                    }
                    log.info("重复数据为：" + storeNames.toString());
                    throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的店铺名称：%s,请检查!", storeNames.toString()));
                }

                //2、接着获取店铺分类并保存到广告关联表mm_advice_rel_associaiton
                MmAdviceRelAssociaitonPo relAssociaitonPo = new MmAdviceRelAssociaitonPo();
                relAssociaitonPo.setAdviceId(advicePo.getId())
                        .setAssociationId(a.getStoreClassificationId())
                        .setCreateBy(sysUser.getUsername())
                        .setId(null)
                        .setType(AssociationTypeEnum.STORE_CLASSIFICATION.getId());
                relAssociaitonMapper.insert(relAssociaitonPo);
                //获取店铺分类对应的Tabs选项卡内容
                a.getTabInfos().stream().forEach(b -> {
                    //3、保存选项卡信息
                    MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                    adviceTabPo.setName(b.getTabName());
                    adviceTabPo.setId(null);
                    adviceTabPo.setCreateBy(sysUser.getUsername());
                    adviceTabMapper.insert(adviceTabPo);
                    //4、保存选项卡和店铺分类的信息
                    MmAdviceRelTabAssociationPo relTabAssociationPo = new MmAdviceRelTabAssociationPo();
                    relTabAssociationPo.setCreateBy(sysUser.getUsername())
                            .setId(null)
                            .setRelId(relAssociaitonPo.getId())
                            .setTabId(adviceTabPo.getId());
                    relTabAssociationMapper.insert(relTabAssociationPo);
                    //5、保存每个选项卡关联的店铺ID
                    MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                    b.getStoreIds().stream().forEach(c -> {
                        relTabThingsPo.setCreateBy(sysUser.getUsername())
                                .setId(null)
                                .setTabId(adviceTabPo.getId())
                                .setAssociationId(c)
                                .setType(AssociationTypeEnum.STORE.getId());
                        relTabThingsMapper.insert(relTabThingsPo);
                    });
                });

            });
        }

        //对广告进行编辑操作,编辑操作页面的店铺分类不能修改
        else {
            //操作五:
            //识别哪个与该广告关联的店铺分类被删除了，先获取该广告下所有绑定的店铺分类
            List<Long> allAssociationIds = relAssociaitonMapper.selectList(new QueryWrapper<MmAdviceRelAssociaitonPo>().lambda().
                    eq(MmAdviceRelAssociaitonPo::getAdviceId, saveStoreClassificationDto.getAdviceId())).stream().map(e -> e.getId()).collect(Collectors.toList());
            //获取前端传过来的除了storeClassificationId=0的数据
            List<Long> remainAssociationIds = saveStoreClassificationDto.getStoreTabsDtoList().stream().
                    filter(y -> y.getAdviceAssociationId() != 0).map(associationId -> associationId.getAdviceAssociationId()).collect(Collectors.toList());
            //获取需要被删除的数据
            if (ListUtil.isListNullAndEmpty(allAssociationIds)) {
                List<Long> delIds = Lists.newArrayList();
                if (ListUtil.isListNullAndEmpty(remainAssociationIds)) {
                    delIds = allAssociationIds;
                    delIds.forEach(ac -> {
                        if (relAssociaitonMapper.selectById(ac) == null) {
                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在该广告【%s】关联的店铺分类【%s】",
                                    saveStoreClassificationDto.getName(), ac));
                        }
                        //删除该广告关联店铺记录对应的所有选项卡关联的店铺
                        List<Long> tabIdList = relTabAssociationMapper.selectList(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                eq(MmAdviceRelTabAssociationPo::getRelId, ac)).stream().map(d -> d.getTabId()).collect(Collectors.toList());
                        tabIdList.forEach(e -> {
                            relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, e));
                            //删除该店铺分类下的所有选项卡
                            adviceTabMapper.deleteById(e);
                        });
                        //删除该广告关联店铺记录对应的与选项卡关联的记录
                        relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                eq(MmAdviceRelTabAssociationPo::getRelId, ac));
                    });
                    //删除广告关联店铺记录
                    relAssociaitonMapper.deleteBatchIds(delIds);
                } else {
                    delIds = allAssociationIds.stream().filter(del -> !remainAssociationIds.contains(del)).collect(Collectors.toList());
                    if (!ListUtil.isListNullAndEmpty(delIds)) {
                        delIds.forEach(ac -> {
                            if (relAssociaitonMapper.selectById(ac) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在该广告【%s】关联的店铺分类【%s】",
                                        saveStoreClassificationDto.getName(), ac));
                            }
                            //删除该广告关联店铺记录对应的所有选项卡关联的店铺
                            List<Long> tabIdList = relTabAssociationMapper.selectList(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                    eq(MmAdviceRelTabAssociationPo::getRelId, ac)).stream().map(d -> d.getTabId()).collect(Collectors.toList());
                            tabIdList.forEach(e -> {
                                relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                        .eq(MmAdviceRelTabThingsPo::getTabId, e));
                                //删除该店铺分类下的所有选项卡
                                adviceTabMapper.deleteById(e);
                            });
                            //删除该广告关联店铺记录对应的与选项卡关联的记录
                            relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                    eq(MmAdviceRelTabAssociationPo::getRelId, ac));
                        });
                        //删除广告关联店铺记录
                        relAssociaitonMapper.deleteBatchIds(delIds);

                    }
                }
            }

            //判断一：广告名称不能相同
            //获取该广告名称
            String adviceName = adviceMapper.selectById(saveStoreClassificationDto.getAdviceId()).getName();
            //获取除该广告名称之外的所有广告名称
            List<String> nameList = adviceMapper.selectList(null).stream().filter(name -> !name.getName().equals(adviceName)).map(a -> a.getName()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(nameList) && nameList.contains(saveStoreClassificationDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveStoreClassificationDto.getName()));
            }
            //1、更改广告信息到广告表——mm_advice
            MmAdvicePo mmAdvicePo = adviceMapper.selectById(saveStoreClassificationDto.getAdviceId());
            BeanUtils.copyProperties(saveStoreClassificationDto, mmAdvicePo);
            mmAdvicePo.setUpdateBy(sysUser.getUsername());
            adviceMapper.updateById(mmAdvicePo);

            /**判断二: 不能有相同的推荐分类,判断新增的店铺分类不能相同,识别出哪些是需要删除、哪些是需要更改、哪些是需要添加*/

            //不能有相同的推荐分类
            //获取该广告下的店铺分类
            List<Long> storeClassificationIds = saveStoreClassificationDto.getStoreTabsDtoList().stream().map(classification -> classification.getStoreClassificationId()).collect(Collectors.toList());
            // 通过去重之后的HashSet长度来判断原list是否包含重复元素
            boolean isRepeat = storeClassificationIds.size() != new HashSet<Long>(storeClassificationIds).size();
            if (isRepeat) {
                //存放重复的数据
                List<String> names = Lists.newArrayList();
                //获取重复的店铺分类ID
                Map<Long, Integer> repeatMap = Maps.newHashMap();
                storeClassificationIds.forEach(ints -> {
                    Integer i = 1;//定义一个计时器，计算重复的个数
                    if (repeatMap.get(ints) != null) {
                        i = repeatMap.get(ints) + 1;
                    }
                    repeatMap.put(ints, i);
                });
                for (Long l : repeatMap.keySet()) {
                    if (repeatMap.get(l) > 1) {
                        names.add(storeCategoryMapper.selectById(l).getName());
                    }
                }
                log.info("重复数据为：" + names.toString());
                throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的店铺分类：%s,请检查!", names.toString()));
            }

            //识别出哪些是需要删除、哪些是需要更改、哪些是需要添加,执行对应的 操作
            saveStoreClassificationDto.getStoreTabsDtoList().forEach(a -> {

                //当广告和店铺分类关联的ID为空时执行当前的一个storeTabsDtoList新增操作

                if (a.getAdviceAssociationId() == 0) {
                    //判断三：同个广告下的同个店铺分类不能有相同名称的选项卡
                    List<String> tabNames = a.getTabInfos().stream().map(tabs -> tabs.getTabName()).collect(Collectors.toList());
                    boolean tabNamesIsRepeat = tabNames.size() != new HashSet<String>(tabNames).size();
                    if (tabNamesIsRepeat) {
                        //存放重复的数据
                        List<String> repeatTabNames = Lists.newArrayList();
                        //获取重复的选项卡名称
                        Map<String, Integer> repeatMap = Maps.newHashMap();
                        tabNames.forEach(str -> {
                            Integer i = 1;//定义一个计时器，计算重复的个数
                            if (repeatMap.get(str) != null) {
                                i = repeatMap.get(str) + 1;
                            }
                            repeatMap.put(str, i);
                        });
                        for (String s : repeatMap.keySet()) {
                            if (repeatMap.get(s) > 1) {
                                repeatTabNames.add(s);
                            }
                        }
                        log.info("重复数据为：" + repeatTabNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("店铺分类:【%s】存在重复选项卡名称：%s,请检查!",
                                storeCategoryMapper.selectById(a.getStoreClassificationId()).getName(), repeatTabNames.toString()));
                    }
                    //判断四：同个广告下的同个店铺分类不同的选项卡不能有相同店铺
                    List<Long> stores = Lists.newArrayList();
                    a.getTabInfos().stream().forEach(store -> {
                        store.getStoreIds().forEach(ids -> {
                            stores.add(ids);
                        });
                    });
                    boolean storeIsRepeat = stores.size() != new HashSet<Long>(stores).size();
                    if (storeIsRepeat) {
                        //存放重复的数据
                        List<String> storeNames = Lists.newArrayList();
                        //获取重复的店铺ID
                        Map<Long, Integer> repeatMap = Maps.newHashMap();
                        stores.forEach(storeId -> {
                            Integer i = 1;
                            if (repeatMap.get(storeId) != null) {
                                i = repeatMap.get(storeId) + 1;
                            }
                            repeatMap.put(storeId, i);
                        });
                        for (Long l : repeatMap.keySet()) {
                            if (repeatMap.get(l) > 1) {
                                storeNames.add(storeMapper.selectById(l).getName());
                            }
                        }
                        log.info("重复数据为：" + storeNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的店铺名称：%s,请检查!", storeNames.toString()));
                    }
                    //2、接着获取店铺分类并保存到广告关联表mm_advice_rel_associaiton
                    MmAdviceRelAssociaitonPo relAssociaitonPo = new MmAdviceRelAssociaitonPo();
                    relAssociaitonPo.setAdviceId(mmAdvicePo.getId())
                            .setAssociationId(a.getStoreClassificationId())
                            .setCreateBy(sysUser.getUsername())
                            .setId(null)
                            .setType(AssociationTypeEnum.STORE_CLASSIFICATION.getId());
                    relAssociaitonMapper.insert(relAssociaitonPo);
                    //获取店铺分类对应的Tabs选项卡内容
                    a.getTabInfos().stream().forEach(b -> {
                        //3、保存选项卡信息
                        MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                        adviceTabPo.setName(b.getTabName());
                        adviceTabPo.setId(null);
                        adviceTabPo.setCreateBy(sysUser.getUsername());
                        adviceTabMapper.insert(adviceTabPo);
                        //4、保存选项卡和店铺分类的信息
                        MmAdviceRelTabAssociationPo relTabAssociationPo = new MmAdviceRelTabAssociationPo();
                        relTabAssociationPo.setCreateBy(sysUser.getUsername())
                                .setId(null)
                                .setRelId(relAssociaitonPo.getId())
                                .setTabId(adviceTabPo.getId());
                        relTabAssociationMapper.insert(relTabAssociationPo);
                        //5、保存每个选项卡关联的店铺ID
                        MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                        b.getStoreIds().stream().forEach(c -> {
                            if (storeMapper.selectOne(new QueryWrapper<SmStorePo>().lambda().and(ob -> ob.
                                    eq(SmStorePo::getStoreCategoryId, a.getStoreClassificationId()).
                                    eq(SmStorePo::getId, c))) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该店铺【%s】不属于该店铺分类【%s】,请检查",
                                        storeMapper.selectById(c).getName(), storeCategoryMapper.selectById(a.getStoreClassificationId()).getName()));
                            }
                            relTabThingsPo.setCreateBy(sysUser.getUsername())
                                    .setId(null)
                                    .setTabId(adviceTabPo.getId())
                                    .setAssociationId(c)
                                    .setType(AssociationTypeEnum.STORE.getId());
                            relTabThingsMapper.insert(relTabThingsPo);
                        });
                    });
                }

                //当广告和店铺分类关联的ID不为为空时执行当前的一个storeTabsDtoList修改操作
                else {
                    //判断三：同个广告下的同个店铺分类不能有相同名称的选项
                    List<String> tabNames = a.getTabInfos().stream().map(tabs -> tabs.getTabName()).collect(Collectors.toList());
                    boolean tabNamesIsRepeat = tabNames.size() != new HashSet<String>(tabNames).size();
                    if (tabNamesIsRepeat) {
                        //存放重复的数据
                        List<String> repeatTabNames = Lists.newArrayList();
                        //获取重复的选项卡名称
                        Map<String, Integer> repeatMap = Maps.newHashMap();
                        tabNames.forEach(str -> {
                            Integer i = 1;//定义一个计时器，计算重复的个数
                            if (repeatMap.get(str) != null) {
                                i = repeatMap.get(str) + 1;
                            }
                            repeatMap.put(str, i);
                        });
                        for (String s : repeatMap.keySet()) {
                            if (repeatMap.get(s) > 1) {
                                repeatTabNames.add(s);
                            }
                        }
                        log.info("重复数据为：" + repeatTabNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("店铺分类:【%s】存在重复选项卡名称：%s,请检查!",
                                storeCategoryMapper.selectById(a.getStoreClassificationId()).getName(), repeatTabNames.toString()));
                    }
                    //判断四：同个广告下的同个店铺分类不同的选项卡不能有相同店铺
                    List<Long> stores = Lists.newArrayList();
                    a.getTabInfos().stream().forEach(store -> {
                        store.getStoreIds().forEach(ids -> {
                            stores.add(ids);
                        });
                    });
                    boolean storeIsRepeat = stores.size() != new HashSet<Long>(stores).size();
                    if (storeIsRepeat) {
                        //存放重复的数据
                        List<String> storeNames = Lists.newArrayList();
                        //获取重复的店铺ID
                        Map<Long, Integer> repeatMap = Maps.newHashMap();
                        stores.forEach(storeId -> {
                            Integer i = 1;
                            if (repeatMap.get(storeId) != null) {
                                i = repeatMap.get(storeId) + 1;
                            }
                            repeatMap.put(storeId, i);
                        });
                        for (Long l : repeatMap.keySet()) {
                            if (repeatMap.get(l) > 1) {
                                storeNames.add(storeMapper.selectById(l).getName());
                            }
                        }
                        log.info("重复数据为：" + storeNames.toString());
                        throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的店铺名称：%s,请检查!", storeNames.toString()));
                    }
                    //操作五：
                    //识别哪个选项卡被删除了，先获取该广告对应的所有选项卡ID
                    List<Long> tabIds = relTabAssociationMapper.selectList(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                            eq(MmAdviceRelTabAssociationPo::getRelId, a.getAdviceAssociationId())).stream().map(ass -> ass.getTabId()).collect(Collectors.toList());
                    //获取前端传过来的除了tabId=0的数据
                    List<Long> remainTabIds = a.getTabInfos().stream().filter(z -> z.getTabId() != 0).map(tabId -> tabId.getTabId()).collect(Collectors.toList());
                    if (!ListUtil.isListNullAndEmpty(tabIds)) {
                        List<Long> delTabIds = Lists.newArrayList();
                        if (ListUtil.isListNullAndEmpty(remainTabIds)) {
                            delTabIds = tabIds;
                            delTabIds.forEach(del -> {
                                //删除该广告下的店铺分类下的选项卡
                                relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                        eq(MmAdviceRelTabAssociationPo::getTabId, del));
                                //删除该选项卡关联的店铺
                                relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda().
                                        eq(MmAdviceRelTabThingsPo::getTabId, del));
                                //删除该选项卡
                                adviceTabMapper.deleteById(del);
                            });
                        } else {
                            delTabIds = tabIds.stream().filter(del -> !remainTabIds.contains(del)).collect(Collectors.toList());
                            if (!ListUtil.isListNullAndEmpty(delTabIds)) {
                                delTabIds.forEach(del -> {
                                    //删除该广告下的店铺分类下的选项卡
                                    relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                            eq(MmAdviceRelTabAssociationPo::getTabId, del));
                                    //删除该选项卡关联的店铺
                                    relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda().
                                            eq(MmAdviceRelTabThingsPo::getTabId, del));
                                    //删除该选项卡
                                    adviceTabMapper.deleteById(del);
                                });
                            }

                        }

                    }
                    //判断选项卡是否为新增，当tabId为0时为新增，不为0为修改
                    a.getTabInfos().forEach(b -> {
                        //新增选项卡
                        if (b.getTabId() == 0) {
                            //3、保存选项卡信息
                            MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                            adviceTabPo.setName(b.getTabName());
                            adviceTabPo.setId(null);
                            adviceTabPo.setCreateBy(sysUser.getUsername());
                            adviceTabMapper.insert(adviceTabPo);
                            //4、保存选项卡和店铺分类的信息
                            MmAdviceRelTabAssociationPo relTabAssociationPo = new MmAdviceRelTabAssociationPo();
                            relTabAssociationPo.setCreateBy(sysUser.getUsername())
                                    .setId(null)
                                    .setRelId(a.getAdviceAssociationId())
                                    .setTabId(adviceTabPo.getId());
                            relTabAssociationMapper.insert(relTabAssociationPo);
                            //5、保存每个选项卡关联的店铺ID
                            MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                            b.getStoreIds().stream().forEach(c -> {
                                if (storeMapper.selectOne(new QueryWrapper<SmStorePo>().lambda().and(ob -> ob.
                                        eq(SmStorePo::getStoreCategoryId, a.getStoreClassificationId()).
                                        eq(SmStorePo::getId, c))) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("该店铺【%s】不属于该店铺分类【%s】,请检查",
                                            storeMapper.selectById(c).getName(), storeCategoryMapper.selectById(a.getStoreClassificationId()).getName()));
                                }
                                relTabThingsPo.setCreateBy(sysUser.getUsername())
                                        .setId(null)
                                        .setTabId(adviceTabPo.getId())
                                        .setAssociationId(c)
                                        .setType(AssociationTypeEnum.STORE.getId());
                                relTabThingsMapper.insert(relTabThingsPo);
                            });
                        }
                        //修改选项卡的内容（编辑和删除）
                        else {
                            //3、编辑并保存选项卡信息
                            MmAdviceTabPo adviceTabPo = adviceTabMapper.selectById(b.getTabId());
                            if (relTabAssociationMapper.selectOne(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().and(obje ->
                                    obje.eq(MmAdviceRelTabAssociationPo::getTabId, b.getTabId())
                                            .eq(MmAdviceRelTabAssociationPo::getRelId, a.getAdviceAssociationId()))) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该广告位【%s】下的店铺分类【%s】不存在该选项卡【%s】",
                                        saveStoreClassificationDto.getName(), storeCategoryMapper.selectById(a.getStoreClassificationId()).getName(), b.getTabName()));
                            }
                            adviceTabPo.setName(b.getTabName());
                            adviceTabPo.setId(b.getTabId());
                            adviceTabPo.setUpdateBy(sysUser.getUsername());
                            adviceTabMapper.updateById(adviceTabPo);
                            //5、更新选项卡与店铺关联表
                            Map<String, Object> map = Maps.newHashMap();
                            map.put("tab_id", b.getTabId());
                            relTabThingsMapper.deleteByMap(map);
                            b.getStoreIds().stream().forEach(c -> {
                                //保存每个选项卡关联的店铺ID
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setCreateBy(sysUser.getUsername())
                                        .setId(null)
                                        .setTabId(b.getTabId())
                                        .setAssociationId(c)
                                        .setType(AssociationTypeEnum.STORE.getId());
                                relTabThingsMapper.insert(relTabThingsPo);
                            });

                        }
                    });
                }

            });
        }
    }

    /**
     * @return void
     * @Author chauncy
     * @Date 2019-09-22 22:14
     * @Description //保存积分、满减活动广告
     * @Update chauncy
     * @Param [saveActivityGroupAdviceDto]
     **/
    @Override
    public void saveActivityGroupAdvice(SaveActivityGroupAdviceDto saveActivityGroupAdviceDto) {

        SysUserPo sysUser = securityUtil.getCurrUser();
        MmAdvicePo advicePo = new MmAdvicePo();

        Integer relAssociationType = null;

        AdviceLocationEnum adviceLocationEnum = AdviceLocationEnum.fromEnumName(saveActivityGroupAdviceDto.getLocation());
        if (adviceLocationEnum.equals(AdviceLocationEnum.INTEGRALS_ACTIVITY)) {
            relAssociationType = AssociationTypeEnum.INTEGRALS_GROUP.getId();
        } else {
            relAssociationType = AssociationTypeEnum.REDUCED_GROUP.getId();
        }

        /********************公共判断 Start*************/

        /******************** 公共判断1：同个广告下的活动分组不能重复 ****************************/
        List<Long> activityGroupIds = saveActivityGroupAdviceDto.getActivityGroupDtoList().stream().map(group -> group.getActivityGroupId()).collect(Collectors.toList());
        // 通过去重之后的HashSet长度来判断原list是否包含重复元素
        boolean isRepeat = activityGroupIds.size() != new HashSet<Long>(activityGroupIds).size();
        if (isRepeat) {
            //存放重复的数据
            List<String> names = Lists.newArrayList();
            //获取重复的店铺分类ID
            Map<Long, Integer> repeatMap = Maps.newHashMap();
            activityGroupIds.forEach(ints -> {
                AmActivityGroupPo activityGroupPo = activityGroupMapper.selectById(ints);
                if (activityGroupPo == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在id为:%s的活动分组", ints));
                }
                Integer i = 1;//定义一个计时器，计算重复的个数
                if (repeatMap.get(ints) != null) {
                    i = repeatMap.get(ints) + 1;
                }
                repeatMap.put(ints, i);
            });
            for (Long l : repeatMap.keySet()) {
                if (repeatMap.get(l) > 1) {
                    names.add(activityGroupMapper.selectById(l).getName());
                }
            }
            log.info("重复数据为：" + names.toString());
            throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的活动分组：%s,请检查!", names.toString()));
        }
        /******************** 公共判断2：同个广告下同个活动分组下不同热销广告下关联的商品不能重复 ****************************/
        saveActivityGroupAdviceDto.getActivityGroupDtoList().forEach(c -> {
            List<Long> goods = Lists.newArrayList();
            c.getActivitySellHotTabInfosDtoList().stream().forEach(good -> {
                good.getGoodsIds().forEach(ids -> {
                    goods.add(ids);
                });
            });
            boolean goodIsRepeat = goods.size() != new HashSet<Long>(goods).size();
            if (goodIsRepeat) {
                //存放重复的数据
                List<String> goodsNames = Lists.newArrayList();
                //获取重复的商品ID
                Map<Long, Integer> repeatMap = Maps.newHashMap();
                goods.forEach(storeId -> {
                    Integer i = 1;
                    if (repeatMap.get(storeId) != null) {
                        i = repeatMap.get(storeId) + 1;
                    }
                    repeatMap.put(storeId, i);
                });
                for (Long l : repeatMap.keySet()) {
                    if (repeatMap.get(l) > 1) {
                        goodsNames.add(goodsMapper.selectById(l).getName());
                    }
                }
                log.info("重复数据为：" + goodsNames.toString());
                throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复的商品名称：%s,请检查!", goodsNames.toString()));
            }
        });
        /******************** 公共判断3：同个广告下同个活动分组下轮播图关联的商品、资讯、店铺不能重复 ****************************/
        saveActivityGroupAdviceDto.getActivityGroupDtoList().forEach(d -> {
            List<Long> associatedList = Lists.newArrayList();
            d.getActivityGroupShufflingDtoList().forEach(e -> {
                if (e.getAdviceType() != AdviceTypeEnum.HTML_DETAIL.getId()) {
                    associatedList.add(e.getDetailId());
                }
            });

            boolean goodIsRepeat = associatedList.size() != new HashSet<Long>(associatedList).size();
            if (goodIsRepeat) {
                throw new ServiceException(ResultCode.DUPLICATION, String.format("轮播图存在重复的关联,请检查!"));
            }

        });

        /******************** 公共判断4：商品是否是该活动分组下且活动生效的商品 ****************************/
//        saveActivityGroupAdviceDto.getActivityGroupDtoList().forEach(a->{
//            a.getActivitySellHotTabInfosDtoList().forEach(c->{
//                AmActivityRelActivityGoodsPo relActivityGoodsPo = relActivityGoodsMapper.selectOne(new QueryWrapper<AmActivityRelActivityGoodsPo>().lambda().and(obj->obj
//                        .eq(AmActivityRelActivityGoodsPo::)))
//            });
//        });

        /*****************公共判断 End****************/

        //添加操作
        if (saveActivityGroupAdviceDto.getAdviceId() == 0) {
            /******************** 一：广告名称不能重复 ****************************/
            List<String> nameList = adviceMapper.selectList(null).stream().map(a -> a.getName()).collect(Collectors.toList());
            if (nameList.contains(saveActivityGroupAdviceDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveActivityGroupAdviceDto.getName()));
            }

            /**########################## 1、保存广告信息到广告表——mm_advice ################################## */
            BeanUtils.copyProperties(saveActivityGroupAdviceDto, advicePo);
            advicePo.setCreateBy(sysUser.getUsername());
            advicePo.setId(null);
            adviceMapper.insert(advicePo);
            /**########################## 2、保存活动分组到广告关联表——mm_advice_rel_Association ################################## */
            Integer finalRelAssociationType = relAssociationType;
            Integer finalRelAssociationType1 = relAssociationType;
            saveActivityGroupAdviceDto.getActivityGroupDtoList().forEach(a -> {
                MmAdviceRelAssociaitonPo relAssociaitonPo = new MmAdviceRelAssociaitonPo();
                relAssociaitonPo.setAdviceId(advicePo.getId())
                        .setAssociationId(a.getActivityGroupId())
                        .setCreateBy(sysUser.getUsername())
                        .setId(null)
                        .setType(finalRelAssociationType);
                relAssociaitonMapper.insert(relAssociaitonPo);
                a.getActivitySellHotTabInfosDtoList().forEach(b -> {
                    /**########################## 3、活动中部热销广告选项卡到选项卡表——mm_advice_tab ################*/
                    MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                    adviceTabPo.setName(b.getTabName());
                    adviceTabPo.setId(null);
                    adviceTabPo.setCreateBy(sysUser.getUsername());
                    adviceTabMapper.insert(adviceTabPo);
                    /**########################## 4、保存活动中部热销广告选项卡下和活动分组的信息到选项卡和广告下绑定的活动分组关联表到——mm_advice_rel_tab_association表 ################*/
                    MmAdviceRelTabAssociationPo relTabAssociationPo = new MmAdviceRelTabAssociationPo();
                    relTabAssociationPo.setCreateBy(sysUser.getUsername())
                            .setId(null)
                            .setRelId(relAssociaitonPo.getId())
                            .setTabId(adviceTabPo.getId());
                    relTabAssociationMapper.insert(relTabAssociationPo);
                    /**########################## 5、保存每个选项卡关联的商品ID到mm_advice_rel_tab_things ################*/
                    MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                    b.getGoodsIds().stream().forEach(c -> {
                        relTabThingsPo.setCreateBy(sysUser.getUsername())
                                .setId(null)
                                .setTabId(adviceTabPo.getId())
                                .setAssociationId(c)
                                .setType(finalRelAssociationType1);
                        relTabThingsMapper.insert(relTabThingsPo);
                    });
                });
                /**########################## 6、保存该广告下不同活动分组的轮播图信息——mm_advice_rel_shuffling ################################## */
                a.getActivityGroupShufflingDtoList().forEach(d -> {
                    //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                    if (d.getEndTime().isBefore(d.getStartTime()) || d.getEndTime().equals(d.getStartTime())) {
                        throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                    }
                    if (d.getStartTime().isBefore(LocalDateTime.now())) {
                        throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                    }
                    if (d.getEndTime().isBefore(LocalDateTime.now())) {
                        throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                    }

                    MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                    //获取广告类型
                    AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(d.getAdviceType());
                    switch (adviceTypeEnum) {
                        case HTML_DETAIL:
                            relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(advicePo.getId())
                                    .setAdviceType(adviceTypeEnum).setHtmlDetail(d.getHtmlDetail()).setRelTabBrandId(null)
                                    .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                    .setCoverPhoto(d.getCoverPhoto()).setDetailId(null).setRelCategoryId(null)
                                    .setFirstCategoryId(null).setRelActivityGroupId(relAssociaitonPo.getId());
                            relShufflingMapper.insert(relShufflingPo);
                            break;
                        case INFORMATION:
                        case STROE:
                        case GOODS:
                            if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                if (goodsMapper.selectById(d.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", d.getDetailId()));
                                }
                            } else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                if (informationMapper.selectById(d.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", d.getDetailId()));
                                }
                            } else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                if (storeMapper.selectById(d.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", d.getDetailId()));
                                }
                            }
                            relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(advicePo.getId())
                                    .setAdviceType(adviceTypeEnum).setDetailId(d.getDetailId()).setRelTabBrandId(null)
                                    .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                    .setCoverPhoto(d.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null)
                                    .setFirstCategoryId(null).setRelActivityGroupId(relAssociaitonPo.getId());
                            relShufflingMapper.insert(relShufflingPo);
                            break;
                    }
                });
            });
        }
        //修改操作
        else {
            /*****************一：删除活动分组 start***************/
            //获取该广告下全部的活动分组信息
            List<Long> allActivityGroupIds = relAssociaitonMapper.selectList(new QueryWrapper<MmAdviceRelAssociaitonPo>().lambda().
                    eq(MmAdviceRelAssociaitonPo::getAdviceId, saveActivityGroupAdviceDto.getAdviceId())).stream()
                    .map(e -> e.getId()).collect(Collectors.toList());
            //获取前端传的值
            List<Long> remainIds = saveActivityGroupAdviceDto.getActivityGroupDtoList().stream().filter(y -> y.getRelAdviceActivityGroupId() != 0)
                    .map(remains -> remains.getRelAdviceActivityGroupId()).collect(Collectors.toList());

            if (!ListUtil.isListNullAndEmpty(allActivityGroupIds)) {
                List<Long> delIds = new ArrayList<>();
                if (ListUtil.isListNullAndEmpty(remainIds)) {
                    delIds = allActivityGroupIds;
                    delIds.forEach(a -> {
                        if (relAssociaitonMapper.selectById(a) == null) {
                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在该广告【%s】关联的活动分类【%s】",
                                    saveActivityGroupAdviceDto.getName(), a));
                        }
                        //删除该广告关联活动分组记录对应的所有选项卡关联的商品记录
                        List<Long> tabIdList = relTabAssociationMapper.selectList(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                eq(MmAdviceRelTabAssociationPo::getRelId, a)).stream().map(d -> d.getTabId()).collect(Collectors.toList());
                        tabIdList.forEach(e -> {
                            relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, e));
                            //删除该活动分类下的所有选项卡
                            adviceTabMapper.deleteById(e);
                        });
                        //删除该广告关联活动分组记录对应的与选项卡关联的记录
                        relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                eq(MmAdviceRelTabAssociationPo::getRelId, a));
                        //删除该广告下的活动分组的轮播图广告
                        relShufflingMapper.delete(new QueryWrapper<MmAdviceRelShufflingPo>().lambda().and(obj -> obj
                                .eq(MmAdviceRelShufflingPo::getRelActivityGroupId, a)));
                    });
                    //删除该广告关联该活动分组的记录
                    relAssociaitonMapper.deleteBatchIds(delIds);
                } else {
                    delIds = allActivityGroupIds.stream().filter(a -> !remainIds.contains(a)).collect(Collectors.toList());
                    if (!ListUtil.isListNullAndEmpty(delIds)) {
                        delIds.forEach(a -> {
                            if (relAssociaitonMapper.selectById(a) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("不存在该广告【%s】关联的活动分类【%s】",
                                        saveActivityGroupAdviceDto.getName(), a));
                            }
                            //删除该广告关联活动分组记录对应的所有选项卡关联的商品记录
                            List<Long> tabIdList = relTabAssociationMapper.selectList(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                    eq(MmAdviceRelTabAssociationPo::getRelId, a)).stream().map(d -> d.getTabId()).collect(Collectors.toList());
                            tabIdList.forEach(e -> {
                                relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                        .eq(MmAdviceRelTabThingsPo::getTabId, e));
                                //删除该活动分类下的所有选项卡
                                adviceTabMapper.deleteById(e);
                            });
                            //删除该广告关联活动分组记录对应的与选项卡关联的记录
                            relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                    eq(MmAdviceRelTabAssociationPo::getRelId, a));
                            //删除该广告下的活动分组的轮播图广告
                            relShufflingMapper.delete(new QueryWrapper<MmAdviceRelShufflingPo>().lambda().and(obj -> obj
                                    .eq(MmAdviceRelShufflingPo::getRelActivityGroupId, a)));
                        });
                        //删除该广告关联该活动分组的记录
                        relAssociaitonMapper.deleteBatchIds(delIds);
                    }
                }
            }
            /*****************一：删除活动分组 end***************/

            /*****************二：删除活动分组下的选项卡 start***************/
            saveActivityGroupAdviceDto.getActivityGroupDtoList().forEach(a -> {
                //获取该广告下对应的活动分组下的所有热销广告选项卡
                List<Long> allSelHotTabIds = relTabAssociationMapper.selectList(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                        eq(MmAdviceRelTabAssociationPo::getRelId, a.getRelAdviceActivityGroupId())).stream().map(ass -> ass.getTabId()).collect(Collectors.toList());
                //获取前端传过来的除了tabId=0的数据
                List<Long> remainTabIds = a.getActivitySellHotTabInfosDtoList().stream().filter(z -> z.getTabId() != 0).map(tabId -> tabId.getTabId()).collect(Collectors.toList());
                if (!ListUtil.isListNullAndEmpty(allSelHotTabIds)) {
                    List<Long> delTabIds = Lists.newArrayList();
                    if (ListUtil.isListNullAndEmpty(remainIds)) {
                        delTabIds = allSelHotTabIds;
                        delTabIds.forEach(b -> {
                            relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                    .eq(MmAdviceRelTabThingsPo::getTabId, b));
                        });
                        //删除该活动分组下的所有选项卡
                        adviceTabMapper.deleteBatchIds(delTabIds);
                        //删除该广告关联活动分组记录对应的与选项卡关联的记录
                        relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                eq(MmAdviceRelTabAssociationPo::getRelId, a.getRelAdviceActivityGroupId()));
                    } else {
                        delTabIds = allSelHotTabIds.stream().filter(delId -> !remainIds.contains(delId)).collect(Collectors.toList());
                        if (!ListUtil.isListNullAndEmpty(delTabIds)) {
                            delTabIds.forEach(b -> {
                                relTabThingsMapper.delete(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                                        .eq(MmAdviceRelTabThingsPo::getTabId, b));
                            });
                            //删除该活动分组下的所有选项卡
                            adviceTabMapper.deleteBatchIds(delTabIds);
                            //删除该广告关联活动分组记录对应的与选项卡关联的记录
                            relTabAssociationMapper.delete(new QueryWrapper<MmAdviceRelTabAssociationPo>().lambda().
                                    eq(MmAdviceRelTabAssociationPo::getRelId, a.getRelAdviceActivityGroupId()));
                        }
                    }
                }
            });
            /*****************二：删除活动分组下的选项卡 end***************/

            /*****************二：删除活动分组下的轮播图 start***************/
            saveActivityGroupAdviceDto.getActivityGroupDtoList().forEach(a -> {
                //获取该广告下对应的活动分组下的所有轮播图
                List<Long> allShufflingIds = relShufflingMapper.selectList(new QueryWrapper<MmAdviceRelShufflingPo>()
                        .lambda().eq(MmAdviceRelShufflingPo::getRelActivityGroupId,a.getRelAdviceActivityGroupId()))
                        .stream().map(all->all.getId()).collect(Collectors.toList());
                //获取前端传的轮播图信息
                List<Long> remainShufflingIds = a.getActivityGroupShufflingDtoList().stream().filter(z -> z.getShufflingId() != 0).map(shuffling -> shuffling.getShufflingId()).collect(Collectors.toList());

                if (!ListUtil.isListNullAndEmpty(allShufflingIds)){
                    List<Long> delShufflingIds = Lists.newArrayList();
                    if (ListUtil.isListNullAndEmpty(remainShufflingIds)){
                        delShufflingIds = allShufflingIds;
                        relShufflingMapper.deleteBatchIds(delShufflingIds);
                    }else {
                        delShufflingIds = allShufflingIds.stream().filter(t->!remainIds.contains(t)).collect(Collectors.toList());
                        relShufflingMapper.deleteBatchIds(delShufflingIds);
                    }
                }

            });
            /*****************二：删除活动分组下的轮播图 end***************/

            /*****************二：广告名称不能相同 start***************/
            //获取该广告名称
            String adviceName = adviceMapper.selectById(saveActivityGroupAdviceDto.getAdviceId()).getName();
            //获取除该广告名称之外的所有广告名称
            List<String> nameList = adviceMapper.selectList(null).stream().filter(name -> !name.getName().equals(adviceName)).map(a -> a.getName()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(nameList) && nameList.contains(saveActivityGroupAdviceDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveActivityGroupAdviceDto.getName()));
            }
            /*****************二：广告名称不能相同 end***************/

            //1、更改广告信息到广告表——mm_advice
            MmAdvicePo mmAdvicePo = adviceMapper.selectById(saveActivityGroupAdviceDto.getAdviceId());
            BeanUtils.copyProperties(saveActivityGroupAdviceDto, mmAdvicePo);
            mmAdvicePo.setUpdateBy(sysUser.getUsername());
            adviceMapper.updateById(mmAdvicePo);

            //识别出哪些活动分组是需要更改、哪些活动分组是需要添加,执行对应的操作
            Integer finalRelAssociationType2 = relAssociationType;
            saveActivityGroupAdviceDto.getActivityGroupDtoList().forEach(a -> {
                //活动分组新增操作，热销广告、关联轮播图都是新增操作
                if (a.getRelAdviceActivityGroupId() == 0) {
                    /**########################## 2、保存活动分组到广告关联表——mm_advice_rel_Association ################################## */
                    MmAdviceRelAssociaitonPo relAssociaitonPo = new MmAdviceRelAssociaitonPo();
                    relAssociaitonPo.setAdviceId(saveActivityGroupAdviceDto.getAdviceId())
                            .setAssociationId(a.getActivityGroupId())
                            .setCreateBy(sysUser.getUsername())
                            .setId(null)
                            .setType(finalRelAssociationType2);
                    relAssociaitonMapper.insert(relAssociaitonPo);
                    a.getActivitySellHotTabInfosDtoList().forEach(b -> {
                        /**########################## 3、活动中部热销广告选项卡到选项卡表——mm_advice_tab ################*/
                        MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                        adviceTabPo.setName(b.getTabName());
                        adviceTabPo.setId(null);
                        adviceTabPo.setCreateBy(sysUser.getUsername());
                        adviceTabMapper.insert(adviceTabPo);
                        /**########################## 4、保存活动中部热销广告选项卡下和活动分组的信息到选项卡和广告下绑定的活动分组关联表到——mm_advice_rel_tab_association表 ################*/
                        MmAdviceRelTabAssociationPo relTabAssociationPo = new MmAdviceRelTabAssociationPo();
                        relTabAssociationPo.setCreateBy(sysUser.getUsername())
                                .setId(null)
                                .setRelId(relAssociaitonPo.getId())
                                .setTabId(adviceTabPo.getId());
                        relTabAssociationMapper.insert(relTabAssociationPo);
                        /**########################## 5、保存每个选项卡关联的商品ID到mm_advice_rel_tab_things ################*/
                        MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                        b.getGoodsIds().stream().forEach(c -> {
                            relTabThingsPo.setCreateBy(sysUser.getUsername())
                                    .setId(null)
                                    .setTabId(adviceTabPo.getId())
                                    .setAssociationId(c)
                                    .setType(finalRelAssociationType2);
                            relTabThingsMapper.insert(relTabThingsPo);
                        });
                    });
                    /**########################## 6、保存该广告下不同活动分组的轮播图信息——mm_advice_rel_shuffling ################################## */
                    a.getActivityGroupShufflingDtoList().forEach(d -> {
                        //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                        if (d.getEndTime().isBefore(d.getStartTime()) || d.getEndTime().equals(d.getStartTime())) {
                            throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                        }
                        if (d.getStartTime().isBefore(LocalDateTime.now())) {
                            throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                        }
                        if (d.getEndTime().isBefore(LocalDateTime.now())) {
                            throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                        }

                        MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                        //获取广告类型
                        AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(d.getAdviceType());
                        switch (adviceTypeEnum) {
                            case HTML_DETAIL:
                                relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveActivityGroupAdviceDto.getAdviceId())
                                        .setAdviceType(adviceTypeEnum).setHtmlDetail(d.getHtmlDetail()).setRelTabBrandId(null)
                                        .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                        .setCoverPhoto(d.getCoverPhoto()).setDetailId(null).setRelCategoryId(null)
                                        .setFirstCategoryId(null).setRelActivityGroupId(relAssociaitonPo.getId());
                                relShufflingMapper.insert(relShufflingPo);
                                break;
                            case INFORMATION:
                            case STROE:
                            case GOODS:
                                if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                    if (goodsMapper.selectById(d.getDetailId()) == null) {
                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", d.getDetailId()));
                                    }
                                } else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                    if (informationMapper.selectById(d.getDetailId()) == null) {
                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", d.getDetailId()));
                                    }
                                } else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                    if (storeMapper.selectById(d.getDetailId()) == null) {
                                        throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", d.getDetailId()));
                                    }
                                }
                                relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveActivityGroupAdviceDto.getAdviceId())
                                        .setAdviceType(adviceTypeEnum).setDetailId(d.getDetailId()).setRelTabBrandId(null)
                                        .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                        .setCoverPhoto(d.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null)
                                        .setFirstCategoryId(null).setRelActivityGroupId(relAssociaitonPo.getId());
                                relShufflingMapper.insert(relShufflingPo);
                                break;
                        }
                    });
                }
                //活动分组编辑操作
                else {
                    //热销选项卡操作
                    a.getActivitySellHotTabInfosDtoList().forEach(b->{
                        //新增热销选项卡
                        if (b.getTabId() == 0){
                            /**########################## 1、活动中部热销广告选项卡到选项卡表——mm_advice_tab ################*/
                            MmAdviceTabPo adviceTabPo = new MmAdviceTabPo();
                            adviceTabPo.setName(b.getTabName());
                            adviceTabPo.setId(null);
                            adviceTabPo.setCreateBy(sysUser.getUsername());
                            adviceTabMapper.insert(adviceTabPo);
                            /**########################## 2、保存活动中部热销广告选项卡下和活动分组的信息到选项卡和广告下绑定的活动分组关联表到——mm_advice_rel_tab_association表 ################*/
                            MmAdviceRelTabAssociationPo relTabAssociationPo = new MmAdviceRelTabAssociationPo();
                            relTabAssociationPo.setCreateBy(sysUser.getUsername())
                                    .setId(null)
                                    .setRelId(a.getRelAdviceActivityGroupId())
                                    .setTabId(adviceTabPo.getId());
                            relTabAssociationMapper.insert(relTabAssociationPo);
                            /**########################## 3、保存每个选项卡关联的商品ID到mm_advice_rel_tab_things ################*/
                            MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                            b.getGoodsIds().stream().forEach(c -> {
                                relTabThingsPo.setCreateBy(sysUser.getUsername())
                                        .setId(null)
                                        .setTabId(adviceTabPo.getId())
                                        .setAssociationId(c)
                                        .setType(finalRelAssociationType2);
                                relTabThingsMapper.insert(relTabThingsPo);
                            });
                        }
                        //编辑热销选项卡
                        else{
                            /**########################## 1、更新活动中部热销广告选项卡到选项卡表——mm_advice_tab ################*/
                            MmAdviceTabPo adviceTab = adviceTabMapper.selectByTabId(b.getTabId());
                            adviceTab.setName(b.getTabName());
                            adviceTab.setCreateBy(sysUser.getUsername());
                            adviceTabMapper.updateById(adviceTab);
                            //5、更新选项卡与商品关联表
                            Map<String, Object> map = Maps.newHashMap();
                            map.put("tab_id", b.getTabId());
                            relTabThingsMapper.deleteByMap(map);
                            b.getGoodsIds().stream().forEach(c -> {
                                //保存每个选项卡关联的店商品ID
                                MmAdviceRelTabThingsPo relTabThingsPo = new MmAdviceRelTabThingsPo();
                                relTabThingsPo.setCreateBy(sysUser.getUsername())
                                        .setId(null)
                                        .setTabId(b.getTabId())
                                        .setAssociationId(c)
                                        .setType(finalRelAssociationType2);
                                relTabThingsMapper.insert(relTabThingsPo);
                            });
                        }
                    });
                    //轮播图操作
                    a.getActivityGroupShufflingDtoList().forEach(d->{
                        //新增轮播图
                        if (d.getShufflingId() == 0) {
                            //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                            if (d.getEndTime().isBefore(d.getStartTime()) || d.getEndTime().equals(d.getStartTime())) {
                                throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                            }
                            if (d.getStartTime().isBefore(LocalDateTime.now())) {
                                throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                            }
                            if (d.getEndTime().isBefore(LocalDateTime.now())) {
                                throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                            }

                            MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                            //获取广告类型
                            AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(d.getAdviceType());
                            switch (adviceTypeEnum) {
                                case HTML_DETAIL:
                                    relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveActivityGroupAdviceDto.getAdviceId())
                                            .setAdviceType(adviceTypeEnum).setHtmlDetail(d.getHtmlDetail()).setRelTabBrandId(null)
                                            .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                            .setCoverPhoto(d.getCoverPhoto()).setDetailId(null).setRelCategoryId(null)
                                            .setFirstCategoryId(null).setRelActivityGroupId(a.getRelAdviceActivityGroupId());
                                    relShufflingMapper.insert(relShufflingPo);
                                    break;
                                case INFORMATION:
                                case STROE:
                                case GOODS:
                                    if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                        if (goodsMapper.selectById(d.getDetailId()) == null) {
                                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", d.getDetailId()));
                                        }
                                    } else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                        if (informationMapper.selectById(d.getDetailId()) == null) {
                                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", d.getDetailId()));
                                        }
                                    } else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                        if (storeMapper.selectById(d.getDetailId()) == null) {
                                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", d.getDetailId()));
                                        }
                                    }
                                    relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveActivityGroupAdviceDto.getAdviceId())
                                            .setAdviceType(adviceTypeEnum).setDetailId(d.getDetailId()).setRelTabBrandId(null)
                                            .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                            .setCoverPhoto(d.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null)
                                            .setFirstCategoryId(null).setRelActivityGroupId(a.getRelAdviceActivityGroupId());
                                    relShufflingMapper.insert(relShufflingPo);
                                    break;

                            }
                        }
                        //编辑轮播图操作
                        else {
                            //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                            if (d.getEndTime().isBefore(d.getStartTime()) || d.getEndTime().equals(d.getStartTime())) {
                                throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                            }
                            //判断是否修改开始时间和结束时间
                            LocalDateTime startTime = relShufflingMapper.selectById(d.getShufflingId()).getStartTime();
                            LocalDateTime endTime = relShufflingMapper.selectById(d.getShufflingId()).getEndTime();
                            if (!startTime.equals(d.getStartTime())) {
                                //已经开始但还没结束，不能修改开始时间
                                if (startTime.isBefore(LocalDateTime.now()) && endTime.isAfter(LocalDateTime.now())) {
                                    throw new ServiceException(ResultCode.FAIL, String.format("存在轮播图广告已经开始,不能修改开始时间,请检查"));
                                }
                                if (d.getStartTime().isBefore(LocalDateTime.now())) {
                                    throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                                }
                            }
                            if (!endTime.equals(d.getEndTime())) {
                                if (d.getEndTime().isBefore(LocalDateTime.now())) {
                                    throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                                }
                            }

                            MmAdviceRelShufflingPo relShufflingPo = relShufflingMapper.selectById(d.getShufflingId());
                            //获取广告类型
                            AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(d.getAdviceType());
                            switch (adviceTypeEnum) {
                                case HTML_DETAIL:
                                    relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveActivityGroupAdviceDto.getAdviceId())
                                            .setAdviceType(adviceTypeEnum).setHtmlDetail(d.getHtmlDetail()).setRelTabBrandId(null)
                                            .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                            .setCoverPhoto(d.getCoverPhoto()).setDetailId(null).setRelCategoryId(null)
                                            .setFirstCategoryId(null).setRelActivityGroupId(a.getRelAdviceActivityGroupId());
                                    relShufflingMapper.insert(relShufflingPo);
                                    break;
                                case INFORMATION:
                                case STROE:
                                case GOODS:
                                    if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                        if (goodsMapper.selectById(d.getDetailId()) == null) {
                                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", d.getDetailId()));
                                        }
                                    } else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                        if (informationMapper.selectById(d.getDetailId()) == null) {
                                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", d.getDetailId()));
                                        }
                                    } else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                        if (storeMapper.selectById(d.getDetailId()) == null) {
                                            throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", d.getDetailId()));
                                        }
                                    }
                                    relShufflingPo.setId(null).setCreateBy(sysUser.getUsername()).setAdviceId(saveActivityGroupAdviceDto.getAdviceId())
                                            .setAdviceType(adviceTypeEnum).setDetailId(d.getDetailId()).setRelTabBrandId(null)
                                            .setBrandId(null).setStartTime(d.getStartTime()).setEndTime(d.getEndTime())
                                            .setCoverPhoto(d.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null)
                                            .setFirstCategoryId(null).setRelActivityGroupId(a.getRelAdviceActivityGroupId());
                                    relShufflingMapper.insert(relShufflingPo);
                                    break;
                            }
                        }
                    });

                }
            });
        }
    }

    /**
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo>
     * @Author chauncy
     * @Date 2019-09-24 10:34
     * @Description //条件分页查询参与对应活动的商品信息
     * @Update chauncy
     * @Param [searchActivityGoodsDto]
     **/
    @Override
    public PageInfo<SearchActivityGoodsVo> searchActivityGoods(SearchActivityGoodsDto searchActivityGoodsDto) {

        Integer pageNo = searchActivityGoodsDto.getPageNo() == null ? defaultPageNo : searchActivityGoodsDto.getPageNo();
        Integer pageSize = searchActivityGoodsDto.getPageSize() == null ? defaultPageSize : searchActivityGoodsDto.getPageSize();
        PmGoodsCategoryPo goodsCategoryPo = goodsCategoryMapper.selectById(searchActivityGoodsDto.getCategoryId());

        if (searchActivityGoodsDto.getCategoryId() != null && goodsCategoryPo == null) {
            throw new ServiceException(ResultCode.FAIL, "数据库不存在该商品分类，请检查");
        }

        PageInfo<SearchActivityGoodsVo> searchAdviceGoodsVoPageInfo = new PageInfo<>();

        ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.getActivityTypeEnumById(searchActivityGoodsDto.getActivityType());
        if (activityTypeEnum == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, String.format("[activityType]为[%s]：所传的值在枚举类中不存在！", searchActivityGoodsDto.getActivityType()));
        }

        switch (activityTypeEnum) {
            case NON:
                searchAdviceGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> goodsMapper.searchGoods(searchActivityGoodsDto));
                break;
            case REDUCED:
                if (searchActivityGoodsDto.getActivityGroupId() == null) {
                    throw new ServiceException(ResultCode.FAIL, String.format("广告位置类型为积分活动时活动分组ID：activityGroupId不能为空，请检查"));
                }
                AmActivityGroupPo activityGroupPo1 = activityGroupMapper.selectById(searchActivityGoodsDto.getActivityGroupId());
                if (activityGroupPo1 == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在该活动分组，请检查"));
                }
                searchAdviceGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> goodsMapper.searchReducedGoods(searchActivityGoodsDto));
                break;
            case INTEGRALS:
                if (searchActivityGoodsDto.getActivityGroupId() == null) {
                    throw new ServiceException(ResultCode.FAIL, String.format("广告位置类型为积分活动时活动分组ID：activityGroupId不能为空，请检查"));
                }
                AmActivityGroupPo activityGroupPo = activityGroupMapper.selectById(searchActivityGoodsDto.getActivityGroupId());
                if (activityGroupPo == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在该活动分组，请检查"));
                }
                searchAdviceGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> goodsMapper.searchIntegralsGoods(searchActivityGoodsDto));
                break;
            case SECKILL:
                searchAdviceGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> goodsMapper.searchSeckillGoods(searchActivityGoodsDto));
                break;
            case SPELL_GROUP:
                searchAdviceGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> goodsMapper.searchSpellGroupGoods(searchActivityGoodsDto));
                break;
        }
        //获取商品所属分类
        AtomicReference<String> level3 = new AtomicReference<>("");
        AtomicReference<String> level2 = new AtomicReference<>("");
        AtomicReference<String> level1 = new AtomicReference<>("");
        searchAdviceGoodsVoPageInfo.getList().forEach(a -> {
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


        return searchAdviceGoodsVoPageInfo;
    }

    /**
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.BaseVo>
     * @Author chauncy
     * @Date 2019-09-20 09:31
     * @Description //条件分页查询活动分组信息
     * @Update chauncy
     * @Param [searchActivityGroupDto]
     **/
    @Override
    public PageInfo<BaseVo> searchActivityGroup(SearchActivityGroupDto searchActivityGroupDto) {
        //TODO 此条件分页查询全部信息,前端选择数据的时候需要将已经关联的打钩上

        Integer pageNo = searchActivityGroupDto.getPageNo() == null ? defaultPageNo : searchActivityGroupDto.getPageNo();
        Integer pageSize = searchActivityGroupDto.getPageSize() == null ? defaultPageSize : searchActivityGroupDto.getPageSize();

        PageInfo<BaseVo> activityGroups = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> activityGroupMapper.searchActivityGroup(searchActivityGroupDto));

        return activityGroups;
    }

}
