package com.chauncy.data.vo.app.advice.home;

import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-08-27 12:51
 *
 * 轮播图信息
 */
@Data
@ApiModel(description = "轮播图信息")
@Accessors(chain = true)
public class ShufflingVo {

    @ApiModelProperty("轮播图ID")
    private Long shufflingId;

    @ApiModelProperty("封面图片")
    private String coverPhoto;

    @ApiModelProperty("广告类型:1-图文详情,2-资讯,3-店铺,4-商品")
    private AdviceTypeEnum adviceType;

    @ApiModelProperty("资讯/店铺/商品ID")
    private Long detailId;

    @ApiModelProperty("图文详情")
    private String htmlDetail;
}
