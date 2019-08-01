//package com.chauncy.poi;
//
//import com.chauncy.data.areaService.IAreaShopLogisticsService;
//import com.chauncy.data.domain.po.area.AreaShopLogisticsPo;
//import com.chauncy.data.domain.po.test.UserPO;
//import com.chauncy.poi.util.ReadExcelUtil;
//import com.chauncy.test.service.UserService;
//import com.chauncy.web.StartApplication;
//import com.google.common.collect.Lists;
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
//    @Autowired
//    private IAreaShopLogisticsService areaShopLogisticsService;
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
//            List<Map<String,String>> list = ReadExcelUtil.readExcelInfos("/Users/cheng/document/ship/快递100快递公司标准编码-20190801113704.xlsx");
//            System.out.println(list);
//            List<AreaShopLogisticsPo> areaShopLogisticsPos = Lists.newArrayList();
//            //遍历数据
//            for (Map<String,String> map : list){
//                AreaShopLogisticsPo areaShopLogisticsPo = new AreaShopLogisticsPo();
//                /**
//                 * 通过map.getKey()获取对应的值
//                 */
//                areaShopLogisticsPo.setLogiName((map.get("公司名称")==null||map.get("公司名称")==" ")?null:map.get("公司名称"));
//                areaShopLogisticsPo.setLogiCode((map.get("公司编码")==null||map.get("公司编码")==" ")?null:map.get("公司编码"));
//                areaShopLogisticsPo.setSort((map.get("sort")==null||map.get("sort")==" ")?null:Integer.valueOf(map.get("sort")));
////                areaShopLogisticsService.save(areaShopLogisticsPo);
//                areaShopLogisticsPos.add(areaShopLogisticsPo);
//            }
//            areaShopLogisticsService.saveBatch(areaShopLogisticsPos);
//
//        } catch (Exception e) {
//            e.getStackTrace();
//            System.out.println(e.getMessage());
//        }
//    }
//}
