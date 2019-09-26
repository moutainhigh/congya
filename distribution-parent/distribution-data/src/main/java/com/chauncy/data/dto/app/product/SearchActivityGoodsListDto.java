package com.chauncy.data.dto.app.product;

import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/25 15:44
 */
@Data
@ApiModel(value = "SearchActivityGoodsDto对象", description = "查询积分/满减活动商品列表参数")
public class SearchActivityGoodsListDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分组类型   \n1-满减   \n2-积分   \n")
    @EnumConstraint(target = GroupTypeEnum.class)
    @NotNull(message = "分组类型不能为空")
    private Integer groupType;

    @ApiModelProperty(value = "商品分类id，此时活动分组id也是必传   \n")
    private Long categoryId;

    @ApiModelProperty(value = "活动分组id   \n")
    private Long groupId;

    @ApiModelProperty(value = "选项卡id   \n")
    private Long tabId;

    @ApiModelProperty(value = "排序方式 默认降序（desc）   \n")
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）   \nCOMPREHENSIVE_SORT：综合排序   \n" +
            "SALES_SORT：销量排序   \n PRICE_SORT：价格排序   \n")
    private SortFileEnum sortFile;

    @ApiModelProperty(value = "是否分页  0 不分页  1分页")
    @JsonIgnore
    private Integer isPaging;

}
