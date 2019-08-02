package com.chauncy.data.mapper.activity.group;

import com.chauncy.data.domain.po.activity.group.AmActivityGroupPo;
import com.chauncy.data.dto.manage.activity.group.select.SearchGroupDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.activity.group.SearchActivityGroupVo;

import java.util.List;

/**
 * <p>
 * 活动分组管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface AmActivityGroupMapper extends IBaseMapper<AmActivityGroupPo> {

    /**
     * 条件查询活动分组信息
     * @param searchGroupDto
     * @return
     */
    List<SearchActivityGroupVo> search(SearchGroupDto searchGroupDto);
}
