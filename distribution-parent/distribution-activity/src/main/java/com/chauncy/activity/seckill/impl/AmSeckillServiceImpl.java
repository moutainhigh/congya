package com.chauncy.activity.seckill.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.seckill.IAmSeckillService;
import com.chauncy.common.constant.ServiceConstant;
import com.chauncy.common.enums.app.activity.ActivityStatusEnum;
import com.chauncy.common.enums.app.activity.SpellGroupStatusEnum;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.activity.AmActivityRelActivityCategoryPo;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.app.product.SearchSeckillGoodsDto;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.seckill.SaveSeckillDto;
import com.chauncy.data.mapper.activity.AmActivityRelActivityCategoryMapper;
import com.chauncy.data.mapper.activity.group.AmActivityGroupMapper;
import com.chauncy.data.mapper.activity.seckill.AmSeckillMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo;
import com.chauncy.data.vo.app.goods.SeckillGoodsVo;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
public class AmSeckillServiceImpl extends AbstractService<AmSeckillMapper, AmSeckillPo> implements IAmSeckillService {

    @Autowired
    private AmSeckillMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private AmActivityRelActivityCategoryMapper relActivityCategoryMapper;


    /**
     * @Author yeJH
     * @Date 2019/10/9 11:04
     * @Description 获取秒杀活动商品一级分类
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    @Override
    public List<BaseVo> findGoodsCategory(SearchSeckillGoodsDto searchSeckillGoodsDto) {
        return mapper.findGoodsCategory(searchSeckillGoodsDto);
    }

    /**
     * @Author yeJH
     * @Date 2019/10/8 20:58
     * @Description 获取秒杀活动商品列表
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.SeckillGoodsVo>
     **/
    @Override
    public PageInfo<SeckillGoodsVo> searchActivityGoodsList(SearchSeckillGoodsDto searchSeckillGoodsDto) {

        Integer pageNo = searchSeckillGoodsDto.getPageNo()==null ? defaultPageNo : searchSeckillGoodsDto.getPageNo();
        Integer pageSize = searchSeckillGoodsDto.getPageSize()==null ? defaultPageSize : searchSeckillGoodsDto.getPageSize();


        PageInfo<SeckillGoodsVo> seckillGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() ->
                mapper.searchActivityGoodsList(searchSeckillGoodsDto));

        if(null != seckillGoodsVoPageInfo.getList() && seckillGoodsVoPageInfo.getList().size() > 0) {
            seckillGoodsVoPageInfo.getList().forEach(seckillGoodsVo -> {
                //获取活动结束时间时间戳  抢购中以及已抢购需要展示
                if(searchSeckillGoodsDto.getActivityStartTime().isBefore(LocalDateTime.now())) {
                    if (null != seckillGoodsVo.getActivityEndTime()) {
                        //活动已开始
                        seckillGoodsVo.setIsStart(true);
                        //秒杀活动截止时间
                        seckillGoodsVo.setEndTime(seckillGoodsVo.getActivityEndTime().toEpochSecond(ZoneOffset.of("+8")));
                    } else {
                        //活动未开始
                        seckillGoodsVo.setIsStart(false);
                    }
                }
                //剩余库存  活动总库存 - 已售数量
                Integer remainingStock = seckillGoodsVo.getActivityStock() - seckillGoodsVo.getSalesVolume();
                seckillGoodsVo.setRemainingStock(remainingStock > 0 ? remainingStock : 0);
                //销售百分比
                if(seckillGoodsVo.getActivityStock() == 0) {
                    seckillGoodsVo.setSalePercentage(0);
                } else {
                    seckillGoodsVo.setSalePercentage(
                            seckillGoodsVo.getSalesVolume() * 100 / seckillGoodsVo.getActivityStock());
                }
                //是否即将售罄
                if(seckillGoodsVo.getSalePercentage() > ServiceConstant.SALE_PERCENTAGE) {
                    seckillGoodsVo.setIsSellOut(true);
                } else {
                    seckillGoodsVo.setIsSellOut(false);
                }
            });
        }

