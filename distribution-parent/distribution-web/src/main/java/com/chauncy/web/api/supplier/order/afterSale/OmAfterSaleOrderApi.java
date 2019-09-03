package com.chauncy.web.api.supplier.order.afterSale;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchSendOrderDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.supplier.order.SmOrderLogisticsVo;
import com.chauncy.data.vo.supplier.order.SmSearchOrderVo;
import com.chauncy.data.vo.supplier.order.SmSendOrderVo;
import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
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
 * 商家端售后订单管理
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@RestController
@RequestMapping("/supplier/order/after")
@Api(tags = "商家端_售后订单")
public class OmAfterSaleOrderApi extends BaseApi {



    @Autowired
    private IOmAfterSaleOrderService afterSaleOrderService;

    @Autowired
    private SecurityUtil securityUtil;



    @ApiOperation(value = "商家同意退款")
    @PostMapping("/refund/permit/{id}")
    public JsonViewData permit(@PathVariable Long id) {
        afterSaleOrderService.permitRefund(id);
        return setJsonViewData(ResultCode.SUCCESS);
    }




}
