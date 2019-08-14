package com.chauncy.data.domain.po.message.advice;

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
 * 广告基本信息表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_advice")
@ApiModel(value = "MmAdvicePo对象", description = "广告基本信息表")
public class MmAdvicePo implements Serializable {

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

    @ApiModelProperty(value = "广告位置：葱鸭优选-商品；葱鸭百货-商品分类；首页有品-(选项名称+对应的品牌);葱鸭有店-店铺分类+分类下的店铺；首页主题-(选项图片+对应的商品)；首页特卖-(选项名称+对应的商品);其它-(开始时间+结束时间+广告类型+广告类型对应的详情+封面图片);葱鸭百货二级分类-(其它+一级分类)")
    private String location;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "1启用/0禁用，默认为0")
    private Boolean enbaled;


}
