package com.chauncy.activity.integrals.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.integrals.IAmIntegralsService;
import com.chauncy.common.enums.app.activity.ActivityStatusEnum;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.TreeUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.AmActivityRelActivityCategoryPo;
import com.chauncy.data.domain.po.activity.integrals.AmIntegralsPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.SearchCategoryByActivityIdDto;
import com.chauncy.data.dto.manage.activity.integrals.add.SaveIntegralsDto;
import com.chauncy.data.mapper.activity.AmActivityRelActivityCategoryMapper;
import com.chauncy.data.mapper.activity.group.AmActivityGroupMapper;
import com.chauncy.data.mapper.activity.integrals.AmIntegralsMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.manage.activity.SearchCategoryByActivityIdVo;
import com.chauncy.data.vo.manage.activity.SearchGoodsCategoryVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.apache.regexp.internal.RE;
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
 * 积分活动管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmIntegralsServiceImpl extends AbstractService<AmIntegralsMapper, AmIntegralsPo> implements IAmIntegralsService {

    @Autowired
    private AmIntegralsMapper mapper;

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Autowired
    private AmActivityRelActivityCategoryMapper relActivityCategoryMapper;

    @Autowired
    private AmActivityGroupMapper activityGroupMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 获取分类信息
     *
     * @param searchCategoryByActivityIdDto
     * @return
     */
    @Override
    public Map<String, Object> searchCategory(SearchCategoryByActivityIdDto searchCategoryByActivityIdDto) throws Exception {

        Integer pageNo=searchCategoryByActivityIdDto.getPageNo()==null?defaultPageNo:searchCategoryByActivityIdDto.getPageNo();
        Integer pageSize=searchCategoryByActivityIdDto.getPageSize()==null?defaultPageSize:searchCategoryByActivityIdDto.getPageSize();

        Map<String,Object> map= Maps.newHashMap();
        Integer totalCount = categoryMapper.count(searchCategoryByActivityIdDto);
        map.put("totalCount",totalCount);
        if (totalCount==0){
            map.put("categoryList", Lists.newArrayList());
        }
        else {
            List<SearchCategoryByActivityIdVo> searchCategoryVos = categoryMapper.searchCategoryByActivityId(searchCategoryByActivityIdDto, pageSize, (pageNo-1)*pageSize);
            if (searchCategoryByActivityIdDto.getActivityId()!=null) {
                List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id",searchCategoryByActivityIdDto.getActivityId()));
                List<Long> categoryIds = relActivityCategoryPos.stream().map(a->a.getCategoryId()).collect(Collectors.toList());
                searchCategoryVos.stream().filter(b->categoryIds.contains(b.getId())).forEach(c->{
                    c.setIsInclude(true);
                });
            }
            List<SearchCategoryByActivityIdVo> searchCategoryList = Lists.newArrayList();
            searchCategoryList = TreeUtil.getTree(searchCategoryVos,"id","parentId","children");

            map.put("categoryList",searchCategoryList);
        }
        return map;
    }

    /**
     * 保存积分信息
     * @param saveIntegralsDto
     */
    @Override
    public void saveIntegrals(SaveIntegralsDto saveIntegralsDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        //判断分组是否存在
        if (saveIntegralsDto.getGroupId()!=null && activityGroupMapper.selectById(saveIntegralsDto.getGroupId())==null){
            throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在该分组:[%s]",saveIntegralsDto.getGroupId()));
        }
        //判断分类是否存在
        List<Long> categoryIds = saveIntegralsDto.getCategoryIds();
        categoryIds.forEach(a->{
            if (categoryMapper.selectById(a)==null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("该分类不存在:[%s]",a));
            }
            else if (categoryMapper.selectById(a).getLevel()!=3){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("该分类:[%s]不是三级分类",categoryMapper.selectById(a).getName()));
            }
        });
        //时间判断

        LocalDateTime registrationStartTime = saveIntegralsDto.getRegistrationStartTime();
        LocalDateTime registrationEndTime = saveIntegralsDto.getRegistrationEndTime();
        LocalDateTime activityStartTime = saveIntegralsDto.getActivityStartTime();
        LocalDateTime activityEndTime = saveIntegralsDto.getActivityEndTime();
        if (registrationEndTime.isBefore(registrationStartTime) || registrationEndTime.equals(registrationStartTime)){
            throw new ServiceException(ResultCode.FAIL,"报名结束时间不能小于报名开始时间");
        }
        if (activityEndTime.isBefore(activityStartTime) || activityEndTime.equals(activityStartTime)){
            throw new ServiceException(ResultCode.FAIL,"活动结束时间不能小于活动开始时间");
        }
        if (activityStartTime.isBefore(registrationEndTime) || registrationEndTime.equals(activityStartTime)){
            throw new ServiceException(ResultCode.FAIL,"活动开始时间不能小于报名结束时间");
        }
        //可领取会员为全部会员操作
        Long memberLevelId = 0L;
        if (saveIntegralsDto.getMemberLevelId() == 0){
            PmMemberLevelPo memberLevelPo = memberLevelMapper.selectOne(new QueryWrapper<PmMemberLevelPo>().lambda()
                    .eq(PmMemberLevelPo::getLevel,1));
            if (memberLevelPo != null){
                memberLevelId = memberLevelPo.getId();
            }
        }else {
            memberLevelId = saveIntegralsDto.getMemberLevelId();
        }

        //判断所选的分类不能重复
        List<Long> categoryIdList = saveIntegralsDto.getCategoryIds();
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
//            log.info("重复数据为：" + repeatNames.toString());
            throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复分类名称：%s,请检查!", repeatNames.toString()));
        }

        //新增操作
        if (saveIntegralsDto.getId() == 0){

            if (saveIntegralsDto.getRegistrationStartTime().isBefore(LocalDateTime.now())) {
                throw new ServiceException(ResultCode.FAIL, String.format("活动报名开始时间需要在当前时间之后"));
            }
            if (saveIntegralsDto.getActivityStartTime().isBefore(LocalDateTime.now())) {
                throw new ServiceException(ResultCode.FAIL, String.format("活动开始时间需要在当前时间之后"));
            }

            AmIntegralsPo integralsPo = new AmIntegralsPo();
            BeanUtils.copyProperties(saveIntegralsDto,integralsPo);
            integralsPo.setId(null);
            integralsPo.setCreateBy(userPo.getUsername());
            integralsPo.setMemberLevelId(memberLevelId);
            mapper.insert(integralsPo);
            //保存积分活动与分类的信息
            if (!ListUtil.isListNullAndEmpty(categoryIds)){
                categoryIds.forEach(a->{
                    AmActivityRelActivityCategoryPo relActivityCategoryPo = new AmActivityRelActivityCategoryPo();
                    relActivityCategoryPo.setCategoryId(a);
                    relActivityCategoryPo.setCreateBy(userPo.getUsername());
                    relActivityCategoryPo.setActivityType(ActivityTypeEnum.INTEGRALS.getId());
                    relActivityCategoryPo.setActivityId(integralsPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
        //修改操作
        else{
            AmIntegralsPo integralsPo = mapper.selectById(saveIntegralsDto.getId());

            //判断是否修改开始时间和结束时间
            LocalDateTime registrationStartTime1 =integralsPo.getRegistrationStartTime();
            LocalDateTime registrationEndTime1 = integralsPo.getRegistrationEndTime();
            LocalDateTime activityStartTime1 = integralsPo.getActivityStartTime();
            LocalDateTime activityEndTime1 = integralsPo.getActivityEndTime();

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
            if (integralsPo.getRegistrationStartTime().isBefore(LocalDateTime.now())){
                throw new ServiceException(ResultCode.FAIL,"该活动报名已经开始，不能修改！");
            }
            BeanUtils.copyProperties(saveIntegralsDto,integralsPo);
            integralsPo.setUpdateBy(userPo.getUsername());
            integralsPo.setMemberLevelId(memberLevelId);
            mapper.updateById(integralsPo);
            List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id",saveIntegralsDto.getId()));
            //删除关联
            relActivityCategoryMapper.deleteBatchIds(relActivityCategoryPos.stream().map(a->a.getId()).collect(Collectors.toList()));
            //重新保存
            //保存积分活动与分类的信息
            if (!ListUtil.isListNullAndEmpty(categoryIds)){
                categoryIds.forEach(a->{
                    AmActivityRelActivityCategoryPo relActivityCategoryPo = new AmActivityRelActivityCategoryPo();
                    relActivityCategoryPo.setCategoryId(a);
                    relActivityCategoryPo.setCreateBy(userPo.getUsername());
                    relActivityCategoryPo.setActivityType(ActivityTypeEnum.INTEGRALS.getId());
                    relActivityCategoryPo.setActivityId(integralsPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
    }

    /**
     * 条件查询积分活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    @Override
    public PageInfo<SearchActivityListVo> searchIntegralsList(SearchActivityListDto searchActivityListDto) {

        Integer pageNo = searchActivityListDto.getPageNo()==null ? defaultPageNo : searchActivityListDto.getPageNo();
        Integer pageSize = searchActivityListDto.getPageSize()==null ? defaultPageSize : searchActivityListDto.getPageSize();

        PageInfo<SearchActivityListVo> searchActivityListVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchIntegralsList(searchActivityListDto));
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
          AmIntegralsPo integralsPo = mapper.selectById(id);
          if (integralsPo == null){
              throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在该活动:[%s],id"));
          }
          if (!integralsPo.getRegistrationStartTime().isAfter(LocalDateTime.now())){
              throw new ServiceException(ResultCode.FAIL,String.format("该活动[%s:%s]的报名状态不是待开始状态，不能删除",id,integralsPo.getName()));
          }
      });
        mapper.deleteBatchIds(ids);
    }
}
