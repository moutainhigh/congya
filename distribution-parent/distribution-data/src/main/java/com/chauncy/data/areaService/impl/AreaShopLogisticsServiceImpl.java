package com.chauncy.data.areaService.impl;

import com.chauncy.data.areaService.IAreaShopLogisticsService;
import com.chauncy.data.domain.po.area.AreaShopLogisticsPo;
import com.chauncy.data.mapper.area.AreaShopLogisticsMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.area.ShopLogisticsVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 物流公司表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AreaShopLogisticsServiceImpl extends AbstractService<AreaShopLogisticsMapper, AreaShopLogisticsPo> implements IAreaShopLogisticsService {

    @Autowired
    private AreaShopLogisticsMapper mapper;

    /**
     * 生成快递100所有物流Json
     *
     * @return
     */
    @Override
    public List<ShopLogisticsVo> generateLogistics() {
        return mapper.generateLogistics();
    }
}
