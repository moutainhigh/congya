package com.chauncy.product.service.impl;

import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品属性业务处理
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsAttributeServiceImpl extends ServiceImpl<PmGoodsAttributeMapper, PmGoodsAttributePo> implements IPmGoodsAttributeService {

    @Autowired
    private PmGoodsAttributeMapper mapper;

    @Override
    public PmGoodsAttributePo findByTypeAndName(Integer type, String name) {
        return mapper.findByTypeAndName(type,name);
    }
}
