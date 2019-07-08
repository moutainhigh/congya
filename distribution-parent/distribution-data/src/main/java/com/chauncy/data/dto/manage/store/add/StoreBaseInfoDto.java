package com.chauncy.data.dto.manage.store.add;

import com.chauncy.common.enums.store.StoreTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: xiaoye
 * @Date: 2019/6/5 15:04
 */
@Data
@ApiModel(value = "StoreInfoDto对象", description = "店铺基本信息")
public class StoreBaseInfoDto implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id,当新增时为空")
    @NeedExistConstraint(tableName = "sm_store",groups = IUpdateGroup.class)
    @Min(message = "店铺id错误", value = 1)
    private Long id;


    @ApiModelProperty(value = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String name;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

    @ApiModelProperty(value = "店铺账号")
    @NotBlank(message = "店铺账号不能为空")
    private String userName;

    @ApiModelProperty(value = "是否展示在前端 0 不展示 1 展示")
    private Boolean showStatus;

    /*@ApiModelProperty(value = "店铺类型标签id（sm_store_label主键）")
    @NeedExistConstraint(tableName = "sm_store_label")
    private Long storeLabelId;*/

    @ApiModelProperty(value = "店铺标签id(sm_store_label主键)")
    @NeedExistConstraint(tableName = "sm_store_label")
    private Long storeLabelId;

    @ApiModelProperty(value = "店铺分类id（sm_store_category主键）")
    @NeedExistConstraint(tableName = "sm_store_category")
    private Long storeCategoryId;

    @ApiModelProperty(value = "商家类型（推广店铺，商品店铺）")
    @NotNull(message = "商家类型不能为空")
    @EnumConstraint(target = StoreTypeEnum.class)
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
    @NotEmpty
    @NeedExistConstraint(tableName = "pm_goods_attribute")
    private Long[] attributeIds;

    @ApiModelProperty(value = "绑定店铺关系")
    private List<StoreRelStoreDto> storeRelStoreDtoList;
}
