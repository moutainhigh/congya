package com.chauncy.data.dto.store;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: xiaoye
 * @Date: 2019/6/5 15:04
 */
@Data
@ApiModel(value = "StoreInfoDto对象", description = "店铺基本信息")
public class StoreBaseInfoDto {


    @ApiModelProperty(value = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String name;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

    @ApiModelProperty(value = "店铺账号")
    @NotBlank(message = "店铺账号不能为空")
    private String userName;

    @ApiModelProperty(value = "是否展示在前端 0 不展示 1 展示")
    private boolean showStatus;

    @ApiModelProperty(value = "店铺类型标签id（pm_goods_attribute主键）")
    @Min(value = 0, message = "店铺类型标签选择错误")
    private Long storeTypeLabelId;

    @ApiModelProperty(value = "店铺分类id（pm_goods_attribute主键）")
    @Min(value = 0, message = "店铺分类标签选择错误")
    private Long storeCategoryId;

    @ApiModelProperty(value = "商家类型（推广店铺，商品店铺）")
    @NotBlank(message = "商家类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "所属店铺Id")
    private Long parentId;

    @ApiModelProperty(value = "排序数值")
    @Min(value = 0, message = "排序数字必须大于0")
    private Integer sort;


    @ApiModelProperty(value = "主理人姓名")
    private String ownerName;

    @ApiModelProperty(value = "主理人联系电话")
    private String ownerMobile;

    @ApiModelProperty(value = "店铺logo")
    @NotBlank(message = "店铺logo不能为空")
    private String logoImage;

    @ApiModelProperty(value = "店铺缩略图，展示用")
    @NotBlank(message = "店铺缩略图不能为空")
    private String storeImage;

    @ApiModelProperty(value = "店铺背景图")
    @NotBlank(message = "店铺背景图不能为空")
    private String backgroundImage;

    @ApiModelProperty(value = "所属品牌集合")
    @NotNull
    private Long[] attributeIds;
}
