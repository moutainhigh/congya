package com.chauncy.order.report.service;

import com.chauncy.data.domain.po.order.report.OmOrderReportPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.order.report.select.SearchReportDto;
import com.chauncy.data.vo.manage.order.report.ReportBaseInfoVo;
import com.github.pagehelper.PageInfo;

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
    ReportBaseInfoVo findReportById(Long id);
}
