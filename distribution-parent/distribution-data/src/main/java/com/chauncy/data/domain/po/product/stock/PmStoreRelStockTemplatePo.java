package com.chauncy.data.domain.po.product.stock;

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
 * 店铺-商品虚拟库存模板关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_store_rel_stock_template")
@ApiModel(value = "PmStoreRelStockTemplatePo对象", description = "店铺-商品虚拟库存模板关联表")
public class PmStoreRelStockTemplatePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺-商品虚拟库存模板关联表")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "库存模板id")
    private Long stockTemplateId;

    @ApiModelProperty(value = "商品所属店铺id")
    private Long storeId;

    @ApiModelProperty(value = "分配店铺id")
    private Long distributeStoreId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    private Boolean delFlag;


}
