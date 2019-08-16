package com.chauncy.data.vo.manage.message.advice.tab.association;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-16 12:34
 *
 * 广告位置为首页有店+店铺分类详情
 */
@Data
@ApiModel(description = "广告位置为首页有店+店铺分类详情")
@Accessors(chain = true)
public class YoudianDetailVo {

    @ApiModelProperty("店铺分类及其相关的选项卡")
    @JSONField(ordinal = 4)
    private List<StoreTabsVo> storeTabsVoList;
}
