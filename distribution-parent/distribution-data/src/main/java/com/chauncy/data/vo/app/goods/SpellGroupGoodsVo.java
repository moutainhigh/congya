package com.chauncy.data.vo.app.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/2 21:42
 */
@Data
@ApiModel(description = "拼团活动商品信息")
public class SpellGroupGoodsVo  extends GoodsBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "团长头像")
    private String headPortrait;
    

}