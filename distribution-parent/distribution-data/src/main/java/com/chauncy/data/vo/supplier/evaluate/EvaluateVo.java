package com.chauncy.data.vo.supplier.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-07-02 14:32
 *
 * 获取商品对应的所有评价信息
 */
@Data
@ApiModel(description = "获取商品对应的所有评价信息")
public class EvaluateVo {

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

    @ApiModelProperty(value = "评价星级")
    private Integer startLevel;

}
