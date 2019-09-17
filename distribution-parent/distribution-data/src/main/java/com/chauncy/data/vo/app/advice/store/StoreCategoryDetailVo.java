package com.chauncy.data.vo.app.advice.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.store.AppStoreBaseInfoVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/5 18:07
 */
@Data
@ApiModel(description = "广告选项卡详情 店铺+店铺推荐商品列表")
@Accessors(chain = true)
public class StoreCategoryDetailVo extends AppStoreBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

    @ApiModelProperty(value = "店铺背景图")
    private String backgroundImage;

    @ApiModelProperty(value = "店铺推荐商品列表")
    private List<GoodsBaseInfoVo> goodsBaseInfoVoList;


}
