package com.chauncy.store.rel.service.impl;

import com.chauncy.data.domain.po.store.rel.SmRelUserFoucsStorePo;
import com.chauncy.data.mapper.store.rel.SmRelUserFoucsStoreMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.store.rel.service.ISmRelUserFoucsStoreService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户关注店铺关联信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmRelUserFoucsStoreServiceImpl extends AbstractService<SmRelUserFoucsStoreMapper,SmRelUserFoucsStorePo> implements ISmRelUserFoucsStoreService {

 @Autowired
 private SmRelUserFoucsStoreMapper mapper;

}
