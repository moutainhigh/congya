package com.chauncy.data.vo.manage.product;

import com.chauncy.data.domain.MyBaseTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrtGoodsCategory
 * @Date 2019/6/27 18:54
 **/

@Data
@ApiModel(value = "GoodsCategoryTreeVo", description = "所有商品分类")
public class GoodsCategoryTreeVo extends MyBaseTree<Long,GoodsCategoryTreeVo> {

    @ApiModelProperty(value = "名称")
    private String label;

    @ApiModelProperty(value = "图片")
    private String icon;
}
