package com.chauncy.data.domain.po.order.report;

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
 * 报表商品快照关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_report_rel_goods_temp")
@ApiModel(value = "OmReportRelGoodsTempPo对象", description = "报表商品快照关联表")
public class OmReportRelGoodsTempPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "报表id")
    private Long reportId;

    @ApiModelProperty(value = "直属店铺id")
    private Long storeId;

    @ApiModelProperty(value = "分配店铺id")
    private Long branchId;

    @ApiModelProperty(value = "商品快照id")
    private Long goodsTempId;

    @ApiModelProperty(value = "分配价格")
    private BigDecimal distributePrice;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "小计")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "是否上级店铺的报表关联，扣除虚拟库存的店铺有报表，库存关联的上级店铺也有对应的报表，" +
            "false:branchId（分配店铺）的报表关联，true:上级店铺的报表关联")
    private Boolean isParentStore;

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


}
