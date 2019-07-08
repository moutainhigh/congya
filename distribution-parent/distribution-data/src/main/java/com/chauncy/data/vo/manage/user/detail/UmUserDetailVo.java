package com.chauncy.data.vo.manage.user.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/7 12:09
 **/

@Data
@Accessors(chain = true)
@ApiModel(description = "用户详情vo")
public class UmUserDetailVo {

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "头衔名称")
    private String actor;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "1-使用  0-禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "用户标签")
    private String labelNames;

    @ApiModelProperty(value = "0-女  1-男")
    private Boolean sex;

    @ApiModelProperty(value = "登录次数")
    private Integer loginTimes;

    @ApiModelProperty(value = "邀请码")
    private Long inviteCode;

    @ApiModelProperty(value = "注册时间")
    private LocalDateTime createTime;

    //数据库先查出id再去组装name   别连表
    @ApiModelProperty(value = "节点用户")
    private String parentName;

    @ApiModelProperty(value = "最近登录时间")
    private LocalDateTime recentLoginTime;

    @ApiModelProperty(value = "归属店铺id")
    private Long storeId;

    @ApiModelProperty(value = "归属店铺名称")
    private String storeName;

    @ApiModelProperty(value = "佣金判断资格 0-关闭  1-开始")
    private Boolean commissionStatus;

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

    @ApiModelProperty(value = "下个等级经验值")
    private Integer nextLevelExperience;

    @ApiModelProperty(value = "累计获得红包")
    private BigDecimal totalRedEnvelops;

    @ApiModelProperty(value = "累计获得积分")
    private Integer totalIntegral;

    @ApiModelProperty(value = "累计获得购物券")
    private BigDecimal totalShopTicket;

    @ApiModelProperty(value = "总订单数")
    private Integer totalOrder;
}
