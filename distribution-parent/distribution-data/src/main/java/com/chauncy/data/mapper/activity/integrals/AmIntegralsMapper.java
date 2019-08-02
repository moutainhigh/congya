package com.chauncy.data.mapper.activity.integrals;

import com.chauncy.data.domain.po.activity.integrals.AmIntegralsPo;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;

import java.util.List;

/**
 * <p>
 * 积分活动管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface AmIntegralsMapper extends IBaseMapper<AmIntegralsPo> {

    /**
     * 条件查询积分活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    List<SearchActivityListVo> searchIntegralsList(SearchActivityListDto searchActivityListDto);
}
