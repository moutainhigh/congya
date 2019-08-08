package com.chauncy.product.service.impl;

import com.chauncy.data.bo.app.car.MoneyShipBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmMoneyShippingPo;
import com.chauncy.data.mapper.product.PmMoneyShippingMapper;
import com.chauncy.product.service.IPmMoneyShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 按金额计算运费 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmMoneyShippingServiceImpl extends AbstractService<PmMoneyShippingMapper, PmMoneyShippingPo> implements IPmMoneyShippingService {


    @Autowired
    private PmMoneyShippingMapper moneyShippingMapper;


    @Override
    public List<MoneyShipBo> getBySkuId(Long id) {
        return moneyShippingMapper.loadByTemplateId(id);
    }
}
