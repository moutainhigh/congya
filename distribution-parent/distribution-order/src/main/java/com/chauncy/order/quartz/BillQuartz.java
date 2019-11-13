package com.chauncy.order.quartz;

import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.order.report.service.IOmOrderReportService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yeJH
 * @Description 账单定时任务  店铺设置结算周期  周为单位  每周一生成上周的账单
 * @since 2019/11/5 22:25
 */
@Slf4j
@Component
@Data
public class BillQuartz {


    @Autowired
    private IOmOrderBillService omOrderBillService;

    @Autowired
    private IOmOrderReportService omOrderReportService;

    /**
     * @Author yeJH
     * @Date 2019/11/11 14:13
     * @Description //每周一创建一次货款账单
     *
     * @Update yeJH
     *
     * @return void
     **/
    public void createPaymentBill(){
        omOrderBillService.batchCreateStoreBill(BillTypeEnum.PAYMENT_BILL.getId());
    }
    /**
     * @Author yeJH
     * @Date 2019/11/11 14:13
     * @Description //每周一创建一次利润账单
     *
     * @Update yeJH
     *
     * @return void
     **/
    public void createProfitBill(){
        omOrderBillService.batchCreateStoreBill(BillTypeEnum.PROFIT_BILL.getId());
    }
    /**
     * @Author yeJH
     * @Date 2019/11/11 14:13
     * @Description //每周一创建一次商品销售报表
     *
     * @Update yeJH
     *
     * @return void
     **/
    public void createSaleReport(){
        omOrderReportService.batchCreateSaleReport();
    }
}
