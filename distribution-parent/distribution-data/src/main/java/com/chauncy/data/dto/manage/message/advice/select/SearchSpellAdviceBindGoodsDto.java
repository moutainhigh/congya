package com.chauncy.data.dto.manage.message.advice.select;

import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-10-09 20:41
 *
 * 条件分页查询今日必拼广告已经绑定的参加拼团的商品信息
 */
@Data
@ApiModel(description = "条件分页查询今日必拼广告已经绑定的参加拼团的商品信息")
@Accessors(chain = true)
public class SearchSpellAdviceBindGoodsDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "三级分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "广告ID")
    @NotNull(message = "广告ID不能为空")
    @NeedExistConstraint(tableName = "mm_advice")
    private Long adviceId;
}
