package com.chauncy.data.dto.supplier.activity.select;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-24 21:02
 *
 * 通过ID和类型查看活动详情
 */
@Data
@ApiModel(description = "通过ID和类型查看活动详情")
@Accessors(chain = true)
public class FindByIdAndTypeDto {

    @ApiModelProperty("活动ID")
    private Long id;

    @ApiModelProperty("活动类型:满减、积分、秒杀、拼团")
    @EnumConstraint(target = ActivityTypeEnum.class)
    private String type;
}
