package com.chauncy.data.vo.app.activity.seckill;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/8 10:50
 */
@Data
@ApiModel(description = "秒杀时间段")
public class SeckillTimeQuantumVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "秒杀活动具体时间点")
    private LocalDateTime activityTime;

    @ApiModelProperty(value = "秒杀活动时分")
    private String seckillTime;

    @ApiModelProperty(value = "秒杀活动状态")
    private String SpellGroupStatus;

}
