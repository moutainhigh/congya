package com.chauncy.activity.spell.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.spell.IAmSpellGroupService;
import com.chauncy.common.constant.ServiceConstant;
import com.chauncy.common.enums.app.activity.ActivityStatusEnum;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.activity.AmActivityRelActivityCategoryPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.activity.SearchMySpellGroupDto;
import com.chauncy.data.dto.app.activity.SearchSpellGroupInfoDto;
import com.chauncy.data.dto.app.product.SearchSpellGroupGoodsDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.spell.SaveSpellDto;
import com.chauncy.data.mapper.activity.AmActivityRelActivityCategoryMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelActivityGoodsMapper;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.activity.MySpellGroupVo;
import com.chauncy.data.vo.app.activity.SpellGroupInfoVo;
import com.chauncy.data.vo.app.goods.SpellGroupGoodsVo;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.manage.activity.SearchGoodsCategoryVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 秒杀活动管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmSpellGroupServiceImpl extends AbstractService<AmSpellGroupMapper, AmSpellGroupPo> implements IAmSpellGroupService {

    @Autowired
    private AmSpellGroupMapper mapper;

    @Autowired
    private AmActivityRelActivityGoodsMapper amActivityRelActivityGoodsMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Autowired
    private AmActivityRelActivityCategoryMapper relActivityCategoryMapper;

    /**
     * @Author yeJH
     * @Date 2019/10/6 23:47
     * @Description 查询我的拼团
     *
     * @Update yeJH
     *
     * @param  searchMySpellGroupDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.activity.MySpellGroupVo>
     **/
    @Override
    public PageInfo<MySpellGroupVo> searchMySpellGroup(SearchMySpellGroupDto searchMySpellGroupDto) {

        //获取当前APP用户
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        searchMySpellGroupDto.setUserId(umUserPo.getId());

        Integer pageNo = searchMySpellGroupDto.getPageNo()==null ? defaultPageNo : searchMySpellGroupDto.getPageNo();
        Integer pageSize = searchMySpellGroupDto.getPageSize()==null ? defaultPageSize : searchMySpellGroupDto.getPageSize();

        PageInfo<MySpellGroupVo> mySpellGroupVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                mapper.searchMySpellGroup(searchMySpellGroupDto));


        if(null != mySpellGroupVoPageInfo.getList() && mySpellGroupVoPageInfo.getList().size() > 0) {
            //获取团员头像
            mySpellGroupVoPageInfo.getList().forEach(mySpellGroupVo -> {
                if(null != mySpellGroupVo.getMainId()) {
                    List<String> headPortrait = mapper.getMemberHeadPortrait(mySpellGroupVo.getMainId());
                    mySpellGroupVo.setHeadPortrait(headPortrait);
                }
            });
        }

        return mySpellGroupVoPageInfo;

    }

    /**
     * @Author yeJH
     * @Date 2019/10/6 18:23
     * @Description 根据商品id获取拼团信息
     *
     * @Update yeJH
     *
     * @param  searchSpellGroupInfoDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.activity.SpellGroupInfoVo>
     **/
    @Override
    public PageInfo<SpellGroupInfoVo> searchSpellGroupInfo(SearchSpellGroupInfoDto searchSpellGroupInfoDto) {

        //获取该商品跟活动关联的id
        QueryWrapper<AmActivityRelActivityGoodsPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AmActivityRelActivityGoodsPo::getGoodsId, searchSpellGroupInfoDto.getGoodsId())
                .eq(AmActivityRelActivityGoodsPo::getVerifyStatus, VerifyStatusEnum.CHECKED.getId())
                .eq(AmActivityRelActivityGoodsPo::getDelFlag, 0)
                .lt(AmActivityRelActivityGoodsPo::getActivityStartTime, LocalDateTime.now())
                .gt(AmActivityRelActivityGoodsPo::getActivityEndTime, LocalDateTime.now());
        AmActivityRelActivityGoodsPo amActivityRelActivityGoodsPo = amActivityRelActivityGoodsMapper.selectOne(queryWrapper);

        Long relId ;
        if(null != amActivityRelActivityGoodsPo) {
            relId = amActivityRelActivityGoodsPo.getId();
        } else {
            relId = null;
        }


        Integer pageNo = searchSpellGroupInfoDto.getPageNo()==null ? defaultPageNo : searchSpellGroupInfoDto.getPageNo();
        Integer pageSize = searchSpellGroupInfoDto.getPageSize()==null ? defaultPageSize : searchSpellGroupInfoDto.getPageSize();

        PageInfo<SpellGroupInfoVo> spellGroupInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                mapper.searchSpellGroupInfo(relId));

        //活动结束时间  时间戳
        spellGroupInfoVoPageInfo.getList().forEach(spellGroupInfoVo -> {
            if(null != spellGroupInfoVo.getExpireTime()) {
                spellGroupInfoVo.setEndTime(
                        spellGroupInfoVo.getExpireTime().toEpochSecond(ZoneOffset.of("+8")));
            }
        });

        return spellGroupInfoVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/3 18:37
     * @Description 获取拼团动商品列表
     *
     * @Update yeJH
     *
     * @param  searchSpellGroupGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.SpellGroupGoodsVo>
     **/
    @Override
    public PageInfo<SpellGroupGoodsVo> searchActivityGoodsList(SearchSpellGroupGoodsDto searchSpellGroupGoodsDto) {

        Integer pageNo = searchSpellGroupGoodsDto.getPageNo()==null ? defaultPageNo : searchSpellGroupGoodsDto.getPageNo();
        Integer pageSize = searchSpellGroupGoodsDto.getPageSize()==null ? defaultPageSize : searchSpellGroupGoodsDto.getPageSize();

        if(null == searchSpellGroupGoodsDto.getSortFile()) {
            //默认综合排序
            searchSpellGroupGoodsDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if(null == searchSpellGroupGoodsDto.getSortWay()) {
            //默认降序
            searchSpellGroupGoodsDto.setSortWay(SortWayEnum.DESC);
        }
        PageInfo<SpellGroupGoodsVo> spellGroupGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                        mapper.searchActivityGoodsList(searchSpellGroupGoodsDto));

        if(null != spellGroupGoodsVoPageInfo.getList() && spellGroupGoodsVoPageInfo.getList().size() > 0) {
            //获取团长头像
            spellGroupGoodsVoPageInfo.getList().forEach(spellGroupGoodsVo -> {
                if(Strings.isNotBlank(spellGroupGoodsVo.getMainIds())) {
                    String[] mainIds = spellGroupGoodsVo.getMainIds().split(",", ServiceConstant.VICE_COMMENT_NUM);
                    List<String> headPortrait = mapper.getMainHeadPortrait(StringUtils.join(mainIds, ","));
                    spellGroupGoodsVo.setHeadPortrait(headPortrait);
                }
            });
        }

        return spellGroupGoodsVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/6 17:26
     * @Description 获取拼团活动商品一级分类
     *
     * @Update yeJH
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    @Override
    public List<BaseVo> findGoodsCategory() {
        return mapper.findGoodsCategory();
    }

    /**
     * 保存秒杀活动信息
     *
     * @param saveSpellDto
     * @return
     */
    @Override
    public void saveSpell(SaveSpellDto saveSpellDto) {
        SysUserPo userPo = securityUtil.getCurrUser();
        //判断分类是否存在
        List<Long> categoryIds = saveSpellDto.getCategoryIds();
        categoryIds.forEach(a -> {
            if (categoryMapper.selectById(a) == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该分类不存在:[%s]", a));
            } else if (categoryMapper.selectById(a).getLevel() != 3) {
                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该分类:[%s]不是三级分类", categoryMapper.selectById(a).getName()));
            }
        });
        //时间判断
        LocalDateTime registrationStartTime = saveSpellDto.getRegistrationStartTime();
        LocalDateTime registrationEndTime = saveSpellDto.getRegistrationEndTime();
        LocalDateTime activityStartTime = saveSpellDto.getActivityStartTime();
        LocalDateTime activityEndTime = saveSpellDto.getActivityEndTime();
        if (registrationEndTime.isBefore(registrationStartTime) || registrationEndTime.equals(registrationStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "报名结束时间不能小于报名开始时间");
        }
        if (activityEndTime.isBefore(activityStartTime) || activityEndTime.equals(activityStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "活动结束时间不能小于活动开始时间");
        }
        if (activityStartTime.isBefore(registrationEndTime) || registrationEndTime.equals(activityStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "活动开始时间不能小于报名结束时间");
        }
        //新增操作
        if (saveSpellDto.getId() == 0) {
            AmSpellGroupPo spellGroupPo = new AmSpellGroupPo();
            BeanUtils.copyProperties(saveSpellDto, spellGroupPo);
            spellGroupPo.setId(null);
            spellGroupPo.setCreateBy(userPo.getUsername());
            mapper.insert(spellGroupPo);
            //保存积分活动与分类的信息
            if (!ListUtil.isListNullAndEmpty(categoryIds)) {
                categoryIds.forEach(a -> {
                    AmActivityRelActivityCategoryPo relActivityCategoryPo = new AmActivityRelActivityCategoryPo();
                    relActivityCategoryPo.setCategoryId(a);
                    relActivityCategoryPo.setCreateBy(userPo.getUsername());
                    relActivityCategoryPo.setActivityType(ActivityTypeEnum.SPELL_GROUP.getId());
                    relActivityCategoryPo.setActivityId(spellGroupPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
        //修改操作
        else {
            AmSpellGroupPo spellGroupPo = mapper.selectById(saveSpellDto.getId());
            BeanUtils.copyProperties(saveSpellDto, spellGroupPo);
            spellGroupPo.setUpdateBy(userPo.getUsername());
            mapper.updateById(spellGroupPo);
            List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id", saveSpellDto.getId()));
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
                    relActivityCategoryPo.setActivityId(spellGroupPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
    }

    /**
     * 条件查询拼团活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    @Override
    public PageInfo<SearchActivityListVo> searchSpellList(SearchActivityListDto searchActivityListDto) {

        Integer pageNo = searchActivityListDto.getPageNo() == null ? defaultPageNo : searchActivityListDto.getPageNo();
        Integer pageSize = searchActivityListDto.getPageSize() == null ? defaultPageSize : searchActivityListDto.getPageSize();

        PageInfo<SearchActivityListVo> searchActivityListVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchSpellList(searchActivityListDto));
        searchActivityListVoPageInfo.getList().forEach(a -> {
            //处理报名状态、活动状态
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime registrationStartTime = a.getRegistrationStartTime();
            LocalDateTime registrationEndTime = a.getRegistrationEndTime();
            LocalDateTime activityStartTime = a.getActivityStartTime();
            LocalDateTime activityEndTime = a.getActivityEndTime();
            //报名待开始
            if (registrationStartTime.isAfter(now)) {
                a.setRegistrationStatus(ActivityStatusEnum.TO_START.getName());
            }
            //报名中
            else if (registrationStartTime.isBefore(now) && registrationEndTime.isAfter(now)) {
                a.setRegistrationStatus(ActivityStatusEnum.REGISTRATION.getName());
            }
            //报名已结束
            else if (registrationEndTime.isBefore(now)) {
                a.setRegistrationStatus(ActivityStatusEnum.HAS_ENDED.getName());
            }

            //活动待开始
            if (activityStartTime.isAfter(now)) {
                a.setActivityStatus(ActivityStatusEnum.TO_START.getName());
            }
            //活动中
            else if (activityStartTime.isBefore(now) && activityEndTime.isAfter(now)) {
                a.setActivityStatus(ActivityStatusEnum.REGISTRATION.getName());
            }
            //活动已结束
            else if (activityEndTime.isBefore(now)) {
                a.setActivityStatus(ActivityStatusEnum.HAS_ENDED.getName());
            }

            //处理分类
            List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id", a.getId()));
            List<Long> categoryIds = relActivityCategoryPos.stream().map(b -> b.getCategoryId()).collect(Collectors.toList());
            List<SearchGoodsCategoryVo> goodsCategoryVoList = Lists.newArrayList();
            List<PmGoodsCategoryPo> goodsCategoryPos = categoryMapper.selectBatchIds(categoryIds);
            categoryIds.forEach(c -> {
                PmGoodsCategoryPo goodsCategoryPo = categoryMapper.selectById(c);
                if (goodsCategoryPo == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在该分类:[%s]", c));
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
        ids.forEach(id -> {
            AmSpellGroupPo spellGroupPo = mapper.selectById(id);
            if (spellGroupPo == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在该活动:[%s],id"));
            }
            if (!spellGroupPo.getRegistrationStartTime().isAfter(LocalDateTime.now())) {
                throw new ServiceException(ResultCode.FAIL, String.format("该活动[%s:%s]的报名状态不是待开始状态，不能删除", id, spellGroupPo.getName()));
            }
        });
        mapper.deleteBatchIds(ids);
    }
}
