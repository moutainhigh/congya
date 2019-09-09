package com.chauncy.data.vo.app.advice.store;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/8 19:42
 */
@Data
@ApiModel(description = "店铺详情-商品二级分类列表")
@Accessors(chain = true)
public class GoodsSecondCategoryListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "二级分类名称")
    private String categoryName;

    @ApiModelProperty(value = "二级分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "三级分类id")
    private List<GoodsThirdCategoryListVo> categoryList;


}
