package com.chauncy.order.log.service;

import com.chauncy.data.domain.po.order.log.OmOrderAccountLogPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 账目流水表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
public interface IOmOrderAccountLogService extends Service<OmOrderAccountLogPo> {

    /**
     * 平台流水
     * @param searchPlatformLogDto
     * @return
     */
    PageInfo<SearchPlatformLogVo> searchPlatformLogPaging(SearchPlatformLogDto searchPlatformLogDto);
}
