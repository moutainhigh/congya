package com.chauncy.data.dto.supplier.good.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-07-02 13:10
 *
 * 条件查询评价信息
 */
@Data
@ApiModel(description = "条件查询评价信息")
@Accessors(chain = true)
public class SearchEvaluatesDto {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("订单号")
    private Long orderId;

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("评价开始时间")
    private LocalDate evaluateStartTime;

    @ApiModelProperty("评价结束时间")
    private LocalDate evaluateEndTime;

    @ApiModelProperty("店铺ID")
    private Long storeId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty("评价星级")
    private Integer startLevel;
}
