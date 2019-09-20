package com.chauncy.data.dto.manage.message.interact.add;

import com.chauncy.common.enums.message.PushObjectEnum;
import com.chauncy.common.enums.message.PushTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zhangrt
 * @create 2019-07-08 18:03
 *
 * 添加短信推送消息
 */
@Data
@ApiModel(description = "添加短信推送消息Dto")
@Accessors(chain = true)
public class AddSmsMessageDto {


    @ApiModelProperty(value = "消息标题")
    @NotBlank(message = "消息标题不能为空")
    private String title;

    @ApiModelProperty(value = "短信模板code")
    @NotBlank(message = "短信模板code不能为空")
    private String templateCode;

    @ApiModelProperty(value = "消息内容")
    @NotBlank(message = "消息内容")
    private String content;

    @ApiModelProperty(value = "推送对象类型,传数组 1、全部用户 2、指定用户 3、指定会员等级")
    @NotNull(message = "推送对象类型不能为空")
    private Integer pushObject;

    @ApiModelProperty(value = "全部用户：该字段不用传；指定用户：如果是指定用户，需要传指定的用户的手机号码；" +
            "指定会员等级：需要传指定会员等级的id")
    private List<String> objectIds;

}
