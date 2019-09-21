package com.chauncy.order.service.impl;

import com.chauncy.data.domain.po.order.PayUserRelationNextLevelPo;
import com.chauncy.data.mapper.order.PayUserRelationNextLevelMapper;
import com.chauncy.order.service.IPayUserRelationNextLevelService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 返积分、经验值用户关系链，下一级用户id 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayUserRelationNextLevelServiceImpl extends AbstractService<PayUserRelationNextLevelMapper,PayUserRelationNextLevelPo> implements IPayUserRelationNextLevelService {

 @Autowired
 private PayUserRelationNextLevelMapper mapper;

}
