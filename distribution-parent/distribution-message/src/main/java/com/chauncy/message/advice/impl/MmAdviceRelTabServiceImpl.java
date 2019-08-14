package com.chauncy.message.advice.impl;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabPo;
import com.chauncy.data.mapper.message.advice.MmAdviceRelTabMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.message.advice.IMmAdviceRelTabService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告与广告选项卡表关联表，广告位置为除了具体店铺分类的所有有选项卡的广告 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceRelTabServiceImpl extends AbstractService<MmAdviceRelTabMapper, MmAdviceRelTabPo> implements IMmAdviceRelTabService {

    @Autowired
    private MmAdviceRelTabMapper mapper;

}
