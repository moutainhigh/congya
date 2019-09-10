package com.chauncy.data.vo.app.user.favorites;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-09 16:59
 */
@Data
@ApiModel(description = "我的店铺收藏")
@Accessors(chain = true)
public class FavoritesStoreVo {

    @ApiModelProperty("id")
    public Long id;

    @ApiModelProperty("店铺id")
    public Long storeId;

    @ApiModelProperty("店铺名称")
    public String storeName;

    @ApiModelProperty("店铺logo")
    public String storeLogo;

    @ApiModelProperty("店铺标签")
    public List<String> storeLabels;

    @ApiModelProperty("店铺介绍")
    public String storeIntroduction;
}
