/*
package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

*/
/**
 * <p>
 * 商品和会员等级关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-16
 *//*

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_goods_rel_goods_member_level")
@ApiModel(value = "PmGoodsRelGoodsMemberLevelPo对象", description = "商品和会员等级关联表")
public class PmGoodsRelGoodsMemberLevelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long value;

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

    @ApiModelProperty(value = "商品ID")
    @NotNull(content = "商品ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsGoodId;

    @ApiModelProperty(value = "会员等级ID")
    @NotNull(content = "会员等级ID不能为空")
    @NeedExistConstraint(tableName = "pm_member_level")
    private Long memberLevelId;


}
*/
