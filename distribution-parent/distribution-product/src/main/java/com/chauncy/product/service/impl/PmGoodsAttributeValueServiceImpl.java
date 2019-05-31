package com.chauncy.product.service.impl;

import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.mapper.product.PmGoodsAttributeValueMapper;
import com.chauncy.product.service.IPmGoodsAttributeValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储产品参数信息的表
 * <p>
 * 规格值
 * 参数值 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsAttributeValueServiceImpl extends ServiceImpl<PmGoodsAttributeValueMapper, PmGoodsAttributeValuePo> implements IPmGoodsAttributeValueService {

    @Autowired
    private PmGoodsAttributeValueMapper mapper;
    @Override
    public void deleteByAttributeId(Long ProductAttributeId) {

        mapper.deleteByAttributeId(ProductAttributeId);
    }
}
