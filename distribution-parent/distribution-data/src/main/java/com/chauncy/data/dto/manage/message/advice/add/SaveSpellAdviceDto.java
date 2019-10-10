package com.chauncy.data.dto.manage.message.advice.add;

import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.data.dto.manage.message.advice.tab.tab.add.RelTabInfosDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-09 22:19
 *
 * 保存今日必拼广告
 */
@Data
@ApiModel(description = "SaveSpellAdviceDto")
@Accessors(chain = true)
public class SaveSpellAdviceDto {

    @ApiModelProperty("广告ID，新增时传0")
    private Long adviceId;

    @ApiModelProperty("广告位置()")
    @EnumConstraint(target = AdviceLocationEnum.class)
    private String location;

    @ApiModelProperty("广告名称")
    @NotNull(message = "广告名称不能为空")
    private String name;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("商品列表")
    @NotNull(message = "商品列表不能为空")
    @Valid
    private List<Long> goodsIds;
}
