package com.chauncy.web.api.app.order.logistics;


import com.chauncy.data.dto.app.order.logistics.TaskRequestDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.order.logistics.IOmOrderLogisticsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

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
@RequestMapping("app/order/logistics")
@Api(tags = "app_订单_物流管理")
@Slf4j
public class OmOrderLogisticsApi extends BaseApi {

    @Autowired
    private IOmOrderLogisticsService service;

    /**
     * 订单订阅物流信息
     *
     * @param orderId
     * @param taskRequestDto
     * @return
     */
    @ApiOperation("订单订阅物流信息")
    @PostMapping("/subscribe/{orderId")
    public JsonViewData<String> subscribleLogistics(@RequestBody @ApiParam(required = true,name="taskRequestDto",value = "订单订阅物流信息")
                                            @PathVariable long orderId,@Validated TaskRequestDto taskRequestDto) {
        return service.subscribleLogistics(taskRequestDto, orderId);
    }
}
