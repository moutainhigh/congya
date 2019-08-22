package com.chauncy.data.dto.manage.message.advice.add;

import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-20 16:09
 *
 * 保存充值入口/拼团鸭广告
 *
 */
@Data
@ApiModel(description = "保存充值入口/拼团鸭广告")
@Accessors(chain = true)
public class SaveOtherAdviceDto {

    @ApiModelProperty("广告ID,新增时传0")
    private Long adviceId;

    @ApiModelProperty("广告位置(保存充值入口/拼团鸭广告)")
    @EnumConstraint(target = AdviceLocationEnum.class)
    private String location;

    @ApiModelProperty("广告名称")
    @NotNull(message = "广告名称不能为空")
    private String name;

    @ApiModelProperty("图片")
    private String picture;
}
