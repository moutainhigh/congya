package com.chauncy.data.vo.app.user.favorites;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-09-09 16:59
 */
@Data
@ApiModel(description = "我的商品收藏")
@Accessors(chain = true)
public class FavoritesGoosVo {

    @ApiModelProperty("收藏id")
    public Long id;

    @ApiModelProperty("商品id")
    public Long goodsId;

    @ApiModelProperty("图片")
    public String picture;

    @ApiModelProperty("价格")
    public String price;

    @ApiModelProperty("商品名称")
    public String name;

    @ApiModelProperty("购买人数")
    public Integer buyNum;
}
