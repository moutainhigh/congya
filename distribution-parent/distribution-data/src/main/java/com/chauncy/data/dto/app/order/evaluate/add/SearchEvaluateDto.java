package com.chauncy.data.dto.app.order.evaluate.add;

import com.chauncy.common.enums.app.activity.evaluate.EvaluateEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-01 17:39
 *
 * 获取商品评价信息
 */
@Data
@ApiModel(description = "分页获取商品评价信息")
@Accessors(chain = true)
public class SearchEvaluateDto {

    @ApiModelProperty(value = "sku Id",hidden = true)
    private Long skuId;

    @ApiModelProperty(value = "goodsId")
    private Long goodsId;

    @ApiModelProperty(value = "评价级别 0-全部 1-好评 2-中评 3-差评 4-图片")
    @NotNull(message = "评价级别不能为空")
    @EnumConstraint(target = EvaluateEnum.class)
    private Integer evaluateLevel;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
