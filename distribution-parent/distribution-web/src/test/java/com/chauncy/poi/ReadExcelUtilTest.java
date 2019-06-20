//package com.chauncy.poi;
//
//import com.chauncy.data.domain.po.test.UserPO;
//import com.chauncy.poi.util.ReadExcelUtil;
//import com.chauncy.test.service.UserService;
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
///**
// * @Author: HUANGWANCHENG
// * @Date: 2019/04/29 15:37
// * @Version 1.0
// *
// *  从Excel导入数据库
// */
//@SpringBootTest(classes = StartApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@Slf4j
//public class ReadExcelUtilTest {
//
//    @Autowired
//    private UserService service;
//
//    /**
//     * 测试ReadExcelUtil工具类的readExcelInfo()方法
//     * 该方法返回List<List<String>>
//     */
//    @Test
//    public void readExcelInfotest() {
//
//        List<UserPO> devices = new ArrayList<UserPO>();
//        try {
//            List<List<String>> list = ReadExcelUtil.readExcelInfo(null);
//            System.out.println(list);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
////        插入数据库
////        调用相关插入方法，可以批量也可单条插入循环实现，看具体业务需要选择
//    }
//
//    /**
//     * 测试ReadExcelUtil工具类的readExcelInfos()方法
//     * 该方法返回List<Map<String,String>>
//     */
//    @Test
//    public void readExcelInfosTest(){
//        try {
//            List<Map<String,String>> list = ReadExcelUtil.readExcelInfos("/Users/huangwancheng/Desktop/poi/工作簿1.xlsx");
//            System.out.println(list);
//            //遍历数据
//            for (Map<String,String> map : list){
//                UserPO userPO = new UserPO();
//                /**
//                 * 通过map.getKey()获取对应的值
//                 */
//                userPO.setName((map.get("名字")==null||map.get("名字")==" ")?null:map.get("名字"));
//                userPO.setAge((map.get("年龄")==null||map.get("年龄")==" ")?null:Integer.valueOf(map.get("年龄")));
//                userPO.setSalary((map.get("薪水")==null||map.get("薪水")==" ")?null:Double.valueOf(map.get("薪水")));
//                service.insert(userPO);
//            }
//
//        } catch (Exception e) {
//            e.getStackTrace();
//            System.out.println(e.getMessage());
//        }
//    }
//}