        return seckillGoodsVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/8 11:10
     * @Description 获取秒杀时间段  当前时间前24小时，之后24小时范围内的所有活动时间
     *
     * @Update yeJH
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo>
     **/
    @Override
    public List<SeckillTimeQuantumVo> getSeckillTimeQuantum() {

        LocalDateTime nowDateTime = LocalDateTime.now();

        LocalDateTime startDateTime = nowDateTime.plusHours(-24);
        LocalDateTime endDateTime = nowDateTime.plusDays(24);
        //获取前24小时，后24小时时间段内的所有活动对应的时间点 按时间降序  方便处理抢购中跟已抢购的状态
        List<SeckillTimeQuantumVo> seckillTimeQuantumVoList = mapper.getSeckillTimeQuantum(startDateTime, endDateTime);
        if(null != seckillTimeQuantumVoList && seckillTimeQuantumVoList.size() > 0) {
            final Boolean[] isInProgress = {true};
            seckillTimeQuantumVoList.stream().forEach(seckillTimeQuantumVo -> {
                //秒杀活动时分
                DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
                String seckillTime = df.format(seckillTimeQuantumVo.getActivityTime());
                seckillTimeQuantumVo.setSeckillTime(seckillTime);
                //秒杀活动状态
                if(seckillTimeQuantumVo.getActivityTime().isBefore(nowDateTime)) {
                    //活动时间在当前时间之前
                    if(true == isInProgress[0]) {
                        seckillTimeQuantumVo.setSpellGroupStatus(SpellGroupStatusEnum.IN_PROGRESS.getName());
                        isInProgress[0] = false;
                    } else {
                        seckillTimeQuantumVo.setSpellGroupStatus(SpellGroupStatusEnum.SNAPPED_UP.getName());
                    }
                } else {
                    //活动还未开始
                    LocalDate nowDate = nowDateTime.toLocalDate();
                    LocalDate activityDate = seckillTimeQuantumVo.getActivityTime().toLocalDate();
                    //获取相差天数
                    long days = activityDate.toEpochDay() - nowDate.toEpochDay();
                    if(days != 0) {
                        //不是同一天  预告
                        seckillTimeQuantumVo.setSpellGroupStatus(SpellGroupStatusEnum.ANNOUNCE_IN_ADVANCE.getName());
                    } else {
                        //是同一天  即将开始
                        seckillTimeQuantumVo.setSpellGroupStatus(SpellGroupStatusEnum.ABOUT_TO_START.getName());
                    }
                }
            });
            //倒序  前端展示数据为按照时间升序
            Collections.reverse(seckillTimeQuantumVoList);
        }


        return seckillTimeQuantumVoList;
    }

