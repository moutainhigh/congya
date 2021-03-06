package com.chauncy.data.mapper.product;

import com.chauncy.data.bo.app.car.MoneyShipBo;
import com.chauncy.data.domain.po.product.PmMoneyShippingPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.goods.MoneyShippingVo;
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
    List<MoneyShipBo> loadByTemplateId(@Param("id") Long id);

    /**
     * 按金额计算的运费详情
     *
     * @param templateId
     * @return
     */
    List<MoneyShippingVo> findByTemplateId(Long templateId);
}
