package com.chauncy.message.information.rel.service.impl;

import com.chauncy.data.domain.po.message.information.MmUserInformationTimePo;
import com.chauncy.data.mapper.message.information.MmUserInformationTimeMapper;
import com.chauncy.message.information.rel.service.IMmUserInformationTimeService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户关注资讯最近查看时间表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmUserInformationTimeServiceImpl extends AbstractService<MmUserInformationTimeMapper,MmUserInformationTimePo> implements IMmUserInformationTimeService {

 @Autowired
 private MmUserInformationTimeMapper mapper;

}
