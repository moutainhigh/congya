package com.chauncy.data.vo.app.advice.activity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/26 16:26
 */
@Data
@ApiModel(description = "首页限时秒杀，积分抵现，囤货鸭，拼团鸭")
public class HomePageActivityVo   implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "限时秒杀")
    @JSONField(ordinal = 1)
    private ArrayList<HomePageActivityGoodsVo> secKillGoods;

    @ApiModelProperty(value = "积分抵现 ")
    @JSONField(ordinal = 2)
    private ArrayList<HomePageActivityGoodsVo> integralsGoods;

    @ApiModelProperty(value = "囤货鸭")
    @JSONField(ordinal = 3)
    private ArrayList<HomePageActivityGoodsVo> reducedGoods;

    @ApiModelProperty(value = "拼团鸭")
    @JSONField(ordinal = 4)
    private ArrayList<HomePageActivityGoodsVo> spellGroupGoods;

}