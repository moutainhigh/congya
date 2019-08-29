package com.chauncy.data.vo.app.advice.home;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-27 12:43
 *
 * app获取首页广告信息
 */
@Data
@ApiModel(description = "app获取首页广告信息")
@Accessors(chain = true)
public class GetAdviceInfoVo {

    @ApiModelProperty("广告ID")
    private Long adviceId;

    @ApiModelProperty("广告名称")
    private String adviceName;

    @ApiModelProperty("广告图片")
    private String advicePicture;

    @ApiModelProperty("广告位置")
    private String location;

    @ApiModelProperty("轮播图，首页中部1、2、3位置")
    private List<ShufflingVo> shufflingVoList;
}
