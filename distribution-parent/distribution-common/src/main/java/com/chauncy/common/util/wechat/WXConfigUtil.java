package com.chauncy.common.util.wechat;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author yeJH
 * @since 2019/7/5 16:26
 */
@Slf4j
@Component
public class WXConfigUtil implements WXPayConfig {
    private byte[] certData;

    @Value("${distribution.wxpay.APP_ID}")
    public String APP_ID;

    @Value("${distribution.wxpay.KEY}")
    public String KEY;

    @Value("${distribution.wxpay.MCH_ID}")
    public String MCH_ID;

    @Value("${distribution.wxpay.BODY}")
    public String BODY;

    public WXConfigUtil() throws Exception {
        //从微信商户平台下载的安全证书存放的路径
       /* String certPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                .concat("cert/BoHUI20190802_apiclient_cert.p12");
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();*/
    }

    @Override
    public String getAppID() {
        return APP_ID;
    }

    @Override
    public String getMchID() {
        return MCH_ID;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    public String getBody() {
        return BODY;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}


