package com.chauncy.data.dto.app.order.logistics;

import com.chauncy.common.constant.logistics.LogisticsContantsConfig;
import com.chauncy.data.bo.app.logistics.LogisticsRequestParametersBo;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * @Author cheng
 * @create 2019-08-01 17:51
 *
 * 订阅请求参数
 * schema= json
 *  param ={
 *  company				快递公司编码
 *  number				快递单号
 *  from					出发地城市
 *  to					目的地城市
 *  parameters:{
 *  callbackurl			回调地址URL
 *  salt					加密签名字符串
 *  resultv2				行政区域解析
 *  autoCom				智能判断单号归属快递公司
 *  interCom				开启国际版
 *  departureCountry		出发国编码
 *  departureCom			出发国快递公司编码
 *  destinationCountry	目的国编码
 *  destinationCom		目的国快递公司编码
 *  phone	             手机号
 *  }
 *  }
 * @return
 *
 * schema= json
 *     param={
 *         "company":"ems",
 *         "number":"em263999513jp",
 *         "from":"广东省深圳市南山区",
 *         "to":"北京市朝阳区",
 *         "key":"XXX ",
 *         "parameters":{
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
 *     }
 */
@Data
@ApiModel(description = "快递100订阅请求参数")
public class TaskRequestDto {

    @ApiModelProperty("快递公司编码")
    private String company;

    @ApiModelProperty("快递单号")
    private String number;

    @ApiModelProperty("订单编号")
    @NotNull(message = "订单id不能为空")
    @NeedExistConstraint(tableName = "om_order",message = "该订单数据库中不存在！")
    private Long orderId;

    @ApiModelProperty(value = "出发地城市",hidden = true)
    private String from;

    @ApiModelProperty(value = "目的地城市",hidden = true)
    private String to;

    @ApiModelProperty(value = "企业授权key",hidden = true)
    private String key;

    @ApiModelProperty(value = "parameters")
    private LogisticsRequestParametersBo parameters;

//    @ApiModelProperty(value = "parameter",hidden = true)
//    private HashMap<String, String> parameters = new HashMap<String, String>();

}
