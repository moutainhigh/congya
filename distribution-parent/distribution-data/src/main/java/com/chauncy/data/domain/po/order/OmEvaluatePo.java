package com.chauncy.data.domain.po.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 商品评价表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_evaluate")
@ApiModel(value = "OmEvaluatePo对象", description = "商品评价表")
public class OmEvaluatePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价id")
    @TableId(value = "id", type = IdType.ID_WORKER)
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
    private Boolean delFlag;

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @ApiModelProperty(value = "sku Id")
    private Long skuId;

    @ApiModelProperty(value = "评价父ID")
    private Long parentId;

    @ApiModelProperty(value = "宝贝描述星级")
    private Integer descriptionStartLevel;

    @ApiModelProperty(value = "物流服务星级")
    private Integer shipStartLevel;

    @ApiModelProperty(value = "服务态度星级")
    private Integer attitudeStartLevel;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评价图片")
    private String picture;

    @ApiModelProperty(value = "评论点赞数")
    private Integer likedNum;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLiked;

}
