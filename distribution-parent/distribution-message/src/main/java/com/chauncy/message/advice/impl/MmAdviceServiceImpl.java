package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.app.advice.AssociationTypeEnum;
import com.chauncy.common.enums.app.advice.ConditionTypeEnum;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.bo.app.order.reward.RewardShopTicketBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.group.AmActivityGroupPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.domain.po.message.advice.*;
import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.advice.brand.select.FindBrandShufflingDto;
import com.chauncy.data.dto.app.advice.brand.select.SearchBrandAndSkuBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.FindRelGoodsParamDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseListDto;
import com.chauncy.data.dto.app.product.FindActivityGoodsCategoryDto;
import com.chauncy.data.dto.app.product.FindTabGoodsListDto;
import com.chauncy.data.dto.app.product.SearchActivityGoodsListDto;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveClassificationAdviceDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAssociatedClassificationDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchInformationCategoryDto;
import com.chauncy.data.mapper.activity.coupon.AmCouponMapper;
import com.chauncy.data.mapper.activity.group.AmActivityGroupMapper;
import com.chauncy.data.mapper.activity.reduced.AmReducedMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelActivityGoodsMapper;
import com.chauncy.data.mapper.message.advice.*;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.category.MmInformationCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.AdviceTabVo;
import com.chauncy.data.vo.app.advice.activity.*;
import com.chauncy.data.vo.app.advice.goods.BrandGoodsVo;
import com.chauncy.data.vo.app.advice.goods.SearchBrandAndSkuBaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseVo;
import com.chauncy.data.vo.app.advice.home.GetAdviceInfoVo;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryDetailVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryInfoVo;
import com.chauncy.data.vo.app.component.ScreenGoodsParamVo;
import com.chauncy.data.vo.app.component.ScreenParamVo;
import com.chauncy.data.vo.app.goods.ActivityGoodsVo;
import com.chauncy.data.vo.manage.message.advice.ClassificationVo;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.chauncy.data.vo.manage.message.advice.shuffling.FindShufflingVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreTabsVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.TabInfosVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.ActivityGroupShufflingVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.ActivitySellHotTabInfosVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.AdviceActivityGroupVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SellHotRelGoodsVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.*;
import com.chauncy.data.vo.supplier.StandardValueAndStatusVo;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
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
    private AmReducedMapper amReducedMapper;

    @Autowired
    private AmActivityRelActivityGoodsMapper amActivityRelActivityGoodsMapper;

    @Autowired
    private AmActivityGroupMapper amActivityGroupMapper;

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
    private PmGoodsAttributeMapper attributeMapper;

    @Autowired
    private AmCouponMapper couponMapper;

    @Autowired
    private PmGoodsSkuMapper skuMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private PmGoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * @Author yeJH
     * @Date 2019/9/24 16:28
     * @Description 点击积分专区，满减专区获取活动分组信息
     *
     * @Update yeJH
     *
     * @param  groupType 活动分组类型 1：满减  2：积分
     * @return java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupListVo>
     **/
    @Override
    public List<ActivityGroupListVo> findActivityGroup(Integer groupType) {

        List<ActivityGroupListVo> activityGroupListVoList ;
        if (groupType.equals(GroupTypeEnum.REDUCED.getId())) {
            //满减活动
            activityGroupListVoList = mapper.findActivityGroup(AdviceLocationEnum.REDUCED_ACTIVITY.name(), groupType);
        } else if(groupType.equals(GroupTypeEnum.INTEGRALS.getId())) {
            //积分活动
            activityGroupListVoList = mapper.findActivityGroup(AdviceLocationEnum.INTEGRALS_ACTIVITY.name(), groupType);
        } else {
            throw new ServiceException(ResultCode.PARAM_ERROR, "groupType参数错误");
        }

        return activityGroupListVoList;

    }

    /**
     * @Author yeJH
     * @Date 2019/9/24 18:01
     * @Description 根据活动分组关联表id获取活动分组详情
     *              获取选项卡信息（满减：热销精选；积分：精选商品）
     *              获取轮播图信息
     *
     * @Update yeJH
     *
     * @param  relId  广告与活动分组关联表id
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupTabVo>>
     **/
    @Override
    public ActivityGroupDetailVo findActivityGroupDetail(Long relId) {
        MmAdviceRelAssociaitonPo adviceRelAssociaitonPo = relAssociaitonMapper.selectById(relId);
        if(null == adviceRelAssociaitonPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "活动分组记录不存在");
        }
        List<ActivityGroupTabVo> activityGroupTabVoList;
        if(adviceRelAssociaitonPo.getType().equals(AssociationTypeEnum.INTEGRALS_GROUP.getId())) {
            //积分活动
            activityGroupTabVoList = mapper.findIntegralsGroupTab(relId);
            activityGroupTabVoList.forEach(activityGroupTabVo -> {
                if(null != activityGroupTabVo.getActivityGoodsVoList()
                        && activityGroupTabVo.getActivityGoodsVoList().size() > 0) {
                    activityGroupTabVo.getActivityGoodsVoList().forEach(goodsVo -> {
                        //积分抵扣金额 = 商品售价 * （积分抵扣比例 / 100）
                        BigDecimal deductibleAmount = BigDecimalUtil.safeMultiply(goodsVo.getSellPrice(),
                                BigDecimalUtil.safeDivide(goodsVo.getDiscountPriceRatio(), new BigDecimal("100")));
                        goodsVo.setDeductibleAmount(deductibleAmount);
                    });
                }
            });
        } else if(adviceRelAssociaitonPo.getType().equals(AssociationTypeEnum.REDUCED_GROUP.getId())) {
            //满减活动
            activityGroupTabVoList = mapper.findReducedGroupTab(relId);
            activityGroupTabVoList.forEach(activityGroupTabVo -> {
                if(null != activityGroupTabVo.getActivityGoodsVoList()
                        && activityGroupTabVo.getActivityGoodsVoList().size() > 0) {
                    //获取商品标签
                    activityGroupTabVo.getActivityGoodsVoList().forEach(goodsVo ->
                            goodsVo.setLabelList(Splitter.on(",").omitEmptyStrings().splitToList(goodsVo.getLabels())));
                }
            });
        } else {
            throw new ServiceException(ResultCode.FAIL, "活动分组关联类型出错");
        }

        //活动分组详情--轮播图
        List<ShufflingVo> shufflingVoList = mapper.getShufflingByRelId(relId);

        ActivityGroupDetailVo activityGroupDetailVo = new ActivityGroupDetailVo();
        activityGroupDetailVo.setActivityGroupTabVoList(activityGroupTabVoList);
        activityGroupDetailVo.setShufflingVoList(shufflingVoList);

        return activityGroupDetailVo;
    }

    /**
     * @Author yeJH
     * @Date 2019/9/25 16:14
     * @Description  获取积分/满减活动商品列表
     *
     * @Update yeJH
     *
     * @param  searchActivityGoodsListDto  查询积分/满减活动商品列表参数
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.ActivityGoodsVo>>
     **/
    @Override
    public PageInfo<ActivityGoodsVo> searchActivityGoodsList(SearchActivityGoodsListDto searchActivityGoodsListDto) {

        PageInfo<ActivityGoodsVo> activityGoodsVoPageInfo;

        //分页
        searchActivityGoodsListDto.setIsPaging(1);

        Integer pageNo = searchActivityGoodsListDto.getPageNo()==null ? defaultPageNo : searchActivityGoodsListDto.getPageNo();
        Integer pageSize = searchActivityGoodsListDto.getPageSize()==null ? defaultPageSize : searchActivityGoodsListDto.getPageSize();

        if(null == searchActivityGoodsListDto.getSortFile()) {
            //默认综合排序
            searchActivityGoodsListDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if(null == searchActivityGoodsListDto.getSortWay()) {
            //默认降序
            searchActivityGoodsListDto.setSortWay(SortWayEnum.DESC);
        }
        if(GroupTypeEnum.REDUCED.getId().equals(searchActivityGoodsListDto.getGroupType())) {
            //满减
            if(null != searchActivityGoodsListDto.getTabId()) {
                //根据选项卡id获取商品列表
                activityGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                        mapper.findReducedGroupTabDetail(searchActivityGoodsListDto));
            } else if(null != searchActivityGoodsListDto.getGroupId()) {
                //根据活动分组id获取商品列表
                activityGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                        mapper.findReducedGroupGoods(searchActivityGoodsListDto));
            } else {
                throw new ServiceException(ResultCode.PARAM_ERROR);
            }
            //获取商品标签
            activityGoodsVoPageInfo.getList().forEach(activityGoodsVo -> {
                activityGoodsVo.setLabelList(Splitter.on(",").omitEmptyStrings().splitToList(activityGoodsVo.getLabels()));
            });
        } else {
            //积分
            if(null != searchActivityGoodsListDto.getTabId()) {
                //根据选项卡id获取商品列表
                activityGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                        mapper.findIntegralsGroupTabDetail(searchActivityGoodsListDto));
            } else if(null != searchActivityGoodsListDto.getGroupId()) {
                //根据活动分组id获取商品列表
                activityGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                        mapper.findIntegralsGroupGoods(searchActivityGoodsListDto));
            } else {
                throw new ServiceException(ResultCode.PARAM_ERROR);
            }
           activityGoodsVoPageInfo.getList().forEach(activityGoodsVo -> {
                //积分抵扣金额 = 商品售价 * （积分抵扣比例 / 100）
                BigDecimal deductibleAmount = BigDecimalUtil.safeMultiply(activityGoodsVo.getSellPrice(),
                        BigDecimalUtil.safeDivide(activityGoodsVo.getDiscountPriceRatio(), new BigDecimal("100")));
                activityGoodsVo.setDeductibleAmount(deductibleAmount);
            });
        }
        return activityGoodsVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/9/26 11:24
     * @Description 获取活动分组下的活动商品一级分类
     *
     * @Update yeJH
     *
     * @param  findActivityGoodsCategoryDto
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    @Override
    public List<BaseVo> findGoodsCategory(FindActivityGoodsCategoryDto findActivityGoodsCategoryDto) {
        List<BaseVo> baseVoList;

        if (null != findActivityGoodsCategoryDto.getGroupId()) {
            Long groupId = findActivityGoodsCategoryDto.getGroupId();
            //活动分组下的满减、积分活动对应的商品一级分类
            AmActivityGroupPo amActivityGroupPo = amActivityGroupMapper.selectById(groupId);
            if(null == amActivityGroupPo) {
                throw new ServiceException(ResultCode.NO_EXISTS, "活动分组记录不存在");
            }
            if(GroupTypeEnum.REDUCED.getId().equals(amActivityGroupPo.getType())) {
                //满减活动
                baseVoList = mapper.findReducedGoodsCategory(groupId);
            } else if(GroupTypeEnum.INTEGRALS.getId().equals(amActivityGroupPo.getType())) {
                //积分活动
                baseVoList = mapper.findIntegralsGoodsCategory(groupId);
            } else {
                throw new ServiceException(ResultCode.FAIL, "活动分组关联类型出错");
            }
        } else if (null != findActivityGoodsCategoryDto.getGoodsId()) {
            //查询商品正在参加的活动
            QueryWrapper<AmActivityRelActivityGoodsPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(AmActivityRelActivityGoodsPo::getGoodsId, findActivityGoodsCategoryDto.getGoodsId())
                    .lt(AmActivityRelActivityGoodsPo::getActivityStartTime, LocalDateTime.now())
                    .gt(AmActivityRelActivityGoodsPo::getActivityEndTime, LocalDateTime.now())
                    .eq(AmActivityRelActivityGoodsPo::getVerifyStatus, VerifyStatusEnum.CHECKED.getId());
            AmActivityRelActivityGoodsPo amActivityRelActivityGoodsPo = amActivityRelActivityGoodsMapper.selectOne(queryWrapper);
            if(null != amActivityRelActivityGoodsPo) {
                //满减活动去凑单商品列表对应的商品一级分类
                baseVoList = amReducedMapper.findReducedGoodsCategory(amActivityRelActivityGoodsPo.getActivityId());
            } else {
                throw new ServiceException(ResultCode.FAIL, "该商品没有参加满减活动");
            }
        } else {
            throw new ServiceException(ResultCode.PARAM_ERROR, "goodsId跟groupId参数不能都为空");
        }



        return baseVoList;
    }

    /**
     * @Author yeJH
     * @Date 2019/9/26 13:55
     * @Description 点击选项卡获取3个热销/推荐商品
     *
     * @Update yeJH
     *
     * @param  findTabGoodsListDto
     * @return java.util.List<com.chauncy.data.vo.app.goods.ActivityGoodsVo>
     **/
    @Override
    public List<ActivityGoodsVo> findTabGoodsList(FindTabGoodsListDto findTabGoodsListDto) {

        MmAdviceTabPo mmAdviceTabPo = tabMapper.selectById(findTabGoodsListDto.getTabId());
        if(null == mmAdviceTabPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }

        SearchActivityGoodsListDto searchActivityGoodsListDto = new SearchActivityGoodsListDto();
        BeanUtils.copyProperties(findTabGoodsListDto, searchActivityGoodsListDto);
        searchActivityGoodsListDto.setIsPaging(0);

        List<ActivityGoodsVo> activityGoodsVoList = new ArrayList<>();

        if(GroupTypeEnum.REDUCED.getId().equals(findTabGoodsListDto.getGroupType())) {
            activityGoodsVoList = mapper.findReducedGroupTabDetail(searchActivityGoodsListDto);
        } else if (GroupTypeEnum.INTEGRALS.getId().equals(findTabGoodsListDto.getGroupType())){
            activityGoodsVoList = mapper.findIntegralsGroupTabDetail(searchActivityGoodsListDto);
        } else {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }

        return activityGoodsVoList;
    }

    /**
     * @Author yeJH
     * @Date 2019/9/26 20:53
     * @Description 获取APP首页限时秒杀，积分抵现，囤货鸭，拼团鸭
     *
     * @Update yeJH
     *
     * @param
     * @return com.chauncy.data.vo.app.advice.activity.HomePageActivityVo
     **/
    @Override
    public HomePageActivityVo findHomePageActivity() {

        List<HomePageActivityGoodsVo> goodsVoList = mapper.findHomePageActivity();

        HomePageActivityVo homePageActivityVo = new HomePageActivityVo();
        List<HomePageActivityGoodsVo> secKillGoods = new ArrayList<>();
        List<HomePageActivityGoodsVo> integralsGoods = new ArrayList<>();
        List<HomePageActivityGoodsVo> reducedGoods = new ArrayList<>();
        List<HomePageActivityGoodsVo> spellGroupGoods = new ArrayList<>();
        goodsVoList.stream().forEach(homePageActivityGoodsVo -> {
            //ActivityTypeEnum
            switch (homePageActivityGoodsVo.getGroupType()) {
                case 1:
                    reducedGoods.add(homePageActivityGoodsVo);
                    break;
                case 2:
                    integralsGoods.add(homePageActivityGoodsVo);
                    break;
                case 3:
                    if(null != homePageActivityGoodsVo.getActivityEndTime()) {
                        homePageActivityGoodsVo.setEndTime(
                                homePageActivityGoodsVo.getActivityEndTime().toEpochSecond(ZoneOffset.of("+8")));
                    }
                    secKillGoods.add(homePageActivityGoodsVo);
                    break;
                case 4:
                    spellGroupGoods.add(homePageActivityGoodsVo);
                    break;
            }
        });
        homePageActivityVo.setReducedGoods(reducedGoods)
                .setIntegralsGoods(integralsGoods)
                .setSecKillGoods(secKillGoods)
                .setSpellGroupGoods(spellGroupGoods);
        return homePageActivityVo;

    }

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
     * 获取有店下的店铺分类
     *
     * @return
     */
    @Override
    public List<StoreCategoryInfoVo> findStoreCategory() {
        return mapper.findStoreCategory(AdviceLocationEnum.STORE_DETAIL.name());
    }

    /**
     * 获取资讯动态下推荐的分类
     * @return
     */
    @Override
    public List<BaseVo> findInfoCategory() {
        return mapper.findInfoCategory(AdviceLocationEnum.information_recommended.name());
    }

    /**
     * 获取有店下的店铺分类选项卡内容
     *
     * @param relId
     * @return
     */
    @Override
    public List<AdviceTabVo> findStoreCategoryTab(Long relId) {
        MmAdviceRelAssociaitonPo adviceRelAssociaitonPo = relAssociaitonMapper.selectById(relId);
        if(null == adviceRelAssociaitonPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }
        return mapper.findStoreCategoryTab(relId);
    }

    /**
     * 根据选项卡id获取有店下的店铺分类详情
     *
     * @param tabId
     * @return
     */
    @Override
    public PageInfo<StoreCategoryDetailVo> findStoreCategoryDetail(Long tabId,  BaseSearchPagingDto baseSearchPagingDto) {
        MmAdviceTabPo mmAdviceTabPo = tabMapper.selectById(tabId);
        if(null == mmAdviceTabPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }
        //查找关联信息
        Integer pageNo = baseSearchPagingDto.getPageNo() == null ? defaultPageNo : baseSearchPagingDto.getPageNo();
        Integer pageSize  = baseSearchPagingDto.getPageSize() == null ? defaultPageSize : baseSearchPagingDto.getPageSize();

        Long userId = securityUtil.getAppCurrUser().getId();
        PageInfo<StoreCategoryDetailVo> storeCategoryDetailVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.findStoreCategoryDetail(tabId, userId));

        storeCategoryDetailVoPageInfo.getList().forEach(storeCategoryDetailVo -> {
            if (null != storeCategoryDetailVo.getStoreLabels()){
                storeCategoryDetailVo.setStoreLabelList(Splitter.on(",")
                        .omitEmptyStrings().splitToList(storeCategoryDetailVo.getStoreLabels()));
            }
        });
        return storeCategoryDetailVoPageInfo;
    }

    /**
     * 首页跳转内容-有店（所有店铺列表）
     * @param baseSearchPagingDto
     * @return
     */
    @Override
    public PageInfo<StoreCategoryDetailVo> searchAll(BaseSearchPagingDto baseSearchPagingDto) {

        //查找关联信息
        Integer pageNo = baseSearchPagingDto.getPageNo() == null ? defaultPageNo : baseSearchPagingDto.getPageNo();
        Integer pageSize  = baseSearchPagingDto.getPageSize() == null ? defaultPageSize : baseSearchPagingDto.getPageSize();

        Long userId = securityUtil.getAppCurrUser().getId();
        PageInfo<StoreCategoryDetailVo> storeCategoryDetailVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchAllStoreDetail(userId));

        storeCategoryDetailVoPageInfo.getList().forEach(storeCategoryDetailVo -> {
            if (null != storeCategoryDetailVo.getStoreLabels()){
                storeCategoryDetailVo.setStoreLabelList(Splitter.on(",")
                        .omitEmptyStrings().splitToList(storeCategoryDetailVo.getStoreLabels()));
            }
        });
        return storeCategoryDetailVoPageInfo;
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
            if (adviceLocationEnum == null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库存储的枚举值：%s有错，请检查",a.getLocation()));
            }
            switch (adviceLocationEnum) {

                /******************* Tab start ****************/
                //首页有店+店铺分类详情
                case STORE_DETAIL:
                    //获取该广告下的店铺分类信息以及该广告与店铺分类关联的ID
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

                 //积分满减活动广告
                case INTEGRALS_ACTIVITY:
                case REDUCED_ACTIVITY:
                    //获取该广告下的活动分组
                    List<AdviceActivityGroupVo> adviceActivityGroupVos = relAssociaitonMapper.findAdviceActivityGroupVos(a.getAdviceId());
                    //热销广告选项卡以及选项卡关联的商品
                    adviceActivityGroupVos.forEach(b->{
                        List<ActivitySellHotTabInfosVo> activitySellHotTabInfosVos = relTabAssociationMapper.findActivitySellHotTabInfos(b.getRelAdviceActivityGroupId());
                        activitySellHotTabInfosVos.forEach(c->{
                            PageInfo<SellHotRelGoodsVo> goodsList =  PageHelper.startPage(defaultPageNo,defaultPageSize)
                                    .doSelectPageInfo(() -> relTabThingsMapper.findSellHotGoodsList(c.getSellHotTabId()));
                            c.setSellHotRelGoods(goodsList);
                        });
                        b.setActivitySellHotTabInfosVos(activitySellHotTabInfosVos);
                        //活动分组对应的轮播图
                       List<ActivityGroupShufflingVo> activityGroupShufflingVos = relShufflingMapper.findActivityGroupShuffling(b.getRelAdviceActivityGroupId());
                       activityGroupShufflingVos.forEach(d->{
                           AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(d.getAdviceType());
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
                       b.setActivityGroupShufflingVos(activityGroupShufflingVos);
                    });
                    a.setDetail(adviceActivityGroupVos);

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
                                AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(d.getAdviceType());
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
                case BAIHUO_MIDDLE:
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
                case SPELL_GROUP_SHUFFLING:
                case REDUCED_INSIDE_SHUFFLING:
                case INTEGRALS_INSIDE_HUFFLING:
                case COUPON:
                case EXPERIENCE_PACKAGE:
                    List<FindShufflingVo> shufflingVoList = relShufflingMapper.findShuffling(a.getAdviceId());
                    shufflingVoList.forEach(b -> {
                        AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(b.getAdviceType());
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

                /*******************充值入口+拼团鸭+优惠券+个人中心顶部*********************/
                case TOP_UP_ENTRY:
                case INVITATION:
                case PERSONAL_CENTER:
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
                //积分、满减活动广告
                case STORE_DETAIL:
                case INTEGRALS_ACTIVITY:
                case REDUCED_ACTIVITY:
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
                        if (adviceLocationEnum.getId() == AdviceLocationEnum.INTEGRALS_ACTIVITY.getId() ||
                            adviceLocationEnum.getId() == AdviceLocationEnum.REDUCED_ACTIVITY.getId()){
                            //删除该广告下的活动分组的轮播图广告
                            relShufflingMapper.delete(new QueryWrapper<MmAdviceRelShufflingPo>().lambda().and(obj -> obj
                                    .eq(MmAdviceRelShufflingPo::getRelActivityGroupId, c)));
                        }
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
                case SPELL_GROUP_SHUFFLING:
                case INTEGRALS_INSIDE_HUFFLING:
                case REDUCED_INSIDE_SHUFFLING:
                case COUPON:
                case EXPERIENCE_PACKAGE:
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
                case INVITATION:
                case PERSONAL_CENTER:
                case TOP_UP_ENTRY:
                    break;
            }

        });
        //最后一步，删除对应的广告
        mapper.deleteBatchIds(idList);

    }

    /**
     * 保存充值入口+拼团鸭+拼团鸭广告+个人中心顶部背景图+邀请有礼
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
        if (baseUpdateStatusDto.getEnabled()) {
            //判断该广告对应的广告位置是否已有启用广告，若有则置为0
            String location = mapper.selectById(id).getLocation();
            //获取该广告位置下所有启用的广告
            List<MmAdvicePo> advicePos = mapper.selectList(new QueryWrapper<MmAdvicePo>().lambda()
                    .eq(MmAdvicePo::getLocation, location)).stream().filter(a -> a.getEnabled().equals(true)).collect(Collectors.toList());
            advicePos.forEach(b -> {
                b.setEnabled(false);
                mapper.updateById(b);
            });
        }
            MmAdvicePo advicePo = mapper.selectById(id);
            advicePo.setEnabled(baseUpdateStatusDto.getEnabled()).setUpdateBy(user.getUsername());
            mapper.updateById(advicePo);
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
     * 根据选项卡分页获取特卖、主题、优选、百货中部广告等选项卡关联的商品基本信息
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

        goodsBaseVoPageInfo.getList().forEach(a->{
            if (a.getLinePrice() == null){
                a.setLinePrice(a.getSellPrice());
            }
            //获取商品的标签
            List<String> labelNames = mapper.getLabelNames(a.getGoodsId());
            a.setLabelNames(labelNames);

        });
        return goodsBaseVoPageInfo;
    }

    /**
     * 根据选项卡分页获取关联的品牌和商品具体的sku基本信息
     *
     * 这里显示的商品是具体的sku信息
     *
     * @param searchBrandAndSkuBaseDto
     * @return
     */
    @Override
    public PageInfo<SearchBrandAndSkuBaseVo> searchBrandAndSkuBase(SearchBrandAndSkuBaseDto searchBrandAndSkuBaseDto) {
        Integer pageNo = searchBrandAndSkuBaseDto.getPageNo() == null ? defaultPageNo : searchBrandAndSkuBaseDto.getPageNo();
        Integer pageSize = searchBrandAndSkuBaseDto.getPageSize() == null ? defaultPageSize : searchBrandAndSkuBaseDto.getPageSize();
        PageInfo<SearchBrandAndSkuBaseVo> searchBrandAndSkuBaseVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchBrandBase(searchBrandAndSkuBaseDto));
        //获取品牌下的销量前6的商品
        searchBrandAndSkuBaseVoPageInfo.getList().forEach(a->{
            //获取具体的sku信息
            List<BrandGoodsVo> brandGoodsVos = skuMapper.findBrandGoodsVos(a.getBrandId());
            brandGoodsVos.forEach(b->{
                if (b.getLinePrice() == null || b.getLinePrice().compareTo(new BigDecimal(0))==0){
                    b.setLinePrice(b.getSellPrice());
                }
            });
            a.setBrandGoodsVos(brandGoodsVos);
        });

        return searchBrandAndSkuBaseVoPageInfo;
    }

    /**
     * 分页条件查询首页下面的商品列表/品牌id/选项卡id/商品分类id/葱鸭百货关联/优惠券关联下的商品列表
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    @Override
    public PageInfo<SearchGoodsBaseListVo> searchGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto) {

        UmUserPo user = securityUtil.getAppCurrUser();
        Long memberLevelId = user.getMemberLevelId();

        Integer pageNo = searchGoodsBaseListDto.getPageNo() == null ? defaultPageNo : searchGoodsBaseListDto.getPageNo();
        Integer pageSize = searchGoodsBaseListDto.getPageSize() == null ? defaultPageSize : searchGoodsBaseListDto.getPageSize();

        if(null == searchGoodsBaseListDto.getSortFile()) {
            //默认综合排序
            searchGoodsBaseListDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if(null == searchGoodsBaseListDto.getSortWay()) {
            //默认降序
            searchGoodsBaseListDto.setSortWay(SortWayEnum.DESC);
        }

        PageInfo<SearchGoodsBaseListVo> searchGoodsBaseListVoPageInfo = new PageInfo<>();
        ConditionTypeEnum conditionTypeEnum = searchGoodsBaseListDto.getConditionType();
        if (conditionTypeEnum == null){
            throw new ServiceException(ResultCode.NO_EXISTS,String.format("conditionType所传的值在枚举类中不存在！"));
        }
        switch (conditionTypeEnum) {
            case TAB:
                if (tabMapper.selectById(searchGoodsBaseListDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的选项卡",searchGoodsBaseListDto.getConditionId()));
                }
                searchGoodsBaseListVoPageInfo =PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchTabGoodsBaseList(searchGoodsBaseListDto));
                break;
            case BRAND:
                if (attributeMapper.selectById(searchGoodsBaseListDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的品牌",searchGoodsBaseListDto.getConditionId()));
                }
                searchGoodsBaseListVoPageInfo =PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> mapper.searchBrandGoodsBaseList(searchGoodsBaseListDto));
                break;

            case THIRD_CATEGORY:
                if (goodsCategoryMapper.selectById(searchGoodsBaseListDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的商品分类",searchGoodsBaseListDto.getConditionId()));
                }
                searchGoodsBaseListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() ->mapper.searchCategoryGoodsBaseList(searchGoodsBaseListDto));
                break;

            case BAIHUO_ASSOCIATED:
                searchGoodsBaseListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() ->mapper.searchAssociatedGoodsBaseList(searchGoodsBaseListDto));
                break;

            case COUPON:
                if (couponMapper.selectById(searchGoodsBaseListDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的优惠券",searchGoodsBaseListDto.getConditionId()));
                }
                searchGoodsBaseListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() ->mapper.searchCouponGoodsBaseList(searchGoodsBaseListDto));
                break;

            case HOME:
                searchGoodsBaseListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() ->mapper.searchHomeGoodsBaseList(searchGoodsBaseListDto));
                break;

            case SECOND_CATEGORY:
                if (goodsCategoryMapper.selectById(searchGoodsBaseListDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的商品分类",searchGoodsBaseListDto.getConditionId()));
                }
                searchGoodsBaseListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() ->mapper.searchSecondCategoryGoodsBaseList(searchGoodsBaseListDto));
                break;
        }

        searchGoodsBaseListVoPageInfo.getList().forEach(a->{

            //获取商品的标签
            List<String> labelNames = mapper.getLabelNames(a.getGoodsId());
            a.setLabelNames(labelNames);

            //获取最高返券值
            List<Double> rewardShopTickes = Lists.newArrayList();
            List<RewardShopTicketBo> rewardShopTicketBos = skuMapper.findRewardShopTicketInfos(a.getGoodsId());
            rewardShopTicketBos.forEach(b->{
                //商品活动百分比
                b.setActivityCostRate(a.getActivityCostRate());
                //让利成本比例
                b.setProfitsRate(a.getProfitsRate());
                //会员等级比例
                BigDecimal purchasePresent = memberLevelMapper.selectById(memberLevelId).getPurchasePresent();
                b.setPurchasePresent(purchasePresent);
                //购物券比例
                BigDecimal moneyToShopTicket = basicSettingMapper.selectList(null).get(0).getMoneyToShopTicket();
                b.setMoneyToShopTicket(moneyToShopTicket);
                BigDecimal rewardShopTicket= b.getRewardShopTicket();
                rewardShopTickes.add(rewardShopTicket.doubleValue());
            });
            //获取RewardShopTickes列表最大返券值
            Double maxRewardShopTicket = rewardShopTickes.stream().mapToDouble((x)->x).summaryStatistics().getMax();
            a.setMaxRewardShopTicket(BigDecimal.valueOf(maxRewardShopTicket));
        });


        return searchGoodsBaseListVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/19 0:33
     * @Description 获取筛选商品的参数  商品列表如下
     * 分页条件查询首页下面的商品列表/品牌id/选项卡id/商品分类id/葱鸭百货关联/优惠券关联下的商品列表
     *
     * @Update yeJH
     *
     * @param  findRelGoodsParamDto
     * @return com.chauncy.data.vo.app.component.ScreenParamVo
     **/
    @Override
    public ScreenParamVo findScreenRelGoodsParam(FindRelGoodsParamDto findRelGoodsParamDto) {
        //筛选条件
        ScreenParamVo screenParamVo = new ScreenParamVo();
        //关联商品筛选条件
        ScreenGoodsParamVo screenGoodsParamVo = new ScreenGoodsParamVo();
        ConditionTypeEnum conditionTypeEnum = findRelGoodsParamDto.getConditionType();
        if (conditionTypeEnum == null){
            throw new ServiceException(ResultCode.NO_EXISTS,String.format("conditionType所传的值在枚举类中不存在！"));
        }
        switch (conditionTypeEnum) {
            case TAB:
                if (tabMapper.selectById(findRelGoodsParamDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的选项卡",findRelGoodsParamDto.getConditionId()));
                }
                screenGoodsParamVo = mapper.findTabGoodsParam(findRelGoodsParamDto);
                break;
            case BRAND:
                if (attributeMapper.selectById(findRelGoodsParamDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的品牌",findRelGoodsParamDto.getConditionId()));
                }
                screenGoodsParamVo = mapper.findBrandGoodsParam(findRelGoodsParamDto);
                break;
            case THIRD_CATEGORY:
                if (goodsCategoryMapper.selectById(findRelGoodsParamDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的商品分类",findRelGoodsParamDto.getConditionId()));
                }
                screenGoodsParamVo = mapper.findCategoryGoodsParam(findRelGoodsParamDto);
                break;
            case BAIHUO_ASSOCIATED:
                //screenGoodsParamVo = mapper.findAssociatedGoodsParam(findRelGoodsParamDto);
                break;
            case COUPON:
                if (couponMapper.selectById(findRelGoodsParamDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的优惠券",findRelGoodsParamDto.getConditionId()));
                }
                screenGoodsParamVo = mapper.findCouponGoodsParam(findRelGoodsParamDto);
                break;
            case HOME:
                //screenGoodsParamVo = mapper.findHomeGoodsParam(findRelGoodsParamDto);
                break;
            case SECOND_CATEGORY:
                if (goodsCategoryMapper.selectById(findRelGoodsParamDto.getConditionId()) == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为%s的商品分类",findRelGoodsParamDto.getConditionId()));
                }
                screenGoodsParamVo = mapper.findSecondCategoryGoodsParam(findRelGoodsParamDto);
                break;
        }
        screenParamVo.setScreenGoodsParamVo(screenGoodsParamVo);
        return screenParamVo;
    }

    /**
     *
     * 获取选项卡下的品牌下的轮播图广告
     *
     * @param findBrandShufflingDto
     * @return
     */
    @Override
    public List<ShufflingVo> findBrandShuffling(FindBrandShufflingDto findBrandShufflingDto) {

        //获取该选项卡与关联的具体品牌的关联ID
        Long relTabBrandId = relTabThingsMapper.selectOne(new QueryWrapper<MmAdviceRelTabThingsPo>().lambda()
                .and(obj->obj.eq(MmAdviceRelTabThingsPo::getTabId,findBrandShufflingDto.getTabId())
                        .eq(MmAdviceRelTabThingsPo::getAssociationId,findBrandShufflingDto.getBrandId()))).getId();

        List<ShufflingVo> brandShufflingVos = relShufflingMapper.findBrandShuffling(relTabBrandId);

        return brandShufflingVos;
    }

}
