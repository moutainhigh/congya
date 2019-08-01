package com.chauncy.data.mapper.area;

import com.chauncy.data.domain.po.area.AreaShopLogisticsPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.area.ShopLogisticsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 物流公司表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
public interface AreaShopLogisticsMapper extends IBaseMapper<AreaShopLogisticsPo> {

    /**
     * 生成快递100所有物流Json
     *
     * @return
     */
    @Select("select logi_name as label,logi_code as value from area_shop_logistics")
    List<ShopLogisticsVo> generateLogistics();
}
