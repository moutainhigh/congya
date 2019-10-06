package com.chauncy.data.vo.app.order.cart;

import com.chauncy.common.enums.app.order.OrderRealUserEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/10/5 12:12
 **/
@ApiModel(description = "实名认证vo")
@Data
@Accessors(chain = true)
public class RealUserStatusVo {

    @ApiModelProperty("1-认证通过 2-认证失败")
    private Integer status;

    @ApiModelProperty("实名认证id")
    private Long realUserId;
}
