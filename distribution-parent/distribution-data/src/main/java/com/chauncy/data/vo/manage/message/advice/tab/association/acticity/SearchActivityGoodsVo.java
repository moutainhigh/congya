package com.chauncy.data.vo.manage.message.advice.tab.association.acticity;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.app.advice.AssociationTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-09-15 17:06
 *
 * 获取积分/满减/秒杀/拼团商品
 */
@Data
@ApiModel(description = "获取积分/满减/秒杀/拼团商品")
@Accessors(chain = true)
public class SearchActivityGoodsVo {

    @ApiModelProperty("商品ID")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("第三级分类ID")
    private Long goodsCategoryId;

    @ApiModelProperty("所属类目")
    private String category;

    @ApiModelProperty("所属活动名称")
    private String activityName;
}
