package com.chauncy.store.rel.service.impl;

import com.chauncy.data.domain.po.store.rel.SmStoreRelStorePo;
import com.chauncy.data.mapper.store.rel.SmStoreRelStoreMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.store.rel.service.ISmStoreRelStoreService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 店铺与店铺关联信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmStoreRelStoreServiceImpl extends AbstractService<SmStoreRelStoreMapper,SmStoreRelStorePo> implements ISmStoreRelStoreService {

 @Autowired
 private SmStoreRelStoreMapper mapper;

}
