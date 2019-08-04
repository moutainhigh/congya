package com.chauncy.common.util.wechat;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author yeJH
 * @since 2019/7/5 16:26
 */
public class WXConfigUtil implements WXPayConfig {
    private byte[] certData;
    public static final String APP_ID = "wx42bfe19a00e22417";
    public static final String KEY = "BoHUI2028CongYa8BoHUI2028CongYa8";
    public static final String MCH_ID = "1498771872";
    public static final String BODY = "葱鸭百货";

    public WXConfigUtil() throws Exception {
        //从微信商户平台下载的安全证书存放的路径
        String certPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                .concat("cert/BoHUI20190802_apiclient_cert.p12");
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
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


