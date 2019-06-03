package com.chauncy.data.temp.store.service.impl;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.temp.store.service.ISmStoreService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
@Service
public class SmStoreServiceImpl extends AbstractService<SmStoreMapper,SmStorePo> implements ISmStoreService {

 @Autowired
 private SmStoreMapper mapper;

}
