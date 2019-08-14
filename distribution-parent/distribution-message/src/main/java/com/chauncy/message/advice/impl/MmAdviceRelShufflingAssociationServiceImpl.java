package com.chauncy.message.advice.impl;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelShufflingAssociationPo;
import com.chauncy.data.mapper.message.advice.MmAdviceRelShufflingAssociationMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.message.advice.IMmAdviceRelShufflingAssociationService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告与(品牌、一级分类)关联轮播图关联表(不同品牌、不同一级分类有不同广告轮播图) 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceRelShufflingAssociationServiceImpl extends AbstractService<MmAdviceRelShufflingAssociationMapper, MmAdviceRelShufflingAssociationPo> implements IMmAdviceRelShufflingAssociationService {

    @Autowired
    private MmAdviceRelShufflingAssociationMapper mapper;

}
