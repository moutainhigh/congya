package com.chauncy.data.dto.supplier.activity.select;

import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-29 21:20
 *
 * 修改报名的活动的状态
 */
@ApiModel(description = "修改报名的活动的状态")
@Data
@Accessors(chain = true)
public class UpdateVerifyStatusDto {

    @ApiModelProperty("修改的状态:2-待审核 3-审核通过 4-不通过/驳回 5-返回修改 ")
    @EnumConstraint(target = VerifyStatusEnum.class)
    private Integer verifyStatus;

    @ApiModelProperty("记录ID")
    @NeedExistConstraint(tableName = "am_activity_rel_activity_goods")
    private Long activityGoodsRelId;

    @ApiModelProperty("返回修改原因/拒绝原因")
    private String refuseCase;
}
