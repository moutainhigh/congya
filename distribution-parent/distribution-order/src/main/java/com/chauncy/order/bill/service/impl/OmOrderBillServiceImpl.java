package com.chauncy.order.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.order.BillSettlementEnum;
import com.chauncy.common.enums.order.BillStatusEnum;
import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.UserTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.DateFormatUtil;
import com.chauncy.data.bo.order.log.AddAccountLogBo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.order.bill.OmBillRelGoodsTempPo;
import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.rel.SmStoreBankCardPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.update.BillBatchAuditDto;
import com.chauncy.data.dto.manage.order.bill.update.BillCashOutDto;
import com.chauncy.data.dto.manage.order.bill.update.BillDeductionDto;
import com.chauncy.data.dto.supplier.order.CreateStoreBillDto;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.order.bill.OmBillRelGoodsTempMapper;
import com.chauncy.data.mapper.order.bill.OmOrderBillMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.store.rel.SmStoreBankCardMapper;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.order.bill.BillSettlementVo;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        //账单结算进度为四个进度
        int billSettlementStep = 4;
        BillDetailVo billDetailVo = omOrderBillMapper.findBillDetail(id);
        if(null == billDetailVo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }
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
        for(int i=1; i<billSettlementStep; i++) {
            BillSettlementVo billSettlementVo = new BillSettlementVo();
            billSettlementVo.setId(i);
            billSettlementVo.setName(BillSettlementEnum.getById(i).getName());
            if (i == (BillStatusEnum.TO_BE_WITHDRAWN.getId())) {
                //待提现状态
                billSettlementVo.setDateTime(billDetailVo.getCreateTime());
            } else if (i == (BillStatusEnum.TO_BE_AUDITED.getId())) {
                //待审核状态
                billSettlementVo.setDateTime(billDetailVo.getWithdrawalTime());
            } else if (i == (BillStatusEnum.PROCESSING.getId())) {
                if(billDetailVo.getBillStatus().equals(BillStatusEnum.AUDIT_FAIL.getId())) {
                    //审核失败
                    billSettlementVo.setName(BillSettlementEnum.getById(i).getName());
                    billSettlementVo.setDateTime(billDetailVo.getSettlementTime());
                    break;
                }
                //处理中状态
                billSettlementVo.setDateTime(billDetailVo.getProcessingTime());
            } else {
                //结算完成
                billSettlementVo.setDateTime(billDetailVo.getSettlementTime());
            }
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
     * @param billBatchAuditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAudit(BillBatchAuditDto billBatchAuditDto) {
        List<Long> idList = Arrays.asList(billBatchAuditDto.getIds());
        idList.forEach(id -> {
            OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(id);
            if(null == omOrderBillPo) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "的记录不存在");
            } else if(!omOrderBillPo.getBillStatus().equals(BillStatusEnum.TO_BE_AUDITED.getId())) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "账单不是待审核状态");
            }
        });
        if(billBatchAuditDto.getEnabled()) {
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
            updateWrapper.set("reject_reason", billBatchAuditDto.getRejectReason());
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

        //生成流水
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
        int storeSum = omOrderBillMapper.getStoreSumNeedCreateBill(endDate);
        //一次性只处理1000条数据
        for(int pageNo = 1; pageNo <= storeSum / 1000; pageNo++) {
            PageHelper.startPage(pageNo, 1000);
            List<Long> storeIdList = omOrderBillMapper.getStoreNeedCreateBill(endDate);
            storeIdList.forEach(storeId -> createStoreBill(endDate, storeId, billType));
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
        createStoreBill(createStoreBillDto.getEndDate(), sysUserPo.getStoreId(), createStoreBillDto.getBillType());
    }

    /**
     * 根据店铺id，账单日期创建账单
     * @param endDate
     * @param storeId
     */
    private void createStoreBill(LocalDate endDate, Long storeId, Integer billType) {
        SmStorePo smStorePo = smStoreMapper.selectById(storeId);
        if(null == smStorePo) {
            return;
        }
        //结算周期
        Integer paymentBillSettlementCycle = smStorePo.getPaymentBillSettlementCycle();
        //按结算周期往前推几周
        LocalDate startDate = endDate.plusDays(-7L * paymentBillSettlementCycle.longValue());
        //创建账单
        OmOrderBillPo omOrderBillPo = new OmOrderBillPo();
        omOrderBillPo.setYear(endDate.getYear());
        omOrderBillPo.setMonthDay(endDate.getMonthValue() + String.valueOf(endDate.getDayOfMonth()));
        //总货款/总利润
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        omOrderBillPo.setTotalAmount(totalAmount);
        omOrderBillPo.setBillStatus(BillStatusEnum.TO_BE_WITHDRAWN.getId());
        omOrderBillPo.setStoreId(storeId);
        omOrderBillPo.setBillType(billType);
        omOrderBillPo.setCreateBy(String.valueOf(storeId));
        omOrderBillMapper.insert(omOrderBillPo);
        //查询时间段内订单
        List<Map<String, Object>> orderIdList = getBillOrderList(startDate, endDate, storeId, billType);
        for(Map<String, Object> orderMap : orderIdList) {
            QueryWrapper<OmGoodsTempPo> goodsTempQueryWrapper = new QueryWrapper();
            goodsTempQueryWrapper.lambda().eq(OmGoodsTempPo::getOrderId, orderMap.get("id"));
            List<OmGoodsTempPo> omGoodsTempPoList = omGoodsTempMapper.selectList(goodsTempQueryWrapper);
            for(OmGoodsTempPo omGoodsTempPo : omGoodsTempPoList) {
                OmBillRelGoodsTempPo omBillRelGoodsTempPo = new OmBillRelGoodsTempPo();
                omBillRelGoodsTempPo.setBillId(omOrderBillPo.getId());
                omBillRelGoodsTempPo.setGoodsTempId(omGoodsTempPo.getId());
                if (BillTypeEnum.PAYMENT_BILL.getId().equals(billType)) {
                    //货款账单 供应价 * 数量
                    BigDecimal amount = BigDecimalUtil.safeMultiply(omGoodsTempPo.getSupplierPrice(), omGoodsTempPo.getNumber());
                    omBillRelGoodsTempPo.setTotalAmount(amount);
                    totalAmount = BigDecimalUtil.safeAdd(totalAmount, amount);
                } else if (BillTypeEnum.PROFIT_BILL.getId().equals(billType)) {
                    //货款账单 商品利润比例 * 商品售价 * 店铺利润配置比例
                    BigDecimal profitRate = BigDecimalUtil.safeDivide(omGoodsTempPo.getProfitRate(),new BigDecimal(100));
                    BigDecimal incomeRate = BigDecimalUtil.safeDivide(new BigDecimal(orderMap.get("incomeRate").toString()), new BigDecimal(100));
                    BigDecimal amount = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(profitRate,omGoodsTempPo.getSellPrice()), incomeRate);
                    omBillRelGoodsTempPo.setTotalAmount(amount);
                    totalAmount = BigDecimalUtil.safeAdd(totalAmount, amount);
                }
                omBillRelGoodsTempPo.setCreateBy(String.valueOf(storeId));
                omBillRelGoodsTempMapper.insert(omBillRelGoodsTempPo);
            }
        }
        //更新总利润/总货款
        omOrderBillPo.setTotalAmount(totalAmount);
        omOrderBillMapper.updateById(omOrderBillPo);

        //店铺团队合作：如果是利润账单，店铺绑定的上级店铺也能看到
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
        //todo  售后时间
        queryWrapper.lambda()
                .ge(OmOrderPo::getCreateTime, startDate)
                .le(OmOrderPo::getCreateTime, endDate)
                .select(OmOrderPo::getId)
                .select(OmOrderPo::getIncomeRate);
        return omOrderService.listMaps(queryWrapper);
    }
}
