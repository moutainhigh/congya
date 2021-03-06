package com.chauncy.data.vo.app.activity.spell;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/6 18:00
 */
@Data
@ApiModel(description = "拼团活动团信息")
public class SpellGroupInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "团id")
    private Long mainId;

    @ApiModelProperty(value = "团长/团员头像")
    @JSONField(serialize = false)
    private String headPortrait;

    @ApiModelProperty(value = "团长/团员头像")
    private List<String> headPortraitList;

    @ApiModelProperty(value = "团长姓名")
    private String userName;

    @ApiModelProperty(value = "还差几人拼成")
    private Integer margin;

    @ApiModelProperty(value = "拼团结束时间  毫秒级时间戳")
    private Long endTime;

    @ApiModelProperty(value = "拼团结束时间")
    @JSONField(serialize=false)
    private LocalDateTime expireTime;

}