package com.chauncy.order.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.ServiceConstant;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.order.BillSettlementEnum;
import com.chauncy.common.enums.order.BillSettlementStatusEnum;
import com.chauncy.common.enums.order.BillStatusEnum;
import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.DateFormatUtil;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.bo.order.log.BillRelOrderBo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.order.bill.OmBillRelGoodsTempPo;
import com.chauncy.data.domain.po.order.bill.OmBillRelStorePo;
import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.rel.SmStoreBankCardPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchOrderReportDto;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;
import com.chauncy.data.dto.manage.order.bill.update.BillCashOutDto;
import com.chauncy.data.dto.manage.order.bill.update.BillDeductionDto;
import com.chauncy.data.dto.supplier.order.CreateStoreBillDto;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.bill.OmBillRelGoodsTempMapper;
import com.chauncy.data.mapper.order.bill.OmBillRelStoreMapper;
import com.chauncy.data.mapper.order.bill.OmOrderBillMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.store.rel.SmStoreBankCardMapper;
import com.chauncy.data.mapper.store.rel.SmStoreRelStoreMapper;
import com.chauncy.data.vo.manage.order.bill.*;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 账单表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderBillServiceImpl extends AbstractService<OmOrderBillMapper, OmOrderBillPo> implements IOmOrderBillService {

    @Autowired
    private OmOrderBillMapper omOrderBillMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private OmGoodsTempMapper omGoodsTempMapper;

    @Autowired
    private OmBillRelGoodsTempMapper omBillRelGoodsTempMapper;

    @Autowired
    private SmStoreBankCardMapper smStoreBankCardMapper;

    @Autowired
    private SmStoreRelStoreMapper smStoreRelStoreMapper;

    @Autowired
    private OmBillRelStoreMapper omBillRelStoreMapper;

    @Autowired
    private IOmAccountLogService omAccountLogService;

    @Autowired
    private IOmOrderService omOrderService;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 查询账单列表
     * 根据年，期数，提现状态，时间，审核状态，金额范围等条件查询
     *
     * @param searchBillDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<BillBaseInfoVo> searchBillPaging(SearchBillDto searchBillDto) {
        //获取当前店铺用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null != storeId) {
            searchBillDto.setStoreId(storeId);
        }
        Integer pageNo = searchBillDto.getPageNo() == null ? defaultPageNo : searchBillDto.getPageNo();
        Integer pageSize  = searchBillDto.getPageSize() == null ? defaultPageSize : searchBillDto.getPageSize();

        PageInfo<BillBaseInfoVo> billBaseInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omOrderBillMapper.searchBillPaging(searchBillDto));

        return billBaseInfoVoPageInfo;
    }

    /**
     * 查询账单基本信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BillDetailVo findBillDetail(Long id) {
        OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(id);
        if(null == omOrderBillPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }

        BillDetailVo billDetailVo = omOrderBillMapper.findBillDetail(id);
        //账单结算进度为四个进度
        int billSettlementStep = 4;
        //账单状态
        String billStatusName = BillStatusEnum.getById(billDetailVo.getBillStatus()).getName();
        billDetailVo.setBillStatusName(billStatusName);

        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            billDetailVo.setIsStoreUser(false);
        } else {
            billDetailVo.setIsStoreUser(true);
        }
        //获取结算进度
        List<BillSettlementVo> billSettlementVoList = new ArrayList<>();
        for(int i=1; i<=billSettlementStep; i++) {
            BillSettlementVo billSettlementVo = new BillSettlementVo();
            billSettlementVo.setId(i);
            billSettlementVo.setName(BillSettlementEnum.getById(i).getName());
            billSettlementVo.setStatus(BillSettlementStatusEnum.COMPLETED.getId());
            billSettlementVo.setStatusName(BillSettlementStatusEnum.COMPLETED.getName());
            if (i == (BillStatusEnum.TO_BE_WITHDRAWN.getId())) {
                //待提现状态
                billSettlementVo.setDateTime(billDetailVo.getCreateTime());
            } else if (i == (BillStatusEnum.TO_BE_AUDITED.getId())) {
                //待审核状态
                billSettlementVo.setDateTime(billDetailVo.getWithdrawalTime());
                if(billDetailVo.getBillStatus().equals(BillStatusEnum.TO_BE_WITHDRAWN.getId())) {
                    //账单待提现  未开始
                    billSettlementVo.setStatus(BillSettlementStatusEnum.NOT_STARTED.getId());
                    billSettlementVo.setStatusName(BillSettlementStatusEnum.NOT_STARTED.getName());
                }
            } else if (i == (BillStatusEnum.PROCESSING.getId())) {
                //处理中状态
                billSettlementVo.setDateTime(billDetailVo.getProcessingTime());
                if(billDetailVo.getBillStatus() < i) {
                    //账单待提现或者待审核  未开始
                    billSettlementVo.setStatus(BillSettlementStatusEnum.NOT_STARTED.getId());
                    billSettlementVo.setStatusName(BillSettlementStatusEnum.NOT_STARTED.getName());
                } else if(billDetailVo.getBillStatus().equals(BillStatusEnum.AUDIT_FAIL.getId())) {
                    //账单审核失败 未完成
                    billSettlementVo.setStatus(BillSettlementStatusEnum.INCOMPLETE.getId());
                    billSettlementVo.setStatusName(BillSettlementStatusEnum.INCOMPLETE.getName());
                }
            } else {
                if(billDetailVo.getBillStatus() < i) {
                    //账单待提现或者待审核、处理中  未开始
                    billSettlementVo.setStatus(BillSettlementStatusEnum.NOT_STARTED.getId());
                    billSettlementVo.setStatusName(BillSettlementStatusEnum.NOT_STARTED.getName());
                }
                if(billDetailVo.getBillStatus().equals(BillStatusEnum.AUDIT_FAIL.getId())) {
                    //审核失败
                    billSettlementVo.setName(BillSettlementEnum.AUDIT_FAIL.getName());
                }
                //结算完成
                billSettlementVo.setDateTime(billDetailVo.getSettlementTime());
            }
            billSettlementVoList.add(billSettlementVo);
        }
        billDetailVo.setBillSettlementVoList(billSettlementVoList);
        return billDetailVo;
    }


    /**
     * 商家店铺确定提现 并绑定银行卡
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void billCashOut(BillCashOutDto billCashOutDto) {
        OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(billCashOutDto.getBillId());
        /*if(null == omOrderBillPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }*/
        //获取当前店铺用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null == storeId || !storeId.equals(omOrderBillPo.getStoreId())) {
            //当前登录用户跟操作不匹配
            throw  new ServiceException(ResultCode.FAIL, "操作失败");
        }
        /*if(!omOrderBillPo.equals(BillStatusEnum.TO_BE_WITHDRAWN.getId())) {
            //账单不是待提现状态
            throw  new ServiceException(ResultCode.FAIL, "账单不是待提现状态");
        }*/
        SmStoreBankCardPo smStoreBankCardPo = smStoreBankCardMapper.selectById(billCashOutDto.getCardId());
        if(null == smStoreBankCardPo) {
            //银行卡不存在
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", omOrderBillPo.getId());
        updateWrapper.set("bill_status", BillStatusEnum.TO_BE_AUDITED.getId());
        updateWrapper.set("withdrawal_time", LocalDateTime.now());
        updateWrapper.set("card_id", smStoreBankCardPo.getId());
        updateWrapper.set("opening_bank", smStoreBankCardPo.getOpeningBank());
        updateWrapper.set("account", smStoreBankCardPo.getAccount());
        updateWrapper.set("cardholder", smStoreBankCardPo.getCardholder());
        this.update(updateWrapper);
    }

    /**
     * 平台批量审核账单
     *
     * @param batchAuditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAudit(BatchAuditDto batchAuditDto) {
        List<Long> idList = Arrays.asList(batchAuditDto.getIds());
        idList.forEach(id -> {
            OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(id);
            if(null == omOrderBillPo) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "的记录不存在");
            } else if(!omOrderBillPo.getBillStatus().equals(BillStatusEnum.TO_BE_AUDITED.getId())) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "账单不是待审核状态");
            }
        });
        if(batchAuditDto.getEnabled()) {
            //审核通过状态改为处理中
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.in("id", idList);
            updateWrapper.set("bill_status", BillStatusEnum.PROCESSING.getId());
            //处理中时间
            updateWrapper.set("processing_time", LocalDateTime.now());
            this.update(updateWrapper);
        } else {
            //审核驳回状态改为审核失败
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.in("id", idList);
            updateWrapper.set("bill_status", BillStatusEnum.AUDIT_FAIL.getId());
            //结算时间
            updateWrapper.set("settlement_time", LocalDateTime.now());
            //驳回原因
            updateWrapper.set("reject_reason", batchAuditDto.getRejectReason());
            this.update(updateWrapper);
        }
    }

    /**
     * 平台账单扣款
     *
     * @param billDeductionDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void billDeduction(BillDeductionDto billDeductionDto) {
        OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(billDeductionDto.getBillId());
        if(billDeductionDto.getDeductedAmount().compareTo(omOrderBillPo.getTotalAmount()) > -1) {
            //扣除金额大于等于账单总货款/总利润
            throw new ServiceException(ResultCode.NO_EXISTS, "扣除金额大于等于账单总额");
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", billDeductionDto.getBillId());
        updateWrapper.set("deducted_amount", billDeductionDto.getDeductedAmount());
        updateWrapper.set("deducted_remark", billDeductionDto.getDeductedRemark());
        this.update(updateWrapper);
    }

    /**
     * 平台标记状态为处理中的店铺账单为已处理
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void billSettlementSuccess(Long id) {

        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();

        OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(id);
        if(null == omOrderBillPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }
        if(!omOrderBillPo.getBillStatus().equals(BillStatusEnum.PROCESSING.getId())) {
            throw new ServiceException(ResultCode.NO_EXISTS, "账单不是处理中状态");
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", omOrderBillPo.getId());
        updateWrapper.set("settlement_time", LocalDateTime.now());
        updateWrapper.set("bill_status", BillStatusEnum.SETTLEMENT_SUCCESS.getId());
        this.update(updateWrapper);

        //1.店铺利润、货款账单提现生成流水
        AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
        addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.STORE_WITHDRAWAL);
        addAccountLogBo.setRelId(omOrderBillPo.getId());
        addAccountLogBo.setOperator(sysUserPo.getUsername());
        omAccountLogService.saveAccountLog(addAccountLogBo);
    }

    /**
     * 批量创建货款、利润账单
     * @param billType  账单类型  1 货款账单  2 利润账单
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCreateStoreBill(Integer billType) {
        //获取当前时间的上一周的最后一天  直接用周数-1 每年的第一周会有问题
        /*LocalDate localDate = LocalDate.now();
        Date date = DateFormatUtil.getLastDayOfWeek(localDate.getYear(),
                DateFormatUtil.getWeekOfYear(DateFormatUtil.localDateToDate(localDate)) - 1);
        LocalDate lastDate = DateFormatUtil.datetoLocalDate(date);*/
        LocalDate localDate = LocalDate.now();
        //获取上一周所在周
        LocalDate lastWeek = localDate.plusDays(-7L);
        //上一周时间所在周的结束日期
        Date date = DateFormatUtil.getLastDayOfWeek(DateFormatUtil.localDateToDate(lastWeek));
        LocalDate endDate = DateFormatUtil.datetoLocalDate(date);
        //获取需要创建账单的店铺的数量
        int storeSum = omOrderBillMapper.getStoreSumNeedCreateBill(billType, endDate, null);
        //一次性只处理1000条数据
        if(storeSum > 0) {
            for (int pageNo = 1; pageNo <= storeSum / 1000 + 1; pageNo++) {
                PageHelper.startPage(pageNo, 1000);
                List<Long> storeIdList = omOrderBillMapper.getStoreNeedCreateBill(billType, endDate, null);
                    storeIdList.forEach(storeId -> {
                        try {
                            createStoreBill(endDate, storeId, billType);
                        } catch (Exception e) {
                            if(BillTypeEnum.PROFIT_BILL.getId().equals(billType)) {
                                log.error("店铺id为" + storeId + "的店铺生成利润账单报错");
                            } else if(BillTypeEnum.PAYMENT_BILL.getId().equals(billType)) {
                                log.error("店铺id为" + storeId + "的店铺生成货款账单报错");
                            }
                        }
                    });
            }
        }
    }

    /**
     * 根据时间创建货款/利润账单
     * @param createStoreBillDto
     * endDate   需要创建账单的那一周   任何一天都可以
     * billType  账单类型  1 货款账单  2 利润账单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createStoreBillByDate(CreateStoreBillDto createStoreBillDto) {
        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }
        //时间所在周的结束日期
        Date date = DateFormatUtil.getLastDayOfWeek(DateFormatUtil.localDateToDate(createStoreBillDto.getEndDate()));
        createStoreBillDto.setEndDate(DateFormatUtil.datetoLocalDate(date));
        //判断这一周需不需要生成账单
        int storeSum = omOrderBillMapper.getStoreSumNeedCreateBill(createStoreBillDto.getBillType(),
                createStoreBillDto.getEndDate(), sysUserPo.getStoreId());
        if(storeSum > 0) {
            createStoreBill(createStoreBillDto.getEndDate(), sysUserPo.getStoreId(), createStoreBillDto.getBillType());
        } else {
            throw new ServiceException(ResultCode.FAIL, "选择的时间不需要生成账单");
        }

    }

    /**
     * 根据店铺id，账单日期创建账单
     * @param endDate   账单最后一天
     * @param storeId   账单所属店铺
     */
    private void createStoreBill(LocalDate endDate, Long storeId, Integer billType) {
        SmStorePo smStorePo = smStoreMapper.selectById(storeId);
        if(null == smStorePo) {
            return;
        }
        //结算周期
        Integer settlementCycle = BillTypeEnum.PAYMENT_BILL.getId().equals(billType) ?
                smStorePo.getPaymentBillSettlementCycle() : smStorePo.getIncomeBillSettlementCycle();
        //按结算周期往前推几周
        LocalDate startDate = endDate.plusDays(-7L * settlementCycle.longValue());
        //创建账单
        OmOrderBillPo omOrderBillPo = new OmOrderBillPo();
        omOrderBillPo.setYear(endDate.getYear());
        String month = endDate.getMonthValue() > 10 ? "0" + endDate.getMonthValue() : String.valueOf(endDate.getMonthValue());
        omOrderBillPo.setMonthDay(month + String.valueOf(endDate.getDayOfMonth()));
        //总货款/总利润
        BigDecimal totalAmount = BigDecimal.ZERO;
        omOrderBillPo.setTotalAmount(totalAmount);
        //商品总数量
        Integer totalNum = 0;
        omOrderBillPo.setTotalNum(totalNum);
        omOrderBillPo.setBillStatus(BillStatusEnum.TO_BE_WITHDRAWN.getId());
        omOrderBillPo.setStoreId(storeId);
        omOrderBillPo.setStartDate(startDate);
        omOrderBillPo.setEndDate(endDate);
        omOrderBillPo.setBillType(billType);
        omOrderBillPo.setCreateBy(String.valueOf(storeId));
        omOrderBillMapper.insert(omOrderBillPo);
        //查询时间段内属于账单的订单
        List<BillRelOrderBo> billRelOrderBoList = omOrderBillMapper.getBillOrderList(startDate, endDate, storeId, billType);
        for(BillRelOrderBo billRelOrderBo : billRelOrderBoList) {
            QueryWrapper<OmGoodsTempPo> goodsTempQueryWrapper = new QueryWrapper();
            goodsTempQueryWrapper.lambda().eq(OmGoodsTempPo::getOrderId, billRelOrderBo.getId());
            List<OmGoodsTempPo> omGoodsTempPoList = omGoodsTempMapper.selectList(goodsTempQueryWrapper);
            for(OmGoodsTempPo omGoodsTempPo : omGoodsTempPoList) {
                if (omGoodsTempPo.getCanAfterSale()) {
                    OmBillRelGoodsTempPo omBillRelGoodsTempPo = new OmBillRelGoodsTempPo();
                    omBillRelGoodsTempPo.setBillId(omOrderBillPo.getId());
                    omBillRelGoodsTempPo.setGoodsTempId(omGoodsTempPo.getId());
                    totalNum += omGoodsTempPo.getNumber();
                    if (BillTypeEnum.PAYMENT_BILL.getId().equals(billType)) {
                        //货款账单 供应价 * 数量
                        BigDecimal amount = BigDecimalUtil.safeMultiply(omGoodsTempPo.getSupplierPrice(), omGoodsTempPo.getNumber());
                        omBillRelGoodsTempPo.setTotalAmount(amount);
                        totalAmount = BigDecimalUtil.safeAdd(totalAmount, amount);
                    } else if (BillTypeEnum.PROFIT_BILL.getId().equals(billType)) {
                        //利润账单 商品数量 * 商品利润比例 * 商品售价 * 店铺利润配置比例
                        BigDecimal profitRate = BigDecimalUtil.safeDivide(omGoodsTempPo.getProfitRate(), new BigDecimal(100));
                        BigDecimal incomeRate = BigDecimalUtil.safeDivide(
                                new BigDecimal(billRelOrderBo.getIncomeRate().toString()),
                                new BigDecimal(100));
                        BigDecimal amount = BigDecimalUtil.safeMultiply(
                                BigDecimalUtil.safeMultiply(profitRate, omGoodsTempPo.getSellPrice()),
                                BigDecimalUtil.safeMultiply(incomeRate, new BigDecimal(omGoodsTempPo.getNumber())));
                        omBillRelGoodsTempPo.setTotalAmount(amount);
                        totalAmount = BigDecimalUtil.safeAdd(totalAmount, amount);
                    }
                    omBillRelGoodsTempPo.setCreateBy(String.valueOf(storeId));
                    omBillRelGoodsTempMapper.insert(omBillRelGoodsTempPo);
                }
            }
        }
        //更新商品总数
        omOrderBillPo.setTotalNum(totalNum);
        //更新总利润/总货款
        omOrderBillPo.setTotalAmount(totalAmount);
        omOrderBillMapper.updateById(omOrderBillPo);

        //团队合作的店铺  该店铺的利润账单作为上级绑定的店铺的交易报表
        if (BillTypeEnum.PROFIT_BILL.getId().equals(billType)) {
            createBillRelStore(omOrderBillPo, storeId, ServiceConstant.TEAM_WORK_LEVEL);
        }
    }

    /**
     * 团队合作的店铺  该店铺的利润账单作为上级绑定的店铺的交易报表
     * @param omOrderBillPo   账单
     * @param storeId  账单所属店铺
     */
    private void createBillRelStore(OmOrderBillPo omOrderBillPo, Long storeId, Integer teamWorkLevel) {
        if(teamWorkLevel > 0) {
            //查找店铺storeId在账单周期内绑定的关系为团队合作的上级店铺
            Long parentStoreId = smStoreRelStoreMapper.getTeamWorkParentStoreId(
                    storeId, omOrderBillPo.getStartDate(), omOrderBillPo.getEndDate().plusDays(1));
            if (null != parentStoreId) {
                OmBillRelStorePo omBillRelStorePo = new OmBillRelStorePo();
                omBillRelStorePo.setBillId(omOrderBillPo.getId());
                omBillRelStorePo.setStoreId(parentStoreId);
                omBillRelStorePo.setCreateBy(omOrderBillPo.getCreateBy());
                omBillRelStoreMapper.insert(omBillRelStorePo);
                //递归调用 团队合作的店铺层级最多为5层
                teamWorkLevel--;
                createBillRelStore(omOrderBillPo, parentStoreId, teamWorkLevel);
            }
        }
    }

    /**
     * 查询时间段内属于账单的订单
     * @param endDate
     * @param storeId
     * @param billType
     * @return
     */
    private List<Map<String, Object>> getBillOrderList(LocalDate startDate, LocalDate endDate, Long storeId, Integer billType) {
        QueryWrapper<OmOrderPo> queryWrapper = new QueryWrapper<>();
        if(BillTypeEnum.PAYMENT_BILL.getId().equals(billType)) {
            //货款账单
            queryWrapper.lambda().eq(OmOrderPo::getStoreId, storeId);
        } else if(BillTypeEnum.PROFIT_BILL.getId().equals(billType)) {
            //利润账单
            queryWrapper.lambda().eq(OmOrderPo::getUserStoreId, storeId);
        }
        queryWrapper.lambda()
                //.eq(OmOrderPo::getStatus, OrderStatusEnum.ALREADY_EVALUATE)
                .ge(OmOrderPo::getAfterSaleDeadline, startDate)
                .le(OmOrderPo::getAfterSaleDeadline, endDate.plusDays(1))
                .select(OmOrderPo::getId)
                .select(OmOrderPo::getIncomeRate);
        return omOrderService.listMaps(queryWrapper);
    }

    /**
     * 查询订单交易报表  团队合作利润账单
     *
     * @param searchOrderReportDto
     * @return
     */
    @Override
    public PageInfo<BillReportVo> searchBillReportPaging(SearchOrderReportDto searchOrderReportDto) {
        //获取当前店铺用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null != storeId) {
            searchOrderReportDto.setStoreId(storeId);
        } else {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }

        Integer pageNo = searchOrderReportDto.getPageNo() == null ? defaultPageNo : searchOrderReportDto.getPageNo();
        Integer pageSize  = searchOrderReportDto.getPageSize() == null ? defaultPageSize : searchOrderReportDto.getPageSize();

        PageInfo<BillReportVo> billReportVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omOrderBillMapper.searchBillReportPaging(searchOrderReportDto));

        return billReportVoPageInfo;
    }


    /**
     * 查询账单关联订单商品详情
     *
     * @param baseSearchPagingDto
     * @param id
     * @return
     */
    @Override
    public PageInfo<BillRelGoodsTempVo> findRelBillDetail(BaseSearchPagingDto baseSearchPagingDto, Long id) {

        Integer pageNo = baseSearchPagingDto.getPageNo() == null ? defaultPageNo : baseSearchPagingDto.getPageNo();
        Integer pageSize  = baseSearchPagingDto.getPageSize() == null ? defaultPageSize : baseSearchPagingDto.getPageSize();

        PageInfo<BillRelGoodsTempVo> billRelGoodsTempVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omOrderBillMapper.findRelBillDetail(id));

        return billRelGoodsTempVoPageInfo;
    }
}
