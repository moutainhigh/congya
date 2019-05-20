package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author huangwancheng
 * @create 2019-05-19 22:12
 *
 * 商品属性信息
 *
 * 服务说明管理(平台、商家)
 * 活动说明管理(平台)
 * 标签管理(平台）
 * 购买须知管理(平台)
 * 规格管理(平台)
 * 商品参数管理(平台)
 * 品牌管理(平台)
 *
 */
@Data
@TableName(value = "_goods_attribute")
public class PmGoodsAttributePo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否启用")
    private boolean enabled;

    @ApiModelProperty(value = "服务说明类型：1->平台服务说明。2->商家服务说明")
    private int serviceType;

    @ApiModelProperty(value = "类型：1->服务说明管理类型 2->活动说明管理类型 3->商品参数管理类型 4->标签管理类型 5->购买须知管理类型 6->规格管理类型 7->品牌管理")
    private int type;

    @ApiModelProperty(value = "副标题")
    private boolean subtitle;

    @ApiModelProperty(value = "logo图片")
    private boolean logoImage;

    @ApiModelProperty(value = "logo缩略图")
    private boolean logoIcon;

}
