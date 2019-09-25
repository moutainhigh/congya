package com.chauncy.data.vo.app.advice.activity;

import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.data.vo.app.goods.ActivityGoodsVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/24 17:33
 */
@Data
@ApiModel(description = "活动分组选项卡信息")
@Accessors(chain = true)
public class ActivityGroupTabVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选项卡id")
    private Long tabId;

    @ApiModelProperty(value = "选项卡图片")
    private String tabPicture;

    @ApiModelProperty(value = "店铺推荐商品列表")
    private List<ActivityGoodsVo> activityGoodsVoList;

    @ApiModelProperty(value = "轮播图信息")
    private List<ShufflingVo> shufflingVoList;


}