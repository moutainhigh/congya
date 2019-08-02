package com.chauncy.data.dto.manage.message.interact.add;

import com.baomidou.mybatisplus.annotation.TableField;
import com.chauncy.common.enums.message.PushObjectEnum;
import com.chauncy.common.enums.message.PushTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-08 18:03
 *
 * 添加推送消息
 */
@Data
@ApiModel(description = "添加推送消息Dto")
@Accessors(chain = true)
public class AddPushMessageDto {

    @ApiModelProperty(value = "推送方式,传中文 1、通知栏推送 2、app内消息中心推送")
    @EnumConstraint(target = PushTypeEnum.class)
    @NotNull(message = "推送方式不能为空")
    private String pushType;

    @ApiModelProperty(value = "消息标题")
    @NotNull(message = "消息标题不能为空")
    private String title;

    @ApiModelProperty(value = "图文详情")
    private String detailHtml;

    @ApiModelProperty(value = "推送对象类型,传中文 1、全部用户 2、指定用户 3、指定会员等级")
    @NotNull(message = "推送对象类型不能为空")
    @EnumConstraint(target = PushObjectEnum.class)
    private String objectType;

    @ApiModelProperty(value = "指定的对象ID")
    private List<Long> objectIds;

}
