package com.chauncy.order.evaluate.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.dto.app.order.evaluate.AddValuateDto;

/**
 * <p>
 * 商品评价表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface IOmEvaluateService extends Service<OmEvaluatePo> {

    /**
     * 用户进行商品评价
     *
     * @param addValuateDto
     * @return
     */
    void addEvaluate(AddValuateDto addValuateDto);
}
