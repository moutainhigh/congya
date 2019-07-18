package com.chauncy.data.dto.app.store;

import com.chauncy.common.enums.message.InformationTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/15 15:30
 */
@Data
@ApiModel(value = "SearchStoreDto对象", description = "查询店铺的条件")
public class SearchStoreDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模糊搜索关键字")
    private String keyword;

    @ApiModelProperty(value = "店铺分类id")
    @NeedExistConstraint(tableName = "sm_store_category")
    private Long storeCategoryId;

    @Min(1)
    @ApiModelProperty(value = "店铺下商品展示的数量，默认4")
    private Integer goodsNum;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
