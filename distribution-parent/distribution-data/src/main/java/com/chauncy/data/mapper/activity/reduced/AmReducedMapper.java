package com.chauncy.data.mapper.activity.reduced;

import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;

import java.util.List;

/**
 * <p>
 * 满减活动管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface AmReducedMapper extends IBaseMapper<AmReducedPo> {

    /**
     * 条件查询满减活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    List<SearchActivityListVo> searchReduceList(SearchActivityListDto searchActivityListDto);
}
