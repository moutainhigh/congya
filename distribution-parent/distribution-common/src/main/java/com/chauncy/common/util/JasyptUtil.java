package com.chauncy.common.util;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * @Author huangwancheng
 * @create 2019-05-29 14:38
 *
 * 加密工具类
 */
@Slf4j
public class JasyptUtil {


    /**
     * Jasypt生成加密结果
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value 待加密值
     * @return
     */
    public static String encyptPwd(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.encrypt(value);
        return result;
    }

    /**
     * 解密
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value 待解密密文
     * @return
     */
    public static String decyptPwd(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.decrypt(value);
        return result;
    }

    public static SimpleStringPBEConfig cryptor(String password){
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    public static void main(String[] args){

        //加密
        System.out.println(encyptPwd("congya2019","congya@2019"));
        System.out.println(encyptPwd("congya2019","congya"));
        System.out.println(encyptPwd("congya2019","admins"));
        //解密
        System.out.println(decyptPwd("congya2019","I/RglyglQQM961R19e0FFw=="));

        StandardPBEStringEncryptor standardPBEStringEncryptor =new StandardPBEStringEncryptor();

        /*配置文件中配置如下的算法*/
        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        /*配置文件中配置的password*/
        standardPBEStringEncryptor.setPassword("EWRREWRERWECCCXC");
        /*要加密的文本*/
        String name = standardPBEStringEncryptor.encrypt("root");
        String password =standardPBEStringEncryptor.encrypt("123456");
        /*将加密的文本写到配置文件中*/
        System.out.println("name="+name);
        System.out.println("password="+password);
    }
}
