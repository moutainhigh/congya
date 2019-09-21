package com.chauncy.data.dto.app.component;

import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/19 17:13
 */
@Data
@ApiModel(value = "ScreenParamDto对象", description = "查询店铺/商品/资讯参数")
public class ScreenParamDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜索类型   \n1.商品   \n2.店铺   \n3.资讯")
    @NotNull(message = "搜索类型不能为空")
    private Integer keyWordType;

    @ApiModelProperty(value = "keyWordType = 1 时传参")
    private SearchStoreGoodsDto searchStoreGoodsDto;

    @ApiModelProperty(value = "keyWordType = 2 时传参")
    private SearchStoreDto searchStoreDto;

    @ApiModelProperty(value = "keyWordType = 3 时传参")
    private SearchInfoByConditionDto searchInformationDto;
}
