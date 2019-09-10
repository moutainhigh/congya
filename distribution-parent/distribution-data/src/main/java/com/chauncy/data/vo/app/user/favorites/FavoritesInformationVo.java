package com.chauncy.data.vo.app.user.favorites;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-09-09 16:59
 *
 * 我的资讯收藏
 */
@Data
@ApiModel(description = "我的资讯收藏")
@Accessors(chain = true)
public class FavoritesInformationVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("资讯id")
    private Long informationId;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("店铺名称")
    private String name;

    @ApiModelProperty("店铺 logo ")
    private String logoImage;

    @ApiModelProperty("图文详情/内容")
    private String detailHtml;

}
