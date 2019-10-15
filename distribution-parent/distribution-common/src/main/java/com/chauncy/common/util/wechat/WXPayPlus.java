package com.chauncy.common.util.wechat;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;

import java.util.Map;

/**
 * @author yeJH
 * @since 2019/10/10 20:38
 */
public class WXPayPlus extends WXPay {

    private WXPayConfig config;
    private WXPayConstants.SignType signType;
    private boolean useSandbox;

    public WXPayPlus(WXPayConfig config) {
            super(config);
            this.config = config;
            this.signType = WXPayConstants.SignType.MD5;
            this.useSandbox = false;
    }

    public Map<String, String> customDeclareOrder(Map<String, String> reqData) throws Exception {
        return this.customDeclareOrder(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> customDeclareOrder(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url = "https://api.mch.weixin.qq.com/cgi-bin/mch/customs/customdeclareorder";

        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> fillRequestData(Map<String, String> reqData) throws Exception {
        reqData.put("appid", this.config.getAppID());
        reqData.put("mch_id", this.config.getMchID());
        //reqData.put("nonce_str", WXPayUtil.generateNonceStr());
        if (WXPayConstants.SignType.MD5.equals(this.signType)) {
            reqData.put("sign_type", "MD5");
        } else if (WXPayConstants.SignType.HMACSHA256.equals(this.signType)) {
            reqData.put("sign_type", "HMAC-SHA256");
        }

        reqData.put("sign", WXPayUtil.generateSignature(reqData, this.config.getKey(), this.signType));
        return reqData;
    }

}
