package com.chauncy.data.mapper.order.bill;

import com.chauncy.data.bo.order.log.BillRelGoodsTempBo;
import com.chauncy.data.bo.order.log.BillRelOrderBo;
import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchOrderReportDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.order.bill.BillRelGoodsTempVo;
import com.chauncy.data.vo.manage.order.bill.BillReportVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 账单表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@Repository
public interface OmOrderBillMapper extends IBaseMapper<OmOrderBillPo> {

    /**
     * 查询账单列表
     * 根据年，期数，提现状态，时间，审核状态，金额范围等条件查询
     *
     * @param searchBillDto
     * @return
     */
    List<BillBaseInfoVo> searchBillPaging(SearchBillDto searchBillDto);

    /**
     * 查询账单基本信息
     *
     * @param id
     * @return
     */
    BillDetailVo findBillDetail(@Param("id") Long id);

    /**
     * 获取需要创建账单的店铺的数量
     * @param endDate   需要创建账单的那一周   任何一天都可以
     * @return
     */
    int getStoreSumNeedCreateBill(@Param("billType") Integer billType, @Param("endDate") LocalDate endDate, @Param("storeId") Long storeId);

    /**
     * 获取需要创建账单的店铺的id
     * @param endDate
     * @return
     */
    List<Long> getStoreNeedCreateBill(@Param("billType") Integer billType, @Param("endDate") LocalDate endDate, @Param("storeId") Long storeId);

    /**
     * 查询订单交易报表  团队合作利润账单
     * @param searchOrderReportDto
     * @return
     */
    List<BillReportVo> searchBillReportPaging(SearchOrderReportDto searchOrderReportDto);

    /**
     * 查询账单关联订单商品列表
     * @param id
     * @return
     */
    List<BillRelGoodsTempVo> findRelBillDetail(Long id);

    /**
     * @Author yeJH
     * @Date 2019/11/14 23:10
     * @Description 查询时间段内属于账单的订单
     *
     * @Update yeJH
     *
     * @param  startDate  开始时间
     * @param  endDate   结束时间
     * @param  storeId  店铺id
     * @param  billType  账单类型
     * @return java.util.List<com.chauncy.data.bo.order.log.BillRelOrderBo>
     **/
    List<BillRelOrderBo> getBillOrderList(@Param("startDate") LocalDate startDate,
                                          @Param("endDate")LocalDate endDate,
                                          @Param("storeId")Long storeId,
                                          @Param("billType")Integer billType);

    /**
     * @Author yeJH
     * @Date 2019/12/8 13:21
     * @Description 创建账单时获取时间内的所有可以售后的订单快照信息
     *
     * @Update yeJH
     *
     * @param  startDate    账单结算开始时间
     * @param  endDate      账单结算结束时间
     * @param  storeId      创建账单店铺
     * @param  billType     账单类型  1 货款  2 利润
     * @return java.util.List<com.chauncy.data.bo.order.log.BillRelGoodsTempBo>
     **/
    List<BillRelGoodsTempBo> getBillGoodsTempList(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate")LocalDate endDate,
                                                  @Param("storeId")Long storeId,
                                                  @Param("billType")Integer billType);
}
