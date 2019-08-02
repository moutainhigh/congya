package com.chauncy.data.vo.app.store;

import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/15 11:18
 */
@Data
@ApiModel(value = "StorePagingVo", description =  "APP店铺列表分页查询结果")
public class StorePagingVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "value")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺logo")
    private String logoImage;

    @ApiModelProperty(value = "店铺背景图")
    private String backgroundImage;

    @ApiModelProperty(value = "收藏量")
    private Integer collectionNum;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

    @ApiModelProperty(value = "展示商品列表")
    private List<GoodsBaseInfoVo> goodsBaseInfoVoList;

}
