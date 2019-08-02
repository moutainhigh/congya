package com.chauncy.data.vo.app.message.information;

import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/2 19:52
 */
@Data
@ApiModel(value = "InformationBaseVo", description =   "APP资讯详情查询结果")
public class InformationBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "资讯id")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "封面图片")
    private String coverImage;

    @ApiModelProperty(value = "资讯正文带格式文本")
    private String detailHtml;

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
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "所属店铺Id")
    private Long storeId;

    @ApiModelProperty(value = "所属店铺名称")
    private String storeName;

    @ApiModelProperty(value = "所属店铺粉丝数量")
    private Integer fansNum;

    @ApiModelProperty(value = "是否关注过店铺 true 已关注 false 未关注")
    private Boolean focusStatus;

    @ApiModelProperty(value = "资讯关联的商品列表按最晚添加时间的第一个商品信息")
    private GoodsBaseInfoVo goodsBaseInfoVo;
}
