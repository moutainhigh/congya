package com.chauncy.data.vo.manage.message.advice.tab.association.acticity;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-08-14 14:50
 *
 * 每个热销广告选项卡信息及其关联的商品ID信息
 */
@ApiModel(description = "每个热销广告选项卡信息及其关联的商品ID信息")
@Data
@Accessors(chain = true)
public class ActivitySellHotTabInfosVo {

    @ApiModelProperty("热销广告选项卡ID")
    @JSONField(ordinal = 9)
    private Long sellHotTabId;

    @ApiModelProperty("热销广告选项卡图片")
    @JSONField(ordinal = 10)
    private String sellHotTabName;

    @ApiModelProperty("关联的推荐商品")
    @JSONField(ordinal = 11)
    private PageInfo<SellHotRelGoodsVo> sellHotRelGoods;

}
