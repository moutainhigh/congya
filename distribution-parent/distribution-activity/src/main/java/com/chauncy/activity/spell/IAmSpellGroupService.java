package com.chauncy.activity.spell;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.spell.SaveSpellDto;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 秒杀活动管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface IAmSpellGroupService extends Service<AmSpellGroupPo> {

    /**
     * 保存秒杀活动信息
     * @param saveSpellDto
     * @return
     */
    void saveSpell(SaveSpellDto saveSpellDto);

    /**
     * 条件查询拼团活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    PageInfo<SearchActivityListVo> searchSpellList(SearchActivityListDto searchActivityListDto);

    /**
     * 批量删除活动
     *
     * @param ids
     * @return
     */
    void delByIds(List<Long> ids);
}
