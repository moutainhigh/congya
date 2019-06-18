package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.product.service.IPmGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsCategoryServiceImpl extends AbstractService<PmGoodsCategoryMapper, PmGoodsCategoryPo> implements IPmGoodsCategoryService {

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Override
    public Map<String, Object> findById(Long id) {
        return categoryMapper.loadById(id);
    }
}
