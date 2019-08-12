package com.chauncy.order.report.service.impl;

import com.chauncy.data.domain.po.order.report.OmOrderReportPo;
import com.chauncy.data.mapper.order.report.OmOrderReportMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.report.service.IOmOrderReportService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品销售报表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderReportServiceImpl extends AbstractService<OmOrderReportMapper,OmOrderReportPo> implements IOmOrderReportService {

 @Autowired
 private OmOrderReportMapper mapper;

}
