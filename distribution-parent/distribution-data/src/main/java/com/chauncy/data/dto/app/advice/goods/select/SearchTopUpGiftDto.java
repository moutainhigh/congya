package com.chauncy.data.dto.app.advice.goods.select;

import com.chauncy.common.enums.app.gift.GiftTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-09-09 22:58
 */
@Data
@ApiModel(description = "分页查询购买经验包信息")
@Accessors(chain = true)
public class SearchTopUpGiftDto {

    @ApiModelProperty(value = "礼包类型为1--充值",hidden = true)
    private Integer type = GiftTypeEnum.TOP_UP.getId();

    @Min(1)
    @ApiModelProperty(value = "页码 默认1")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小 默认10")
    private Integer pageSize;
}
