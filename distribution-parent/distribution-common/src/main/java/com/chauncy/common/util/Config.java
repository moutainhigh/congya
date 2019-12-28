package com.chauncy.common.util;

import java.io.*;
import java.util.Properties;

/**
 * @Author zhangrt
 * @Date 2019/12/18 23:13
 **/

public class Config {



    static String savePath = Config.class.getResource("/redis.properties").getPath();

    // 根据key读取value

    public static String getValue(String key) {

        try {

            //getResourceAsStream 有缓存  修改后 还是返回以前的。。

            //InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties");



            Properties config = new Properties();

            try {

                InputStream in =new BufferedInputStream(new FileInputStream(savePath));

                config.load(in);

                in.close();

            } catch (IOException e) {

                System.out.println("加载初始化配置文件失败!");

            }

            String value = config.getProperty(key);

            return value;

        } catch (Exception e) {

            System.err.println("读取初始化配置文件失败!");

            e.printStackTrace();

            return null;

        }

    }



    /**

     * 修改文件中的值

     * @param Key 要修改的 key 值

     * @param value 要修改的 key 值对应的 value 值

     */

    public static void setProper(String Key, Object value) {

        Properties pro = new Properties();

        FileInputStream fis = null;


        // 路径中有空格，路径会将空格自动转为 %20，所以把他替换为了空格

        savePath = savePath.replace("%20", " ");

        File file = new File(savePath);

        BufferedInputStream bis = null;

        try {

            fis = new FileInputStream(file);

            bis = new BufferedInputStream(fis);

            pro.load(bis);

            FileOutputStream fos = new FileOutputStream(file);

            pro.setProperty(Key, String.valueOf(value));

            pro.store(fos, null);

            fos.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
