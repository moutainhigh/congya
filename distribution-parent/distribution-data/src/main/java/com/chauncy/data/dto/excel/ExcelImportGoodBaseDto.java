package com.chauncy.data.dto.excel;

import com.chauncy.common.enums.goods.GoodsTypeEnum;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.dto.supplier.good.add.AddGoodsParamValueDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * excel导入商品基本属性的dto
 * @Author zhangrt
 * @Date 2019/6/17 23:09
 **/

@Data
public class ExcelImportGoodBaseDto {

    @ApiModelProperty(value="商品ID")
    @NeedExistConstraint(tableName = "pm_goods",groups = IUpdateGroup.class)
    @NotNull(message = "商品ID不能为空",groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "商品类型")
    @NotBlank(message = "商品类型图不能为空")
    @EnumConstraint(target = GoodsTypeEnum.class)
    private String goodsType;

    @ApiModelProperty(value = "分类ID")
    @JsonSerialize(using = LongJsonSerializer.class)
    @NotNull(message = "分类ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods_category")
    private Long goodsCategoryId;

    @ApiModelProperty(value = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "spu编码")
    private String spu;

    @ApiModelProperty(value = "是否是店铺推荐；0->不推荐；1->推荐")
    private Boolean recommandStatus;

    @ApiModelProperty(value = "是否是明星单品；0->否；1->是")
    private Boolean starStatus;

    @ApiModelProperty(value = "商品缩略图")
    @NotBlank(message = "商品缩略图不能为空")
    private String icon;

    @ApiModelProperty(value = "轮播图")
    @NotBlank(message = "轮播图不能为空")
    private String carouselImage;

    @ApiModelProperty(value = "产品详情网页内容")
    private String detailHtml;

    @ApiModelProperty(value = "品牌ID")
    @NeedExistConstraint(tableName = "pm_goods_attribute")
    private Long brandId;

    @ApiModelProperty(value = "标签ID")
    @NeedExistConstraint(tableName = "pm_goods_attribute")
    private Long labelId;

    @ApiModelProperty(value = "服务说明ID")
    @NeedExistConstraint(tableName = "pm_goods_attribute")
    private Long ServiceId;

    @ApiModelProperty(value = "商品参数信息")
    private List<AddGoodsParamValueDto> goodsParamDtoList;

//    @ApiModelProperty(value = "平台活动说明Id")
//    @NeedExistConstraint(tableName = "pm_goods_attribute")
//    private Long platformActivityId;



    @ApiModelProperty(value = "运费说明id")
    @NotNull(message = "运费说明ID")
    private Long shippingId;
}
