package com.chauncy.order.service.impl;

import com.chauncy.common.enums.message.NoticeTitleEnum;
import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.data.bo.app.message.SaveUserNoticeBo;
import com.chauncy.message.interact.service.IMmUserNoticeService;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.order.logistics.impl.OmOrderLogisticsServiceImpl;
import com.chauncy.order.report.service.IOmOrderReportService;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.StartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

/**
 * @Author zhangrt
 * @Date 2019/7/11 12:20
 **/
@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OmShoppingCartServiceImplTest {

    @Autowired
    private OmShoppingCartServiceImpl shoppingCartService;
    @Autowired
    private OmOrderServiceImpl orderService;
    @Autowired
    private IMmUserNoticeService mmUserNoticeService;
    @Autowired
    private OmOrderLogisticsServiceImpl omOrderLogisticsService;
    @Autowired
    private IOmOrderBillService omOrderBillService;
    @Autowired
    private IOmOrderReportService omOrderReportService;


    @Autowired
    private IUmUserService userService;

    /* @Test
     public void searchByIds() {
         List<SettleAccountsDto> totalCarVos= Lists.newArrayList(
                 new SettleAccountsDto(1147887217915179009L,1),
                 new SettleAccountsDto(1147887218905034753L,1),
                 new SettleAccountsDto(1147887219978776577L,1),
                 new SettleAccountsDto(1148259822342352897L,1),
                 new SettleAccountsDto(1148258455756460033L,1)
         );
         shoppingCartService.searchByIds(totalCarVos);
     }*/
    @Test
    public void updateUserLevel() {
        userService.list().forEach(  x->userService.updateLevel(x.getId()));


    }

    @Test
    public void firstSecond() {

//        omOrderBillService.batchCreateStoreBill(BillTypeEnum.PAYMENT_BILL.getId());
//
//
//        omOrderBillService.batchCreateStoreBill(BillTypeEnum.PROFIT_BILL.getId());
        omOrderReportService.batchCreateSaleReport(LocalDate.now());
        //商品销售报表
        /*omOrderReportService.orderClosure(206983497670201344L);
        omOrderReportService.orderClosure(206982238737272832L);*/
    }


}