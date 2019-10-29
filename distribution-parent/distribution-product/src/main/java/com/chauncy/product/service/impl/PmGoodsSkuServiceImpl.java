package com.chauncy.product.service.impl;

import com.chauncy.data.bo.app.activity.GroupStockBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.product.service.IPmGoodsSkuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品sku信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsSkuServiceImpl extends AbstractService<PmGoodsSkuMapper, PmGoodsSkuPo> implements IPmGoodsSkuService {

    @Override
    public int addStockInGroup(GroupStockBo groupStockBo) {
        return baseMapper.addStockInGroup(groupStockBo);
    }
}
