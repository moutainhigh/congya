package com.chauncy.data.dto.manage.message.advice.tab.association.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-22 21:57
 *
 * 每个热销广告选项卡信息及其关联的商品信息
 */
@Data
@ApiModel(description = "每个选项卡信息及其关联的商品信息")
@Accessors(chain = true)
public class ActivitySellHotTabInfosDto {

    @ApiModelProperty("热销广告选项卡ID，新增时传0")
    private Long tabId;

    @ApiModelProperty("热销广告选项卡图片")
    @NotNull(message = "热销广告选项卡图片不能为空")
    private String tabName;

    @ApiModelProperty("关联的商品ID")
    @NotNull(message = "关联的商品ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    private List<Long> goodsIds;

}
