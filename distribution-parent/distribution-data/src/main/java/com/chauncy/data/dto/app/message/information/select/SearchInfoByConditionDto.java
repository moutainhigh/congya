package com.chauncy.data.dto.app.message.information.select;

import com.chauncy.common.enums.message.InformationTypeEnum;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/2 14:24
 */
@Data
@ApiModel("条件查询资讯")
public class SearchInfoByConditionDto  extends BaseSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺id")
    @NeedExistConstraint(tableName = "sm_store")
    private Long storeId;

    @ApiModelProperty(value = "资讯标签id")
    @NeedExistConstraint(tableName = "mm_information_label")
    private Long infoLabelId;

    @ApiModelProperty(value = "资讯分类id")
    @NeedExistConstraint(tableName = "mm_information_category")
    private Long infoCategoryId;

    @ApiModelProperty(value = "资讯类型：  关注->FOCUSLIST  热榜->HOTLIST")
    @EnumConstraint(target = InformationTypeEnum.class)
    private InformationTypeEnum informationTypeEnum;

    @ApiModelProperty(value = "模糊搜索关键字")
    @EnumConstraint(target = InformationTypeEnum.class)
    private String keyword;

}
