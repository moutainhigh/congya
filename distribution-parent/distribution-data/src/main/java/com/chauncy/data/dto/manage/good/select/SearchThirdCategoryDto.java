package com.chauncy.data.dto.manage.good.select;

import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-07 16:03
 * <p>
 * 分页获取第三级分类信息条件
 */
@ApiModel(description = "分页获取第三级分类信息条件")
@Accessors(chain = true)
@Data
public class SearchThirdCategoryDto extends BaseSearchPagingDto {

    @ApiModelProperty(value = "第二级分类ID")
    private Long secondCategoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

}
