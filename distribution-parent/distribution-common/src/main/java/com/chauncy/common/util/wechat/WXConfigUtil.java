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

    @Value("${distribution.wxpay.CUSTOMS}")
    public String CUSTOMS;

    @Value("${distribution.wxpay.MCH_CUSTOMS_NO}")
    public String MCH_CUSTOMS_NO;

    public WXConfigUtil() throws Exception {
        //判断项目服务环境
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        String certPath;
        if(isWindows) {
            //Windows环境
            certPath = "D:" + File.separator + "cert" + File.separator + "BoHUI20190802_apiclient_cert.p12";
        } else {
            certPath = "congya" + File.separator + "cert" + File.separator + "BoHUI20190802_apiclient_cert.p12";
        }
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

    public String getCustoms() {
        return CUSTOMS;
    }

    public String getMchCustomsNo() {
        return MCH_CUSTOMS_NO;
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


