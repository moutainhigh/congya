package com.chauncy.data.vo.manage.message.information.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/6/25 23:11
 */
@Data
@ApiModel(value = "店铺资讯标签")
public class InformationCategoryVo   implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "资讯分类id")
    private Long id;

    @ApiModelProperty(value = "资讯分类名称")
    private String name;

    @ApiModelProperty(value = "分类缩略图")
    private String icon;

    @ApiModelProperty(value = "排序数字")
    private BigDecimal sort;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
