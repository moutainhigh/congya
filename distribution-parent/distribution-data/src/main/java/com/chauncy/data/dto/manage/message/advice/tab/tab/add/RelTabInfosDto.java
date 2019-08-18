package com.chauncy.data.dto.manage.message.advice.tab.tab.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-14 14:50
 *
 * 每个选项卡信息及其关联的品牌/商品ID信息
 */
@ApiModel(description = "每个选项卡信息及其关联的店铺分类ID信息")
@Data
@Accessors(chain = true)
public class RelTabInfosDto {

    @ApiModelProperty("选项卡ID,新增时传0")
    private Long tabId;

    @ApiModelProperty("选项卡名称")
    @NotNull(message = "选项卡名称")
    private String tabName;

    @ApiModelProperty("关联的ID")
    @NotNull(message = "关联的Id不能为空")
    private List<Long> associatedIds;

}
