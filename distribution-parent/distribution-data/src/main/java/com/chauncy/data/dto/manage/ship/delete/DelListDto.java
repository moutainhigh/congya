package com.chauncy.data.dto.manage.ship.delete;

import com.chauncy.common.enums.ship.ShipCalculateWayEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-25 22:33
 *
 * 批量删除运费计算列表
 */
@Data
@ApiModel("根据列表ID和计算方式批量删除运费计算列表")
public class DelListDto {

    @ApiModelProperty("列表ID结合")
    private Long[] ids;

    @ApiModelProperty("计算方式: 1--按金额 2--按件数")
    @EnumConstraint(target = ShipCalculateWayEnum.class)
    private Integer calculateType;
}
