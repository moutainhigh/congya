package com.chauncy.data.vo.excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * excel导入出错记录
 * @Author zhangrt
 * @Date 2019/6/18 17:38
 **/

@Data
@ApiModel
public class ExcelImportErrorLogVo {

    @ApiModelProperty("excel导入出错的第几行")
    private Integer rowNumber;

    @ApiModelProperty("excel导入出错的具体信息")
    private String errorMessage;

}
