package com.chauncy.data.dto.app.advice.category.select;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-08 23:18
 */
@Data
@Accessors(chain = true)
public class TabAdviceVo {

    @ApiModelProperty(value = "图片选项卡ID")
    private Long id;

    @ApiModelProperty(value = "选项卡图片")
    private String picture;
}
