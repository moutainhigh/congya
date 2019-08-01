package com.chauncy.data.vo.app.order.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-01 17:27
 *
 * 根据提供的订阅回调地址将物流轨迹信息回调到您的接口，回调请求使用http的post方式，回调参数名是param，订阅方接收到回调参数后请及时响应，业务处理逻辑尽量异步处理。
 * <p>
 * 订阅请求返回值
 * <p>
 * {
 * "result":true,     true表示成功，false表示失败。如果提交回调接口的地址失败，30分钟后重新回调，3次仍旧失败的，自动放弃
 * "returnCode":"200",  200: 接收成功   500: 服务器错误
 * "message":"接收成功"  返回的提示
 * }
 */
@Data
@ApiModel(description = "订阅物流信息返回的信息")
public class NoticeResponseVo {

    @ApiModelProperty("true表示成功，false表示失败")
    private Boolean result;

    @ApiModelProperty("返回的状态码")
    private String returnCode;

    @ApiModelProperty("返回的提示")
    private String message;
}

