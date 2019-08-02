package com.chauncy.data.dto.manage.activity.spell.select;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-31 17:10
 *
 * 商家条件分页查看拼团记录
 */
@Data
@ApiModel(description = "商家条件分页查看拼团记录")
@Accessors(chain = true)
public class SearchSpellRecordDto {

    @ApiModelProperty("活动ID")
    private Long activityId;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("拼团状态")
    private Integer status;

    @ApiModelProperty(value = "下单开始时间")
    private LocalDate orderStartTime;

    @ApiModelProperty(value = "下单结束时间")
    private LocalDate orderEndTime;

    @ApiModelProperty(value = "拼团成功时间")
    private LocalDate spellStartTime;

    @ApiModelProperty(value = "拼团失败时间")
    private LocalDate spellEndTime;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "拼团编号")
    private Long spellId;

    @ApiModelProperty(value = "店铺ID",hidden = true)
    private Long storeId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
