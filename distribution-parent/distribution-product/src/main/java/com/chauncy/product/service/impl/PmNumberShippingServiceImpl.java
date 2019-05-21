package com.chauncy.product.service.impl;

import com.chauncy.data.domain.po.product.PmNumberShippingPo;
import com.chauncy.data.mapper.product.PmNumberShippingMapper;
import com.chauncy.product.service.IPmNumberShippingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 按件数计算运费 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmNumberShippingServiceImpl extends ServiceImpl<PmNumberShippingMapper, PmNumberShippingPo> implements IPmNumberShippingService {

}
