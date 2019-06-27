package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 关联商品—包括关联搭配商品合关联推荐商品，外键为商品id
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("pm_association_goods")
@ApiModel(value = "PmAssociationGoodsPo对象", description = "关联商品—包括关联搭配商品合关联推荐商品，外键为商品id")
public class PmAssociationGoodsPo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联商品信息ID")
    @TableId(value = "value",type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "被关联店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "被关联商品ID")
    private Long associatedGoodsId;

    @ApiModelProperty(value = "关联类型 1->搭配 2->推荐")
    private Integer associationType;

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

    public PmAssociationGoodsPo(Long goodsId, Long storeId, Long associatedGoodsId, Integer associationType,String createBy) {
        this.goodsId = goodsId;
        this.storeId = storeId;
        this.associatedGoodsId = associatedGoodsId;
        this.associationType = associationType;
        this.createBy=createBy;
    }
}
