package com.chauncy.product.service.impl;

import com.chauncy.data.domain.po.product.PmGoodsForwardPo;
import com.chauncy.data.mapper.product.PmGoodsForwardMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.product.service.IPmGoodsForwardService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户商品转发表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-11-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmGoodsForwardServiceImpl extends AbstractService<PmGoodsForwardMapper, PmGoodsForwardPo> implements IPmGoodsForwardService {

    @Autowired
    private PmGoodsForwardMapper mapper;

}
