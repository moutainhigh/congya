package com.chauncy.data.vo.app.advice.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.app.advice.goods.StarGoodsVo;
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
 * @since 2019/9/6 12:26
 */
@Data
@ApiModel(description = "店铺首页信息")
@Accessors(chain = true)
public class StoreHomePageVo extends AppStoreBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

    @ApiModelProperty(value = "收藏量/粉丝数")
    private Integer collectionNum;

    @ApiModelProperty(value = "点赞数")
    private Integer likedSum;

    @ApiModelProperty(value = "店铺背景图")
    private String backgroundImage;

    @ApiModelProperty(value = "明星单品商品图片")
    private List<StarGoodsVo>  starGoodsVoList;

}