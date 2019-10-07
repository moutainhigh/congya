package com.chauncy.activity.group.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.activity.group.IAmActivityGroupService;
import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.group.AmActivityGroupPo;
import com.chauncy.data.domain.po.activity.integrals.AmIntegralsPo;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.activity.EditEnableDto;
import com.chauncy.data.dto.manage.activity.group.add.SaveGroupDto;
import com.chauncy.data.dto.manage.activity.group.select.SearchGroupDto;
import com.chauncy.data.mapper.activity.group.AmActivityGroupMapper;
import com.chauncy.data.mapper.activity.integrals.AmIntegralsMapper;
import com.chauncy.data.mapper.activity.reduced.AmReducedMapper;
import com.chauncy.data.vo.manage.activity.group.SearchActivityGroupVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 活动分组管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmActivityGroupServiceImpl extends AbstractService<AmActivityGroupMapper, AmActivityGroupPo> implements IAmActivityGroupService {

    @Autowired
    private AmActivityGroupMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AmReducedMapper reducedMapper;

    @Autowired
    private AmIntegralsMapper integralsMapper;

    /**
     * 保存活动分组信息
     *
     * @param saveGroupDto
     * @return
     */
    @Override
    public void saveGroup(SaveGroupDto saveGroupDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        AmActivityGroupPo activityGroupPo = new AmActivityGroupPo();
        //新增
        if (saveGroupDto.getId()==0){
            BeanUtils.copyProperties(saveGroupDto,activityGroupPo);
            activityGroupPo.setId(null);
            activityGroupPo.setCreateBy(userPo.getUsername());
            mapper.insert(activityGroupPo);
        }else{
            activityGroupPo = mapper.selectById(saveGroupDto.getId());
            BeanUtils.copyProperties(saveGroupDto,activityGroupPo);
            activityGroupPo.setUpdateBy(userPo.getUsername());
            mapper.updateById(activityGroupPo);
        }
    }

    /**
     * 条件查询活动分组信息
     * @param searchGroupDto
     * @return
     */
    @Override
    public PageInfo<SearchActivityGroupVo> search(SearchGroupDto searchGroupDto) {

        Integer pageNo = searchGroupDto.getPageNo()==null ? defaultPageNo : searchGroupDto.getPageNo();
        Integer pageSize = searchGroupDto.getPageSize()==null ? defaultPageSize : searchGroupDto.getPageSize();

        if (searchGroupDto.getType() != null) {
            GroupTypeEnum groupTypeEnum = GroupTypeEnum.getGroupTypeEnumById(searchGroupDto.getType());
            if (groupTypeEnum == null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("GroupTypeEnum枚举类中不存在【type】为%s的枚举值,请检查",searchGroupDto.getType()));
            }
        }

        PageInfo<SearchActivityGroupVo> searchActivityGroupVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() ->mapper.search(searchGroupDto));

        return searchActivityGroupVoPageInfo;
    }

    /**
     * 批量删除活动分组
     *
     * @param ids
     * @return
     */
    @Override
    public void delByIds(List<Long> ids) {

        ids.forEach(a->{
            AmActivityGroupPo activityGroupPo = mapper.selectById(a);
            if (activityGroupPo == null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在该分组:[%s],a"));
            }
            //活动分组被绑定则不能删除
            List<AmReducedPo> reducedPos = reducedMapper.selectList(new QueryWrapper<AmReducedPo>().eq("group_id",a));
            List<AmIntegralsPo> integralsPos = integralsMapper.selectList(new QueryWrapper<AmIntegralsPo>().eq("group_id",a));
            if (!ListUtil.isListNullAndEmpty(reducedPos) || !ListUtil.isListNullAndEmpty(integralsPos)){
                throw new ServiceException(ResultCode.FAIL,String.format("该活动分组[%s]已经被活动绑定，不能删除",activityGroupPo.getName()));
            }
        });
        //批量删除
        mapper.deleteBatchIds(ids);
    }
}
