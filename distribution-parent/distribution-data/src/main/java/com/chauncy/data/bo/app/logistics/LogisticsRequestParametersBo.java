package com.chauncy.data.bo.app.logistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-02 16:38
 */
@Data
public class LogisticsRequestParametersBo {

    @ApiModelProperty(value = "加密串",hidden = true)
    private String salt;

    @ApiModelProperty(value = "单号智能识别",hidden = true)
    private int autoCom;

    @ApiModelProperty(value = "开启国际版",hidden = true)
    private int interCom;

    @ApiModelProperty(value = "出发国",hidden = true)
    private String departureCountry;

    @ApiModelProperty(value = "出发国快递公司编码",hidden = true)
    private String departureCom;

    @ApiModelProperty(value = "目的国",hidden = true)
    private String destinationCountry;

    @ApiModelProperty(value = "目的国快递公司编码",hidden = true)
    private String destinationCom;

    @ApiModelProperty(value = "行政区域解析",hidden = true)
    private int resultv2;

    @ApiModelProperty(value = "回调接口",hidden = true)
    private String callbackurl;

    @ApiModelProperty(value = "顺丰单号必填，也可以填写后四位，如果是固话，请不要上传分机号）")
    private String phone;
}
/**
 * "parameters":{
 *             "callbackurl":"您的回调接口的地址，如http://www.您的域名.com/kuaidi?callbackid=...",
 *             "salt":"XXXXXXXXXX",
 *             "resultv2":"1",
 *             "autoCom":"1",
 *             "interCom"："1",
 *             "departureCountry":"CN",
 *             "departureCom":"ems",
 *             "destinationCountry":"JP",
 *             "destinationCom":"japanposten"
 *         }
 */
