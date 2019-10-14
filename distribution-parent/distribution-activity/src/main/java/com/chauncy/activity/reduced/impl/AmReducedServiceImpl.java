package com.chauncy.activity.reduced.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.reduced.IAmReducedService;
import com.chauncy.common.enums.app.activity.ActivityStatusEnum;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.AmActivityRelActivityCategoryPo;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelGoodsSkuPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.dto.app.product.SearchReducedGoodsDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.reduced.add.SaveReducedDto;
import com.chauncy.data.mapper.activity.AmActivityRelActivityCategoryMapper;
import com.chauncy.data.mapper.activity.group.AmActivityGroupMapper;
import com.chauncy.data.mapper.activity.reduced.AmReducedMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelActivityGoodsMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelGoodsSkuMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.app.goods.ActivityGoodsVo;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.manage.activity.SearchGoodsCategoryVo;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 满减活动管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AmReducedServiceImpl extends AbstractService<AmReducedMapper, AmReducedPo> implements IAmReducedService {

    @Autowired
    private AmReducedMapper mapper;

    @Autowired
    private AmActivityRelActivityGoodsMapper amActivityRelActivityGoodsMapper;

    @Autowired
    private AmActivityRelGoodsSkuMapper relGoodsSkuMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AmActivityGroupMapper activityGroupMapper;

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private AmActivityRelActivityCategoryMapper relActivityCategoryMapper;

    /**
     * @Author yeJH
     * @Date 2019/9/28 15:27
     * @Description 商品详情页，购物车页面点击去凑单获取满减商品列表
     *
     * @Update yeJH
     *
     * @param  searchReducedGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.ActivityGoodsVo>
     **/
    @Override
    public PageInfo<ActivityGoodsVo> searchReducedGoods(SearchReducedGoodsDto searchReducedGoodsDto) {

        if(null == searchReducedGoodsDto.getSortFile()) {
            //默认综合排序
            searchReducedGoodsDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if(null == searchReducedGoodsDto.getSortWay()) {
            //默认降序
            searchReducedGoodsDto.setSortWay(SortWayEnum.DESC);
        }

        //查询商品正在参加的活动
        QueryWrapper<AmActivityRelActivityGoodsPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AmActivityRelActivityGoodsPo::getGoodsId, searchReducedGoodsDto.getGoodsId())
                .lt(AmActivityRelActivityGoodsPo::getActivityStartTime, LocalDateTime.now())
                .gt(AmActivityRelActivityGoodsPo::getActivityEndTime, LocalDateTime.now())
                .eq(AmActivityRelActivityGoodsPo::getVerifyStatus, VerifyStatusEnum.CHECKED.getId());
        AmActivityRelActivityGoodsPo amActivityRelActivityGoodsPo = amActivityRelActivityGoodsMapper.selectOne(queryWrapper);
        searchReducedGoodsDto.setActivityId(amActivityRelActivityGoodsPo.getActivityId());

        Integer pageNo = searchReducedGoodsDto.getPageNo()==null ? defaultPageNo : searchReducedGoodsDto.getPageNo();
        Integer pageSize = searchReducedGoodsDto.getPageSize()==null ? defaultPageSize : searchReducedGoodsDto.getPageSize();

        PageInfo<ActivityGoodsVo> activityGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                mapper.searchReducedGoods(searchReducedGoodsDto));
        //获取商品标签
        activityGoodsVoPageInfo.getList().forEach(activityGoodsVo -> {
            activityGoodsVo.setLabelList(Splitter.on(",").omitEmptyStrings().splitToList(activityGoodsVo.getLabels()));
        });

        return activityGoodsVoPageInfo;
    }

    /**
     * 保存满减活动信息
     * @param saveReducedDto
     * @return
     */
    @Override
    public void saveReduced(SaveReducedDto saveReducedDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        //判断分组是否存在
        if (saveReducedDto.getGroupId() != null && activityGroupMapper.selectById(saveReducedDto.getGroupId()) == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在该分组:[%s]", saveReducedDto.getGroupId()));
        }
        //判断分类是否存在
        List<Long> categoryIds = saveReducedDto.getCategoryIds();
        categoryIds.forEach(a -> {
            if (categoryMapper.selectById(a) == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该分类不存在:[%s]", a));
            }else if (categoryMapper.selectById(a).getLevel()!=3){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("该分类:[%s]不是三级分类",categoryMapper.selectById(a).getName()));
            }
        });

        //时间判断
        LocalDateTime registrationStartTime = saveReducedDto.getRegistrationStartTime();
        LocalDateTime registrationEndTime = saveReducedDto.getRegistrationEndTime();
        LocalDateTime activityStartTime = saveReducedDto.getActivityStartTime();
        LocalDateTime activityEndTime = saveReducedDto.getActivityEndTime();
        if (registrationEndTime.isBefore(registrationStartTime) || registrationEndTime.equals(registrationStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "报名结束时间不能小于报名开始时间");
        }
        if (activityEndTime.isBefore(activityStartTime) || activityEndTime.equals(activityStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "活动结束时间不能小于活动开始时间");
        }
        if (activityStartTime.isBefore(registrationEndTime) || registrationEndTime.equals(activityStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "活动开始时间不能小于报名结束时间");
        }

        //可领取会员为全部会员操作
        Long memberLevelId = 0L;
        if (saveReducedDto.getMemberLevelId() == 0){
            PmMemberLevelPo memberLevelPo = memberLevelMapper.selectOne(new QueryWrapper<PmMemberLevelPo>().lambda()
                    .eq(PmMemberLevelPo::getLevel,1));
            if (memberLevelPo != null){
                memberLevelId = memberLevelPo.getId();
            }
        }else {
            memberLevelId = saveReducedDto.getMemberLevelId();
        }

        //判断所选的分类不能重复
        List<Long> categoryIdList = saveReducedDto.getCategoryIds();
        boolean categoryIdIsRepeat = categoryIdList.size() != new HashSet<Long>(categoryIdList).size();
        if (categoryIdIsRepeat) {
            List<String> repeatNames = Lists.newArrayList();
            //查找重复的数据
            Map<Long, Integer> repeatMap = Maps.newHashMap();
            categoryIdList.forEach(str -> {
                Integer i = 1;
                if (repeatMap.get(str) != null) {
                    i = repeatMap.get(str) + 1;
                }
                repeatMap.put(str, i);
            });
            for (Long s : repeatMap.keySet()) {
                if (repeatMap.get(s) > 1) {

                    repeatNames.add(categoryMapper.selectById(s).getName());
                }
            }
            log.info("重复数据为：" + repeatNames.toString());
            throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复分类名称：%s,请检查!", repeatNames.toString()));
        }


        //新增操作
        if (saveReducedDto.getId() == 0) {

            if (saveReducedDto.getRegistrationStartTime().isBefore(LocalDateTime.now())) {
                throw new ServiceException(ResultCode.FAIL, String.format("活动报名开始时间需要在当前时间之后"));
            }
            if (saveReducedDto.getActivityStartTime().isBefore(LocalDateTime.now())) {
                throw new ServiceException(ResultCode.FAIL, String.format("活动开始时间需要在当前时间之后"));
            }
            AmReducedPo reducedPo = new AmReducedPo();
            BeanUtils.copyProperties(saveReducedDto, reducedPo);
            reducedPo.setId(null);
            reducedPo.setCreateBy(userPo.getUsername());
            reducedPo.setMemberLevelId(memberLevelId);
            mapper.insert(reducedPo);
            //保存积分活动与分类的信息
            if (!ListUtil.isListNullAndEmpty(categoryIds)) {
                categoryIds.forEach(a -> {
                    AmActivityRelActivityCategoryPo relActivityCategoryPo = new AmActivityRelActivityCategoryPo();
                    relActivityCategoryPo.setCategoryId(a);
                    relActivityCategoryPo.setCreateBy(userPo.getUsername());
                    relActivityCategoryPo.setActivityType(ActivityTypeEnum.REDUCED.getId());
                    relActivityCategoryPo.setActivityId(reducedPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
        //修改操作
        else {
            AmReducedPo reducedPo = mapper.selectById(saveReducedDto.getId());

            //判断是否修改开始时间和结束时间
            LocalDateTime registrationStartTime1 =reducedPo.getRegistrationStartTime();
            LocalDateTime registrationEndTime1 = reducedPo.getRegistrationEndTime();
            LocalDateTime activityStartTime1 = reducedPo.getActivityStartTime();
            LocalDateTime activityEndTime1 = reducedPo.getActivityEndTime();

            if (!registrationStartTime.equals(registrationStartTime1)){
                if (registrationStartTime.isBefore(LocalDateTime.now())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("活动报名开始时间需要在当前时间之后"));
                }
            }
            if (!activityStartTime.equals(activityStartTime1)) {
                if (activityStartTime.isBefore(LocalDateTime.now())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("活动开始时间需要在当前时间之后"));
                }
            }

            //活动报名已开始则不能修改
            if (reducedPo.getRegistrationStartTime().isBefore(LocalDateTime.now())){
                throw new ServiceException(ResultCode.FAIL,"该活动报名已经开始，不能修改！");
            }

            BeanUtils.copyProperties(saveReducedDto, reducedPo);
            reducedPo.setUpdateBy(userPo.getUsername());
            reducedPo.setMemberLevelId(memberLevelId);
            mapper.updateById(reducedPo);
            List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id", saveReducedDto.getId()));
            //删除关联
            relActivityCategoryMapper.deleteBatchIds(relActivityCategoryPos.stream().map(a -> a.getId()).collect(Collectors.toList()));
            //重新保存
            //保存积分活动与分类的信息
            if (!ListUtil.isListNullAndEmpty(categoryIds)) {
                categoryIds.forEach(a -> {
                    AmActivityRelActivityCategoryPo relActivityCategoryPo = new AmActivityRelActivityCategoryPo();
                    relActivityCategoryPo.setCategoryId(a);
                    relActivityCategoryPo.setCreateBy(userPo.getUsername());
                    relActivityCategoryPo.setActivityType(ActivityTypeEnum.INTEGRALS.getId());
                    relActivityCategoryPo.setActivityId(reducedPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
    }

    /**
     * 条件查询满减活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    @Override
    public PageInfo<SearchActivityListVo> searchReduceList(SearchActivityListDto searchActivityListDto) {
        Integer pageNo = searchActivityListDto.getPageNo()==null ? defaultPageNo : searchActivityListDto.getPageNo();
        Integer pageSize = searchActivityListDto.getPageSize()==null ? defaultPageSize : searchActivityListDto.getPageSize();

        PageInfo<SearchActivityListVo> searchActivityListVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchReduceList(searchActivityListDto));
        searchActivityListVoPageInfo.getList().forEach(a->{

            //当可领取会员为全部会员时，memberLevelId返回0给前端
            PmMemberLevelPo memberLevelPo = memberLevelMapper.selectById(a.getMemberLevelId());
            Long memberLevelId = 0L;
            if (memberLevelPo != null){
                if (memberLevelPo.getLevel() != 1){
                    memberLevelId = a.getMemberLevelId();
                }
            }
            a.setMemberLevelId(memberLevelId);

            //分组名称
            String groupName = activityGroupMapper.selectById(a.getGroupId()).getName();
            a.setGroupName(groupName);
            //处理报名状态、活动状态
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime registrationStartTime = a.getRegistrationStartTime();
            LocalDateTime registrationEndTime = a.getRegistrationEndTime();
            LocalDateTime activityStartTime = a.getActivityStartTime();
            LocalDateTime activityEndTime = a.getActivityEndTime();
            //报名待开始
            if (registrationStartTime.isAfter(now)){
                a.setRegistrationStatus(ActivityStatusEnum.TO_START.getName());
            }
            //报名中
            else if (registrationStartTime.isBefore(now) && registrationEndTime.isAfter(now)){
                a.setRegistrationStatus(ActivityStatusEnum.REGISTRATION.getName());
            }
            //报名已结束
            else if(registrationEndTime.isBefore(now)){
                a.setRegistrationStatus(ActivityStatusEnum.HAS_ENDED.getName());
            }

            //活动待开始
            if (activityStartTime.isAfter(now)){
                a.setActivityStatus(ActivityStatusEnum.TO_START.getName());
            }
            //活动中
            else if (activityStartTime.isBefore(now) && activityEndTime.isAfter(now)){
                a.setActivityStatus(ActivityStatusEnum.ONGOING.getName());
            }
            //活动已结束
            else if(activityEndTime.isBefore(now)){
                a.setActivityStatus(ActivityStatusEnum.HAS_ENDED.getName());
            }

            //处理分类
            List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id",a.getId()));
            List<Long> categoryIds = relActivityCategoryPos.stream().map(b->b.getCategoryId()).collect(Collectors.toList());
            List<SearchGoodsCategoryVo> goodsCategoryVoList = Lists.newArrayList();
            List<PmGoodsCategoryPo> goodsCategoryPos = categoryMapper.selectBatchIds(categoryIds);
            categoryIds.forEach(c->{
                PmGoodsCategoryPo goodsCategoryPo = categoryMapper.selectById(c);
                if (goodsCategoryPo == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在该分类:[%s]",c));
                }
                SearchGoodsCategoryVo searchGoodsCategoryVo = new SearchGoodsCategoryVo();
                searchGoodsCategoryVo.setId(c);
                searchGoodsCategoryVo.setName(goodsCategoryPo.getName());
                String level3 = goodsCategoryPo.getName();
                PmGoodsCategoryPo goodsCategoryPo2 = categoryMapper.selectById(goodsCategoryPo.getParentId());
                String level2 = goodsCategoryPo2.getName();
                String level1 = categoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();

                String categoryName = level1 + "/" + level2 + "/" + level3;
                searchGoodsCategoryVo.setCategoryName(categoryName);
                goodsCategoryVoList.add(searchGoodsCategoryVo);
            });
            a.setGoodsCategoryVoList(goodsCategoryVoList);
        });

        return searchActivityListVoPageInfo;
    }

    /**
     * 批量删除活动
     *
     * @param ids
     * @return
     */
    @Override
    public void delByIds(List<Long> ids) {
        ids.forEach(id->{
            AmReducedPo amReducedPo = mapper.selectById(id);
            if (amReducedPo == null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在该活动:[%s],id"));
            }
            if (!amReducedPo.getRegistrationStartTime().isAfter(LocalDateTime.now())){
                throw new ServiceException(ResultCode.FAIL,String.format("该活动[%s:%s]的报名状态不是待开始状态，不能删除",id,amReducedPo.getName()));
            }

            //删除活动与分类关联表am_activity_rel_activity_category
            relActivityCategoryMapper.delete(new QueryWrapper<AmActivityRelActivityCategoryPo>().lambda().and(obj->
                    obj.eq(AmActivityRelActivityCategoryPo::getActivityId,id)));
            //获取am_activity_rel_activity_goods信息
            List<AmActivityRelActivityGoodsPo> relActivityGoodsPos = amActivityRelActivityGoodsMapper.selectList(new QueryWrapper<AmActivityRelActivityGoodsPo>().lambda().and(obj->obj
                    .eq(AmActivityRelActivityGoodsPo::getActivityId,id)));
            //删除活动与sku关联表
            if (!ListUtil.isListNullAndEmpty(relActivityGoodsPos)) {
                relActivityGoodsPos.forEach(b->{
                    relGoodsSkuMapper.delete(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda().and(obj -> obj
                            .eq(AmActivityRelGoodsSkuPo::getRelId, b.getId())));
                });
            }
            //删除活动与商品关联表
            amActivityRelActivityGoodsMapper.delete(new QueryWrapper<AmActivityRelActivityGoodsPo>().lambda()
                    .eq(AmActivityRelActivityGoodsPo::getActivityId,id));
        });
        mapper.deleteBatchIds(ids);
    }
}
