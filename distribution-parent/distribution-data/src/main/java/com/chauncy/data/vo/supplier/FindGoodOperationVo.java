package com.chauncy.data.vo.supplier;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-16 12:12
 *
 * 查找运营角色填写数据显示给前端
 */
@Data
@ApiModel(value = "FindGoodOperationVo", description = "查找运营角色填写数据显示给前端")
public class FindGoodOperationVo {

    @ApiModelProperty(value = "商品ID")
    @NeedExistConstraint(tableName = "pm_goods")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "商品类型")
    private String goodsType;

    @ApiModelProperty(value = "活动成本比例")
    @NotNull(message = "活动成本比例不能为空")
    private BigDecimal activityCostRate;

    @ApiModelProperty(value = "让利成本比例")
    @NotNull(message = "让利成本比例不能为空")
    private BigDecimal profitsRate;

    @ApiModelProperty(value = "推广成本比例")
    @NotNull(message = "推广成本比例不能为空")
    private BigDecimal generalizeCostRate;

    @ApiModelProperty(value = "会员等级ID")
    @NotNull(message = "会员等级ID不能为空")
    private Long memberLevelId;

    @ApiModelProperty(value = "会员等级名称")
    @NotNull(message = "会员等级名称不能为空")
    private  String memberLevelName;

    @ApiModelProperty(value = "限定会员等级列表集合")
    private List<MemberLevelInfos> memberLevelInfos;

    /*@ApiModelProperty(value = "最低会员等级ID")
    private Long lowestLevelId;*/

    @ApiModelProperty(value = "商品排序数字")
    @NotNull(message = "商品排序数字不能为空")
    private BigDecimal sort;

    @ApiModelProperty(value="税率选择，即税率类型1--平台税率 2--自定义税率,只有在商品类型goodsType为保税仓或海外直邮才显示")
    private Integer taxRateType;

    @ApiModelProperty(value="税率,商品类型goodsType为保税仓或海外直邮才显示")
    private BigDecimal taxRate;

    @ApiModelProperty(value = "是否包邮 默认为0不包邮")
    @NotNull(message = "是否包邮不能为空")
    private Boolean isFreePostage;
}
