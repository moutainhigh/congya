package com.chauncy.data.dto.app.component;

import com.chauncy.common.enums.app.component.ShareTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-09-07 12:26
 *
 * 分享dto
 */
@Data
@ApiModel(description = "分享需要的数据")
@Accessors(chain = true)
public class ShareDto {

    @ApiModelProperty(value = "分享类型")
    private ShareTypeEnum shareType;

    @ApiModelProperty(value = "需要被分享的商品ID/资讯ID")
    private Long shareId;
}
