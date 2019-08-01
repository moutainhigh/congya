package com.chauncy.web.api.app.order.logistics;


import com.chauncy.data.dto.app.order.logistics.TaskRequestDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.order.logistics.NoticeResponseVo;
import com.chauncy.order.logistics.IOmOrderLogisticsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import com.chauncy.web.base.BaseApi;

import javax.servlet.http.HttpServletRequest;

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
     * 快递100订阅请求接口
     *
     * @param orderId
     * @param taskRequestDto
     * @return
     */
    @ApiOperation("订单订阅物流信息请求接口")
    @PostMapping("/subscribe/{orderId")
    public JsonViewData<String> subscribleLogistics(@RequestBody @ApiParam(required = true,name="taskRequestDto",value = "订单订阅物流信息")
                                            @PathVariable long orderId,@Validated TaskRequestDto taskRequestDto) {
        return setJsonViewData(service.subscribleLogistics(taskRequestDto, orderId));
    }

    /**
     * 快递100订阅推送数据
     * 快递结果回调接口
     *
     * @param request
     * @param orderId
     * @return
     */
    @PostMapping("/callback/{orderId}")
    @ApiOperation("快递结果回调接口")
    public JsonViewData<NoticeResponseVo> expressCallback(HttpServletRequest request, @PathVariable String orderId) {
        String param = request.getParameter("param");
        log.info("订单物流回调开始，入参为：" + param);
        return setJsonViewData(service.updateExpressInfo(param, orderId));
    }
}
