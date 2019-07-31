package com.chauncy.order.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.log.*;
import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.UserTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.bo.order.log.AddAccountLogBo;
import com.chauncy.data.domain.po.order.OmUserWithdrawalPo;
import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.domain.po.order.log.OmAccountLogPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.log.SearchUserLogDto;
import com.chauncy.data.dto.app.order.log.UserWithdrawalDto;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchUserWithdrawalDto;
import com.chauncy.data.mapper.order.OmUserWithdrawalMapper;
import com.chauncy.data.mapper.order.bill.OmOrderBillMapper;
import com.chauncy.data.mapper.order.log.OmAccountLogMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.order.log.service.IOmUserWithdrawalService;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.data.vo.manage.order.log.SearchUserLogVo;
import com.chauncy.data.vo.manage.order.log.SearchUserWithdrawalVo;
import com.chauncy.data.vo.manage.order.log.UserLogDetailVo;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    private OmOrderBillMapper omOrderBillMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private UmUserMapper umUserMapper;

    @Autowired
    private OmUserWithdrawalMapper omUserWithdrawalMapper;

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

        Integer pageNo = searchPlatformLogDto.getPageNo()==null ? defaultPageNo : searchPlatformLogDto.getPageNo();
        Integer pageSize = searchPlatformLogDto.getPageSize()==null ? defaultPageSize : searchPlatformLogDto.getPageSize();

        PageInfo<SearchPlatformLogVo> searchPlatformLogVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omAccountLogMapper.searchPlatformLogPaging(searchPlatformLogDto));
        return searchPlatformLogVoPageInfo;
    }


    /**
     * 保存流水
     *
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
                this.appWithdrawal(addAccountLogBo);
                break;
        }
    }

    /**
     * 获取支出流水
     * @return
     */
    private OmAccountLogPo getFromOmAccountLogPo(AddAccountLogBo addAccountLogBo) {
        OmAccountLogPo fromOmAccountLogPo = new OmAccountLogPo();
        //流水关联的账单，订单id
        fromOmAccountLogPo.setOmRelId(addAccountLogBo.getRelId());
        fromOmAccountLogPo.setCreateBy(addAccountLogBo.getOperator());
        return fromOmAccountLogPo;
    }

    /**
     * 获取收入流水
     * @return
     */
    private OmAccountLogPo getToOmAccountLogPo(OmAccountLogPo fromOmAccountLogPo) {
        OmAccountLogPo toOmAccountLogPo = new OmAccountLogPo();
        //流水关联的账单，订单id
        toOmAccountLogPo.setOmRelId(fromOmAccountLogPo.getOmRelId());
        toOmAccountLogPo.setCreateBy(fromOmAccountLogPo.getCreateBy());
        toOmAccountLogPo.setParentId(fromOmAccountLogPo.getId());
        //流水发生金额
        toOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(fromOmAccountLogPo.getAmount(), -1));
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
            //平台支出流水
            OmAccountLogPo fromOmAccountLogPo = getFromOmAccountLogPo(addAccountLogBo);
            //todo  平台没有id
            //fromOmAccountLogPo.setUserId();
            fromOmAccountLogPo.setAccountType(AccountTypeEnum.ONLINE_FUNDS.getId());
            //流水发生金额
            BigDecimal amount = BigDecimalUtil.safeSubtract(true, omOrderBillPo.getTotalAmount(), omOrderBillPo.getDeductedAmount());
            fromOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(amount, -1));
            //流水类型 支出
            fromOmAccountLogPo.setLogType(LogTypeEnm.EXPENDITURE.getName());
            //流水用户类型 平台
            fromOmAccountLogPo.setUserType(UserTypeEnum.PLATFORM.getId());
            //支付方式
            fromOmAccountLogPo.setPaymentWay(PaymentWayEnum.OFF_LINE.getId());
            //流水事由
            if(omOrderBillPo.getBillType().equals(BillTypeEnum.PAYMENT_BILL)) {
                fromOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.PAYMENT_WITHDRAWAL.getId());
            } else if(omOrderBillPo.getBillType().equals(BillTypeEnum.PROFIT_BILL)) {
                fromOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.PROFIT_WITHDRAWAL.getId());
            }
            omAccountLogMapper.insert(fromOmAccountLogPo);
            //店铺收入流水
            OmAccountLogPo toOmAccountLogPo = getToOmAccountLogPo(fromOmAccountLogPo);
            SmStorePo smStorePo = smStoreMapper.selectById(omOrderBillPo.getStoreId());
            toOmAccountLogPo.setUserId(smStorePo.getId());
            toOmAccountLogPo.setAccountType(AccountTypeEnum.ONLINE_FUNDS.getId());
            //流水类型  收入
            toOmAccountLogPo.setLogType(LogTypeEnm.INCOME.getName());
            //流水用户类型  商家
            toOmAccountLogPo.setUserType(UserTypeEnum.STORE.getId());
            //到账方式
            toOmAccountLogPo.setArrivalWay(PaymentWayEnum.OFF_LINE.getId());
            //流水事由
            if(omOrderBillPo.getBillType().equals(BillTypeEnum.PAYMENT_BILL)) {
                toOmAccountLogPo.setLogMatter(StoreLogMatterEnum.PAYMENT_INCOME.getId());
            } else if(omOrderBillPo.getBillType().equals(BillTypeEnum.PROFIT_BILL)) {
                toOmAccountLogPo.setLogMatter(StoreLogMatterEnum.PROFIT_INCOME.getId());
            }
            omAccountLogMapper.insert(toOmAccountLogPo);
        }
    }

    /**
     * 流水触发事件：APP用户提现红包 后台标记已处理
     * 过程：平台给APP用户线下转账
     * 平台线上资金（实发金额）-  用户线上资金（实发金额）+  用户红包余额（提现金额）-
     */
    private void appWithdrawal (AddAccountLogBo addAccountLogBo) {
        OmUserWithdrawalPo omUserWithdrawalPo = omUserWithdrawalMapper.selectById(addAccountLogBo.getRelId());
        if (null != omUserWithdrawalPo) {
            //平台支出流水 平台线上资金（实发金额）-
            OmAccountLogPo fromOmAccountLogPo = getFromOmAccountLogPo(addAccountLogBo);
            fromOmAccountLogPo.setAccountType(AccountTypeEnum.ONLINE_FUNDS.getId());
            //流水发生金额  平台实发金额
            fromOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(omUserWithdrawalPo.getActualAmount(), -1));
            //流水类型 支出
            fromOmAccountLogPo.setLogType(LogTypeEnm.EXPENDITURE.getName());
            //流水用户类型 平台
            fromOmAccountLogPo.setUserType(UserTypeEnum.PLATFORM.getId());
            //支付方式
            fromOmAccountLogPo.setPaymentWay(PaymentWayEnum.OFF_LINE.getId());
            //流水事由
            fromOmAccountLogPo.setLogMatter(PlatformLogMatterEnum.USER_WITHDRAWAL.getId());
            omAccountLogMapper.insert(fromOmAccountLogPo);
            //用户红包流水  用户红包余额（提现金额）-
            OmAccountLogPo fromRedEnvelopsLog = getFromOmAccountLogPo(addAccountLogBo);
            UmUserPo umUserPo = umUserMapper.selectById(omUserWithdrawalPo.getUmUserId());
            fromRedEnvelopsLog.setUserId(umUserPo.getId());
            //红包
            fromRedEnvelopsLog.setAccountType(AccountTypeEnum.RED_ENVELOPS.getId());
            //流水发生金额  用户提现金额
            fromRedEnvelopsLog.setAmount(BigDecimalUtil.safeMultiply(omUserWithdrawalPo.getWithdrawalAmount(), -1));
            //流水类型 支出
            fromRedEnvelopsLog.setLogType(LogTypeEnm.EXPENDITURE.getName());
            //流水用户类型 平台
            fromRedEnvelopsLog.setUserType(UserTypeEnum.APP_USER.getId());
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
        if(searchUserLogDto.getAccountTypeEnum().equals(AccountTypeEnum.RED_ENVELOPS)) {
            searchUserLogVo.setAmount(umUserPo.getCurrentRedEnvelops());
        } else if(searchUserLogDto.getAccountTypeEnum().equals(AccountTypeEnum.SHOP_TICKET)) {
            searchUserLogVo.setAmount(umUserPo.getCurrentShopTicket());
        }


        Integer pageNo = searchUserLogDto.getPageNo()==null ? defaultPageNo : searchUserLogDto.getPageNo();
        Integer pageSize = searchUserLogDto.getPageSize()==null ? defaultPageSize : searchUserLogDto.getPageSize();

        PageInfo<UserLogDetailVo> userLogDetailVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omAccountLogMapper.searchUserLogPaging(searchUserLogDto));
        searchUserLogVo.setUserLogDetailVoPageInfo(userLogDetailVoPageInfo);

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


}
