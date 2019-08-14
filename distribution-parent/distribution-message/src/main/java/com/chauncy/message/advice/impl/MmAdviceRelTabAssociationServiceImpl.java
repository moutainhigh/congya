package com.chauncy.message.advice.impl;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabAssociationPo;
import com.chauncy.data.mapper.message.advice.MmAdviceRelTabAssociationMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.message.advice.IMmAdviceRelTabAssociationService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表与广告选项卡表关联表，广告位置为具体店铺分类下面不同选项卡+推荐的店铺，多重关联选项卡 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceRelTabAssociationServiceImpl extends AbstractService<MmAdviceRelTabAssociationMapper, MmAdviceRelTabAssociationPo> implements IMmAdviceRelTabAssociationService {

    @Autowired
    private MmAdviceRelTabAssociationMapper mapper;

}
