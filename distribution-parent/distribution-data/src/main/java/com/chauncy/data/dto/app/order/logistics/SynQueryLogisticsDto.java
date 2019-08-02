package com.chauncy.data.dto.app.order.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;

/**
 * @Author cheng
 * @create 2019-08-01 17:51
 *customer=89dfcabd892kdjmkg43opui
 *     sign=FOI09kDFNBTRGKD0PUYDS
 *     param={
 *         "com":"ems",
 *         "num":"em263999513jp",
 *         "phone":"13868688888",
 *         "from":"广东省深圳市南山区",
 *         "to":"北京市朝阳区",
 *         "key":"XXX ",
 *         "resultv2":"1"
 *     }
 */
@Data
@ApiModel(description = "快递100订阅请求参数")
public class SynQueryLogisticsDto {

    @ApiModelProperty(value = "贵司的查询公司编号",hidden = true)
    private String customer;

    @ApiModelProperty(value = "签名， 用于验证身份， 按param + key + customer 的顺序进行MD5加密（注意加密后字符串要转大写）",hidden = true)
    private String sign;

    @ApiModelProperty(value = "实时请求参数param")
    private SynQueryParamDto param;

}
