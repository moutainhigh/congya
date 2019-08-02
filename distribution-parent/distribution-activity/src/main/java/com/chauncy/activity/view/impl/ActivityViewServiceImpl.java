package com.chauncy.activity.view.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.view.IActivityViewService;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.activity.AmActivityRelActivityCategoryPo;
import com.chauncy.data.domain.po.activity.group.AmActivityGroupPo;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.domain.po.activity.view.ActivityViewPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.dto.supplier.activity.add.SearchAllActivitiesDto;
import com.chauncy.data.dto.supplier.activity.select.FindByIdAndTypeDto;
import com.chauncy.data.mapper.activity.AmActivityRelActivityCategoryMapper;
import com.chauncy.data.mapper.activity.group.AmActivityGroupMapper;
import com.chauncy.data.mapper.activity.integrals.AmIntegralsMapper;
import com.chauncy.data.mapper.activity.reduced.AmReducedMapper;
import com.chauncy.data.mapper.activity.seckill.AmSeckillMapper;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMapper;
import com.chauncy.data.mapper.activity.view.ActivityViewMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.manage.activity.SearchGoodsCategoryVo;
import com.chauncy.data.vo.supplier.activity.ActivityVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityViewServiceImpl extends AbstractService<ActivityViewMapper, ActivityViewPo> implements IActivityViewService {

    @Autowired
    private ActivityViewMapper mapper;

    @Autowired
    private AmReducedMapper reducedMapper;

    @Autowired
    private AmIntegralsMapper integralsMapper;

    @Autowired
    private AmSeckillMapper seckillMapper;

    @Autowired
    private AmSpellGroupMapper spellGroupMapper;

    @Autowired
    private AmActivityGroupMapper activityGroupMapper;

    @Autowired
    private AmActivityRelActivityCategoryMapper relActivityCategoryMapper;

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    /**
     *
     * 查询全部活动列表信息
     * @param searchAllActivitiesDto
     * @return
     */
    @Override
    public PageInfo<ActivityViewPo> searchAllActivitiesVo(SearchAllActivitiesDto searchAllActivitiesDto) {

        Integer pageNo = searchAllActivitiesDto.getPageNo()==null ? defaultPageNo : searchAllActivitiesDto.getPageNo();
        Integer pageSize = searchAllActivitiesDto.getPageSize()==null ? defaultPageSize : searchAllActivitiesDto.getPageSize();

        PageInfo<ActivityViewPo> searchAllActivitiesVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchAllActivitiesVo(searchAllActivitiesDto));

        return searchAllActivitiesVoPageInfo;
    }


    /**
     * 查询活动详情
     * @param findByIdAndTypeDto
     * @return
     */
    @Override
    public SearchActivityListVo findActivityDetailByIdAndType(FindByIdAndTypeDto findByIdAndTypeDto) {

        ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.fromName(findByIdAndTypeDto.getType());
        SearchActivityListVo activityVo = new SearchActivityListVo();
        List<SearchGoodsCategoryVo> goodsCategoryVoList = Lists.newArrayList();
        switch (activityTypeEnum) {
            case REDUCED:
                activityVo = reducedMapper.findReducedById(findByIdAndTypeDto.getId());
                if (activityGroupMapper.selectById(activityVo.getGroupId()) !=null) {
                    activityVo.setGroupName(activityGroupMapper.selectById(activityVo.getGroupId()).getName());
                }
                List<AmActivityRelActivityCategoryPo> relActivityCategoryPos = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id",findByIdAndTypeDto.getId()));
                List<Long> categoryIds = relActivityCategoryPos.stream().map(a->a.getCategoryId()).collect(Collectors.toList());

                getCategoryName(goodsCategoryVoList, categoryIds);
                activityVo.setGoodsCategoryVoList(goodsCategoryVoList);
                activityVo.setMemberName(memberLevelMapper.selectById(activityVo.getMemberLevelId()).getLevelName());
                break;
            case INTEGRALS:
                activityVo = integralsMapper.findIntegralById(findByIdAndTypeDto.getId());
                activityVo.setGroupName(activityGroupMapper.selectById(activityVo.getGroupId()).getName());
                List<AmActivityRelActivityCategoryPo> relActivityCategoryPos1 = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id",findByIdAndTypeDto.getId()));
                List<Long> categoryIds1 = relActivityCategoryPos1.stream().map(a->a.getCategoryId()).collect(Collectors.toList());
                List<SearchGoodsCategoryVo> goodsCategoryVoList1 = Lists.newArrayList();
                getCategoryName(goodsCategoryVoList, categoryIds1);
                activityVo.setGoodsCategoryVoList(goodsCategoryVoList);
                activityVo.setMemberName(memberLevelMapper.selectById(activityVo.getMemberLevelId()).getLevelName());
                break;
            case SECKILL:
                activityVo = seckillMapper.findSeckillById(findByIdAndTypeDto.getId());
                List<AmActivityRelActivityCategoryPo> relActivityCategoryPos2 = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id",findByIdAndTypeDto.getId()));
                List<Long> categoryIds2 = relActivityCategoryPos2.stream().map(a->a.getCategoryId()).collect(Collectors.toList());
                List<SearchGoodsCategoryVo> goodsCategoryVoList2 = Lists.newArrayList();
                getCategoryName(goodsCategoryVoList, categoryIds2);
                activityVo.setGoodsCategoryVoList(goodsCategoryVoList);
                activityVo.setMemberName(memberLevelMapper.selectById(activityVo.getMemberLevelId()).getLevelName());
                break;
            case SPELL_GROUP:
                activityVo = spellGroupMapper.findSpellGroupById(findByIdAndTypeDto.getId());
                List<AmActivityRelActivityCategoryPo> relActivityCategoryPos3 = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>().eq("activity_id",findByIdAndTypeDto.getId()));
                List<Long> categoryIds3 = relActivityCategoryPos3.stream().map(a->a.getCategoryId()).collect(Collectors.toList());
                List<SearchGoodsCategoryVo> goodsCategoryVoList3 = Lists.newArrayList();
                getCategoryName(goodsCategoryVoList, categoryIds3);
                activityVo.setGoodsCategoryVoList(goodsCategoryVoList);
                activityVo.setMemberName(memberLevelMapper.selectById(activityVo.getMemberLevelId()).getLevelName());
                break;
        }
        return activityVo;
    }

    private void getCategoryName(List<SearchGoodsCategoryVo> goodsCategoryVoList, List<Long> categoryIds3) {
        categoryIds3.forEach(c -> {
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
    }
}
