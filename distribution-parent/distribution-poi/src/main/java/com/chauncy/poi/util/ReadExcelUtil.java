package com.chauncy.poi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  读取excel数据
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/29 15:27
 * @Version 1.0
 */
@Slf4j
public class ReadExcelUtil {
    private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";

    /**
     * 读取excel数据,返回List<List<String>>
     *
     * @throws Exception
     */
    public static List<List<String>> readExcelInfo(String url) throws Exception {
        /*
         * workbook:工作簿,就是整个Excel文档
         * sheet:工作表
         * row:行
         * cell:单元格
         */

//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(url)));
//        支持excel2003、2007
        File excelFile = new File(url);//创建excel文件对象
        InputStream is = new FileInputStream(excelFile);//创建输入流对象
        checkExcelVaild(excelFile);
        Workbook workbook = getWorkBook(excelFile);
//        Workbook workbook = WorkbookFactory.create(is);//同时支持2003、2007、2010
//        获取Sheet数量
        int sheetNum = workbook.getNumberOfSheets();
//      创建二维数组保存所有读取到的行列数据，外层存行数据，内层存单元格数据
        List<List<String>> dataList = new ArrayList<List<String>>();
//        FormulaEvaluator formulaEvaluator = null;
//        遍历工作簿中的sheet,第一层循环所有sheet表
        for (int index = 0; index < sheetNum; index++) {
            Sheet sheet = workbook.getSheetAt(index);
            if (sheet == null) {
                continue;
            }
            System.out.println("表单行数：" + sheet.getLastRowNum());
//            如果当前行没有数据跳出循环，第二层循环单sheet表中所有行
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
//                根据文件头可以控制从哪一行读取，在下面if中进行控制
                if (row == null) {
                    continue;
                }
//                遍历每一行的每一列，第三层循环行中所有单元格
                List<String> cellList = new ArrayList<String>();
                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    System.out.println("遍历行中cell数据:" + getCellValue(cell));
                    cellList.add(getCellValue(cell).toString().trim());
                    System.out.println("第" + cellIndex + "个:     cell个数：" + cellList.size());
                }
                dataList.add(cellList);
                System.out.println("第" + rowIndex + "行:     共几行：" + dataList.size());
            }

        }
        is.close();
        return dataList;
    }

    /**
     * 读取excel数据,返回List<Map<String,String>>
     * 对应的key-value,可以使用keySet()方法根据Key找到对应的value，保存在对象中，存入数据库
     * 这个方法只适用需要和标题耦合的导出数据
     *
     * @throws Exception
     */
    public static List<Map<String, String>> readExcelInfos(String url) throws Exception {
        /*
         * workbook:工作簿,就是整个Excel文档
         * sheet:工作表
         * row:行
         * cell:单元格
         */

//        支持excel2003、2007
        File excelFile = new File(url);//创建excel文件对象
        InputStream is = new FileInputStream(excelFile);//创建输入流对象
        checkExcelVaild(excelFile);
        //创建工作簿
        Workbook workbook = getWorkBook(excelFile);
//        Workbook workbook = WorkbookFactory.create(is);//同时支持2003、2007、2010
//        获取Sheet数量
        int sheetNum = workbook.getNumberOfSheets();
//      创建二维数组保存所有读取到的行列数据，外层存行数据，内层存单元格数据
        List<Map<String, String>> dataList = new ArrayList<>();
//        FormulaEvaluator formulaEvaluator = null;
//        遍历工作簿中的sheet,第一层循环所有sheet表
        for (int index = 0; index < sheetNum; index++) {
            Sheet sheet = workbook.getSheetAt(index);
            if (sheet == null) {
                continue;
            }
            //保存每个sheet对应的标题
            List<String> headerList = new ArrayList<>();
            //获取对应的工作表的表单行数
            System.out.println("表单行数：" + sheet.getLastRowNum());
//            如果当前行没有数据跳出循环，第二层循环单sheet表中所有行

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

                Row row = sheet.getRow(rowIndex);
//                根据文件头可以控制从哪一行读取，在下面if中进行控制
                if (row == null) {
                    continue;
                }
//                遍历每一行的每一列，第三层循环行中所有单元格
                Map<String, String> map = new HashMap<>();
                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    System.out.println("遍历行中cell数据:" + getCellValue(cell));

                    //如果是第一行，则保存为标题
                    if (rowIndex == 0) {
                        headerList.add(cell.getStringCellValue());
                    } else {
                        map.put(headerList.get(cellIndex), getCellValue(cell).toString());
//                        System.out.print(getCellValues(cell).toString());
                    }
                }
                if (rowIndex != 0)
                    dataList.add(map);
                System.out.println("第" + rowIndex + "行:     共几行：" + dataList.size());
            }

        }
        is.close();
        return dataList;
    }

    /**
     * 获取单元格的数据,暂时不支持公式
     */
