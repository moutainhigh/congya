package com.chauncy.data.areaService;

import com.chauncy.data.domain.po.area.AreaShopLogisticsPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.vo.area.ShopLogisticsVo;

import java.util.List;

/**
 * <p>
 * 物流公司表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
public interface IAreaShopLogisticsService extends Service<AreaShopLogisticsPo> {

    /**
     * 生成快递100所有物流Json
     *
     * @return
     */
    List<ShopLogisticsVo> generateLogistics();
}
