package com.chauncy.data.vo.app.message.information;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/2 19:52
 */
@Data
@ApiModel(value = "InformationBaseVo", description =   "APP资讯详情查询结果")
public class InformationBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "资讯店铺信息")
    private InformationStoreInfoVo informationStoreInfo;

    @ApiModelProperty(value = "资讯id")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    /*@ApiModelProperty(value = "作者")
    private String author;*/

    @ApiModelProperty(value = "封面图片")
    @JsonIgnore
    @JSONField(serialize=false)
    private String coverImage;

    @ApiModelProperty(value = "封面图片")
    private List<String> coverImageList;

    @ApiModelProperty(value = "资讯正文带格式文本")
    private String detailHtml;

    @ApiModelProperty(value = "转发量")
    private Integer forwardNum;

    @ApiModelProperty(value = "是否转发")
    private Boolean isForward;

    @ApiModelProperty(value = "评论量")
    private Integer commentNum;

    @ApiModelProperty(value = "是否评论")
    private Boolean isComment;

    @ApiModelProperty(value = "点赞量")
    private Integer likedNum;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLiked;

    @ApiModelProperty(value = "收藏量")
    private Integer collectionNum;

    @ApiModelProperty(value = "是否收藏")
    private Boolean isCollection;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /*@ApiModelProperty(value = "所属店铺Id")
    private Long storeId;

    @ApiModelProperty(value = "所属店铺名称")
    private String storeName;

    @ApiModelProperty(value = "所属店铺粉丝数量")
    private Integer fansNum;

    @ApiModelProperty(value = "是否关注过店铺 true 已关注 false 未关注")
    private Boolean focusStatus;*/

    @ApiModelProperty(value = "资讯关联的商品列表")
    private List<GoodsBaseInfoVo> goodsBaseInfoVoList;
}
