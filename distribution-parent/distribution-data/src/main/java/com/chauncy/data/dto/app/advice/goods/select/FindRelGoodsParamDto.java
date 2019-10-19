package com.chauncy.data.dto.app.advice.goods.select;

import com.chauncy.common.enums.app.advice.ConditionTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @description: 广告关联下的商品列表筛选参数
 * @since 2019/10/19 0:23
 */
@Data
@ApiModel(value = "FindRelGoodsParamDto", description = "广告关联下的商品列表筛选参数")
public class FindRelGoodsParamDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "查询条件类型  BAIHUO_ASSOCIATED/HOME没有筛选条件")
    private ConditionTypeEnum conditionType;

    /*@ApiModelProperty(value = "只当查询条件类型为BAIHUO_ASSOCIATED——百货关联的所有商品,才传葱鸭百货广告ID,其它类型不传")
    private Long adviceId;*/

    @ApiModelProperty(value = "查询商品列表前提条件Id(品牌ID/店铺ID/选项卡Id/商品二级/三级分类ID等)")
    private Long conditionId;

}
