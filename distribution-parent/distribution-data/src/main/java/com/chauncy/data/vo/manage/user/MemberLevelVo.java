package com.chauncy.data.vo.manage.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/6/25 17:40
 **/
@Data
@Accessors(chain = true)

public class MemberLevelVo extends PmMemberLevelPo {

    @ApiModelProperty(value = "会员ID")
    private Long id;

    @ApiModelProperty(value = "头衔名称")
    private String actor;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "购物赠送比例")
    private BigDecimal purchasePresent;

    @ApiModelProperty(value = "会员等级经验值")
    private Integer levelExperience;

    @ApiModelProperty(value = "红包赠送比例")
    private BigDecimal packetPresent;



}
