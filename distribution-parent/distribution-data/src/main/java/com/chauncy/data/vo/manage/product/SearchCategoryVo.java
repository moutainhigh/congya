package com.chauncy.data.vo.manage.product;

import com.chauncy.data.domain.BaseTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/6/24 22:41
 **/
@Data
public class SearchCategoryVo extends BaseTree<Long,SearchCategoryVo> {


    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private BigDecimal sort;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "层级")
    private Integer level;
}
