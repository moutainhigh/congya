package com.chauncy.data.dto.manage.message.advice.tab.association.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-14 14:38
 *
 * 推荐的店铺分类及其对应tabs选项卡
 *
 */
@Data
@ApiModel(description = "推荐的店铺分类及其对应tabs选项卡")
@Accessors(chain = true)
public class  StoreTabsDto {

    @ApiModelProperty("广告和推荐的店铺关联ID,新增时传0")
    private Long adviceAssociationId;

    @ApiModelProperty("推荐的店铺分类ID")
    @NeedExistConstraint(tableName = "sm_store_category")
    @NotNull(message = "推荐的店铺分类ID不能为空")
    private Long storeClassificationId;

    @ApiModelProperty(value = "关联类型:1->店铺分类,2->品牌,3->商品,4->商品三级分类",hidden = true)
    private Integer type;

    @ApiModelProperty("选项卡信息")
    @Valid
    @NotNull(message = "选项卡信息不能为空")
    private List<TabInfosDto> tabInfos;

}
