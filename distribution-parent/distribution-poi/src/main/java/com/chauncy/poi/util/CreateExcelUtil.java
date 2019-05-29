package com.chauncy.poi.util;

import com.chauncy.poi.vo.ExcelSheetVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建excel文件
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/29 18:27
 * @Version 1.0
 */
public class CreateExcelUtil {

    /**
     * 标题样式
     */
    private final static String STYLE_HEADER = "header";
    /**
     * 表头样式
     */
    private final static String STYLE_TITLE = "title";
    /**
     * 数据样式
     */
    private final static String STYLE_DATA = "data";

    /**
     * 存储样式
     */
    private static final HashMap<String, CellStyle> cellStyleMap = new HashMap<>();


    /**
     * 根据版本创建工作簿
     *
     * @param version
     * @return
     */
    private static Workbook createWorkbook(ExcelVersion version) {
        switch (version) {
            case V2003:
                return new HSSFWorkbook();
            case V2007:
                return new XSSFWorkbook();
        }
        return null;
    }


    /**
     * 创建表头
     *
     * @param sheet
     * @param excelSheetVo
     * @param wb
     * @param version
     */
    private static void createTitle(Sheet sheet, ExcelSheetVo excelSheetVo, Workbook wb, ExcelVersion version) {
        Row titleRow = sheet.createRow(0);
        Cell titleCel = titleRow.createCell(0);
        titleCel.setCellValue(excelSheetVo.getTitle());
        titleCel.setCellStyle(getStyle(STYLE_TITLE, wb));
        //获取需要的列数,header的长度
        int column = excelSheetVo.getHeaders().length > version.getMaxColumn() ?version.getMaxColumn() : excelSheetVo.getHeaders().length;
        // 限制最大列数
        /*int column = excelSheetVo.getDataListMap().size() > version.getMaxColumn() ? version.getMaxColumn()
                : excelSheetVo.getDataListMap().size();*/
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, column-1));
    }

    /**
     * 创建标题
     *
     * @param sheet
     * @param excelSheetVo
     * @param wb
     * @param version
     */
    private static void createHeader(Sheet sheet, ExcelSheetVo excelSheetVo, Workbook wb, ExcelVersion version) {
        String[] headers = excelSheetVo.getHeaders();
        Row row = sheet.createRow(1);
        for (int i = 0; i < headers.length && i < version.getMaxColumn(); i++) {
            Cell cellHeader = row.createCell(i);
            cellHeader.setCellStyle(getStyle(STYLE_HEADER, wb));
            cellHeader.setCellValue(headers[i]);
        }

    }

    /**
     *  List<List<body>>创建body,即每一行的每个单元格
     *
     * @param sheet
     * @param excelSheetVo
     * @param wb
     * @param version
     */
    private static void createBody(Sheet sheet, ExcelSheetVo excelSheetVo, Workbook wb, ExcelVersion version) {

        List<List<Object>> dataList = excelSheetVo.getDataList();
        for (int i = 0; i < dataList.size() && i < version.getMaxRow(); i++) {
            List<Object> values = dataList.get(i);
            //从第三行开始，第一行是标题，第二行是header
            Row row = sheet.createRow(2 + i);
            for (int j = 0; j < values.size() && j < version.getMaxColumn(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(getStyle(STYLE_DATA, wb));
                cell.setCellValue(values.get(j).toString());
            }
        }
    }

    /**
     *  List<Map<String,String>>创建body,即每一行的每个单元格
     *
     * @param sheet
     * @param excelSheetVo
     * @param wb
     * @param version
     */
    private static void createBodyMap(Sheet sheet, ExcelSheetVo excelSheetVo, Workbook wb, ExcelVersion version) {

        List<Map<String,Object>> dataListMap = excelSheetVo.getDataListMap();
        for (int i = 0; i < dataListMap.size() && i < version.getMaxRow(); i++) {
            Map<String,Object> values = dataListMap.get(i);
            //从第三行开始，第一行是标题，第二行是header
            Row row = sheet.createRow(2 + i);
            //循环map的counter
            int mapCounter = 0;
            for (Map.Entry<String,Object> data : values.entrySet()){
                Cell cell = row.createCell(mapCounter);
                cell.setCellStyle(getStyle(STYLE_DATA, wb));
                cell.setCellValue((data.getValue() == null||data.getValue()==" ")?null:data.getValue().toString());
                mapCounter++;
            }
            /*for (int j = 0; j < values.size() && j < version.getMaxColumn(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(getStyle(STYLE_DATA, wb));
                cell.setCellValue(values.get(j).toString());
            }*/
        }
    }

    /**
     * 将title、header、body组装
     * @param wb
     * @param sheet
     * @param excelSheetVo
     * @param version
     */
    private static void buildSheetData(Workbook wb, Sheet sheet, ExcelSheetVo excelSheetVo, ExcelVersion version) {
        sheet.setDefaultRowHeight((short) 400);
        sheet.setDefaultColumnWidth((short) 10);
        createTitle(sheet, excelSheetVo, wb, version);
        createHeader(sheet, excelSheetVo, wb, version);
        createBodyMap(sheet, excelSheetVo, wb, version);
    }


    /**
     * 根据Excel版本，工作表列表来创建一个工作簿对象
     *
     * @param version
     * @param excelSheets
     * @return
     */
    private static Workbook createWorkBook(ExcelVersion version, List<ExcelSheetVo> excelSheets) {
        Workbook wb = createWorkbook(version);
        for (int i = 0; i < excelSheets.size(); i++) {
            ExcelSheetVo excelSheetPO = excelSheets.get(i);
            if (excelSheetPO.getSheetName() == null) {
                excelSheetPO.setSheetName("sheet" + i);
            }
            // 过滤特殊字符
            Sheet tempSheet = wb.createSheet(WorkbookUtil.createSafeSheetName(excelSheetPO.getSheetName()));
            System.out.println(tempSheet.toString());
            System.out.println(excelSheetPO.getDataListMap());
            buildSheetData(wb, tempSheet, excelSheetPO, version);
        }
        return wb;
    }

    /**
     * 把excel表格写入输出流中，输出流会被关闭
     *
     * @param version
     * @param excelSheets
     * @param outStream
     * @param closeStream
     *            是否关闭输出流
     * @throws IOException
     */
    public static void createWorkbookAtOutStream(ExcelVersion version, List<ExcelSheetVo> excelSheets,
                                                 OutputStream outStream, boolean closeStream,File filePath) throws Exception {
        if (CollectionUtils.isNotEmpty(excelSheets)) {
            Workbook wb = createWorkBook(version, excelSheets);
            wb.write(outStream);
            if (closeStream) {
                outStream.close();
            }
        }
    }

    /**
     * 在硬盘上写入excel文件
     * @param version
     * @param excelSheets
     * @param filePath
     * @throws IOException
     */
    public static void createWorkbookAtDisk(ExcelVersion version, List<ExcelSheetVo> excelSheets, String filePath)
            throws Exception {
        File excelFile = new File(filePath);//创建excel文件对象
//        InputStream is = new FileInputStream(excelFile);//创建输入流对象
        FileOutputStream fileOut = new FileOutputStream(excelFile);
        createWorkbookAtOutStream(version, excelSheets, fileOut, true,excelFile);
    }


    /**
     * 单元格样式
     *
     * @param type
     * @param wb
     * @return
     */
    private static CellStyle getStyle(String type, Workbook wb) {

        if (cellStyleMap.containsKey(type)) {
            return cellStyleMap.get(type);
        }
        // 生成一个样式
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setWrapText(true);

        if (STYLE_HEADER == type) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 16);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
        } else if (STYLE_TITLE == type) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 18);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
        } else if (STYLE_DATA == type) {
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);
        }
        cellStyleMap.put(type, style);
        return style;
    }
}
