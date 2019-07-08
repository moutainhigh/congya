package com.chauncy.store.rel.service.impl;

import com.chauncy.data.domain.po.store.rel.SmRelUserFocusStorePo;
import com.chauncy.data.mapper.store.rel.SmRelUserFocusStoreMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.store.rel.service.ISmRelUserFocusStoreService;
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
public class SmRelUserFocusStoreServiceImpl extends AbstractService<SmRelUserFocusStoreMapper, SmRelUserFocusStorePo> implements ISmRelUserFocusStoreService {

 @Autowired
 private SmRelUserFocusStoreMapper mapper;

}
