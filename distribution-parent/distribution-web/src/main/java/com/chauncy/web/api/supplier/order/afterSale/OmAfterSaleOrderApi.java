package com.chauncy.web.api.supplier.order.afterSale;


import com.chauncy.common.enums.message.NoticeTitleEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.bo.app.message.SaveUserNoticeBo;
import com.chauncy.data.dto.manage.order.afterSale.OperateAfterSaleDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.message.interact.service.IMmUserNoticeService;
import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
import com.chauncy.order.pay.IWxService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private IMmUserNoticeService mmUserNoticeService;

    @Autowired
    private IWxService wxService;

    @Autowired
    private SecurityUtil securityUtil;


   /* *//**
     * 申请退款
     * 官方文档:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_4&index=6
     * @return
     * @throws Exception
     *//*
    @PostMapping("/refund/{id}")
    @ApiOperation("申请退款")
    public JsonViewData refund(HttpServletRequest request, @PathVariable(value = "id") Long id) {

        try {
            //申请退款
            wxService.refund(id);
            return new JsonViewData(ResultCode.SUCCESS, "操作成功");
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.SYSTEM_ERROR, "系统错误");
        }

    }*/

    @ApiOperation(value = "商家操作")
    @PostMapping("/operate")
    public JsonViewData permit(@Validated @RequestBody OperateAfterSaleDto operateAfterSaleDto) {
        switch (operateAfterSaleDto.getOperateAfterOrderEnum()) {

            //确认退款
            case PERMIT_REFUND:
                wxService.refund(operateAfterSaleDto.getAfterSaleOrderId(), true, false);
                break;

            //拒绝退款
            case REFUSE_REFUND:
                afterSaleOrderService.refuseRefund(operateAfterSaleDto.getAfterSaleOrderId());
                break;
            //确认退货
            case PERMIT_RETURN_GOODS:
                afterSaleOrderService.permitReturnGoods(operateAfterSaleDto.getAfterSaleOrderId(),false);
                //商家同意售后  发送APP内消息给用户
                SaveUserNoticeBo saveUserNoticeBo = new SaveUserNoticeBo();
                saveUserNoticeBo.setAfterSaleOrderId(operateAfterSaleDto.getAfterSaleOrderId());
                mmUserNoticeService.saveUserNotice(NoticeTitleEnum.RETURN_GOODS.getName(), saveUserNoticeBo);
                break;
            //拒绝退货
            case REFUSE_RETURN_GOODS:
                afterSaleOrderService.refuseReturnGoods(operateAfterSaleDto.getAfterSaleOrderId());
                break;
            //确认收货
            case PERMIT_RECEIVE_GOODS:
                afterSaleOrderService.permitReceiveGoods(operateAfterSaleDto.getAfterSaleOrderId());
                break;
        }
        return setJsonViewData(ResultCode.SUCCESS);
    }


}
