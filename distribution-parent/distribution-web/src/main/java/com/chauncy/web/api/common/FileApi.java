package com.chauncy.web.api.common;

import com.chauncy.common.enums.ResultCode;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
public class FileApi extends BaseApi {

    @Value("${upload.file.saveDir}")
    private String saveDir;


    /**
     * 文件上传
     *
     * @param files  文件模型
     * 本地测试别用idea自带的容器  每次重启的项目根目录不同 最好指定本地的容器
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ApiOperation(value = "上传文件,如果上传多个参数名都为file")
    public JsonViewData uploadFile(
            @RequestParam(value = "file") MultipartFile[] files)  {
        if (files==null ||files.length==0) {
            return setJsonViewData(ResultCode.PARAM_ERROR, "请提供需要上传的文件");
        }
        String filePaths="";
        final String splitStr=",";
        String allFilePath="";
        for (MultipartFile file:files) {
            String fileName = file.getOriginalFilename();
            int pointPos = fileName.lastIndexOf(".");
            String extension = fileName.substring(pointPos);
            String newFileName = SnowFlakeUtil.getFlowIdInstance().nextId() + extension;
             String filePath = saveDir + "/" + extension.substring(1) + "/" + newFileName;
             filePaths+=filePath+splitStr;
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(httpSession.getServletContext().getRealPath(""), filePath);
                File myPath = new File(httpSession.getServletContext().getRealPath("") +
                        "/" + saveDir + "/" + extension.substring(1));
                if (!myPath.exists()) {
                    myPath.mkdirs();//多级文件夹目录
                }
                Files.write(path, bytes);
            } catch (IOException e) {
                LoggerUtil.error(e);
                return setJsonViewData(ResultCode.FAIL, "上传文件出错！");
            }
        }
        if (filePaths.endsWith(splitStr)){
             allFilePath = filePaths.substring(0, filePaths.length() - 1);
        }
        return setJsonViewData(allFilePath);
    }

}
