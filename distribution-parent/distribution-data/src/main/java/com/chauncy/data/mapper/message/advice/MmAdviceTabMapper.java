package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceTabPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 广告选项卡表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceTabMapper extends IBaseMapper<MmAdviceTabPo> {

    @Select("select * from mm_advice_tab where del_flag = 0 and id = #{tabId}")
    MmAdviceTabPo selectByTabId(Long tabId);
}
