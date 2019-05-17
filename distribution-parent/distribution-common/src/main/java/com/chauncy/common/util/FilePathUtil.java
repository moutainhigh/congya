package com.chauncy.common.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;

/**
 * @Author huangwancheng
 * @create 2019-05-17 20:04
 *
 * 图片文件路径
 *
 */
public class FilePathUtil {

    public static File getFilePath(String fileUri) throws IOException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists())
            path = new File("");

        File filePath = new File(path.getAbsolutePath(), "static" + fileUri);
        if (!filePath.exists())
            filePath.mkdirs();
        return filePath;
    }

    public static MultipartFile convertFileToMultipartFile(String fileName, String newFileName) throws IOException {
        File file = new File(fileName);
        FileItem fileItem = new DiskFileItem(newFileName,
                Files.probeContentType(file.toPath()), false,
                file.getName(), (int) file.length(),
                file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }
}
