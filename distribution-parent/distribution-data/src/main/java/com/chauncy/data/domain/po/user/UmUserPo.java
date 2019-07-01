package com.chauncy.data.domain.po.user;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 前端用户
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("um_user")
@ApiModel(value = "UmUserPo对象", description = "前端用户")
public class UmUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "前端用户id")
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

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "0-女  1-男")
    private Boolean sex;

    @ApiModelProperty(value = "邀请码")
    private Long inviteCode;

    @ApiModelProperty(value = "节点用户")
    private Long parentId;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "佣金判断资格 0-关闭  1-开始")
    private Boolean commissionStatus;

    @ApiModelProperty(value = "1-使用  0-禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "登录次数")
    private Integer loginTimes;

    @ApiModelProperty(value = "最近登录时间")
    private LocalDateTime recentLoginTime;

    @ApiModelProperty(value = "当前经验值")
    private Integer currentExperience;

    @ApiModelProperty(value = "当前红包")
    private BigDecimal currentRedEnvelops;

    @ApiModelProperty(value = "当前积分")
    private Integer currentIntegral;

    @ApiModelProperty(value = "当前购物券")
    private BigDecimal currentShopTicket;

    @ApiModelProperty(value = "总消费金额")
    private BigDecimal totalConsumeMoney;

    @ApiModelProperty(value = "邀请人数")
    private Integer invitePeopleNum;

    @ApiModelProperty(value = "累计获得红包")
    private BigDecimal totalRedEnvelops;

    @ApiModelProperty(value = "累计获得积分")
    private Integer totalIntegral;

    @ApiModelProperty(value = "累计获得购物券")
    private BigDecimal totalShopTicket;

    @ApiModelProperty(value = "总订单数")
    private Integer totalOrder;

    @ApiModelProperty(value = "会员等级")
    private Long memberLevelId;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "密码")
    private String password;


}
