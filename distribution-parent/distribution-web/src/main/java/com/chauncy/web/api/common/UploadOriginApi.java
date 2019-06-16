package com.chauncy.web.api.common;

import cn.hutool.core.util.StrUtil;
import com.chauncy.common.util.qiniu.Base64DecodeMultipartFileUtil;
import com.chauncy.common.util.qiniu.QiniuUtil;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.data.vo.Result;
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author cheng
 * @create 2019-06-14 14:33
 */
@Slf4j
@RestController
@Api(description = "文件上传接口")
@RequestMapping("/upload")
@Transactional(rollbackFor = Exception.class)
public class UploadOriginApi {
    @Autowired
    private QiniuUtil qiniuUtil;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public Result<Object> upload(@RequestParam(required = false) MultipartFile[] files,
                                 @RequestParam(required = false) String base64,
                                 HttpServletRequest request) {

        if (StrUtil.isNotBlank(base64)) {
            for (MultipartFile file : files) {
                // base64上传
                file = Base64DecodeMultipartFileUtil.base64Convert(base64);
            }
        }

        if (files == null ||files.length==0){
            return new ResultUtil<Object>().setErrorMsg("请选择上传文件");
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
                return new ResultUtil<Object>().setErrorMsg(e.toString());
            }
        }
        if (filePaths.endsWith(splitStr)){
            allFilePath = filePaths.substring(0, filePaths.length() - 1);
        }
        return new ResultUtil<Object>().setData(allFilePath);
    }
}
