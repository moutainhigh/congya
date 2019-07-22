package com.chauncy.data.domain.po.activity.gift;

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
 * 新人礼包和用户关联表，礼包-用户：多对一关系
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_gift_rel_gift_user")
@ApiModel(value = "AmGiftRelGiftUserPo对象", description = "新人礼包和用户关联表，礼包-用户：多对一关系")
public class AmGiftRelGiftUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "领取时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "新人礼包ID")
    private Long giftId;


}
