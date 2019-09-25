package com.chauncy.data.vo.manage.message.advice.tab.tab;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-09-15 17:06
 *
 * 获取商品
 */
@Data
@ApiModel(description = "获取商品")
@Accessors(chain = true)
public class SearchAdviceGoodsVo {

    @ApiModelProperty("商品ID")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty(value = "第三级分类ID",hidden = true)
    @JSONField(serialize = false)
    private Long goodsCategoryId;

    @ApiModelProperty("所属类目")
    private String category;
}
