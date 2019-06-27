package com.chauncy.data.dto.manage.message.content.add;

import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-26 18:23
 *
 * 添加关键字Dto
 */
@Data
@ApiModel(description = "添加关键字Dto")
@Accessors(chain = true)
public class AddKeyWordsDto {

    @ApiModelProperty("热搜关键字ID")
    @NeedExistConstraint(tableName = "mm_keywords_search",groups = IUpdateGroup.class)
    @NotNull(message = "热搜关键字ID不能为空",groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty("热搜关键字")
    @NotNull(message = "热搜关键字不能为空")
    private String name;

    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空")
    private BigDecimal sort;

    @ApiModelProperty("关键字类型")
    @NotNull(message = "关键字类型不能为空")
    @EnumConstraint(target = KeyWordTypeEnum.class)
    private String type;
}
