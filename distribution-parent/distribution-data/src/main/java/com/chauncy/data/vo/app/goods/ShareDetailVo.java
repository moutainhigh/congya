package com.chauncy.data.vo.app.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @Description 分享页面所需的数据
 * @since 2019/11/17 21:20
 */
@Data
@ApiModel(value = "ShareDetailVo", description =  "分享页面所需的数据")
public class ShareDetailVo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "标题")
    private String shareTitle;

    @ApiModelProperty(value = "描述")
    private String shareDescribe;

    @ApiModelProperty(value = "链接")
    private String shareUrl;

    @ApiModelProperty(value = "图片")
    private String sharePicture;

    @ApiModelProperty(value = "用户头像")
    private String userPhoto;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "邀请码")
    private Long inviteCode;

    @ApiModelProperty(value = "商品图片")
    private String goodsPicture;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "最低价格")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "最高价格")
    private BigDecimal maxPrice;

}
