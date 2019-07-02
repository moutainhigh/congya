package com.chauncy.web.api.common;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.CreateVerifyCode;
import com.chauncy.common.util.RedisUtil;
import com.chauncy.common.util.third.SendSms;
import com.chauncy.data.dto.app.common.VerifyCodeDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhangrt
 * @Date 2019/7/1 16:41
 **/
@RestController
@RequestMapping(value = "/common/sms")
@PropertySource("classpath:config/redis.properties")
@Api(tags = "发送短信验证码")
public class SmsApi extends BaseApi {

    @Autowired
    private RedisUtil redisUtil;




    @PostMapping("/send")
    @ApiOperation(value = "发送验证码")
    public JsonViewData send(@Validated @RequestBody VerifyCodeDto verifyCodeDto) {
        //生成4位随机验证码
        String verifyCode = CreateVerifyCode.randomNumber(4);
        String redisKey=String.format(verifyCodeDto.getValidCodeEnum().getRedisKey(),verifyCodeDto.getPhone());
        //5分钟内有效
        redisUtil.set(redisKey,verifyCode,3000);
        SendSms.send(verifyCodeDto.getPhone(), verifyCode,
                verifyCodeDto.getValidCodeEnum().getTemplateCode());
        return setJsonViewData(ResultCode.SUCCESS);
    }


}
