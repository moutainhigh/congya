package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmShippingTemplatePo;
import com.chauncy.data.mapper.product.PmShippingTemplateMapper;
import com.chauncy.product.service.IPmShippingTemplateService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 运费模版说明表。平台运费模版+商家运费模版 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmShippingTemplateServiceImpl extends AbstractService<PmShippingTemplateMapper, PmShippingTemplatePo> implements IPmShippingTemplateService {

}
