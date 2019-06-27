package com.chauncy.data.domain.po.message.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章管理
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_article")
@ApiModel(value = "MmArticlePo对象", description = "文章管理")
public class MmArticlePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @TableId(value = "value",type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "文章名称")
    private String name;

    @ApiModelProperty(value = "图文详情")
    private String detailHtml;

    @ApiModelProperty(value = "文章位置")
    private String articleLocation;


}
