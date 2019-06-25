//package com.chauncy.poi;
//
//import com.chauncy.poi.util.CreateExcelUtil;
//import com.chauncy.poi.vo.ExcelSheetVo;
//import com.chauncy.test.com.chauncy.user.service.UserService;
//import com.chauncy.web.StartApplication;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static com.chauncy.poi.util.ExcelVersion.V2003;
//import static com.chauncy.poi.util.ExcelVersion.V2007;
//
//
///**
// * 创建excel工具类测试>>>>导出到Excel
// *
// * @Author: HUANGWANCHENG
// * @Date: 2019/04/29 18:42
// * @Version 1.0
// */
//@SpringBootTest(classes = StartApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@Slf4j
//public class CreateExcelUtilTest {
//
//    @Autowired
//    private UserService com.chauncy.user.service;
//
//
//    /**
//     * 导出工作簿单个工作表
//     */
//    @Test
//    public void creatSheet() {
//        List<ExcelSheetVo> sheetPOS = new ArrayList<>();
//
//        List<Map<String, Object>> listMap = com.chauncy.user.service.find();
//
//        ExcelSheetVo sheetPO = new ExcelSheetVo();
//
//        sheetPO.setSheetName("用户表");
//        sheetPO.setTitle("标题");
//        sheetPO.setHeaders(new String[]{"1", "2", "3", "4"});
//        sheetPO.setDataListMap(listMap);
//        sheetPOS.add(sheetPO);
//
//        try {
//            CreateExcelUtil.createWorkbookAtDisk(V2007, sheetPOS, "/Users/huangwancheng/Desktop/poi/07.xls");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 导出工作簿多个工作表
//     */
//    @Test
//    public void creatSheets() {
//
//        List<Map<String, Object>> listMap1 = com.chauncy.user.service.find();
//        List<Map<String, Object>> listMap2 = com.chauncy.user.service.find();
//        List<Map<String, Object>> listMap3 = com.chauncy.user.service.find();
//
//        //存放listMap
//        List<List<Map<String, Object>>> listCounter = new ArrayList<>();
//        listCounter.add(listMap1);
//        listCounter.add(listMap2);
//        listCounter.add(listMap3);
//
//        //自定义工作表名
//        String[] sheetNames = new String[listCounter.size()];
//        sheetNames[0] = "主页";
//        sheetNames[1] = "灯及侵权";
//        sheetNames[2] = "高价格";
//
//        //自定义标题
//        String[] titles = new String[listCounter.size()];
//        titles[0] = "标题1";
//        titles[1] = "标题2";
//        titles[2] = "标题3";
//
//        List<ExcelSheetVo> sheetPOS = new ArrayList<>();
//
//        for (int i = 0;i<listCounter.size();i++){
//
//            ExcelSheetVo sheetPO = new ExcelSheetVo();
//
//            for (Map<String, Object> map : listCounter.get(i)){
//
//                String[] headers = new String[listCounter.get(i).get(0).size()];
//                int a =0;
//
//                for (Map.Entry<String, Object> entry : map.entrySet()){
//
//                    headers[a] = entry.getKey();
//                    a++;
//                }
//
//                sheetPO.setSheetName(sheetNames[i]);
//                sheetPO.setTitle(titles[i]);
//                sheetPO.setHeaders(headers);
//                sheetPO.setDataListMap(listCounter.get(i));
//            }
//            sheetPOS.add(sheetPO);
//        }
//
//        try {
//            CreateExcelUtil.createWorkbookAtDisk(V2003, sheetPOS, "/Users/huangwancheng/Desktop/poi/sheets.xls");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
