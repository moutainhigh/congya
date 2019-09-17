package com.chauncy.message.interact.service.impl;

import com.chauncy.data.domain.po.message.interact.MmUserNoticeTimePo;
import com.chauncy.data.mapper.message.interact.MmUserNoticeTimeMapper;
import com.chauncy.message.interact.service.IMmUserNoticeTimeService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户系统消息最近查看时间表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmUserNoticeTimeServiceImpl extends AbstractService<MmUserNoticeTimeMapper,MmUserNoticeTimePo> implements IMmUserNoticeTimeService {

 @Autowired
 private MmUserNoticeTimeMapper mapper;

}
