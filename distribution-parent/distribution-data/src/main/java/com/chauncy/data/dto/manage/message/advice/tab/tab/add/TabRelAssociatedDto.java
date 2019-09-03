package com.chauncy.data.dto.manage.message.advice.tab.tab.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import com.chauncy.data.dto.manage.message.advice.tab.tab.add.shuffling.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-19 17:00
 *
 * 选项卡关联的品牌或者商品，如果是品牌，则还可以添加对应的广告轮播图
 */
@Data
@ApiModel(description = "选项卡关联的品牌或者商品，如果是品牌，则还可以添加对应的广告轮播图")
@Accessors(chain = true)
public class TabRelAssociatedDto {

    @ApiModelProperty("关联的ID")
    @NotNull(message = "关联的Id不能为空")
    private Long associatedId;

    private List<BrandShufflingDto> brandShufflings;
}
