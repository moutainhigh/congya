package com.chauncy.data.dto.app.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.message.InformationTypeEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/15 15:30
 */
@Data
@ApiModel(value = "SearchStoreDto对象", description = "查询店铺的条件")
public class SearchStoreDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "排序方式 默认降序（desc）")
    private SortWayEnum sortWay;

    @ApiModelProperty(value = "排序字段 默认综合排序（COMPREHENSIVE_SORT）")
    private SortFileEnum sortFile;

    @ApiModelProperty(value = "模糊搜索关键字")
    private String keyword;

   /* @ApiModelProperty(value = "店铺分类id")
    @NeedExistConstraint(tableName = "sm_store_category")
    private Long storeCategoryId;*/

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "用户id 获取用户关注的店铺资讯")
    private Long userId;

    @Min(1)
    @JsonIgnore
    @ApiModelProperty(value = "店铺下商品展示的数量，默认3")
    private Integer goodsNum;

    @ApiModelProperty(value = "筛选条件   店铺标签id")
    private List<Long> labelIds;

    @ApiModelProperty(value = "筛选条件   店铺分类id")
    private List<Long> categoryIds;


}
