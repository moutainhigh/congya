package com.chauncy.order.evaluate.service.impl;

import com.chauncy.data.domain.po.order.OmEvaluateLikedPo;
import com.chauncy.data.mapper.order.OmEvaluateLikedMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.evaluate.service.IOmEvaluateLikedService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品评价点赞表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmEvaluateLikedServiceImpl extends AbstractService<OmEvaluateLikedMapper,OmEvaluateLikedPo> implements IOmEvaluateLikedService {

 @Autowired
 private OmEvaluateLikedMapper mapper;

}
