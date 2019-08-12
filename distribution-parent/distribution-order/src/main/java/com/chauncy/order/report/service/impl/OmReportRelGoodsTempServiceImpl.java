package com.chauncy.order.report.service.impl;

import com.chauncy.data.domain.po.order.report.OmReportRelGoodsTempPo;
import com.chauncy.data.mapper.order.report.OmReportRelGoodsTempMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.report.service.IOmReportRelGoodsTempService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 报表商品快照关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmReportRelGoodsTempServiceImpl extends AbstractService<OmReportRelGoodsTempMapper,OmReportRelGoodsTempPo> implements IOmReportRelGoodsTempService {

 @Autowired
 private OmReportRelGoodsTempMapper mapper;

}
