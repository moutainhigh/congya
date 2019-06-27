package com.chauncy.data.dto.manage.good.select;

import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019-06-03 11:34
 **/
@Data
@ApiModel(value = "查找商品分类列表")
public class SearchGoodCategoryDto extends BaseSearchDto {

    @ApiModelProperty("value")
    private Long id;





}
