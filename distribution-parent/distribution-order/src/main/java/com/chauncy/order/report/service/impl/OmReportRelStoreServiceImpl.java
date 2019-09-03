package com.chauncy.order.report.service.impl;

import com.chauncy.data.domain.po.order.report.OmReportRelStorePo;
import com.chauncy.data.mapper.order.report.OmReportRelStoreMapper;
import com.chauncy.order.report.service.IOmReportRelStoreService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品销售报表关联店铺表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmReportRelStoreServiceImpl extends AbstractService<OmReportRelStoreMapper,OmReportRelStorePo> implements IOmReportRelStoreService {

 @Autowired
 private OmReportRelStoreMapper mapper;

}
