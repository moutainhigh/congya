package com.chauncy.data.vo.supplier.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-31 17:26
 *
 * 条件分页查询拼团记录
 */
@ApiModel(description = "条件分页查询拼团记录")
@Accessors(chain = true)
@Data
public class SearchSpellRecordVo {

    @ApiModelProperty("活动ID")
    private Long activityId;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("拼团编号ID")
    private Long spellId;

    @ApiModelProperty("订单号")
    private Long orderId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty("拼团成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty("评团状态")
    private Integer status;

}
