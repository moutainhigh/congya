package com.chauncy.order.evaluate.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.mapper.order.OmEvaluateMapper;
import com.chauncy.order.evaluate.service.IOmEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class OmEvaluateServiceImpl extends AbstractService<OmEvaluateMapper,OmEvaluatePo> implements IOmEvaluateService {

 @Autowired
 private OmEvaluateMapper mapper;

}
