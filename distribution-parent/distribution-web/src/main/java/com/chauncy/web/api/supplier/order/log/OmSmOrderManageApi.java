package com.chauncy.web.api.supplier.order.log;


import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchSendOrderDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.supplier.order.SmOrderLogisticsVo;
import com.chauncy.data.vo.supplier.order.SmSearchOrderVo;
import com.chauncy.data.vo.supplier.order.SmSendOrderVo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商家端订单管理
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@RestController
@RequestMapping("/supplier/order")
@Api(tags = "商家端_普通类型订单管理")
public class OmSmOrderManageApi extends BaseApi {



    @Autowired
    private IOmOrderService orderService;

    @Autowired
    private SecurityUtil securityUtil;



    @ApiOperation(value = "商家查询订单列表")
    @PostMapping("/list")
    public JsonViewData<PageInfo<SmSearchOrderVo>> search(@RequestBody SmSearchOrderDto smSearchOrderDto) {

        smSearchOrderDto.setStoreId(securityUtil.getCurrUser().getStoreId());
        PageInfo<SmSearchOrderVo> search = orderService.searchSmOrderList(smSearchOrderDto);
        return setJsonViewData(search);
    }

    @ApiOperation(value = "商家查询订单详情")
    @PostMapping("/{id}")
    public JsonViewData<OrderDetailVo> search(@PathVariable Long id) {

        return setJsonViewData(orderService.getSmDetailById(id));
    }

    @ApiOperation(value = "商家关闭订单")
    @PostMapping("close/{id}")
    public JsonViewData close(@PathVariable Long id) {

        return setJsonViewData(orderService.closeOrderByOrderId(id));
    }

    @ApiOperation(value = "商家查询发货订单列表")
    @PostMapping("/send/list")
    public JsonViewData<PageInfo<SmSendOrderVo>> search(@Validated @RequestBody SmSearchSendOrderDto smSearchSendOrderDto) {
        smSearchSendOrderDto.setStoreId(securityUtil.getCurrUser().getStoreId());
        PageInfo<SmSendOrderVo> search = orderService.searchSmSendOrderList(smSearchSendOrderDto);
        return setJsonViewData(search);
    }

    @ApiOperation(value = "商家查询发货订单物流信息")
    @PostMapping("/logistics/{id}")
    public JsonViewData<SmOrderLogisticsVo> searchLogistics(@PathVariable Long id) {
        return setJsonViewData(orderService.getLogisticsById(id));
    }

  /*  @ApiOperation(value = "商家发货")
    @PostMapping("/send")
    public JsonViewData<SmOrderLogisticsVo> send(@PathVariable Long id) {
        return setJsonViewData(orderService.getLogisticsById(id));
    }*/
}
