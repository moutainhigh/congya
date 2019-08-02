package com.chauncy.data.mapper.activity.spell;

import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.spell.select.SearchSpellRecordDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.supplier.activity.SearchSpellRecordVo;

import java.util.List;

/**
 * <p>
 * 秒杀活动管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface AmSpellGroupMapper extends IBaseMapper<AmSpellGroupPo> {

    /**
     * 条件查询拼团活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    List<SearchActivityListVo> searchSpellList(SearchActivityListDto searchActivityListDto);

    /**
     * 查询拼团详情
     * @param id
     * @return
     */
    SearchActivityListVo findSpellGroupById(Long id);

}
