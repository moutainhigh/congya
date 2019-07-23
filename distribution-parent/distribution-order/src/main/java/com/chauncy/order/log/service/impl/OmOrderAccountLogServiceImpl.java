package com.chauncy.order.log.service.impl;

import com.chauncy.data.domain.po.order.log.OmOrderAccountLogPo;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.mapper.order.log.OmOrderAccountLogMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.order.log.service.IOmOrderAccountLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 账目流水表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderAccountLogServiceImpl extends AbstractService<OmOrderAccountLogMapper, OmOrderAccountLogPo> implements IOmOrderAccountLogService {

    @Autowired
    private OmOrderAccountLogMapper omOrderAccountLogMapper;


    /**
     * 平台流水
     *
     * @param searchPlatformLogDto
     * @return
     */
    @Override
    public PageInfo<SearchPlatformLogVo> searchPlatformLogPaging(SearchPlatformLogDto searchPlatformLogDto) {

        return omOrderAccountLogMapper.searchPlatformLogPaging(searchPlatformLogDto);
    }
}
