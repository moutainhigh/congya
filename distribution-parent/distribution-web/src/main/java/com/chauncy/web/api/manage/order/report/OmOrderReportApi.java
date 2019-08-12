package com.chauncy.web.api.manage.order.report;


import com.chauncy.order.report.service.IOmOrderReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 商品销售报表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
@RestController
@RequestMapping("om-order-report-po")
@Api(tags = "商品销售报表")
public class OmOrderReportApi extends BaseApi {

    @Autowired
    private IOmOrderReportService omOrderReportService;


}
