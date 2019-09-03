package com.chauncy.data.dto.manage.message.advice.shuffling.select;

import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-20 11:43
 *
 * 分页查询无关联轮播图广告需要关联的资讯、店铺、商品的条件
 */
@Data
@ApiModel("分页查询无关联轮播图广告需要关联的资讯、店铺、商品的条件")
@Accessors(chain = true)
public class SearchShufflingAssociatedDetailDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "广告类型：1-'图文详情',2-'资讯',3-'店铺',4-'商品'")
    @NotNull(message = "广告类型不能为空")
    @EnumConstraint(target = AdviceTypeEnum.class)
    private Integer adviceType;

    @ApiModelProperty(value = "商品/资讯/店铺名称")
    private String name;

    @ApiModelProperty(value = "具体分类")
    private Long categoryId;
}
