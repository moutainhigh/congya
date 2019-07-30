package com.chauncy.store.cooperation;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.store.cooperation.CooperationInvitationPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.cooperation.SaveCooperationDto;
import com.chauncy.data.dto.app.cooperation.select.SearchOperationDto;
import com.chauncy.data.vo.manage.cooperation.SearchCooperationVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 合作邀请表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-30
 */
public interface ICooperationInvitationService extends Service<CooperationInvitationPo> {

    /**
     * 保存用户合作邀请信息
     *
     * @param saveCooperationDto
     * @return
     */
    void saveCooperation(SaveCooperationDto saveCooperationDto);

    /**
     * 条件分页查询合作申请列表
     *
     * @param searchOperationDto
     * @return
     */
    PageInfo<SearchCooperationVo> searchOperation(SearchOperationDto searchOperationDto);
}
