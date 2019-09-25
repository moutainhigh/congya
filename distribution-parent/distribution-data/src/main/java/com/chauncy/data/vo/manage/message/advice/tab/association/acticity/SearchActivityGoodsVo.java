package com.chauncy.data.vo.manage.message.advice.tab.association.acticity;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.app.advice.AssociationTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-09-15 17:06
 *
 * 获取积分/满减/秒杀/拼团商品
 */
@Data
@ApiModel(description = "获取积分/满减/秒杀/拼团商品")
@Accessors(chain = true)
public class SearchActivityGoodsVo {

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty(value = "第三级分类ID",hidden = true)
    @JSONField(serialize = false)
    private Long goodsCategoryId;

    @ApiModelProperty("所属类目")
    private String category;

    @ApiModelProperty("所属活动分组名称")
    private String activityGroupName;

    @ApiModelProperty("所属活动名称")
    private String activityName;

    @ApiModelProperty("活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityStartTime;

    @ApiModelProperty("活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndTime;
}
