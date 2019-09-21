package com.chauncy.data.mapper.activity.group;

import com.chauncy.data.domain.po.activity.group.AmActivityGroupPo;
import com.chauncy.data.dto.manage.activity.group.select.SearchGroupDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchActivityGroupDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.activity.group.SearchActivityGroupVo;
import org.apache.ibatis.annotations.Select;

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

    /**
     * @Author chauncy
     * @Date 2019-09-20 11:29
     * @Description //条件分页查询活动分组信息
     *
     * @Update chauncy
     *
     * @Param []
     * @return void
     **/
    @Select("select id,picture as name from am_activity_group where del_flag = 0 and enable = 1 and type = #{groupType}")
    List<BaseVo> searchActivityGroup(SearchActivityGroupDto searchActivityGroupDto);
}
