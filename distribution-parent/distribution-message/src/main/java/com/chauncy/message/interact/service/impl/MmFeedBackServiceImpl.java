package com.chauncy.message.interact.service.impl;

import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.mapper.message.interact.MmFeedBackMapper;
import com.chauncy.message.interact.service.IMmFeedBackService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * app用户反馈列表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmFeedBackServiceImpl extends AbstractService<MmFeedBackMapper,MmFeedBackPo> implements IMmFeedBackService {

 @Autowired
 private MmFeedBackMapper mapper;

}
