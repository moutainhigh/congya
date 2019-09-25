package com.chauncy.data.dto.manage.message.advice.tab.association.search;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-14 16:07
 *
 * 分页查询需要被关联的商品
 */
@Data
@ApiModel(description = "分页查询需要被关联的商品")
@Accessors(chain = true)
public class SearchActivityGoodsDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "三级分类ID")
    private Long categoryId;

    @ApiModelProperty("活动类型 1-满减；2-积分；3-秒杀；4-拼团")
    @EnumConstraint(target = ActivityTypeEnum.class)
    @NotNull(message = "活动类型不能为空")
    private Integer activityType;

}
