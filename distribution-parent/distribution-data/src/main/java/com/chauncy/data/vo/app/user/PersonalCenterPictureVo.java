package com.chauncy.data.vo.app.user;

import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-27 12:43
 *
 * app获取个人中心顶部和充值入口图片
 */
@Data
@ApiModel(description = "app获取个人中心顶部和充值入口图片")
@Accessors(chain = true)
public class PersonalCenterPictureVo {

    @ApiModelProperty("广告ID")
    private Long adviceId;

    @ApiModelProperty("广告名称")
    private String adviceName;

    @ApiModelProperty("广告图片")
    private String advicePicture;

    @ApiModelProperty("广告位置")
    private String location;
}
