package com.chauncy.data.vo.app.brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/15  18:58
 * @Version 1.0
 */
@Data
@ApiModel (description = "品牌信息Vo")
@Accessors (chain = true)
public class BrandInfoVo {

    @ApiModelProperty ("品牌id")
    private Long brandId;

    @ApiModelProperty ("品牌名称")
    private String brandName;

    @ApiModelProperty ("品牌logo")
    private String brandPicture;
}
