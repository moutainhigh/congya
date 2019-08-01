package com.chauncy.web.api.app.order.logistics;


import com.chauncy.order.logistics.IOmOrderLogisticsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 物流信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
@RestController
@RequestMapping("om-order-logistics-po")
@Api(tags = "物流信息表")
public class OmOrderLogisticsApi extends BaseApi {

    @Autowired
    private IOmOrderLogisticsService service;


}
