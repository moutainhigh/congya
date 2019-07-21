package com.chauncy.data.mapper.order.log;

import com.chauncy.data.domain.po.order.log.OmOrderAccountLogPo;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 账目流水表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
public interface OmOrderAccountLogMapper extends IBaseMapper<OmOrderAccountLogPo> {

    /**
     * 平台流水
     *
     * @param searchPlatformLogDto
     * @return
     */
    PageInfo<SearchPlatformLogVo> searchPlatformLogPaging(SearchPlatformLogDto searchPlatformLogDto);
}
