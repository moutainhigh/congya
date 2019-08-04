package com.chauncy.data.vo.manage.message.content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-06-26 17:17
 *
 * 启动页信息列表Vo
 */
@ApiModel(description = "启动页信息列表Vo")
@Data
public class BootPageVo {

    @ApiModelProperty(value = "启动页ID")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("排序数字")
    private BigDecimal sort;

}
