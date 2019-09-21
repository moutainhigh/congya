package com.chauncy.order.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.log.*;
import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.UserTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.bo.order.log.AccountLogBo;
import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftPo;
import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.order.OmUserWithdrawalPo;
import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.domain.po.order.log.OmAccountLogPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.log.SearchUserLogDto;
import com.chauncy.data.dto.app.order.log.UserWithdrawalDto;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchStoreLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchUserWithdrawalDto;
import com.chauncy.data.mapper.activity.gift.AmGiftMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftOrderMapper;
import com.chauncy.data.mapper.afterSale.OmAfterSaleOrderMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.order.OmUserWithdrawalMapper;
import com.chauncy.data.mapper.order.bill.OmOrderBillMapper;
import com.chauncy.data.mapper.order.log.OmAccountLogMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.manage.order.log.*;
import com.chauncy.order.log.service.IOmUserWithdrawalService;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 * 账目流水表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmAccountLogServiceImpl extends AbstractService<OmAccountLogMapper, OmAccountLogPo> implements IOmAccountLogService {

    @Autowired
    private OmAccountLogMapper omAccountLogMapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private OmAfterSaleOrderMapper omAfterSaleOrderMapper;

    @Autowired
    private OmOrderBillMapper omOrderBillMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private UmUserMapper umUserMapper;

    @Autowired
    private OmUserWithdrawalMapper omUserWithdrawalMapper;

    @Autowired
    private IPayOrderMapper payOrderMapper;

    @Autowired
    private OmOrderMapper omOrderMapper;

    @Autowired
    private AmGiftOrderMapper amGiftOrderMapper;

    @Autowired
    private AmGiftMapper amGiftMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IOmUserWithdrawalService omUserWithdrawalService;


    /**
     * 平台流水
     *
     * @param searchPlatformLogDto
     * @return
     */
    @Override
    public PageInfo<SearchPlatformLogVo> searchPlatformLogPaging(SearchPlatformLogDto searchPlatformLogDto) {

        searchPlatformLogDto.setUserType(UserTypeEnum.PLATFORM.getId());
        Integer pageNo = searchPlatformLogDto.getPageNo()==null ? defaultPageNo : searchPlatformLogDto.getPageNo();
        Integer pageSize = searchPlatformLogDto.getPageSize()==null ? defaultPageSize : searchPlatformLogDto.getPageSize();

        PageInfo<SearchPlatformLogVo> searchPlatformLogVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omAccountLogMapper.searchPlatformLogPaging(searchPlatformLogDto));
        return searchPlatformLogVoPageInfo;
    }


    /**
     * 保存流水
     * 部分流水不考虑  用户线上资金  不考虑用户线上支出收入流水
     * 如:用户订单下单  用户提现
     * @param addAccountLogBo
     * @return
     */
    @Override
    public void saveAccountLog(AddAccountLogBo addAccountLogBo) {

        switch(addAccountLogBo.getLogTriggerEventEnum()) {
            case STORE_WITHDRAWAL:
                //1.店铺利润、货款账单提现
                this.storeWithdrawal(addAccountLogBo);
                break;
            case APP_WITHDRAWAL:
                //APP用户提现红包
                this.appWithdrawal(addAccountLogBo);
                break;
            case APP_ORDER:
                //订单下单
                this.appOrder(addAccountLogBo);
                break;
            case GIFT_RECHARGE:
                //礼包充值  获得购物券，积分
                this.giftRecharge(addAccountLogBo);
                break;
            case NEW_GIFT:
                //新人礼包领取  获得购物券，积分
                this.newGift(addAccountLogBo);
                break;
            case PLATFORM_GIVE:
                //系统赠送  没有赠送记录，直接在修改用户参数的时候调用生成流水的方法
                this.platformGive(addAccountLogBo);
                break;
            case ORDER_REFUND:
                //售后退款，订单按比例退回红包，购物券，没有记录退还多少，直接在退款时候计算调用生成流水的方法
                this.orderRefund(addAccountLogBo);
                break;
            case SHOPPING_REWARD:
                //购物奖励  订单售后时间到，下单用户本身有购物券积分奖励，上两级，下一级有积分奖励
                //this.shoppingReward
        }
    }

    /**
     * 获取支出流水/获取没有支出来源的收入流水（如平台订单收入来自用户线上资金，礼包充值）
     * @param addAccountLogBo  流水关联信息
     * @param userTypeEnum  用户类型
     * @param accountTypeEnum   账目类型
     * @param logTypeEnum  流水类型
     * @return
     */
    private OmAccountLogPo getFromOmAccountLogPo(AddAccountLogBo addAccountLogBo, UserTypeEnum userTypeEnum, AccountTypeEnum accountTypeEnum,
                                                 LogTypeEnum logTypeEnum) {
        OmAccountLogPo fromOmAccountLogPo = new OmAccountLogPo();
        //流水关联的账单，订单id
        fromOmAccountLogPo.setOmRelId(addAccountLogBo.getRelId());
        fromOmAccountLogPo.setCreateBy(addAccountLogBo.getOperator());
        //流水类型
        fromOmAccountLogPo.setLogType(logTypeEnum.getName());
        //流水用户类型
        fromOmAccountLogPo.setUserType(userTypeEnum.getId());
        //账目类型
        fromOmAccountLogPo.setAccountType(accountTypeEnum.getId());
        return fromOmAccountLogPo;
    }

    /**
     * 获取收入流水
     * @param fromOmAccountLogPo  流水来源信息
     * @param userTypeEnum  用户类型
     * @param accountTypeEnum   账目类型
     * @param logTypeEnum  流水类型
     * @return
     */
    private OmAccountLogPo getToOmAccountLogPo(OmAccountLogPo fromOmAccountLogPo, UserTypeEnum userTypeEnum, AccountTypeEnum accountTypeEnum,
                                               LogTypeEnum logTypeEnum) {
        OmAccountLogPo toOmAccountLogPo = new OmAccountLogPo();
        //流水关联的账单，订单id
        toOmAccountLogPo.setOmRelId(fromOmAccountLogPo.getOmRelId());
        toOmAccountLogPo.setCreateBy(fromOmAccountLogPo.getCreateBy());
        toOmAccountLogPo.setParentId(fromOmAccountLogPo.getId());
        //流水发生金额
        toOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(fromOmAccountLogPo.getAmount(), -1));
        //流水类型
        toOmAccountLogPo.setLogType(logTypeEnum.getName());
        //流水用户类型
        toOmAccountLogPo.setUserType(userTypeEnum.getId());
        //账目类型
        toOmAccountLogPo.setAccountType(accountTypeEnum.getId());
        return toOmAccountLogPo;
    }

    /**
     * 流水触发事件：店铺利润、货款账单提现
     * 过程：平台给店铺线下转账
     * 店铺线上资金（提现金额）+   平台线上资金（提现金额）-
     */
    private void storeWithdrawal (AddAccountLogBo addAccountLogBo) {
        OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(addAccountLogBo.getRelId());
        if (null != omOrderBillPo) {
            //平台 线上资金 支出流水
            OmAccountLogPo fromOmAccountLogPo = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.PLATFORM,
                    AccountTypeEnum.ONLINE_FUNDS, LogTypeEnum.EXPENDITURE);
            //todo  平台没有id
            //fromOmAccountLogPo.setUserId();
            //支付方式
            fromOmAccountLogPo.setPaymentWay(PaymentWayEnum.OFF_LINE.getId());
            //流水发生金额
            BigDecimal amount = BigDecimalUtil.safeSubtract(true, omOrderBillPo.getTotalAmount(), omOrderBillPo.getDeductedAmount());
            fromOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(amount, -1));
            //流水事由
            if(omOrderBillPo.getBillType().equals(BillTypeEnum.PAYMENT_BILL.getId())) {
                fromOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.PAYMENT_WITHDRAWAL.getId());
            } else if(omOrderBillPo.getBillType().equals(BillTypeEnum.PROFIT_BILL.getId())) {
                fromOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.PROFIT_WITHDRAWAL.getId());
            }
            fromOmAccountLogPo.setOmRelId(omOrderBillPo.getId());
            omAccountLogMapper.insert(fromOmAccountLogPo);
            //店铺 线上资金 收入流水
            OmAccountLogPo toOmAccountLogPo = getToOmAccountLogPo(fromOmAccountLogPo, UserTypeEnum.STORE,
                    AccountTypeEnum.ONLINE_FUNDS, LogTypeEnum.INCOME);
            SmStorePo smStorePo = smStoreMapper.selectById(omOrderBillPo.getStoreId());
            toOmAccountLogPo.setUserId(smStorePo.getId());
            //到账方式
            toOmAccountLogPo.setArrivalWay(PaymentWayEnum.OFF_LINE.getId());
            //流水事由
            if(omOrderBillPo.getBillType().equals(BillTypeEnum.PAYMENT_BILL.getId())) {
                toOmAccountLogPo.setLogMatter(StoreLogMatterEnum.PAYMENT_INCOME.getId());
            } else if(omOrderBillPo.getBillType().equals(BillTypeEnum.PROFIT_BILL.getId())) {
                toOmAccountLogPo.setLogMatter(StoreLogMatterEnum.PROFIT_INCOME.getId());
            }
            toOmAccountLogPo.setOmRelId(omOrderBillPo.getId());
            omAccountLogMapper.insert(toOmAccountLogPo);
        }
    }

    /**
     * 流水触发事件：订单售后
     * 过程：平台退款给APP用户
     * 平台线上资金（退款金额）-
     */
    private void platformRefund(AddAccountLogBo addAccountLogBo) {
        OmAfterSaleOrderPo omAfterSaleOrderPo = omAfterSaleOrderMapper.selectById(addAccountLogBo.getRelId());
        if (null != omAfterSaleOrderPo) {
            //平台 线上资金 支出流水
            OmAccountLogPo fromOmAccountLogPo = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.PLATFORM,
                    AccountTypeEnum.ONLINE_FUNDS, LogTypeEnum.EXPENDITURE);
            //todo  平台没有id
            //fromOmAccountLogPo.setUserId();
            OmOrderPo omOrderPo = omOrderMapper.selectById(omAfterSaleOrderPo.getOrderId());
            PayOrderPo payOrderPo = payOrderMapper.selectById(omOrderPo.getPayOrderId());
            //支付方式
            fromOmAccountLogPo.setPaymentWay(PaymentWayEnum.getByName(payOrderPo.getPayTypeCode()).getId());
            //流水发生金额
            fromOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(omAfterSaleOrderPo.getRefundMoney(), -1));
            //流水事由
            fromOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.ORDER_REFUND.getId());
            fromOmAccountLogPo.setOmRelId(omAfterSaleOrderPo.getId());
            omAccountLogMapper.insert(fromOmAccountLogPo);
        }
    }

    /**
     * 流水触发事件：系统赠送
     * 过程：用户积分，购物券，红包增加
     * 用户积分余额+  用户红包余额+  用户购物券余额+
     * @param addAccountLogBo
     */
    @Override
    public void platformGive(AddAccountLogBo addAccountLogBo) {
        UmUserPo umUserPo = umUserMapper.selectById(addAccountLogBo.getUmUserId());
        //积分+系统赠送
       if(null != addAccountLogBo.getMarginIntegral()
               && addAccountLogBo.getMarginIntegral().compareTo(BigDecimal.ZERO) > 0) {
            platformGive(addAccountLogBo,
                    umUserPo,
                    AccountTypeEnum.INTEGRATE,
                    addAccountLogBo.getMarginIntegral());
        }
        //红包+系统赠送
        if(null != addAccountLogBo.getMarginRedEnvelops()
                && addAccountLogBo.getMarginRedEnvelops().compareTo(BigDecimal.ZERO) > 0) {
            platformGive(addAccountLogBo,
                    umUserPo,
                    AccountTypeEnum.RED_ENVELOPS,
                    addAccountLogBo.getMarginRedEnvelops());
        }
        //购物券+系统赠送
        if(null != addAccountLogBo.getMarginShopTicket()
                && addAccountLogBo.getMarginShopTicket().compareTo(BigDecimal.ZERO) > 0) {
            platformGive(addAccountLogBo,
                    umUserPo,
                    AccountTypeEnum.SHOP_TICKET,
                    addAccountLogBo.getMarginShopTicket());
        }
    }

    /**
     * 系统赠送
     * @param addAccountLogBo  保存流水参数
     * @param umUserPo   系统赠送用户
     * @param accountTypeEnum   账目类型
     * @param margin  系统赠送数额
     */
    private void platformGive(AddAccountLogBo addAccountLogBo,
                              UmUserPo umUserPo,
                              AccountTypeEnum accountTypeEnum,
                              BigDecimal margin) {
        OmAccountLogPo toAccountLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                accountTypeEnum, LogTypeEnum.INCOME);
        toAccountLog.setUserId(umUserPo.getId());
        BigDecimal current = BigDecimal.ZERO;
        if(accountTypeEnum.equals(AccountTypeEnum.RED_ENVELOPS)) {
            current = umUserPo.getCurrentRedEnvelops();
        } else if (accountTypeEnum.equals(AccountTypeEnum.SHOP_TICKET)) {
            current = umUserPo.getCurrentShopTicket();
        } else if (accountTypeEnum.equals(AccountTypeEnum.INTEGRATE)) {
            current = umUserPo.getCurrentIntegral();
        }
        toAccountLog.setBalance(BigDecimalUtil.safeAdd(current, margin));
        toAccountLog.setLastBalance(current);
        //流水发生金额  系统赠送
        toAccountLog.setAmount(margin);
        //流水事由
        if(accountTypeEnum.equals(AccountTypeEnum.RED_ENVELOPS)) {
            toAccountLog.setLogMatter(RedEnvelopsLogMatterEnum.PLATFORM_GIVE.getId());
        } else if (accountTypeEnum.equals(AccountTypeEnum.SHOP_TICKET)) {
            toAccountLog.setLogMatter(ShopTicketLogMatterEnum.PLATFORM_GIVE.getId());
        } else if (accountTypeEnum.equals(AccountTypeEnum.INTEGRATE)) {
            toAccountLog.setLogMatter(IntegrateLogMatterEnum.PLATFORM_GIVE.getId());
        }
        //流水详情标题
        toAccountLog.setLogDetailTitle(LogDetailTitleEnum.CONGYA_OFFICIAL.getName());
        //流水详情当前状态
        toAccountLog.setLogDetailState(LogDetailStateEnum.DEPOSIT_WALLET.getId());
        //流水详情说明
        toAccountLog.setLogDetailExplain(LogDetailExplainEnum.PLATFORM_GIVE.getId());
        omAccountLogMapper.insert(toAccountLog);
    }

    /**
     * 流水触发事件：订单售后
     * 过程：
     * 1.售后退还用APP户红包购物券  用户红包，购物券增加
     * 2.平台退款给APP用户
     *
     * 用户红包余额+
     * 用户购物券余额+
     * 平台线上资金（退款金额）-
     */
    private void orderRefund(AddAccountLogBo addAccountLogBo) {
        UmUserPo umUserPo = umUserMapper.selectById(addAccountLogBo.getUmUserId());
        //红包+订单退还
        if(null != addAccountLogBo.getMarginRedEnvelops()
                && addAccountLogBo.getMarginRedEnvelops().compareTo(BigDecimal.ZERO) > 0) {
            accountReturn(addAccountLogBo,
                    umUserPo,
                    AccountTypeEnum.RED_ENVELOPS,
                    addAccountLogBo.getMarginRedEnvelops());
        }
        //购物券+订单退还
        if(null != addAccountLogBo.getMarginShopTicket()
                && addAccountLogBo.getMarginShopTicket().compareTo(BigDecimal.ZERO) > 0) {
            accountReturn(addAccountLogBo,
                    umUserPo,
                    AccountTypeEnum.SHOP_TICKET,
                    addAccountLogBo.getMarginShopTicket());
        }
        //平台退款
        platformRefund(addAccountLogBo);
    }

    /**
     * 售后退还
     * @param addAccountLogBo  保存流水参数
     * @param umUserPo   退款用户
     * @param accountTypeEnum   账目类型
     */
    private void accountReturn(AddAccountLogBo addAccountLogBo,
                              UmUserPo umUserPo,
                              AccountTypeEnum accountTypeEnum,
                             BigDecimal margin) {
        OmAccountLogPo toAccountLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                accountTypeEnum, LogTypeEnum.INCOME);
        toAccountLog.setUserId(umUserPo.getId());
        BigDecimal current = BigDecimal.ZERO;
        if(accountTypeEnum.equals(AccountTypeEnum.RED_ENVELOPS)) {
            current = umUserPo.getCurrentRedEnvelops();
        } else if (accountTypeEnum.equals(AccountTypeEnum.SHOP_TICKET)) {
            current = umUserPo.getCurrentShopTicket();
        }
        toAccountLog.setBalance(BigDecimalUtil.safeAdd(current, margin));
        toAccountLog.setLastBalance(current);
        //流水发生金额  售后退还
        toAccountLog.setAmount(margin);
        //流水事由
        if(accountTypeEnum.equals(AccountTypeEnum.RED_ENVELOPS)) {
            toAccountLog.setLogMatter(RedEnvelopsLogMatterEnum.AFTER_SALE_REFUND.getId());
        } else if (accountTypeEnum.equals(AccountTypeEnum.SHOP_TICKET)) {
            toAccountLog.setLogMatter(ShopTicketLogMatterEnum.AFTER_SALE_REFUND.getId());
        }
        //流水详情标题
        toAccountLog.setLogDetailTitle(LogDetailTitleEnum.REFUND.getName());
        //流水详情当前状态
        toAccountLog.setLogDetailState(LogDetailStateEnum.DEPOSIT_WALLET.getId());
        //流水详情说明
        toAccountLog.setLogDetailExplain(LogDetailExplainEnum.STORE_REFUND.getId());
        omAccountLogMapper.insert(toAccountLog);
    }


    /**
     * 流水触发事件：新人领取礼包
     * 过程：用户积分，购物券增加
     * 用户积分余额+    用户购物券余额+
     */
    private void newGift(AddAccountLogBo addAccountLogBo) {
        //新人礼包
        AmGiftPo amGiftPo = amGiftMapper.selectById(addAccountLogBo.getRelId());
        //APP用户 购物券 收入流水 购物券+新人礼包
        OmAccountLogPo toShopTicketLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                AccountTypeEnum.SHOP_TICKET, LogTypeEnum.INCOME);
        UmUserPo umUserPo = umUserMapper.selectById(addAccountLogBo.getRelId());
        toShopTicketLog.setUserId(umUserPo.getId());
        toShopTicketLog.setBalance(BigDecimalUtil.safeAdd(umUserPo.getCurrentShopTicket(), amGiftPo.getVouchers()));
        toShopTicketLog.setLastBalance(umUserPo.getCurrentShopTicket());
        //流水发生金额  礼包充值获得购物券
        toShopTicketLog.setAmount(amGiftPo.getVouchers());
        //流水事由
        toShopTicketLog.setLogMatter(ShopTicketLogMatterEnum.NEW_GIFT.getId());
        //流水详情标题
        toShopTicketLog.setLogDetailTitle(LogDetailTitleEnum.MEMBER_AWARD.getName());
        //流水详情当前状态
        toShopTicketLog.setLogDetailState(LogDetailStateEnum.DEPOSIT_WALLET.getId());
        //流水详情说明
        toShopTicketLog.setLogDetailExplain(LogDetailExplainEnum.MEMBER_AWARD.getId());
        omAccountLogMapper.insert(toShopTicketLog);
        //APP用户 积分 收入流水 积分+新人礼包
        OmAccountLogPo toIntegralsLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                AccountTypeEnum.INTEGRATE, LogTypeEnum.INCOME);
        toIntegralsLog.setUserId(umUserPo.getId());
        toIntegralsLog.setBalance(BigDecimalUtil.safeAdd(umUserPo.getCurrentIntegral(), amGiftPo.getIntegrals()));
        toIntegralsLog.setLastBalance(umUserPo.getCurrentIntegral());
        //流水发生金额  礼包充值获得积分
        toIntegralsLog.setAmount(amGiftPo.getIntegrals());
        //流水事由
        toIntegralsLog.setLogMatter(IntegrateLogMatterEnum.NEW_GIFT.getId());
        //流水详情标题
        toIntegralsLog.setLogDetailTitle(LogDetailTitleEnum.MEMBER_AWARD.getName());
        //流水详情当前状态
        toIntegralsLog.setLogDetailState(LogDetailStateEnum.DEPOSIT_WALLET.getId());
        //流水详情说明
        toIntegralsLog.setLogDetailExplain(LogDetailExplainEnum.MEMBER_AWARD.getId());
        omAccountLogMapper.insert(toIntegralsLog);
    }

    /**
     * 流水触发事件：app用户礼包充值
     * 过程：用户积分，购物券增加
     * 用户积分余额+    用户购物券余额+
     */
    private void giftRecharge(AddAccountLogBo addAccountLogBo) {
        //充值礼包订单
        AmGiftOrderPo amGiftOrderPo = amGiftOrderMapper.selectById(addAccountLogBo.getRelId());
        //APP用户 购物券 收入流水 购物券+礼包充值
        OmAccountLogPo toShopTicketLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                AccountTypeEnum.SHOP_TICKET, LogTypeEnum.INCOME);
        UmUserPo umUserPo = umUserMapper.selectById(amGiftOrderPo.getUserId());
        toShopTicketLog.setUserId(umUserPo.getId());
        toShopTicketLog.setBalance(BigDecimalUtil.safeAdd(umUserPo.getCurrentShopTicket(), amGiftOrderPo.getVouchers()));
        toShopTicketLog.setLastBalance(umUserPo.getCurrentShopTicket());
        //流水发生金额  礼包充值获得购物券
        toShopTicketLog.setAmount(amGiftOrderPo.getVouchers());
        //流水事由
        toShopTicketLog.setLogMatter(ShopTicketLogMatterEnum.EXPERIENCE_PACK.getId());
        //流水详情标题
        toShopTicketLog.setLogDetailTitle(LogDetailTitleEnum.FROM_EXPERIENCE.getName());
        //流水详情当前状态
        toShopTicketLog.setLogDetailState(LogDetailStateEnum.DEPOSIT_WALLET.getId());
        //流水详情说明
        toShopTicketLog.setLogDetailExplain(LogDetailExplainEnum.EXPERIENCE_CONTENT.getId());
        omAccountLogMapper.insert(toShopTicketLog);
        //APP用户 积分 收入流水 积分+礼包充值
        OmAccountLogPo toIntegralsLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                AccountTypeEnum.INTEGRATE, LogTypeEnum.INCOME);
        toIntegralsLog.setUserId(umUserPo.getId());
        toIntegralsLog.setBalance(BigDecimalUtil.safeAdd(umUserPo.getCurrentIntegral(), amGiftOrderPo.getIntegrals()));
        toIntegralsLog.setLastBalance(umUserPo.getCurrentIntegral());
        //流水发生金额  礼包充值获得积分
        toIntegralsLog.setAmount(amGiftOrderPo.getIntegrals());
        //流水事由
        toIntegralsLog.setLogMatter(IntegrateLogMatterEnum.EXPERIENCE_PACK.getId());
        //流水详情标题
        toIntegralsLog.setLogDetailTitle(LogDetailTitleEnum.FROM_EXPERIENCE.getName());
        //流水详情当前状态
        toIntegralsLog.setLogDetailState(LogDetailStateEnum.DEPOSIT_WALLET.getId());
        //流水详情说明
        toIntegralsLog.setLogDetailExplain(LogDetailExplainEnum.EXPERIENCE_CONTENT.getId());
        omAccountLogMapper.insert(toIntegralsLog);
    }

    /**
     * 流水触发事件：APP用户提现红包 后台标记已处理
     * 过程：平台给APP用户线下转账
     * 平台线上资金（实发金额）-    用户红包余额（提现金额）-
     * 用户线上资金（实发金额）+
     */
    private void appWithdrawal (AddAccountLogBo addAccountLogBo) {
        OmUserWithdrawalPo omUserWithdrawalPo = omUserWithdrawalMapper.selectById(addAccountLogBo.getRelId());
        if (null != omUserWithdrawalPo) {
            //平台 线上资金 支出流水   - 实发金额
            OmAccountLogPo fromOmAccountLogPo = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.PLATFORM,
                     AccountTypeEnum.ONLINE_FUNDS, LogTypeEnum.EXPENDITURE);
            //流水发生金额  平台实发金额
            fromOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(omUserWithdrawalPo.getActualAmount(), -1));
            //支付方式
            fromOmAccountLogPo.setPaymentWay(PaymentWayEnum.OFF_LINE.getId());
            //流水事由
            fromOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.USER_WITHDRAWAL.getId());
            omAccountLogMapper.insert(fromOmAccountLogPo);
            //用户 红包 支出流水  用户红包余额-提现金额
            OmAccountLogPo fromRedEnvelopsLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                    AccountTypeEnum.RED_ENVELOPS, LogTypeEnum.EXPENDITURE);
            UmUserPo umUserPo = umUserMapper.selectById(omUserWithdrawalPo.getUmUserId());
            fromRedEnvelopsLog.setUserId(umUserPo.getId());
            //流水发生金额  用户提现金额
            fromRedEnvelopsLog.setAmount(BigDecimalUtil.safeMultiply(omUserWithdrawalPo.getWithdrawalAmount(), -1));
            //支付方式
            fromRedEnvelopsLog.setPaymentWay(PaymentWayEnum.ACCOUNT.getId());
            //流水事由
            fromRedEnvelopsLog.setLogMatter(RedEnvelopsLogMatterEnum.WITHDRAWAL.getId());
            omAccountLogMapper.insert(fromRedEnvelopsLog);
        } else {
            //todo  如何保证数据生成
        }
    }


    /**
     * 流水触发事件：APP用户订单支付成功
     * 过程：
     * 1.用户消费抵扣红包，积分，购物券
     *   红包-  积分-  购物券-
     * 2.平台订单收入
     *   线上资金+
     */
    private void appOrder(AddAccountLogBo addAccountLogBo) {
        PayOrderPo payOrderPo = payOrderMapper.selectById(addAccountLogBo.getRelId());
        if (null != payOrderPo) {
            //用户消费抵扣红包，积分，购物券
            orderPayment(addAccountLogBo, payOrderPo);
            //平台订单收入
            orderIncome(addAccountLogBo, payOrderPo);
        } else {
            //todo  如何保证数据生成
        }
    }

    /**
     * 流水触发事件：用户消费抵扣红包，积分，购物券
     * 红包-  积分-  购物券-
     */
    private void orderPayment(AddAccountLogBo addAccountLogBo, PayOrderPo payOrderPo) {
        //APP用户 购物券 支出流水 购物券-消费抵扣购物券
        OmAccountLogPo fromShopTicketLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                AccountTypeEnum.SHOP_TICKET, LogTypeEnum.EXPENDITURE);
        UmUserPo umUserPo = umUserMapper.selectById(payOrderPo.getUmUserId());
        fromShopTicketLog.setUserId(umUserPo.getId());
        fromShopTicketLog.setBalance(BigDecimalUtil.safeSubtract(umUserPo.getCurrentShopTicket(),payOrderPo.getTotalShopTicket()));
        fromShopTicketLog.setLastBalance(umUserPo.getCurrentShopTicket());
        //流水发生金额  购物券抵扣金额
        fromShopTicketLog.setAmount(BigDecimalUtil.safeMultiply(payOrderPo.getTotalShopTicket(), -1));
        //支付方式
        fromShopTicketLog.setPaymentWay(PaymentWayEnum.ACCOUNT.getId());
        //流水事由
        fromShopTicketLog.setLogMatter(ShopTicketLogMatterEnum.ORDER_PAYMENT.getId());
        //流水详情标题
        fromShopTicketLog.setLogDetailTitle(LogDetailTitleEnum.ORDER_PAYMENT.getName());
        //流水详情当前状态
        fromShopTicketLog.setLogDetailState(LogDetailStateEnum.PAYMENT_SUCCESS.getId());
        //流水详情说明
        fromShopTicketLog.setLogDetailExplain(LogDetailExplainEnum.ORDER_PAYMENT.getId());
        omAccountLogMapper.insert(fromShopTicketLog);
        //APP用户 红包 支出流水 红包-消费抵扣红包
        OmAccountLogPo fromRedEnvelopsLog = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.APP_USER,
                AccountTypeEnum.RED_ENVELOPS, LogTypeEnum.EXPENDITURE);
        fromRedEnvelopsLog.setUserId(umUserPo.getId());
        fromRedEnvelopsLog.setBalance(BigDecimalUtil.safeSubtract(umUserPo.getCurrentRedEnvelops(),payOrderPo.getTotalRedEnvelops()));
        fromRedEnvelopsLog.setLastBalance(umUserPo.getCurrentShopTicket());
        //流水发生金额  红包抵扣金额
        fromRedEnvelopsLog.setAmount(BigDecimalUtil.safeMultiply(payOrderPo.getTotalRedEnvelops(), -1));
        //支付方式
        fromRedEnvelopsLog.setPaymentWay(PaymentWayEnum.ACCOUNT.getId());
        //流水事由
        fromRedEnvelopsLog.setLogMatter(RedEnvelopsLogMatterEnum.ORDER_PAYMENT.getId());
        //流水详情标题
        fromRedEnvelopsLog.setLogDetailTitle(LogDetailTitleEnum.ORDER_PAYMENT.getName());
        //流水详情当前状态
        fromRedEnvelopsLog.setLogDetailState(LogDetailStateEnum.PAYMENT_SUCCESS.getId());
        //流水详情说明
        fromRedEnvelopsLog.setLogDetailExplain(LogDetailExplainEnum.ORDER_PAYMENT.getId());
        omAccountLogMapper.insert(fromRedEnvelopsLog);
        //todo 积分第一期不做
    }

    /**
     * 流水触发事件：平台订单收入
     * 线上资金+
     */
    private void orderIncome(AddAccountLogBo addAccountLogBo, PayOrderPo payOrderPo) {
        //平台 线上资金 输入流水 线上资金+
        OmAccountLogPo toOmAccountLogPo = getFromOmAccountLogPo(addAccountLogBo, UserTypeEnum.PLATFORM,
                AccountTypeEnum.ONLINE_FUNDS, LogTypeEnum.INCOME);
        //流水发生金额  订单实收金额
        toOmAccountLogPo.setAmount(payOrderPo.getTotalRealPayMoney());
        //支付方式
        toOmAccountLogPo.setPaymentWay(PaymentWayEnum.OFF_LINE.getId());
        //流水事由
        toOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.ORDER_INCOME.getId());
        omAccountLogMapper.insert(toOmAccountLogPo);
    }

    /**
     * 查询用户红包，购物券流水
     *
     * @param searchUserLogDto
     * @return
     */
    @Override
    public SearchUserLogVo searchUserLogPaging(SearchUserLogDto searchUserLogDto) {
        //获取当前店铺用户
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if(null == umUserPo) {
            throw  new ServiceException(ResultCode.NO_LOGIN, "未登陆或登陆已超时");
        }
        searchUserLogDto.setUserId(umUserPo.getId());
        SearchUserLogVo searchUserLogVo = new SearchUserLogVo();
        if(searchUserLogDto.getAccountType().equals(AccountTypeEnum.RED_ENVELOPS.getId())) {
            searchUserLogVo.setAmount(umUserPo.getCurrentRedEnvelops());
        } else if(searchUserLogDto.getAccountType().equals(AccountTypeEnum.SHOP_TICKET.getId())) {
            searchUserLogVo.setAmount(umUserPo.getCurrentShopTicket());
        } else if(searchUserLogDto.getAccountType().equals(AccountTypeEnum.INTEGRATE.getId())) {
            searchUserLogVo.setAmount(umUserPo.getCurrentIntegral());
        } else {
            throw  new ServiceException(ResultCode.PARAM_ERROR, "accountType参数错误");
        }

        if(Strings.isNotBlank(searchUserLogDto.getYear()) && Strings.isNotBlank(searchUserLogDto.getMonth())) {
            searchUserLogVo.setYear(searchUserLogDto.getYear());
            searchUserLogVo.setMonth(searchUserLogDto.getMonth());
        } else {
            //默认当前时间的年月
            searchUserLogVo.setYear(String.valueOf(LocalDate.now().getYear()));
            int month = LocalDate.now().getMonthValue();
            searchUserLogVo.setMonth(month < 10 ? "0" + month : String.valueOf(month));
        }
        searchUserLogDto.setLogDate(searchUserLogVo.getYear() + "-" + searchUserLogVo.getMonth());
        Integer pageNo = searchUserLogDto.getPageNo()==null ? defaultPageNo : searchUserLogDto.getPageNo();
        Integer pageSize = searchUserLogDto.getPageSize()==null ? defaultPageSize : searchUserLogDto.getPageSize();

        PageInfo<UserLogDetailVo> userLogDetailVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omAccountLogMapper.searchUserLogPaging(searchUserLogDto));
        searchUserLogVo.setUserLogDetailVoPageInfo(userLogDetailVoPageInfo);

        Map<String, BigDecimal> map = omAccountLogMapper.getIncomeAndConsume(searchUserLogDto);
        if(null == map) {
            searchUserLogVo.setConsume(BigDecimal.ZERO);
            searchUserLogVo.setIncome(BigDecimal.ZERO);
        } else {
            searchUserLogVo.setConsume(null == map.get("consume") ? BigDecimal.ZERO : map.get("consume"));
            searchUserLogVo.setIncome(null == map.get("income") ? BigDecimal.ZERO : map.get("income"));
        }
        return searchUserLogVo;


    }


    /**
     * 用户红包提现
     *
     * @param userWithdrawalDto
     * @return
     */
    @Override
    public void userWithdrawal(UserWithdrawalDto userWithdrawalDto) {
        //获取当前店铺用户
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if(null == umUserPo) {
            throw  new ServiceException(ResultCode.NO_LOGIN, "未登陆或登陆已超时");
        }
        if(userWithdrawalDto.getWithdrawalAmount().compareTo(new BigDecimal(0))  < 1 ) {
            throw  new ServiceException(ResultCode.PARAM_ERROR, "提现金额必须大于0");
        }
        if(userWithdrawalDto.getWithdrawalAmount().compareTo(umUserPo.getTotalRedEnvelops())  < 1 ) {
            throw  new ServiceException(ResultCode.PARAM_ERROR, "提现金额大于用户余额");
        }

        OmUserWithdrawalPo omUserWithdrawalPo = new OmUserWithdrawalPo();
        //提现用户
        omUserWithdrawalPo.setUmUserId(umUserPo.getId());
        omUserWithdrawalPo.setWithdrawalStatus(WithdrawalStatusEnum.TO_BE_AUDITED.getId());
        omUserWithdrawalPo.setWithdrawalAmount(userWithdrawalDto.getWithdrawalAmount());
        omUserWithdrawalPo.setRealName(userWithdrawalDto.getRealName());
        if(userWithdrawalDto.getWithdrawalWayEnum().equals(WithdrawalWayEnum.ALIPAY)) {
            //申请支付宝提现
            omUserWithdrawalPo.setAlipay(userWithdrawalDto.getAccount());
            omUserWithdrawalPo.setWithdrawalWay(WithdrawalWayEnum.ALIPAY.getId());
        } else if(userWithdrawalDto.getWithdrawalWayEnum().equals(WithdrawalWayEnum.WECHAT)) {
            //申请微信提现
            omUserWithdrawalPo.setWechat(userWithdrawalDto.getAccount());
            omUserWithdrawalPo.setWithdrawalWay(WithdrawalWayEnum.WECHAT.getId());
        }

        //获取系统基本设置
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        // 计算红包等价多少金额 用户红包余额/money_to_current_red_envelops(个人消费的订单金额1元=多少红包)
        BigDecimal equalAmount = BigDecimalUtil.safeDivide(userWithdrawalDto.getWithdrawalAmount(), basicSettingPo.getMoneyToCurrentRedEnvelops());
        // 金额扣除对应的手续费  equalAmount*手续费比例/100
        BigDecimal deductedAmount = BigDecimalUtil.safeDivide(BigDecimalUtil.safeMultiply(equalAmount, basicSettingPo.getWithdrawCommission()), new BigDecimal(100));
        //获取实际应发金额  扣除其他费用
        BigDecimal actualAmount = BigDecimalUtil.safeSubtract(userWithdrawalDto.getWithdrawalAmount(), deductedAmount);
        omUserWithdrawalPo.setActualAmount(actualAmount);
        omUserWithdrawalMapper.insert(omUserWithdrawalPo);

    }


    /**
     * 用户提现列表
     * @param searchUserWithdrawalDto
     * @return
     */
    public PageInfo<SearchUserWithdrawalVo> searchUserWithdrawalPaging(SearchUserWithdrawalDto searchUserWithdrawalDto) {

        Integer pageNo = searchUserWithdrawalDto.getPageNo()==null ? defaultPageNo : searchUserWithdrawalDto.getPageNo();
        Integer pageSize = searchUserWithdrawalDto.getPageSize()==null ? defaultPageSize : searchUserWithdrawalDto.getPageSize();

        PageInfo<SearchUserWithdrawalVo> searchUserWithdrawalVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omAccountLogMapper.searchUserWithdrawalPaging(searchUserWithdrawalDto));

        return searchUserWithdrawalVoPageInfo;

    }

    /**
     * 查询店铺交易流水
     *
     * @param searchStoreLogDto
     * @return
     */
    @Override
    public PageInfo<SearchStoreLogVo> searchStoreLogPaging(SearchStoreLogDto searchStoreLogDto) {

        //获取当前店铺用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null == storeId) {
            //当前登录用户跟操作不匹配
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户跟操作不匹配");
        }
        searchStoreLogDto.setStoreId(storeId);
        searchStoreLogDto.setUserType(UserTypeEnum.STORE.getId());
        Integer pageNo = searchStoreLogDto.getPageNo()==null ? defaultPageNo : searchStoreLogDto.getPageNo();
        Integer pageSize = searchStoreLogDto.getPageSize()==null ? defaultPageSize : searchStoreLogDto.getPageSize();

        PageInfo<SearchStoreLogVo> searchStoreLogVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omAccountLogMapper.searchStoreLogPaging(searchStoreLogDto));

        return searchStoreLogVoPageInfo;
    }

}
