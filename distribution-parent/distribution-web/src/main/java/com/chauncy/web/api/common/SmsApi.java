package com.chauncy.web.api.common;

import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhangrt
 * @Date 2019/7/1 16:41
 **/
@RestController
@RequestMapping(value = "/common/sms", method = {RequestMethod.POST, RequestMethod.GET})
@PropertySource("classpath:config/sms.properties")
@Api(tags = "发送短信验证码")
public class SmsApi extends BaseApi {



}
