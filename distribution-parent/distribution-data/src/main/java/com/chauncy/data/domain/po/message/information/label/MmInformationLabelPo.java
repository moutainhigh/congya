package com.chauncy.data.domain.po.message.information.label;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.chauncy.common.util.serializer.LongJsonDeserializer;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sm_information_label")
@ApiModel(value = "SmInformationLabelPo对象", description = "店铺资讯标签信息")
public class MmInformationLabelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯标签id")
    @TableId(value = "value",type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "资讯标签名称")
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    @ApiModelProperty(value = "排序数字")
    private BigDecimal sort;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;

}
