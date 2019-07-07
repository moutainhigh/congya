package com.chauncy.web.controller.pay;

import com.chauncy.common.util.wechat.WxMD5Util;
import com.chauncy.test.service.impl.WXserviceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yeJH
 * @since 2019/7/5 16:14
 */
@Api(tags = "APP_支付管理接口")
@RestController
@RequestMapping("/app/wxPay")
@Slf4j
public class WXController {

    private final static Logger logger = LoggerFactory.getLogger(WXController.class);

    @Autowired
    private WXserviceImpl wxPayService;

    /**
     * 统一下单
     * 官方文档:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @param user_id
     * @param total_fee
     * @return
     * @throws Exception
     */
    @PostMapping("/appPay")
    public Map<String, String> wxPay(@RequestParam(value = "userId") String user_id,
                                     @RequestParam(value = "totalFee") String total_fee
    ) throws Exception {

        String attach = "{\"user_id\":\"" + user_id + "\"}";
        //请求预支付订单
        Map<String, String> result = wxPayService.dounifiedOrder(attach, total_fee);
        Map<String, String> map = new HashMap<>();

        WxMD5Util md5Util = new WxMD5Util();
        //返回APP端的数据
        //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
        //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
        //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
        map.put("appid", result.get("appid"));
        map.put("partnerid", result.get("mch_id"));
        map.put("prepayid", result.get("prepay_id"));
        map.put("package", "Sign=WXPay");
        map.put("noncestr", result.get("nonce_str"));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));//单位为秒
//      这里不要使用请求预支付订单时返回的签名
//      这里不要使用请求预支付订单时返回的签名
//      这里不要使用请求预支付订单时返回的签名
        map.put("sign", md5Util.getSign(map));
        map.put("extdata", attach);
        return map;
    }

    /**
     *   支付异步结果通知，我们在请求预支付订单时传入的地址
     *   官方文档 ：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3
     */
    @RequestMapping(value = "/notify.json", method = {RequestMethod.GET, RequestMethod.POST})
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        String resXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            //将InputStream转换成xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = wxPayService.payBack(resXml);
            return result;
        } catch (Exception e) {
            logger.error("微信手机支付失败:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }
}
