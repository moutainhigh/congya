package com.chauncy.order.report.service;

import com.chauncy.data.domain.po.order.report.OmOrderReportPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.manage.order.report.select.SearchReportDto;
import com.chauncy.data.vo.manage.order.report.ReportBaseInfoVo;
import com.chauncy.data.vo.manage.order.report.ReportRelGoodsTempVo;
import com.github.pagehelper.PageInfo;

import java.time.LocalDate;

/**
 * <p>
 * 商品销售报表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
public interface IOmOrderReportService extends Service<OmOrderReportPo> {

    /**
     * 查询商品销售报表
     * @param searchReportDto
     * @return
     */
    PageInfo<ReportBaseInfoVo> searchReportPaging(SearchReportDto searchReportDto);

    /**
     * 根据ID查找商品销售报表信息
     * @param id
     * @return
     */
    PageInfo<ReportRelGoodsTempVo> findReportById(BaseSearchPagingDto baseSearchPagingDto, Long id);

    /**
     * @Author yeJH
     * @Date 2019/11/11 19:06
     * @Description 批量创建销售报表
     *
     * @Update yeJH
     *
     * @param
     * @return void
     **/
    void batchCreateSaleReport(LocalDate endDate);

    /**
     * 根据时间创建商品销售报表
     * endDate   需要创建账单的那一周   任何一天都可以
     */
    void createSaleReportByDate(LocalDate endDate);

    /**
     * 订单确认不能售后业务处理 扣减商品虚拟库存，插入报表订单关联
     * @param orderId  订单id
     */
    void orderClosure(Long orderId);
}
