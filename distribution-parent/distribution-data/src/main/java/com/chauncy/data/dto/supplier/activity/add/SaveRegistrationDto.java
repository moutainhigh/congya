package com.chauncy.data.dto.supplier.activity.add;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-28 22:06
 *
 * 保存商家报名的活动信息
 */
@Data
@ApiModel(description = "保存商家报名的活动信息")
@Accessors(chain = true)
public class SaveRegistrationDto {

    @ApiModelProperty("活动类型:满减、积分、秒杀、拼团")
    @EnumConstraint(target = ActivityTypeEnum.class)
    @NotNull(message = "活动类型不能为空")
    private String activityType;

    @ApiModelProperty(value = "活动ID")
    @NotNull(message = "活动ID不能为空")
    private Long activityId;

    @ApiModelProperty(value = "活动商品ID")
    @NotNull(message = "活动商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "购买上限")
    @NotNull(message = "购买上限不能为空")
    private Integer buyLimit;

    @ApiModelProperty(value = "活动图片")
    @NotNull(message = "活动图片不能为空")
    private String picture;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "平台活动与商品关联的ID")
    private Long activityGoodsRelId;

    @ApiModelProperty(value = "平台活动的商品与sku关联的ID")
    private List<SaveActivitySkuDto> activitySkuDtoList;
}
