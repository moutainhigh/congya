package com.chauncy.order.bill.service;

import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchOrderReportDto;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;
import com.chauncy.data.dto.manage.order.bill.update.BillCashOutDto;
import com.chauncy.data.dto.manage.order.bill.update.BillDeductionDto;
import com.chauncy.data.dto.supplier.order.CreateStoreBillDto;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.order.bill.BillRelGoodsTempVo;
import com.chauncy.data.vo.manage.order.bill.BillReportVo;
import com.github.pagehelper.PageInfo;

import java.time.LocalDate;

/**
 * <p>
 * 账单表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
public interface IOmOrderBillService extends Service<OmOrderBillPo> {

    /**
     * 查询账单列表
     * 根据年，期数，提现状态，时间，审核状态，金额范围等条件查询
     * @param searchBillDto
     * @return
     */
    PageInfo<BillBaseInfoVo> searchBillPaging(SearchBillDto searchBillDto);

    /**
     * 查询账单基本信息
     * @param id
     * @return
     */
    BillDetailVo findBillDetail(Long id);

    /**
     * 商家店铺确定提现
     * @return
     */
    void billCashOut(BillCashOutDto billCashOutDto);

    /**
     * 平台批量审核账单
     * @param batchAuditDto
     * @return
     */
    void batchAudit(BatchAuditDto batchAuditDto);

    /**
     * 平台账单扣款
     * @param billDeductionDto
     * @return
     */
    void billDeduction(BillDeductionDto billDeductionDto);

    /**
     * 平台标记状态为处理中的店铺账单为已处理
     * @return
     */
    void billSettlementSuccess(Long id);

    /**
     * 批量创建货款、利润账单
     * @return
     */
    void batchCreateStoreBill(Integer billType, LocalDate endDate);

    /**
     * 根据时间创建货款、利润账单
     * @return
     */
    void createStoreBillByDate( CreateStoreBillDto createStoreBillDto);

    /**
     * 查询订单交易报表  团队合作利润账单
     * @param searchOrderReportDto
     * @return
     */
    PageInfo<BillReportVo> searchBillReportPaging(SearchOrderReportDto searchOrderReportDto);

    /**
     * 查询账单关联订单商品详情
     * @param baseSearchPagingDto
     * @param id
     * @return
     */
    PageInfo<BillRelGoodsTempVo> findRelBillDetail(BaseSearchPagingDto baseSearchPagingDto, Long id);

    /**
     * @Author yeJH
     * @Date 2019/12/6 21:46
     * @Description 创建货款账单，利润账单，商品销售报表
     *
     * @Update yeJH
     *
     * @param  billType
     **/
    void createBill(Integer billType, LocalDate endDate);
}
