package com.chauncy.data.dto.manage.good.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-21 10:47
 *
 * 平台驳回商品审核
 */
@Data
@ApiModel(description = "平台驳回商品审核")
public class RejectGoodsDto {

    @ApiModelProperty("商品ID")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @ApiModelProperty("驳回反馈详情")
    private String content;
}
