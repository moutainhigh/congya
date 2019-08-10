package com.chauncy.data.vo.manage.message.information;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/6/28 16:01
 */
@Data
@ApiModel(value = "后台资讯列表分页查询结果")
public class InformationPageInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯id")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    @TableField(condition = SqlCondition.LIKE)
    private String title;

    @ApiModelProperty(value = "资讯标签名称")
    private String infoLabelName;

    @ApiModelProperty(value = "资讯分类名称")
    private String infoCategoryName;

    @ApiModelProperty(value = "审核状态 2-待审核 3-审核通过 4-不通过/驳回")
    private Integer verifyStatus;

    @ApiModelProperty(value = "使用状态 1-启用 0-禁用 为null表示“-”")
    private Boolean enabled;

    @ApiModelProperty(value = "排序数字")
    private BigDecimal sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "浏览量")
    private Integer browsingNum;

    @ApiModelProperty(value = "转发量")
    private Integer forwardNum;

    @ApiModelProperty(value = "评论量")
    private Integer commentNum;

    @ApiModelProperty(value = "点赞量")
    private Integer likedNum;

    @ApiModelProperty(value = "收藏量")
    private Integer collectionNum;

}
