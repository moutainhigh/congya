package com.chauncy.message.information.rel.service.impl;

import com.chauncy.data.domain.po.message.information.rel.MmInformationForwardPo;
import com.chauncy.data.mapper.message.information.rel.MmInformationForwardMapper;
import com.chauncy.message.information.rel.service.IMmInformationForwardService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户资讯转发表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInformationForwardServiceImpl extends AbstractService<MmInformationForwardMapper,MmInformationForwardPo> implements IMmInformationForwardService {

 @Autowired
 private MmInformationForwardMapper mapper;

}
