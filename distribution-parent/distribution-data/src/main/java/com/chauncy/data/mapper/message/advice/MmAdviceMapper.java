package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;

import java.util.List;

/**
 * <p>
 * 广告基本信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceMapper extends IBaseMapper<MmAdvicePo> {

    /**
     * 条件分页查询广告基本信息
     *
     * @param searchAdvicesDto
     * @return
     */
    List<SearchAdvicesVo> searchAdvices(SearchAdvicesDto searchAdvicesDto);
}
