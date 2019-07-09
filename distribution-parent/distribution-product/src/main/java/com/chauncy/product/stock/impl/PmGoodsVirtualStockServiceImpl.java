package com.chauncy.product.stock.impl;

import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockPo;
import com.chauncy.data.mapper.product.PmGoodsVirtualStockMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.product.stock.IPmGoodsVirtualStockService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品虚拟库存信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmGoodsVirtualStockServiceImpl extends AbstractService<PmGoodsVirtualStockMapper, PmGoodsVirtualStockPo> implements IPmGoodsVirtualStockService {

    @Autowired
    private PmGoodsVirtualStockMapper mapper;

}
