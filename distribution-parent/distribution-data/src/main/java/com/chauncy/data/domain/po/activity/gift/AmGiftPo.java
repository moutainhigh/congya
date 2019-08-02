package com.chauncy.data.domain.po.activity.gift;

import java.math.BigDecimal;

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
 * 礼包表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_gift")
@ApiModel(value = "AmGiftPo对象", description = "礼包表")
public class AmGiftPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "礼包类型 1-充值礼包 2-赠送礼包")
    private Integer type;

    @ApiModelProperty(value = "是否启用 1-启用，0-禁用")
    private Boolean enable;

    @ApiModelProperty(value = "礼包名称")
    private String name;

    @ApiModelProperty(value = "经验值")
    private Integer experience;

    @ApiModelProperty(value = "购物券")
    private Integer vouchers;

    @ApiModelProperty(value = "积分")
    private Integer integrals;

    @ApiModelProperty(value = "购买金额")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "图文详情")
    private String detailHtml;


}
