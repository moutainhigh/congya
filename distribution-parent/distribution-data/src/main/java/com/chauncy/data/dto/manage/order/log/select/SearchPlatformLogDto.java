package com.chauncy.data.dto.manage.order.log.select;

import com.chauncy.common.enums.log.PlatformLogMatterEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/7/20 21:51
 */
@Data
@ApiModel(value = "SearchPlatformLogDto对象", description = "查找平台流水参数")
public class SearchPlatformLogDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @JsonIgnore
    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "交易流水（微信支付宝交易号）")
    private String payOrderNo;
    @ApiModelProperty(value = "关联订单id")
    private Long omRelId;

    @ApiModelProperty(value = "流水id")
    private Long logId;

    @ApiModelProperty(value = "下单用户id")
    private Long umUserId;

    @ApiModelProperty(value = "流水类型 收入  支出")
    private String logType;

    @ApiModelProperty(value = "流水事由")
    @EnumConstraint(target = PlatformLogMatterEnum.class)
    private Integer logMatter;

    @ApiModelProperty(value = "下单用户手机号码")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate startTime;

    @JsonFormat( pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate endTime;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
