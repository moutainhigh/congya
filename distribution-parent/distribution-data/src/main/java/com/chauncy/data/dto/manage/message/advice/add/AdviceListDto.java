package com.chauncy.data.dto.manage.message.advice.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-08 21:31
 *
 * 百货中部广告列表
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "百货中部广告列表")
public class AdviceListDto {

    @ApiModelProperty(value = "广告图片")
    @NotNull(message = "广告图片不能为空")
    private String picture;

    @ApiModelProperty(value = "商品ID集合")
    private List<Long> goodsIds;

}
