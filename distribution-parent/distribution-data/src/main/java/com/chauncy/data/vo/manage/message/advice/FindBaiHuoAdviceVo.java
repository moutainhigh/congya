package com.chauncy.data.vo.manage.message.advice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-01 15:39
 *
 * 查找广告位为葱鸭百货的所有广告
 */
@Data
@ApiModel(description = "查找广告位为葱鸭百货的所有广告")
public class FindBaiHuoAdviceVo {

    @ApiModelProperty("广告名称")
    private Long label;

    @ApiModelProperty("广告ID")
    private String value;
}
