package com.chauncy.data.vo.manage.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.util.serializer.LongJsonDeserializer;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.common.util.serializer.LongToStringSerializer;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-30 17:09
 */
@Data
@ApiModel(value = "PmGoodsAttributePo对象", description = "商品属性参数 ")
public class PmGoodsAttributeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "value")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序数字，此排序是展示在前端服务说明的排序")
    private BigDecimal sort;

    @ApiModelProperty(value = "服务说明内容")
    private String content;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "类型 1->平台服务说明管理类型 2->商家服务说明管理类型 3->平台活动说明管理类型  4->商品参数管理类型 5->标签管理类型 6->购买须知管理类型 7->规格管理类型 8->品牌管理")
    private Integer type;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "logo图片")
    private String logoImage;

    @ApiModelProperty(value = "logo缩略图")
    private String logoIcon;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private boolean delFlag;

//    @ApiModelProperty(value = "商品属性值")
//    private String value;

    @ApiModelProperty(value = "商品属性值")
    private List<String> valueList;


}
