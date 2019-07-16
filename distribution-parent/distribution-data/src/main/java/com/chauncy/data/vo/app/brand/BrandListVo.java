package com.chauncy.data.vo.app.brand;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/15  11:29
 * @Version 1.0
 * 品牌列表Vo
 */
@Data
@ApiModel (description = "品牌列表Vo")
@Accessors(chain = true)
public class BrandListVo {

    @ApiModelProperty ("三级分类类目id")
    private Long categoryId;

    @ApiModelProperty("三级分类名称")
    private String categoryName;

    @ApiModelProperty("三级分类下的品牌id和图片")
    private List<BrandInfoVo>  brandInfo;
}
