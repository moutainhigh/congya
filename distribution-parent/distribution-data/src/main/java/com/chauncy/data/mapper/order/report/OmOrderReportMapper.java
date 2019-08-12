package com.chauncy.data.mapper.order.report;

import com.chauncy.data.domain.po.order.report.OmOrderReportPo;
import com.chauncy.data.dto.manage.order.report.select.SearchReportDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.report.ReportBaseInfoVo;
import com.chauncy.data.vo.manage.order.report.ReportRelGoodsTempVo;

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
    List<ReportRelGoodsTempVo> findReportById(Long id);
}
