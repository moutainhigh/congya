package com.chauncy.store.rel.service.impl;

import com.chauncy.data.domain.po.store.rel.SmStoreRelLabelPo;
import com.chauncy.data.mapper.store.rel.SmStoreRelLabelMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.store.rel.service.ISmStoreRelLabelService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 店铺与标签关联信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmStoreRelLabelServiceImpl extends AbstractService<SmStoreRelLabelMapper,SmStoreRelLabelPo> implements ISmStoreRelLabelService {

 @Autowired
 private SmStoreRelLabelMapper mapper;

}
