package com.chauncy.data.vo.manage.message.information;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.chauncy.data.vo.supplier.InformationRelGoodsVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/6/27 18:34
 */
@Data
@ApiModel(value = "资讯")
public class InformationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯id")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    @TableField(condition = SqlCondition.LIKE)
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为0")
    private Boolean enabled;

    @ApiModelProperty(value = "资讯标签id（mm_information_label主键）")
    private Long infoLabelId;

    @ApiModelProperty(value = "资讯标签名称")
    private String infoLabelName;

    @ApiModelProperty(value = "资讯分类id（mm_information_category主键）")
    private Long infoCategoryId;

    @ApiModelProperty(value = "资讯分类名称")
    private String infoCategoryName;

    @ApiModelProperty(value = "所属店铺Id")
    private Long storeId;

    @ApiModelProperty(value = "关联商品")
    private List<InformationRelGoodsVo> goodsList;

    @ApiModelProperty(value = "排序数字")
    private Integer sort;

    @ApiModelProperty(value = "封面图片")
    private String coverImage;

    @ApiModelProperty(value = "资讯正文")
    private String detailHtml;

    @ApiModelProperty(value = " 1-未审核 2-待审核 3-审核通过 4-不通过/驳回")
    private Integer verifyStatus;

    @ApiModelProperty(value = "原因")
    private String remark;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime verifyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
