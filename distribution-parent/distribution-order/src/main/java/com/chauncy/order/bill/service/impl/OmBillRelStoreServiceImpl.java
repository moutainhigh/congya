package com.chauncy.order.bill.service.impl;

import com.chauncy.data.domain.po.order.bill.OmBillRelStorePo;
import com.chauncy.data.mapper.order.bill.OmBillRelStoreMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.bill.service.IOmBillRelStoreService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 团队合作利润账单店铺关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmBillRelStoreServiceImpl extends AbstractService<OmBillRelStoreMapper, OmBillRelStorePo> implements IOmBillRelStoreService {

    @Autowired
    private OmBillRelStoreMapper mapper;

}
