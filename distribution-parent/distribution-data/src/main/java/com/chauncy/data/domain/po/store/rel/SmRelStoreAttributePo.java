package com.chauncy.data.domain.po.store.rel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sm_rel_store_attribute")
@ApiModel(value = "SmRelStoreAttributePo对象", description = "店铺品牌关联表")
public class SmRelStoreAttributePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺-品牌关联表")
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "店铺id(sm_store)")
    private Long storeId;

    @ApiModelProperty(value = "品牌id(pm_goods_attribute type=7)")
    private Long attributeId;

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
    private Boolean delFlag;
}
