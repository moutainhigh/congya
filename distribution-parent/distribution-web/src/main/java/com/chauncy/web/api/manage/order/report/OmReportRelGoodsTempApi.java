package com.chauncy.web.api.manage.order.report;


import com.chauncy.order.report.service.IOmReportRelGoodsTempService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 报表商品快照关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
@RestController
@RequestMapping("om-report-rel-goods-temp-po")
@Api(tags = "报表商品快照关联表")
public class OmReportRelGoodsTempApi extends BaseApi {

 @Autowired
 private IOmReportRelGoodsTempService service;


}
