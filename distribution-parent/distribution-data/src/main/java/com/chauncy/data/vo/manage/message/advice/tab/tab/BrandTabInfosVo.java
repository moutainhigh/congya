package com.chauncy.data.vo.manage.message.advice.tab.tab;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-08-14 14:50
 *
 * 每个选项卡信息及其关联的品牌ID信息
 */
@ApiModel(description = "每个选项卡信息及其关联的品牌ID信息")
@Data
@Accessors(chain = true)
public class BrandTabInfosVo {

    @ApiModelProperty("选项卡ID")
    @JSONField(ordinal = 4)
    private Long tabId;

    @ApiModelProperty("选项卡名称")
    @JSONField(ordinal = 5)
    private String tabName;

    @ApiModelProperty("关联的品牌")
    @JSONField(ordinal = 6)
    private PageInfo<BrandVo> brandList;

}
