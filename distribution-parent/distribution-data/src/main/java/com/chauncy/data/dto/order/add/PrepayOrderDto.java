package com.chauncy.data.dto.order.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/5 17:09
 */
@Data
@ApiModel(value = "PrepayOrderDto对象", description = "新增预支付订单所需参数")
public class PrepayOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "下单商品信息 sku1Id:sku1数量;sku2Id:sku2数量;")
    @NotBlank(message = "下单商品信息不能为空")
    private String target;

    @ApiModelProperty(value = "id,当新增时为空")
    @NeedExistConstraint(tableName = "收货地址数据表")
    private Long addrId;

    @ApiModelProperty(value = "留言")
    private String name;


}
