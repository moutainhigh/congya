package com.chauncy.data.vo.manage.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrt
 * @Date 2019/6/24 12:47
 **/

@Data
public class SearchAttributeVo {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty("是否选中")
    private Boolean isSelect;

}
