package com.chauncy.data.domain.po.area;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 物流公司表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("area_shop_logistics")
@ApiModel(value = "AreaShopLogisticsPo对象", description = "物流公司表")
public class AreaShopLogisticsPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "物流公司名称")
    private String logiName;

    @ApiModelProperty(value = "物流公司编码")
    private String logiCode;

    @ApiModelProperty(value = "排序 越小越靠前")
    private Integer sort;


}
