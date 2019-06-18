package com.chauncy.web.api.supplier.product;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.poi.util.ReadExcelUtil;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @Author zhangrt
 * @Date 2019/6/17 17:46
 **/

@RestController
@Api(description = "商家端商品管理")
@RequestMapping("/manage/supplier/product")
public class ExcelGoodApi extends BaseApi {


    @GetMapping(value = "/importbase")
    @ApiOperation(value = "导入商品基本信息、调用这个接口前先调用上传文件到千牛云的接口获取路径")
    public JsonViewData importBase(@ApiParam("Excel的千牛云地址") String url){
        try {
            List<List<String>> excelData = ReadExcelUtil.readExcelInfo(url);
        } catch (Exception e) {
            LoggerUtil.error(e);
            return setJsonViewData(ResultCode.FAIL,"获取excel数据失败！");
        }

    return null;
    }



}


