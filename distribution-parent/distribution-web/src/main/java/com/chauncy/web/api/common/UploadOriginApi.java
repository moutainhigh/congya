package com.chauncy.web.api.common;

import cn.hutool.core.util.StrUtil;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.qiniu.Base64DecodeMultipartFileUtil;
import com.chauncy.common.util.qiniu.QiniuUtil;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.Result;
import com.chauncy.web.util.FtpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author cheng
 * @create 2019-06-14 14:33
 */
@Slf4j
@RestController
@Api(tags = "文件上传接口")
@RequestMapping("/upload")
@Transactional(rollbackFor = Exception.class)
public class UploadOriginApi {
    @Autowired
    private QiniuUtil qiniuUtil;

    @Autowired
    private FtpUtils ftpUtils;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public JsonViewData upload(@RequestParam(required = false) MultipartFile[] files,
                               @RequestParam(required = false) String base64,
                               HttpServletRequest request) {

        /*if (StrUtil.isNotBlank(base64)) {
            for (MultipartFile file : files) {
                // base64上传
                file = Base64DecodeMultipartFileUtil.base64Convert(base64);
            }
        }*/

        if (StrUtil.isNotBlank(base64)) {
            // base64上传
            MultipartFile file = Base64DecodeMultipartFileUtil.base64Convert(base64);
            files[0] = file;
        }
        if (files == null ||files.length==0){
            return new JsonViewData(ResultCode.FAIL,"请选择上传文件");
        }

        //获取当前日期
        String temp_str="";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        temp_str=sdf.format(dt);

        String result = "";
        final String splitStr = ",";
        String filePaths = "";
        String allFilePath = "";

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            int pointPos = originalName.lastIndexOf(".");
            String extension = originalName.substring(pointPos);
            String fileName = temp_str+"/"+extension.substring(1)+"/"+qiniuUtil.renamePic(file.getOriginalFilename());
            try {
                InputStream inputStream = file.getInputStream();

                //上传七牛云服务器
                result = qiniuUtil.qiniuInputStreamUpload(inputStream, fileName);

                filePaths += result + splitStr;
            } catch (Exception e) {
                log.error(e.toString());
                return new JsonViewData(e.toString());
            }
        }
        if (filePaths.endsWith(splitStr)){
            allFilePath = filePaths.substring(0, filePaths.length() - 1);
        }
        return new JsonViewData(allFilePath);
    }



    @RequestMapping(value = "/app", method = RequestMethod.POST)
    @ApiOperation(value = "app包上传")
    public JsonViewData uploadApp(@RequestParam(required = false) MultipartFile[] files,
                               @RequestParam(required = false) String base64,
                                  @RequestParam(required = true) String fileName) {

        if (StrUtil.isNotBlank(base64)) {
            // base64上传
            MultipartFile file = Base64DecodeMultipartFileUtil.base64Convert(base64);
            files[0] = file;
        }
        if (files == null ||files.length==0){
            return new JsonViewData(ResultCode.FAIL,"请选择上传文件");
        }
        String uri = "";
        for (MultipartFile file : files) {
            try {
                byte[] bytes = file.getBytes();
                uri = ftpUtils.sshSftp(bytes,fileName);
            } catch (Exception e) {
                LoggerUtil.error(e);
                return new JsonViewData(ResultCode.FAIL, "上传文件出错！");
            }
        }



        return new JsonViewData(uri);
    }
}
