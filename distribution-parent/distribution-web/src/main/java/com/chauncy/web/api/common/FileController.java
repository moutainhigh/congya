package com.chauncy.web.api.common;

import cn.hutool.captcha.generator.RandomGenerator;
import com.chauncy.common.enums.ResultCode;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.web.base.FileBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件前端控制器
 *
 * @author zhangruitao
 * @date 2017-05-29
 * @time 10:35
 **/
@Controller
@RequestMapping(value = "/common/file", method = {RequestMethod.POST, RequestMethod.GET})
@PropertySource("classpath:config/fileConfig.properties")
@Api(description = "通用上传文件借口（multipart/form-data）")
public class FileController extends FileBaseController {

    @Value("${upload.file.saveDir}")
    private String saveDir;


    /**
     * 文件上传
     *
     * @param file  文件模型
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ApiOperation(value = "上传文件")
    public JsonViewData uploadFile(
            @RequestParam(value = "file") MultipartFile file)  {
        if ((Objects.isNull(file) || file.isEmpty())) {
            return setJsonViewData(ResultCode.PARAM_ERROR, "请提供需要上传的文件");
        }
        String fileName=file.getOriginalFilename();
        int pointPos = fileName.lastIndexOf(".");
        String extension =  fileName.substring(pointPos);
        String newFileName = SnowFlakeUtil.getFlowIdInstance().nextId() + extension;
        String filePath=saveDir +File.separator+extension.substring(1)+File.separator+ newFileName;
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(httpSession.getServletContext().getRealPath(""),filePath);
            Files.write(path, bytes);
        } catch (IOException e) {
            LoggerUtil.error(e);
        }
        return setJsonViewData(filePath);
    }

}
