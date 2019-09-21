package com.chauncy.data.vo.app.evaluate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-01 12:38
 *
 * 获取商品对应的所有评价信息
 */
@Data
@ApiModel(description = "获取商品对应的所有评价信息")
public class GoodsEvaluateVo {

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String icon;

    @ApiModelProperty("用户评价")
    private String content;

    @ApiModelProperty("商家回复")
    private String reply;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("sku信息")
    private String sku;

    @ApiModelProperty(value = "评价 Id")
    private Long id;

    @ApiModelProperty(value = "评价星级")
    private Integer startLevel;

    @ApiModelProperty(value = "评价时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime evaluateTime;

    @ApiModelProperty(value = "该条评论点赞数")
    private Integer likedNum;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLiked;

}