    /**
     * 保存秒杀活动信息
     *
     * @param saveSeckillDto
     * @return
     */
    @Override
    public void saveReduced(SaveSeckillDto saveSeckillDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        //判断分类是否存在
        List<Long> categoryIds = saveSeckillDto.getCategoryIds();
        categoryIds.forEach(a -> {
            if (categoryMapper.selectById(a) == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, String.format("该分类不存在:[%s]", a));
            }else if (categoryMapper.selectById(a).getLevel()!=3){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("该分类:[%s]不是三级分类",categoryMapper.selectById(a).getName()));
            }
        });
        //时间判断
        LocalDateTime registrationStartTime = saveSeckillDto.getRegistrationStartTime();
        LocalDateTime registrationEndTime = saveSeckillDto.getRegistrationEndTime();
        LocalDateTime activityStartTime = saveSeckillDto.getActivityStartTime();
        LocalDateTime activityEndTime = saveSeckillDto.getActivityEndTime();
        if (registrationEndTime.isBefore(registrationStartTime) || registrationEndTime.equals(registrationStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "报名结束时间不能小于报名开始时间");
        }
        if (activityEndTime.isBefore(activityStartTime) || activityEndTime.equals(activityStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "活动结束时间不能小于活动开始时间");
        }
        if (activityStartTime.isBefore(registrationEndTime) || registrationEndTime.equals(activityStartTime)) {
            throw new ServiceException(ResultCode.FAIL, "活动开始时间不能小于报名结束时间");
        }
        if (activityEndTime.toEpochSecond(ZoneOffset.of("+8"))-activityStartTime.toEpochSecond(ZoneOffset.of("+8"))>86400){
            throw new ServiceException(ResultCode.FAIL, "秒杀活动时间最长可设置24小时!");
        }

        //判断活动开始时间和结束时间是否为整点
        if (!(activityStartTime.getMinute() == 0 && activityStartTime.getSecond() == 0)){
            throw new ServiceException(ResultCode.FAIL,"活动开始时间必须为整点!");
        }
        if (!(activityEndTime.getMinute() == 0 && activityEndTime.getSecond() == 0)){
            throw new ServiceException(ResultCode.FAIL,"活动结束时间必须为整点!");
        }

        //可领取会员为全部会员操作
        Long memberLevelId = 0L;
        if (saveSeckillDto.getMemberLevelId() == 0){
            PmMemberLevelPo memberLevelPo = memberLevelMapper.selectOne(new QueryWrapper<PmMemberLevelPo>().lambda()
                    .eq(PmMemberLevelPo::getLevel,1));
            if (memberLevelPo != null){
                memberLevelId = memberLevelPo.getId();
            }
        }else {
            memberLevelId = saveSeckillDto.getMemberLevelId();
        }

        //新增操作
        if (saveSeckillDto.getId() == 0) {

            if (saveSeckillDto.getRegistrationStartTime().isBefore(LocalDateTime.now())) {
                throw new ServiceException(ResultCode.FAIL, String.format("活动报名开始时间需要在当前时间之后"));
            }
            if (saveSeckillDto.getActivityStartTime().isBefore(LocalDateTime.now())) {
                throw new ServiceException(ResultCode.FAIL, String.format("活动开始时间需要在当前时间之后"));
            }

            AmSeckillPo seckillPo = new AmSeckillPo();
            BeanUtils.copyProperties(saveSeckillDto, seckillPo);
            seckillPo.setId(null);
            seckillPo.setCreateBy(userPo.getUsername());
            seckillPo.setMemberLevelId(memberLevelId);
            mapper.insert(seckillPo);
            //保存积分活动与分类的信息
            if (!ListUtil.isListNullAndEmpty(categoryIds)) {
                categoryIds.forEach(a -> {
                    AmActivityRelActivityCategoryPo relActivityCategoryPo = new AmActivityRelActivityCategoryPo();
                    relActivityCategoryPo.setCategoryId(a);
                    relActivityCategoryPo.setCreateBy(userPo.getUsername());
                    relActivityCategoryPo.setActivityType(ActivityTypeEnum.SECKILL.getId());
                    relActivityCategoryPo.setActivityId(seckillPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
        //修改操作
        else {
            AmSeckillPo seckillPo = mapper.selectById(saveSeckillDto.getId());

            //判断是否修改开始时间和结束时间
            LocalDateTime registrationStartTime1 =seckillPo.getRegistrationStartTime();
            LocalDateTime registrationEndTime1 = seckillPo.getRegistrationEndTime();
            LocalDateTime activityStartTime1 = seckillPo.getActivityStartTime();
            LocalDateTime activityEndTime1 = seckillPo.getActivityEndTime();

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
            if (seckillPo.getRegistrationStartTime().isBefore(LocalDateTime.now())){
                throw new ServiceException(ResultCode.FAIL,"该活动报名已经开始，不能修改！");
            }

            BeanUtils.copyProperties(saveSeckillDto, seckillPo);
            seckillPo.setUpdateBy(userPo.getUsername());
            seckillPo.setMemberLevelId(memberLevelId);
            mapper.updateById(seckillPo);
            List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id", saveSeckillDto.getId()));
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
                    relActivityCategoryPo.setActivityId(seckillPo.getId());
                    relActivityCategoryMapper.insert(relActivityCategoryPo);
                });
            }
        }
    }

    /**
     * 条件查询秒杀活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    @Override
    public PageInfo<SearchActivityListVo> searchSeckillList(SearchActivityListDto searchActivityListDto) {

        Integer pageNo = searchActivityListDto.getPageNo()==null ? defaultPageNo : searchActivityListDto.getPageNo();
        Integer pageSize = searchActivityListDto.getPageSize()==null ? defaultPageSize : searchActivityListDto.getPageSize();

        PageInfo<SearchActivityListVo> searchActivityListVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchSeckillList(searchActivityListDto));
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
            AmSeckillPo amSeckillPo = mapper.selectById(id);
            if (amSeckillPo == null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在该活动:[%s],id"));
            }
            if (!amSeckillPo.getRegistrationStartTime().isAfter(LocalDateTime.now())){
                throw new ServiceException(ResultCode.FAIL,String.format("该活动[%s:%s]的报名状态不是待开始状态，不能删除",id,amSeckillPo.getName()));
            }
        });
        mapper.deleteBatchIds(ids);
    }
}
