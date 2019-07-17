package com.chauncy.data.mapper.product;

import com.chauncy.data.bo.app.car.MoneyShipBo;
import com.chauncy.data.domain.po.product.PmMoneyShippingPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 按金额计算运费 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmMoneyShippingMapper extends IBaseMapper<PmMoneyShippingPo> {

    /**
     * 根据sku找出对应的运费模板信息
     * @param id
     * @return
     */
    List<MoneyShipBo> loadBySkuId(@Param("id") Long id);



}
