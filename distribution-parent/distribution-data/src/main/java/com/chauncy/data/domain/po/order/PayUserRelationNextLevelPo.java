package com.chauncy.data.domain.po.order;

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
 * 返积分、经验值用户关系链，下一级用户id
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pay_user_relation_next_level")
@ApiModel(value = "PayUserRelationNextLevelPo对象", description = "返积分、经验值用户关系链，下一级用户id")
public class PayUserRelationNextLevelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "返佣关系id")
    private Long payUserRealtionId;

    @ApiModelProperty(value = "下一级用户id")
    private Long nextUserId;


}
