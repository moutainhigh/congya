package com.chauncy.data.dto.manage.message.advice.tab.association.search;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-14 20:22
 * <p>
 * 分页查询店铺分类下的店铺的条件
 */
@Data
@ApiModel(description = "分页查询店铺分类下的店铺的条件")
@Accessors(chain = true)
public class SearchClassificationStoreDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "店铺分类Id")
    @NeedExistConstraint(tableName = "sm_store_category")
    @NotNull(message = "店铺分类Id不能为空")
    private Long storeClassificationId;

    @ApiModelProperty(value = "选项ID，新增选项卡时传0")
    @NotNull(message = "选项ID不能为空")
    private Long tabId;
}
