package com.chauncy.data.vo.manage.store;

import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/6/19 22:58
 */
@Data
@ApiModel(value = "店铺运营信息")
public class StoreOperationalInfoVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "本店订单数量")
    private Integer orderNum;

    @ApiModelProperty(value = "本店商品总数")
    private Integer goodsNum;

    @ApiModelProperty(value = "本店营业额")
    private BigDecimal storeTurnover;

    @ApiModelProperty(value = "本店会员数量")
    private Integer storeMemberNum;

    /*@ApiModelProperty(value = "本店收入")
    private BigDecimal storeIncome;

    @ApiModelProperty(value = "旗下店铺数量")
    private Integer storeSubNum;*/

}
