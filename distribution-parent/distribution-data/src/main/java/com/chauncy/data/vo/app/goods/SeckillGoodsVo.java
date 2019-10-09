package com.chauncy.data.vo.app.goods;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/8 20:17
 */
@Data
@ApiModel(description = "秒杀活动商品信息")
public class SeckillGoodsVo  extends GoodsBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品副标题")
    private String subtitle;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "活动总库存")
    private Integer activityStock;

    @ApiModelProperty(value = "剩余库存")
    private Integer remainingStock;

    @ApiModelProperty(value = "已售数量")
    private Integer salesVolume;

    @ApiModelProperty(value = "销售百分比")
    private Integer salePercentage;

    @ApiModelProperty(value = "是否即将售罄")
    private Boolean isSellOut;

    @ApiModelProperty(value = "秒杀活动是否开始")
    private Boolean isStart;

    @ApiModelProperty(value = "秒杀结束时间，秒级时间戳，已抢购或抢购中的活动才展示")
    private Long endTime;

    @ApiModelProperty(value = "秒杀结束时间")
    @JSONField(serialize=false)
    private LocalDateTime activityEndTime;

}