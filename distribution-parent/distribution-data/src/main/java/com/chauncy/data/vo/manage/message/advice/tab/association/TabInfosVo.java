package com.chauncy.data.vo.manage.message.advice.tab.association;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.BaseVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-14 14:50
 *
 * 每个选项卡信息及其关联的店铺分类ID信息
 */
@ApiModel(description = "每个选项卡信息及其关联的店铺分类ID信息")
@Data
@Accessors(chain = true)
public class TabInfosVo {

    @ApiModelProperty("选项卡ID")
    @JSONField(ordinal = 9)
    private Long tabId;

    @ApiModelProperty("选项卡名称")
    @JSONField(ordinal = 10)
    private String tabName;

    @ApiModelProperty("关联的推荐店铺")
    @JSONField(ordinal = 11)
    private PageInfo<StoreVo> storeList;

}
