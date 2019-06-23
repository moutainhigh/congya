package com.chauncy.data.dto.manage.good.add;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-13 21:21
 */
@Data
@ApiModel(value = "PmGoodsAttributeDto对象", description = "商品属性参数")
public class GoodAttributeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(required = true,value = "名称或标题")
    @NotNull(message="名称或标题不能为空")
    private String name;

    @ApiModelProperty(value = "排序数字，此排序是展示在前端服务说明的排序")
    private BigDecimal sort;

    @ApiModelProperty(value = "服务说明内容")
    private String content;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "类型 1->平台服务说明 2->商家服务说明 3->平台活动说明 4->商品参数 5->商品标签 6->购买须知说明 7->商品规格 8->品牌管理 9->敏感词")
    @NotNull(message = "类型不能为空")
    @EnumConstraint(target = GoodsAttributeTypeEnum.class)
    private Integer type;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "logo图片")
    private String logoImage;

    @ApiModelProperty(value = "logo缩略图")
    private String logoIcon;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;

}

