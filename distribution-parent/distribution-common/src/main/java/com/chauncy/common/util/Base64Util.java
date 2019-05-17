package com.chauncy.common.util;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author huangwancheng
 * @create 2019-05-17 20:00
 *
 * base64字符串转化成图片
 *
 */
public class Base64Util
{
    public static String saveBase64ToFile(String base64,String uri,String fileName) throws IOException
    {
        String filePath = FilePathUtil.getFilePath(uri).getAbsolutePath();
        String path = filePath + "/" + fileName;
        File file = new File(path);
        if(!file.exists())
            file.createNewFile();
        generateImage(base64, path);
        return uri + "/" +  fileName;
    }

    //base64字符串转化成图片
    private static void generateImage(String imgStr, String filePath) throws IOException
    {
        BASE64Decoder decoder = new BASE64Decoder();

        //Base64解码
        byte[] b = decoder.decodeBuffer(imgStr);
        for (int i = 0; i < b.length; ++i)
        {
            if (b[i] < 0)
            {//调整异常数据
                b[i] += 256;
            }
        }
        OutputStream out = new FileOutputStream(filePath);
        out.write(b);
        out.flush();
        out.close();
    }
}
