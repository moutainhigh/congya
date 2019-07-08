package com.chauncy.message.interact.service.impl;

import com.chauncy.data.domain.po.message.interact.MmInteractRelMessageObjectPo;
import com.chauncy.data.mapper.message.interact.MmInteractRelMessageObjectMapper;
import com.chauncy.message.interact.service.IMmInteractRelMessageObjectService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 推送信息与推送对象关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInteractRelMessageObjectServiceImpl extends AbstractService<MmInteractRelMessageObjectMapper,MmInteractRelMessageObjectPo> implements IMmInteractRelMessageObjectService {

 @Autowired
 private MmInteractRelMessageObjectMapper mapper;

}
