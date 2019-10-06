package com.chauncy.data.vo.app.goods;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/2 21:42
 */
@Data
@ApiModel(description = "拼团活动商品信息")
public class SpellGroupGoodsVo  extends GoodsBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "团长头像")
    private List<String> headPortrait;

    @ApiModelProperty(value = "已拼数量/已支付数量")
    private Integer payedNum;

    @ApiModelProperty(value = "拼团ids字符串，获取团长头像")
    @JsonIgnore
    @JSONField(serialize=false)
    private String mainIds;


}