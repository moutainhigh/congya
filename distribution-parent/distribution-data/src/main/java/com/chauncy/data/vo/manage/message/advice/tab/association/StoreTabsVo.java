package com.chauncy.data.vo.manage.message.advice.tab.association;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-14 14:38
 *
 * 推荐的店铺分类及其对应tabs选项卡
 *
 */
@Data
@ApiModel(description = "推荐的店铺分类及其对应tabs选项卡")
@Accessors(chain = true)
public class StoreTabsVo {

    @ApiModelProperty("广告和推荐的店铺关联ID")
    @JSONField(ordinal = 5)
    private Long adviceAssociationId;

    @ApiModelProperty("推荐的店铺分类ID")
    @JSONField(ordinal = 6)
    private Long storeClassificationId;

    @ApiModelProperty("推荐的店铺分类名称")
    @JSONField(ordinal = 7)
    private String storeClassificationName;

    @ApiModelProperty("选项卡信息")
    @JSONField(ordinal = 8)
    private List<TabInfosVo> tabInfos;

}
