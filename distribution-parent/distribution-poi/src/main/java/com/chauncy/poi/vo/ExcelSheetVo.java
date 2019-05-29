package com.chauncy.poi.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author huangwancheng
 * @create 2019-05-30 00:16
 *
 * 定义表格的数据对象
 *
 */
@Data
public class ExcelSheetVo {

    /**
     * sheet的名称
     */
    private String sheetName;


    /**
     * 表格标题
     */
    private String title;

    /**
     * 头部标题集合
     */
    private String[] headers;

    /**
     * 数据集合
     */
    private List<List<Object>> dataList;

    /**
     * 数据集合
     */
    private List<Map<String,Object>> dataListMap;

}
