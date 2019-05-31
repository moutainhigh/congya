package com.chauncy.web.base;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 文件下载底层控制器
 *
 * @author zhangrt
 * @date 2019-05-23
 * @time 09:29
 */
public abstract class FileBaseApi extends BaseApi {

    /**
     * 下载，来源输入流
     *
     * @param in       输入流
     * @param fileName 文件名(带文件格式后缀)，支持中文
     * @return ResponseEntity
     * @throws IOException I/O异常
     * @see #writeToResponseForDownload(byte[], String)
     */
    protected ResponseEntity<byte[]> writeToResponseForDownload(InputStream in, String fileName) throws IOException {
        byte[] bytes = ByteStreams.toByteArray(in);
        Closeables.close(in, true);
        return writeToResponseForDownload(bytes, fileName);
    }

    /**
     * 下载，来源于文件
     *
     * @param file 源文件
     * @return ResponseEntity
     * @throws IOException I/O异常
     * @see #writeToResponseForDownload(byte[], String)
     */
    protected ResponseEntity<byte[]> writeToResponseForDownload(File file) throws IOException {
        return writeToResponseForDownload(Files.toByteArray(file), file.getName());
    }

    /**
     * spring 核心下载
     * 注意：小容量文件下载
     *
     * @param bytes    需要下载的字节
     * @param fileName 下载文件名称（带格式后缀），支持中文
     * @return ResponseEntity
     */
    protected ResponseEntity<byte[]> writeToResponseForDownload(byte[] bytes, String fileName) {
        Objects.requireNonNull(bytes, "响应字节不能为空！！！");
        Objects.requireNonNull(fileName, "文件名不能为空！！！");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.CREATED);
    }

    /**
     * 下载，原生servlet下载
     * 注意：大文件下载建议采用
     *
     * @param inputStream 输入流
     * @param displayName 下载文件命名（带格式后缀），支持中文
     * @throws IOException I/O异常
     */
    protected void nativeServletDownload(InputStream inputStream, String displayName) throws IOException {
        try (Closer closer = Closer.create()) {
            closer.register(inputStream);
            // String charset = StandardCharsets.UTF_8.displayName();
            String charset = "GB2312";
            httpResponse.setCharacterEncoding(charset);
            httpResponse.setContentLength(inputStream.available());
            httpResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            String filename = new String(displayName.getBytes(charset), StandardCharsets.ISO_8859_1);
            httpResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            OutputStream outputStream = closer.register(httpResponse.getOutputStream());
            ByteStreams.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }


}
