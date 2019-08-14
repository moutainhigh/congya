package com.chauncy.message.advice.impl;

import com.chauncy.data.domain.po.message.advice.MmAdviceTabPo;
import com.chauncy.data.mapper.message.advice.MmAdviceTabMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.message.advice.IMmAdviceTabService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告选项卡表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceTabServiceImpl extends AbstractService<MmAdviceTabMapper, MmAdviceTabPo> implements IMmAdviceTabService {

    @Autowired
    private MmAdviceTabMapper mapper;

}
