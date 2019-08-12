package com.chauncy.data.mapper.order.bill;

import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import org.apache.ibatis.annotations.Param;

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
    int getStoreSumNeedCreateBill(LocalDate endDate);

    /**
     *
     * @param endDate
     * @return
     */
    List<Long> getStoreNeedCreateBill(LocalDate endDate);
}
