package com.chauncy.data.domain.po.order;

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
 * 物流信息表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_order_logistics")
@ApiModel(value = "OmOrderLogisticsPo对象", description = "物流信息表")
public class OmOrderLogisticsPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "运单号 ")
    private String logisticsNo;

    @ApiModelProperty(value = "物流公司编码")
    private String logiCode;

    @ApiModelProperty(value = "物流公司名称")
    private String logiName;

    @ApiModelProperty(value = "快递单当前签收状态: 0在途，1揽收，2疑难，3签收，4退签，5派件，6退回")
    private String status;

    @ApiModelProperty(value = "物流信息")
    private String data;

    @ApiModelProperty(value = "是否签收标记")
    private String isCheck;

    @ApiModelProperty(value = "订单号")
    private Long orderId;


}
