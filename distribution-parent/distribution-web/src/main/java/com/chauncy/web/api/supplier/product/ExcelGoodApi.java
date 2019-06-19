package com.chauncy.web.api.supplier.product;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.*;
import com.chauncy.data.dto.excel.ExcelImportGoodBaseDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.excel.ExcelImportErrorLogVo;
import com.chauncy.poi.util.ReadExcelUtil;
import com.chauncy.web.base.BaseApi;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author zhangrt
 * @Date 2019/6/17 17:46
 **/

@RestController
@Api(description = "商家端商品管理")
@RequestMapping("/supplier/product")
public class ExcelGoodApi extends BaseApi {

    @Value("${upload.file.path}")
    private String saveDir;



    @PostMapping(value = "/importbase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "导入商品基本信息、调用这个接口前先调用上传文件到千牛云的接口获取路径")
    public JsonViewData importBase(@RequestParam(value = "file") MultipartFile file) throws InstantiationException, IllegalAccessException, IOException {

        List<List<String>> excelData;
        try {
            excelData = ReadExcelUtil.readExcelInfo(file.getInputStream());
            if (ListUtil.isListNullAndEmpty(excelData)) {
                return setJsonViewData(ResultCode.FAIL, "获取excel数据失败！");
            }
        } catch (Exception e) {
            LoggerUtil.error(e);
            return setJsonViewData(ResultCode.FAIL, "获取excel数据失败！");
        }

        Object objectList = getPoListFromExcelData(excelData);
        List<ExcelImportGoodBaseDto> excelImportGoodBaseDtos = (List<ExcelImportGoodBaseDto>) objectList;


        return setJsonViewData(excelData);
    }

    /**
     * 根据excel的数据组装成可以插入数据库的po
     *
     * @param excelDataList
     * @return
     */
    private List<Object> getPoListFromExcelData(List<List<String>> excelDataList) throws IllegalAccessException, InstantiationException {
        //excel数据转为定义的pojo
        List<Object> excelImportGoodBaseDtos = Lists.newArrayListWithCapacity(excelDataList.size());
        //excel导入出错记录
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = Lists.newArrayList();
        Class<Object> clazz = Object.class;

        Field[] fields = clazz.getDeclaredFields();
        /**
         * 前两行是模板导入说明和标题，导入的数据从第三行开始
         */
        for (int i = 2; i < excelDataList.size(); i++) {
            ExcelImportErrorLogVo excelImportErrorLogVo = new ExcelImportErrorLogVo();
            //获得整行数据
            List<String> rowDataList = excelDataList.get(i);
            if (rowDataList.size() < fields.length) {
                buildErrorLogVo(excelImportErrorLogVo, i + 1, "excel列数与模板不符合、");
            }
            Object entity = clazz.newInstance();
            //将excel数据组装成pojo
            for (int j = 0; j < fields.length; j++) {
                String cellString = rowDataList.get(i);
                fields[j].setAccessible(true);
                if (fields[j].getType().getSimpleName().equals("String")) {
                    fields[j].set(entity, cellString);
                } else if (fields[j].getType().getSimpleName().equals("List")) {
                    List<String> cellList = Splitter.on(";").omitEmptyStrings().trimResults().splitToList(cellString);
                    fields[j].set(entity, cellList);
                }
            }
            //如果数据校验通过
            if (MyValidUtil.check(entity)==null){
                excelImportGoodBaseDtos.add(entity);
            }
            else {
                buildErrorLogVo(excelImportErrorLogVo, i + 1, MyValidUtil.check(entity));
                if (excelImportErrorLogVo.getErrorMessage().lastIndexOf("、") == excelImportErrorLogVo.getErrorMessage().length() - 1) {
                    excelImportErrorLogVo.setErrorMessage("");
                }
                excelImportErrorLogVos.add(excelImportErrorLogVo);
            }

        }
        return excelImportGoodBaseDtos;
    }


    /**
     * 如果list为空  新建；如果不为空  添加
     *
     * @param excelImportErrorLogVo
     */
    private void  buildErrorLogVo(ExcelImportErrorLogVo excelImportErrorLogVo, Integer rowNumber, String errorMessage) {
        if (excelImportErrorLogVo.getRowNumber() == null) {
            excelImportErrorLogVo.setRowNumber(rowNumber);
        }
        if (excelImportErrorLogVo.getErrorMessage() == null) {
            excelImportErrorLogVo.setErrorMessage(errorMessage);
        } else {
            excelImportErrorLogVo.setErrorMessage(excelImportErrorLogVo.getErrorMessage()+errorMessage);
        }
    }




}


