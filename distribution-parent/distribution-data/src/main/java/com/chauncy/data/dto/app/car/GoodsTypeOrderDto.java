package com.chauncy.data.dto.app.car;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.app.car.ShopTicketSoWithCarGoodVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/9 23:52
 **/

@Data
@ApiModel(description = "商品类型拆单")
@Accessors(chain = true)
public class GoodsTypeOrderDto {


    @ApiModelProperty("订单商品详情")
    private List<ShopTicketSoWithCarGoodDto> shopTicketSoWithCarGoodDtos;


    @ApiModelProperty("订单备注")
    private String remark;

    @ApiModelProperty("店铺id")
    @NotNull
    private Long storeId;


    @ApiModelProperty("订单商品类型")
    private String goodsType;

    @ApiModelProperty(value = "该订单运费",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "该订单税费",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal taxMoney;

    @ApiModelProperty(value = "该订单金额，包括商品金额，运费，税费",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalMoney;






}
