package com.chauncy.data.vo.app.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value = "评价 Id")
    private Long id;

}
