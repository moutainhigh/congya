package com.chauncy.product.service;

import com.chauncy.data.bo.app.car.MoneyShipBo;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmMoneyShippingPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 按金额计算运费 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmMoneyShippingService extends Service<PmMoneyShippingPo> {

    /**
     * 根据sku找出对应的运费模板信息
     * @param id
     * @return
     */
    List<MoneyShipBo> getBySkuId(Long id);

}
