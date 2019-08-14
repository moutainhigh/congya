package com.chauncy.message.advice.impl;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelShufflingPo;
import com.chauncy.data.mapper.message.advice.MmAdviceRelShufflingMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.message.advice.IMmAdviceRelShufflingService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告与无关联轮播图关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceRelShufflingServiceImpl extends AbstractService<MmAdviceRelShufflingMapper, MmAdviceRelShufflingPo> implements IMmAdviceRelShufflingService {

    @Autowired
    private MmAdviceRelShufflingMapper mapper;

}
