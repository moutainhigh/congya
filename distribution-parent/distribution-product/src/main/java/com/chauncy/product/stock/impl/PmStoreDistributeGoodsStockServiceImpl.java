package com.chauncy.product.stock.impl;

import com.chauncy.data.domain.po.product.stock.PmStoreDistributeGoodsStockPo;
import com.chauncy.data.mapper.product.stock.PmStoreDistributeGoodsStockMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.product.stock.IPmStoreDistributeGoodsStockService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 店铺分配库存信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmStoreDistributeGoodsStockServiceImpl extends AbstractService<PmStoreDistributeGoodsStockMapper,PmStoreDistributeGoodsStockPo> implements IPmStoreDistributeGoodsStockService {

 @Autowired
 private PmStoreDistributeGoodsStockMapper mapper;

}
