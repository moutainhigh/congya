package com.chauncy.message.interact.service.impl;

import com.chauncy.data.domain.po.message.interact.MmInteractPushRelUserPo;
import com.chauncy.data.mapper.message.interact.MmInteractPushRelUserMapper;
import com.chauncy.message.interact.service.IMmInteractPushRelUserService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 平台信息关联APP用户 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInteractPushRelUserServiceImpl extends AbstractService<MmInteractPushRelUserMapper,MmInteractPushRelUserPo> implements IMmInteractPushRelUserService {

 @Autowired
 private MmInteractPushRelUserMapper mapper;

}