//    public static String getCellValue(Cell cell) {
//        CellType cellType = cell.getCellTypeEnum();
//        String cellValue = "";
//        if (cell == null || cell.toString().trim().equals("")) {
//            return null;
//        }
//
//        if (cellType == CellType.STRING) {
//            cellValue = cell.getStringCellValue().trim();
//            return cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
//        }
//        if (cellType == CellType.NUMERIC) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
//            DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
//            if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
////                cellValue = DateFormatUtil.formatDurationYMD(cell.getDateCellValue().getTime());
//                cellValue = nf.format(cell.getNumericCellValue());
//            } else {  //否
//                cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
//            }
//            return cellValue;
//        }
//        if (cellType == CellType.BOOLEAN) {
//            cellValue = String.valueOf(cell.getBooleanCellValue());
//            return cellValue;
//        }
//        return null;
//
//    }

    /**
     * 获取单元格数据
     */
    private static Object getCellValue(/*Workbook wb, */Cell cell) {
        Object columnValue = null;
        if (cell != null) {
            DecimalFormat df = new DecimalFormat("0");// 格式化 number
            // String
            // 字符
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
            DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    columnValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = df.format(cell.getNumericCellValue());
                    } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = nf.format(cell.getNumericCellValue());
                    } else {
                        columnValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    columnValue = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    columnValue = "";
                    break;
                /*case Cell.CELL_TYPE_FORMULA:
                    // 格式单元格
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    evaluator.evaluateFormulaCell(cell);
                    CellValue cellValue = evaluator.evaluate(cell);
                    columnValue = cellValue.getNumberValue();
                    break;*/
                default:
                    columnValue = cell.toString();
            }
        }
        return columnValue;
    }

    /**
     * 判断excel的版本，并根据文件流数据获取workbook
     *
     * @throws IOException
     */
    public static Workbook getWorkBook(File file) throws Exception {

        Workbook workbook = null;
        System.out.println(file.getName().endsWith(EXCEL_XLS));
        if (file.getName().endsWith(EXCEL_XLS)) {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } else if (file.getName().endsWith(EXCEL_XLSX)) {
            workbook = WorkbookFactory.create(file);
        }
        /**
         * 解决针对excel 2003 和 excel 2007 的多种格式,代码更改为
         *  Workbook workbook = WorkbookFactory.create(file.getInputStream());
         *   Sheet hssfSheet = workbook.getSheetAt(0);  //示意访问sheet
         */

        return workbook;
    }

    /**
     * 校验文件是否为excel
     *
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception {
        String message = "该文件是EXCEL文件！";
        if (!file.exists()) {
            message = "文件不存在！";
            throw new Exception(message);
        }
        if (!file.isFile() || ((!file.getName().endsWith(EXCEL_XLS) && !file.getName().endsWith(EXCEL_XLSX)))) {
            System.out.println(file.isFile() + "===" + file.getName().endsWith(EXCEL_XLS) + "===" + file.getName().endsWith(EXCEL_XLSX));
            System.out.println(file.getName());
            message = "文件不是Excel";
            throw new Exception(message);
        }
    }
}

