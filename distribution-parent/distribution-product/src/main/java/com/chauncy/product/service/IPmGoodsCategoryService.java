package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;

import java.util.Map;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsCategoryService extends Service<PmGoodsCategoryPo> {
    /**
     * 查询分类详情
     * @param id
     * @return
     */
    Map<String,Object> findById(Long id);



}
