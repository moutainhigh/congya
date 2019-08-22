package com.chauncy.data.vo.manage.message.advice.shuffling;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-08-20 11:38
 *
 * 条件分页查询无关联轮播图广告需要关联的资讯、店铺、商品
 *
 */
@Data
@ApiModel("条件分页查询无关联轮播图广告需要关联的资讯、店铺、商品")
@Accessors(chain = true)
public class SearchShufflingAssociatedDetailVo {

    @ApiModelProperty("商品/资讯/店铺ID")
    private Long detailId;

    @ApiModelProperty("商品/资讯/店铺名称")
    private String detailName;

    @ApiModelProperty("商品/店铺所属分类")
    private String categoryName;

    @ApiModelProperty(value = "商品三级分类ID",hidden = true)
    @JSONField(serialize = false)
    private Long categoryId;

}
