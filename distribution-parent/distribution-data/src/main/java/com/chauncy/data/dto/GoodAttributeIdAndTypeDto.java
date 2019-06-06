package com.chauncy.data.dto;

import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrt
 * @Date 2019-06-03 12:00
 **/
@Data
public class GoodAttributeIdAndTypeDto {
    @ApiModelProperty(value = "类型 1->平台服务说明管理类型 2->商家服务说明管理类型 3->平台活动说明管理类型  4->商品参数管理类型 5->标签管理类型 6->购买须知管理类型 7->规格管理类型 8->品牌管理")
    @EnumConstraint(target = GoodsAttributeTypeEnum.class)
    private Integer type;

    @ApiModelProperty("对应的属性id")
    private Long id;
}
