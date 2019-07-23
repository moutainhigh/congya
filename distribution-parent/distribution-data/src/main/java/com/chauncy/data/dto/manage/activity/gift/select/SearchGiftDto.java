package com.chauncy.data.dto.manage.activity.gift.select;

import com.chauncy.common.enums.app.gift.GiftTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-22 17:22
 *
 * 多条件查询礼包列表
 */
@Data
@ApiModel(description = "多条件查询礼包列表")
@Accessors(chain = true)
public class SearchGiftDto {

    @ApiModelProperty(value = "礼包类型 1-充值礼包 2-新人礼包")
    @EnumConstraint(target = GiftTypeEnum.class)
    @NotNull(message = "礼包类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "礼包ID")
    private Long id;

    @ApiModelProperty(value = "礼包名称")
    private String name;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
