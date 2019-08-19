package com.chauncy.data.mapper.order.bill;

import com.chauncy.data.domain.po.order.bill.OmBillRelStorePo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 * 团队合作利润账单店铺关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-08
 */
public interface OmBillRelStoreMapper extends IBaseMapper<OmBillRelStorePo> {

    /**
     * 查找店铺storeId在账单周期内绑定的关系为团队合作的上级店铺
     * @param storeId  店铺
     * @param startDate  账单周期第一天
     * @param endDate  账单周期最后一天
     * @return
     */
    Long getTeamWorkParentStoreId(
            @Param("storeId")Long storeId, @Param("startDate") LocalDate startDate,  @Param("startDate") LocalDate endDate);
}
