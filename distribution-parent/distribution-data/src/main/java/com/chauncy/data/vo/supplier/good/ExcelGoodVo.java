package com.chauncy.data.vo.supplier.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/4 14:36
 **/

@Data
@ApiModel("获取导入商品列表")
@Accessors(chain = true)
public class ExcelGoodVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("导入时间")
    private LocalDateTime importTime;


}
