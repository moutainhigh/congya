package com.chauncy.data.areaService.impl;

import com.chauncy.data.domain.po.area.AreaRegionPo;
import com.chauncy.data.mapper.area.AreaRegionMapper;
import com.chauncy.data.areaService.IAreaRegionService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 中国行政地址信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class AreaRegionServiceImpl extends AbstractService<AreaRegionMapper,AreaRegionPo> implements IAreaRegionService {

 @Autowired
 private AreaRegionMapper mapper;

}
