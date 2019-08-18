package com.chauncy.data.dto.manage.message.interact.select;

import com.chauncy.common.enums.message.PushTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-07-09 13:29
 *
 * 条件查询推送信息
 */
@Data
@ApiModel(description = "条件查询推送信息")
@Accessors(chain = true)
public class SearchPushDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "推送方式")
    private String pushType;

    @ApiModelProperty(value = "推送最早时间")
    private LocalDate pushTime;

    @ApiModelProperty(value = "推送最晚时间")
    private LocalDate endTime;
}
