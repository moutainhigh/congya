package com.chauncy.data.dto.app.cooperation.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-07-30 22:29
 * 条件分页查询合作申请条件Dto
 */
@Data
@ApiModel(description = "条件分页查询合作申请条件Dto")
@Accessors(chain = true)
public class SearchOperationDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "app用户姓名")
    private String userName;

    @ApiModelProperty(value = "app用户手机号")
    private String userPhone;

    @ApiModelProperty(value = "申请者")
    private String applicant;

    @ApiModelProperty(value = "申请者手机号")
    private String applicantPhone;

    @ApiModelProperty(value = "申请开始时间")
    private LocalDate startTime;

    @ApiModelProperty(value = "申请结束时间")
    private LocalDate endTime;
}
