package com.chauncy.data.dto.manage.order.afterSale;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Author zhangrt
 * @Date 2019/9/1 14:09
 **/
@Data
@ApiModel( description = "分页搜索售后订单")
public class SearchAfterSaleOrderDto {

    @ApiModelProperty("用户手机")
    private String phone;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("开始申请时间")
    private String startApplyTime;

    @ApiModelProperty("结束申请时间")
    private String endApplyTime;

    @ApiModelProperty(value = "售后类型：ONLY_REFUND-仅退款 RETURN_GOODS-退款退货")
    private AfterSaleTypeEnum afterSaleType;

    @ApiModelProperty(value = "售后状态：NEED_STORE_DO-待商家处理 NEED_BUYER_DO-待买家处理 NEED_BUYER_RETURN-待买家退货 " +
            "NEED_STORE_REFUND-待商家退款 CLOSE-退款关闭 SUCCESS-退款成功")
    private AfterSaleStatusEnum status;

    @ApiModelProperty("售后最小金额")
    private String minRefundMoney;

    @ApiModelProperty("售后最大金额")
    private String maxRefundMoney;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
