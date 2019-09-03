package com.chauncy.data.vo.manage.store;

import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.vo.manage.product.PmGoodsBrandVo;
import com.chauncy.data.vo.manage.store.label.SmStoreLabelVo;
import com.chauncy.data.vo.manage.store.rel.SmRelStoreVo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author yeJH
 * @since 2019/6/19 22:44
 */
@Data
@ApiModel(value = "StoreBaseInfoVo对象", description  = "店铺账户信息")
public class StoreBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

    @ApiModelProperty(value = "店铺账号")
    private String userName;

    @ApiModelProperty(value = "是否展示在前端 0 不展示 1 展示")
    private boolean showStatus;

    @ApiModelProperty(value = "店铺标签")
    private List<SmStoreLabelVo> smStoreLabelVoList;

    @ApiModelProperty(value = "店铺分类id（sm_store_category主键）")
    private Long storeCategoryId;

    @ApiModelProperty(value = "店铺分类名称")
    private String storeCategoryName;

    @ApiModelProperty(value = "商家类型（推广店铺，商品店铺）")
    private Integer type;

    @ApiModelProperty(value = "商家类型名称（推广店铺，商品店铺）")
    private String typeName;

    @ApiModelProperty(value = "所属店铺Id")
    private Long parentId;

    @ApiModelProperty(value = "排序数值")
    private BigDecimal sort;

    @ApiModelProperty(value = "主理人姓名")
    private String ownerName;

    @ApiModelProperty(value = "主理人联系电话")
    private String ownerMobile;

    @ApiModelProperty(value = "店铺logo")
    private String logoImage;

    @ApiModelProperty(value = "店铺缩略图，展示用")
    private String storeImage;

    @ApiModelProperty(value = "店铺背景图")
    private String backgroundImage;

    @ApiModelProperty(value = "店铺综合体验评分")
    private Integer totalScore;

    @ApiModelProperty(value = "宝贝描述评分")
    private Integer babyDescribeScore;

    @ApiModelProperty(value = "服务态度评分")
    private Integer serviceAttitudeScore;

    @ApiModelProperty(value = "物流服务评分")

    private Integer logisticsServiceScore;

    @ApiModelProperty(value = "所属品牌集合")
    private List<PmGoodsBrandVo> pmGoodsBrandVoList;

    @ApiModelProperty(value = "绑定店铺列表")
    private List<SmRelStoreVo> smRelStoreVoList;
}
