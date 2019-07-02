package com.chauncy.data.domain.po.message.information;

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
 * 店铺资讯信息
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_information")
@ApiModel(value = "MmInformationPo对象", description = "店铺资讯信息")
public class MmInformationPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯id")
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    @TableField(condition = SqlCondition.LIKE)
    private String title;


    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为0")
    private Boolean enabled;

    @ApiModelProperty(value = "资讯标签id（mm_information_label主键）")
    private Long infoLabelId;

    @ApiModelProperty(value = "资讯分类id（mm_information_category主键）")
    private Long infoCategoryId;

    @ApiModelProperty(value = "所属店铺Id")
    private Long storeId;

    @ApiModelProperty(value = "关联商品id")
    private Long goodsId;

    @ApiModelProperty(value = "排序数字")
    private Integer sort;

    @ApiModelProperty(value = "封面图片")
    private String coverImage;

    @ApiModelProperty(value = "资讯正文纯文本")
    private String  pureText ;

    @ApiModelProperty(value = "资讯正文带格式文本")
    private String detailHtml;

    @ApiModelProperty(value = " 1-未审核 2-待审核 3-审核通过 4-不通过/驳回")
    private Integer verifyStatus;

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

    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核者")
    private String verifyBy;

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
