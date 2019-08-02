package com.chauncy.data.mapper.store.cooperation;

import com.chauncy.data.domain.po.store.cooperation.CooperationInvitationPo;
import com.chauncy.data.dto.app.cooperation.select.SearchOperationDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.cooperation.SearchCooperationVo;

import java.util.List;

/**
 * <p>
 * 合作邀请表 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-30
 */
public interface CooperationInvitationMapper extends IBaseMapper<CooperationInvitationPo> {

    /**
     * 条件分页查询合作申请列表
     *
     * @param searchOperationDto
     * @return
     */
    List<SearchCooperationVo> searchOperation(SearchOperationDto searchOperationDto);
}
