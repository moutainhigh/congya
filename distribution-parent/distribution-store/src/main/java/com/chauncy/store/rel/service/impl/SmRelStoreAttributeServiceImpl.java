package com.chauncy.store.rel.service.impl;

import com.chauncy.data.domain.po.store.rel.SmRelStoreAttributePo;
import com.chauncy.data.mapper.store.SmRelStoreAttributeMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.store.rel.service.ISmRelStoreAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-19
 */
@Service
public class SmRelStoreAttributeServiceImpl extends AbstractService<SmRelStoreAttributeMapper,SmRelStoreAttributePo> implements ISmRelStoreAttributeService {

 @Autowired
 private SmRelStoreAttributeMapper mapper;

}
