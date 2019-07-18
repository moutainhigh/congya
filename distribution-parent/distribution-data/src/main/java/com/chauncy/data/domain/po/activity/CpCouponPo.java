package com.chauncy.data.domain.po.activity;

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
 * 优惠券
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cp_coupon")
@ApiModel(value = "CpCouponPo对象", description = "优惠券")
public class CpCouponPo implements Serializable {

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

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "可发放总数")
    private Integer totalNum;

    @ApiModelProperty(value = "每人限领数量")
    private Integer everyLimitNum;

    @ApiModelProperty(value = "限定会员等级id")
    private Long levelId;

    @ApiModelProperty(value = "有效天数")
    private Integer effectiveDay;

    @ApiModelProperty(value = "优惠形式 0-满减 1-固定折扣 2-包邮")
    private Integer type;

    @ApiModelProperty(value = "指定范围 0-所有商品 1-指定分类 2-指定商品")
    private Integer scope;


}