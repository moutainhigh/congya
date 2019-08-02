package com.chauncy.data.domain.po.pay;

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
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 'distribution.activity_view' is not BASE TABLE
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-29
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pay_user_relation")
@ApiModel(value = "PayUserRelationPo对象", description = "下单时需要返佣的用户信息")
public class PayUserRelationPo implements Serializable {

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

    @ApiModelProperty(value = "支付单id")
    private Long payId;

    @ApiModelProperty(value = "上两级用户id")
    private Long lastTwoUserId;

    @ApiModelProperty(value = "直接上级用户id")
    private Long lastOneUserId;

    @ApiModelProperty(value = "下一级用户id")
    private Long nextUserId;

    @ApiModelProperty(value = "返佣最高等级userid")
    private Long firstUserId;

    @ApiModelProperty(value = "返佣第二等级userid")
    private Long secondUserId;


}
