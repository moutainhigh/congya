package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-19 23:26
 *
 * 类目表
 *
 */
@Data
@TableName(value = "tb_goods_category")
public class GoodsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "是否启用：0->不是；1->是")
    private boolean enabled;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "分类缩略图")
    private String icon;

    @ApiModelProperty(value = "排序数字")
    private Integer sort;

    @ApiModelProperty(value = "税率")
    private double taxRate;

    @ApiModelProperty(value = "父分类ID")
    private int parentId;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", sort=").append(sort);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }



}
