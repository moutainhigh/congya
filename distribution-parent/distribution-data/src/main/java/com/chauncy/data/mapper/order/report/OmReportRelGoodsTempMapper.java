package com.chauncy.data.mapper.order.report;

import com.chauncy.data.domain.po.order.report.OmReportRelGoodsTempPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 * 报表商品快照关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
public interface OmReportRelGoodsTempMapper extends IBaseMapper<OmReportRelGoodsTempPo> {

    /**
     * 报表关联订单
     * @param startDate  开始时间
     * @param endDate  结束时间
     * @param storeId  直属店铺id
     * @param branchId  分配店铺id
     * @param reportId  报表id
     */
    void updateRelReport(@Param("startDate") LocalDate startDate,
                         @Param("endDate")LocalDate endDate,
                         @Param("storeId")Long storeId,
                         @Param("branchId")Long branchId,
                         @Param("reportId")Long reportId);
}
