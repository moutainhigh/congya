package com.chauncy.data.dto.manage.store.select;

import com.chauncy.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/6/24 10:43
 */
@Data
@ApiModel(value = "查找可绑定的店铺")
public class StoreSearchByConditionDto extends BaseSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺ID")
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String name;
}
