package com.chauncy.web.api.manage.order.afterSale;


import com.chauncy.data.dto.app.order.my.afterSale.RefundDto;
import com.chauncy.data.dto.manage.order.afterSale.SearchAfterSaleOrderDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterSaleVo;
import com.chauncy.data.vo.manage.order.afterSale.AfterSaleListVo;
import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
import com.chauncy.order.report.service.IOmOrderReportService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
售后订单
 * </p>
 *
 * @author zhangrt
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/manage/order/afterSale")
@Api(tags = "平台_订单_售后")
public class OmAfterSaleApi extends BaseApi {

    @Autowired
    private IOmAfterSaleOrderService omAfterSaleOrderService;


    @PostMapping("/list")
    @ApiOperation("售后订单列表")
    public JsonViewData<PageInfo<AfterSaleListVo>> list(@RequestBody SearchAfterSaleOrderDto searchAfterSaleOrderDto) {
        return setJsonViewData(omAfterSaleOrderService.searchAfterList(searchAfterSaleOrderDto));
    }


}
