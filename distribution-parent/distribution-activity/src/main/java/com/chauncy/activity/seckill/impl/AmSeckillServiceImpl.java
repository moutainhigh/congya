package com.chauncy.activity.seckill.impl;

import com.chauncy.activity.seckill.IAmSeckillService;
import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.mapper.activity.seckill.AmSeckillMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 秒杀活动管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmSeckillServiceImpl extends AbstractService<AmSeckillMapper,AmSeckillPo> implements IAmSeckillService {

 @Autowired
 private AmSeckillMapper mapper;

}
