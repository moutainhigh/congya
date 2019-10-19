package com.chauncy.message.interact.service.impl;

import com.chauncy.data.domain.po.message.interact.MmUserNoticeRelUserPo;
import com.chauncy.data.mapper.message.interact.MmUserNoticeRelUserMapper;
import com.chauncy.message.interact.service.IMmUserNoticeRelUserService;
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
public class MmUserNoticeRelUserServiceImpl extends AbstractService<MmUserNoticeRelUserMapper, MmUserNoticeRelUserPo> implements IMmUserNoticeRelUserService {

    @Autowired
    private MmUserNoticeRelUserMapper mapper;

}
