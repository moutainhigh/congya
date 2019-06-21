package com.chauncy.data.dto.excel;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * excel导入商品基本属性的dto
 *
 * @Author zhangrt
 * @Date 2019/6/17 23:09
 **/

@Data
public class ExcelImportGoodBaseDto {

    @NotBlank(message = "商品分类")
    @NeedExistConstraint(tableName = "pm_goods_category", field = "name",
            message = "系统第三级分类中不存在该名称", concatWhereSql = "and `level=3`")
    private String goodsCategoryName;

    @NotBlank(message = "商品类型不能为空")
    private String goodsType;


    @ApiModelProperty(value = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "spu编码")
    private String spu;

    @ApiModelProperty(value = "品牌名称")
    @NotBlank(message = "商品品牌不能为空！")
    @NeedExistConstraint(tableName = "pm_goods_attribute", field = "name",
            message = "系统中不存在该品牌名称", concatWhereSql = "and type=7")
    private String brandName;

    @ApiModelProperty(value = "标签名称")
    @NotBlank(message = "商品标签不能为空")
    @NeedExistConstraint(tableName = "pm_goods_attribute", field = "name", concatWhereSql = "and type=4")
    private List<String> labelNames;

    @ApiModelProperty(value = "是否是店铺推荐；0->不推荐；1->推荐")
    @NotBlank(message = "是否是店铺推荐不能为空")
    private String recommandStatusName;

    @ApiModelProperty(value = "是否是明星单品；0->否；1->是")
    @NotBlank(message = "是否是明星单品不能为空")
    private String starStatusName;


    @ApiModelProperty(value = "平台服务说明名称")
    @NeedExistConstraint(tableName = "pm_goods_attribute", field = "name", concatWhereSql = "and type=1")
    private String platformServiceName;

    @ApiModelProperty(value = "商家服务说明名称")
    @NeedExistConstraint(tableName = "pm_goods_attribute", field = "name", concatWhereSql = "and type=2")
    private String supplierServiceName;

    @ApiModelProperty(value = "平台运费模板")
    @NeedExistConstraint(tableName = "pm_number_shipping", field = "name", concatWhereSql = "and type=1")
    private String platformShipTemplate;

    @ApiModelProperty(value = "商家运费模板")
    @NeedExistConstraint(tableName = "pm_number_shipping", field = "name", concatWhereSql = "and type=2")
    private String supplierShipTemplate;

    @ApiModelProperty(value = "商品参数")
    private List<String> goodAttributionNames;

    @ApiModelProperty(value = "商品参数值")
    private List<String> goodAttributionValueNames;


    @ApiModelProperty(value = "发货地")
    @NotBlank(message = "发货地不能为空")
    private String locationName;

    @ApiModelProperty(value = "限购数量")
    @NotBlank(message = "限购数量不能为空")
    private String purchaseLimit;

    @ApiModelProperty(value = "关联商品id")
    @NeedExistConstraint(tableName = "pm_goods")
    private List<String> goodIds;


}
