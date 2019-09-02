package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmNumberShippingPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.goods.NumberShippingVo;

import java.util.List;

/**
 * <p>
 * 按件数计算运费 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmNumberShippingMapper extends IBaseMapper<PmNumberShippingPo> {

    /**
     * 按件数计算运费详情
     *
     * @param templateId
     * @return
     */
    List<NumberShippingVo> findByTemplateId(Long templateId);
}
