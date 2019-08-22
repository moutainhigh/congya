package com.chauncy.data.dto.manage.message.advice.tab.association.add;

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
 * 有关联的选项卡
 *
 * 添加首页有店+店铺分类详情的广告内容
 *
 */
@Data
@ApiModel(description = "添加首页有店+店铺分类详情的广告内容")
@Accessors(chain = true)
public class SaveStoreClassificationDto {

    @ApiModelProperty("广告ID,新增时传0")
    private Long adviceId;

    @ApiModelProperty("广告位置(首页有店+店铺分类详情)")
    @EnumConstraint(target = AdviceLocationEnum.class)
    private String location;

    @ApiModelProperty("广告名称")
    @NotNull(message = "广告名称不能为空")
    private String name;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("店铺分类及其相关的选项卡")
    @NotNull(message = "店铺分类及其相关的选项卡不能为空")
    @Valid
    private List<StoreTabsDto> storeTabsDtoList;

}
