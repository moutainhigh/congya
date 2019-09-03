package com.chauncy.data.dto.manage.message.advice.tab.tab.add;

import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
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
 * @create 2019-08-14 14:27
 *
 * 无关联的选项卡
 *
 * 保存特卖、有品、主题、优选等广告信息
 *
 */
@Data
@ApiModel(description = "保存特卖、有品、主题、优选等广告信息")
@Accessors(chain = true)
public class SaveRelTabDto {

    @ApiModelProperty("广告ID，新增时传0")
    private Long adviceId;

    @ApiModelProperty("广告位置(特卖、有品、主题、优选)")
    @EnumConstraint(target = AdviceLocationEnum.class)
    private String location;

    @ApiModelProperty("广告名称")
    @NotNull(message = "广告名称不能为空")
    private String name;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("选项卡信息")
    @NotNull(message = "选项卡信息不能为空")
    @Valid
    private List<RelTabInfosDto> tabInfos;

}
