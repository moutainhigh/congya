package com.chauncy.data.vo.manage.user.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/5 23:38
 **/

@ApiModel(description = "用户列表")
@Data
@Accessors(chain = true)
public class UmUserListVo {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("用户名称")
    private String trueName;

    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("用户等级名称")
    private String levelName;

    @ApiModelProperty("总订单数")
    private Integer totalOrder;

    @ApiModelProperty("当前积分")
    private Integer currentIntegral;

    @ApiModelProperty("累计获得红包")
    private BigDecimal totalRedEnvelops;

    @ApiModelProperty("总消费金额")
    private BigDecimal totalConsumeMoney;

    @ApiModelProperty("当前购物券")
    private BigDecimal currentShopTicket;

    @ApiModelProperty("累计获得购物券")
    private BigDecimal totalShopTicket;

    @ApiModelProperty("归属店铺名称")
    private String storeName;

    @ApiModelProperty("节点用户名称")
    private String parent;
    //下级用户就是好友数量
    @ApiModelProperty("好友数量")
    private Integer invitePeopleNum;


    @ApiModelProperty("最近登录时间")
    private LocalDateTime recentLoginTime;


    @ApiModelProperty("状态 0-禁用 1-启用")
    private boolean enabled;


    @ApiModelProperty("佣金判定资格 1-开启  0-关闭")
    private boolean commissionStatus;
}
