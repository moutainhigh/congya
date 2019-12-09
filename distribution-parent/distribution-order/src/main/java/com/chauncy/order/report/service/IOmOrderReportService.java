package com.chauncy.order.report.service;

import com.chauncy.data.domain.po.order.report.OmOrderReportPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.manage.order.report.select.SearchReportDto;
import com.chauncy.data.vo.manage.order.report.ReportBaseInfoVo;
import com.chauncy.data.vo.manage.order.report.ReportRelGoodsTempVo;
import com.github.pagehelper.PageInfo;

import java.time.LocalDate;

/**
 * <p>
 * 商品销售报表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
public interface IOmOrderReportService extends Service<OmOrderReportPo> {

    /**
     * 查询商品销售报表
     * @param searchReportDto
     * @return
     */
    PageInfo<ReportBaseInfoVo> searchReportPaging(SearchReportDto searchReportDto);

    /**
     * 根据ID查找商品销售报表信息
     * @param id
     * @return
     */
    PageInfo<ReportRelGoodsTempVo> findReportById(BaseSearchPagingDto baseSearchPagingDto, Long id);

    /**
     * @Author yeJH
     * @Date 2019/11/11 19:06
     * @Description 批量创建销售报表
     *
     * @Update yeJH
     *
     * @param
     * @return void
     **/
    void batchCreateSaleReport(LocalDate endDate);

    /**
     * 根据时间创建商品销售报表
     * endDate   需要创建账单的那一周   任何一天都可以
     */
    void createSaleReportByDate(LocalDate endDate);


    /**
     * @Author yeJH
     * @Date 2019/12/8 12:38
     * @Description
     * 订单确认不能售后业务处理 扣减商品虚拟库存，插入报表订单关联
     * 1.插入关联 omReportRelGoodsTempPo 一个OmGoodsTempPo可能对应多个PmStoreRelGoodsStockPo
     *   下单的商品不同数量可能来自不同的批次
     * 2.店铺虚拟库存对应批次的商品规格剩余数量扣减
     * 3.店铺商品规格库存对应的库存减少
     * 4.订单中已售后的商品不扣减库存
     * PS:不一定有足够的虚拟库存扣减
     * 店铺A产生了商品销售报表  店铺的上级店铺们产生店铺A的分店销售报表
     *
     * @Update yeJH
     *
     * @param  orderId       订单id
     * @param  goodsTempId   订单快照id  （订单售后失败）
     * @return void
     **/
    void orderClosure(Long orderId, Long goodsTempId);
}
