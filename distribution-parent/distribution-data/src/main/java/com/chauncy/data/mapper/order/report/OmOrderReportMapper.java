package com.chauncy.data.mapper.order.report;

import com.chauncy.data.domain.po.order.report.OmOrderReportPo;
import com.chauncy.data.dto.manage.order.report.select.SearchReportDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.report.ReportBaseInfoVo;
import com.chauncy.data.vo.manage.order.report.ReportRelGoodsTempVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 商品销售报表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
public interface OmOrderReportMapper extends IBaseMapper<OmOrderReportPo> {

    /**
     * 查询商品销售报表
     * @param searchReportDto
     */
    List<ReportBaseInfoVo> searchReportPaging(SearchReportDto searchReportDto);

    /**
     * 根据ID查找商品销售报表信息
     * @param id
     * @return
     */
    List<ReportRelGoodsTempVo> findReportById(@Param("id") Long id);

    /**
     * 查找 om_report_rel_goods_temp  生成对应的om_order_report
     * @param startDate
     * @param endDate
     * @param branchId
     * @return
     */
    List<OmOrderReportPo> searchOrderReport(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate, @Param("branchId") Long branchId);

    /**
     * @Author yeJH
     * @Date 2019/11/11 18:39
     * @Description 判断当前店铺列表多少店铺需要创建商品销售报表
     *
     * @Update yeJH
     *
     * @param  endDate
     * @param  storeId
     * @return int
     **/
    int getStoreSumNeedCreateReport(LocalDate endDate, Long storeId);

    /**
     * @Author yeJH
     * @Date 2019/11/11 19:02
     * @Description /判断当前店铺列表需要创建商品销售报表的店铺id
     *
     * @Update yeJH
     *
     * @param  endDate
     * @param  storeId
     * @return java.util.List<java.lang.Long>
     **/
    List<Long> getStoreNeedCreateReport(LocalDate endDate, Long storeId);
}
