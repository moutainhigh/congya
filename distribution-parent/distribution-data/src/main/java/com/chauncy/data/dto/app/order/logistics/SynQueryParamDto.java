package com.chauncy.data.dto.app.order.logistics;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author cheng
 * @create 2019-08-02 17:27
 */
@Data
public class SynQueryParamDto {

    @ApiModelProperty("快递公司编码")
    @NotBlank(message = "快递公司编码不能为空！！")
    @JSONField(ordinal = 0)
    private String com;

    @ApiModelProperty("快递单号")
    @NotBlank(message = "快递单号不能为空！")
    @JSONField(ordinal = 1)
    private String num;

    @ApiModelProperty(value = "出发地城市",hidden = true)
    @JSONField(ordinal = 3)
    private String from;

    @ApiModelProperty(value = "目的地城市",hidden = true)
    @JSONField(ordinal = 4)
    private String to;

//    @ApiModelProperty(value = "企业授权key",hidden = true)
//    @JSONField(serialize = false)
//    private String key;

    @ApiModelProperty(value = "行政区域解析 0-关闭；1-开通",hidden = true)
    @JSONField(ordinal = 5)
    private int resultv2 = 1;

    @ApiModelProperty(value = "顺丰单号必填，也可以填写后四位，如果是固话，请不要上传分机号）")
    @JSONField(ordinal = 2)
    private String phone;
}
