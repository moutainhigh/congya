package com.chauncy.data.vo.app.user.favorites;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/13  16:35
 * @Version 1.0
 *
 * 条件查询收藏夹Vo
 */
@Data
@ApiModel (description = "条件查询收藏商品Vo")
public class SearchFavoritesVo {

    @ApiModelProperty("id")
    public Long id;

    @ApiModelProperty("商品id")
    public Long goodsId;

    @ApiModelProperty("店铺id")
    public Long storeId;

    @ApiModelProperty("资讯id")
    public Long informationId;

    @ApiModelProperty("图片")
    public String picture;

    @ApiModelProperty("标题")
    public String title;

    @ApiModelProperty("价格")
    public String price;

    @ApiModelProperty("名称")
    public String name;
}
