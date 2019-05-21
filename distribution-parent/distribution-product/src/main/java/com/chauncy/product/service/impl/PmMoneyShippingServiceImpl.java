package com.chauncy.product.service.impl;

import com.chauncy.data.domain.po.product.PmMoneyShippingPo;
import com.chauncy.data.mapper.product.PmMoneyShippingMapper;
import com.chauncy.product.service.IPmMoneyShippingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 按金额计算运费 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmMoneyShippingServiceImpl extends ServiceImpl<PmMoneyShippingMapper, PmMoneyShippingPo> implements IPmMoneyShippingService {

}
