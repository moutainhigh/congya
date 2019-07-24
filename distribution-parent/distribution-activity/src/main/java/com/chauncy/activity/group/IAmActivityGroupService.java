package com.chauncy.activity.group;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.group.AmActivityGroupPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.EditEnableDto;
import com.chauncy.data.dto.manage.activity.group.add.SaveGroupDto;
import com.chauncy.data.dto.manage.activity.group.select.SearchGroupDto;
import com.chauncy.data.vo.manage.activity.group.SearchActivityGroupVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 活动分组管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface IAmActivityGroupService extends Service<AmActivityGroupPo> {

    /**
     * 保存活动分组信息
     *
     * @param saveGroupDto
     * @return
     */
    void saveGroup(SaveGroupDto saveGroupDto);

    /**
     * 条件查询活动分组信息
     * @param searchGroupDto
     * @return
     */
    PageInfo<SearchActivityGroupVo> search(SearchGroupDto searchGroupDto);

    /**
     * 批量删除活动分组
     *
     * @param ids
     * @return
     */
    void delByIds(List<Long> ids);
}
