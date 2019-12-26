package com.chauncy.data.mapper.activity.spell;

import com.chauncy.data.domain.po.activity.spell.AmSpellGroupMainPo;
import com.chauncy.data.dto.manage.activity.spell.select.SearchSpellRecordDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.advice.activity.SpellGroupVo;
import com.chauncy.data.vo.supplier.activity.SearchSpellRecordVo;

import java.util.List;

/**
 * <p>
 * 拼团单号表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-31
 */
public interface AmSpellGroupMainMapper extends IBaseMapper<AmSpellGroupMainPo> {

    /**
     *  条件查询拼团记录
     * @param searchSpellRecordDto
     * @return
     */
    List<SearchSpellRecordVo> searchSpellRecord(SearchSpellRecordDto searchSpellRecordDto);

    /**
     * @Author yeJH
     * @Date 2019/12/14 17:43
     * @Description 获取已拼人数 已拼件数
     *
     * @Update yeJH
     *
     * @param  id
     * @return com.chauncy.data.vo.app.advice.activity.SpellGroupVo
     **/
    SpellGroupVo getSpellGroup(Long id);
}
