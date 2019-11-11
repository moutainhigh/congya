package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户商品转发表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_goods_forward")
@ApiModel(value = "PmGoodsForwardPo对象", description = "用户商品转发表")
public class PmGoodsForwardPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "goods_id")
    private Long goodsId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "删除标志")
    @TableLogic
    private Boolean delFlag;


}
