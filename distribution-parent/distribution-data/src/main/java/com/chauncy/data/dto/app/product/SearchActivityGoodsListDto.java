package com.chauncy.data.dto.app.product;

import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/25 15:44
 */
@Data
@ApiModel(value = "SearchActivityGoodsDto对象", description = "查询积分/满减活动商品列表参数")
public class SearchActivityGoodsListDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动id")
    private Long activityId;

    @ApiModelProperty(value = "活动分组id")
    private Long groupId;

    @ApiModelProperty(value = "选项卡idid")
    private Long tabId;

    @ApiModelProperty(value = "排序方式 默认降序（desc）")
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）")
    private SortFileEnum sortFile;

}
