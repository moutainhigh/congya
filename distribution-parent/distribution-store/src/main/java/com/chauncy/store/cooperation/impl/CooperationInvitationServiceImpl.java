package com.chauncy.store.cooperation.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.store.cooperation.CooperationInvitationPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.cooperation.SaveCooperationDto;
import com.chauncy.data.dto.app.cooperation.select.SearchOperationDto;
import com.chauncy.data.mapper.store.cooperation.CooperationInvitationMapper;
import com.chauncy.data.vo.manage.cooperation.SearchCooperationVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.cooperation.ICooperationInvitationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 合作邀请表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CooperationInvitationServiceImpl extends AbstractService<CooperationInvitationMapper, CooperationInvitationPo> implements ICooperationInvitationService {

    @Autowired
    private CooperationInvitationMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 保存用户合作邀请信息
     *
     * @param saveCooperationDto
     * @return
     */
    @Override
    public void saveCooperation(SaveCooperationDto saveCooperationDto) {

        UmUserPo userPo = securityUtil.getAppCurrUser();
        SysUserPo userPo1 = securityUtil.getCurrUser();
        if (userPo == null){
            throw new ServiceException(ResultCode.FAIL,String.format("用户:[%s]不是App用户,不能执行此操作，请检查!",userPo1.getUsername()));
        }
        CooperationInvitationPo cooperationInvitationPo = new CooperationInvitationPo();
        BeanUtils.copyProperties(saveCooperationDto,cooperationInvitationPo);
        cooperationInvitationPo.setUserId(userPo.getId());
        cooperationInvitationPo.setCreateBy(userPo.getTrueName());
        mapper.insert(cooperationInvitationPo);
    }

    /**
     * 条件分页查询合作申请列表
     *
     * @param searchOperationDto
     * @return
     */
    @Override
    public PageInfo<SearchCooperationVo> searchOperation(SearchOperationDto searchOperationDto) {

        Integer pageNo = searchOperationDto.getPageNo() == null ? defaultPageNo : searchOperationDto.getPageNo();
        Integer pageSize = searchOperationDto.getPageSize() == null ? defaultPageSize : searchOperationDto.getPageSize();
        PageInfo<SearchCooperationVo> searchCooperationVoPageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->mapper.searchOperation(searchOperationDto));

        return searchCooperationVoPageInfo;
    }
}
