package com.chauncy.data.dto.app.order.my.afterSale;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/8/21 23:40
 **/
@Data
@ApiModel(description = "用户申请退款")
public class ApplyRefundDto {

    @NotEmpty
    @ApiModelProperty(value = "商品快照的id集合")
    private List<Long> goodsTempIds;

    @ApiModelProperty(value = "退款原因")
    @NotBlank
    private String reason;

    @ApiModelProperty(value = "退款金额")
    @Min(0)
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "描述")
    private String distribution;

    @ApiModelProperty(value = "图片，多个用逗号隔开")
    private String pictures;
}