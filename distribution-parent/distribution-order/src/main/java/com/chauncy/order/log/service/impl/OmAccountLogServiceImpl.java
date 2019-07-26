package com.chauncy.order.log.service.impl;

import com.chauncy.common.enums.log.*;
import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.UserTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.bo.order.log.AddAccountLogBo;
import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.domain.po.order.log.OmAccountLogPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.mapper.order.bill.OmOrderBillMapper;
import com.chauncy.data.mapper.order.log.OmAccountLogMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.security.util.SecurityUtil;
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
    private OmOrderBillMapper omOrderBillMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 平台流水
     *
     * @param searchPlatformLogDto
     * @return
     */
    @Override
    public PageInfo<SearchPlatformLogVo> searchPlatformLogPaging(SearchPlatformLogDto searchPlatformLogDto) {

        return omAccountLogMapper.searchPlatformLogPaging(searchPlatformLogDto);
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
            case STORE_WITHDRAWAL: {
                //1.店铺利润、货款账单提现
                this.storeWithdrawal(addAccountLogBo);
                break;
            }
            //case

        }
    }

    /**
     * 店铺利润、货款账单提现
     */
    private void storeWithdrawal (AddAccountLogBo addAccountLogBo) {
        OmOrderBillPo omOrderBillPo = omOrderBillMapper.selectById(addAccountLogBo.getRelId());
        if (null != omOrderBillPo) {
            //平台流水
            OmAccountLogPo fromOmAccountLogPo = new OmAccountLogPo();
            //todo  平台没有id
            //fromOmAccountLogPo.setUserId();
            fromOmAccountLogPo.setAccountType(AccountTypeEnum.ONLINE_FUNDS.getId());
            //流水发生金额
            BigDecimal amount = BigDecimalUtil.safeSubtract(true, omOrderBillPo.getTotalAmount(), omOrderBillPo.getDeductedAmount());
            fromOmAccountLogPo.setAmount(BigDecimalUtil.safeMultiply(amount, -1));
            //流水关联的账单，订单id
            fromOmAccountLogPo.setOmRelId(omOrderBillPo.getId());
            //流水类型 支出
            fromOmAccountLogPo.setLogType(LogTypeEnm.EXPENDITURE.getName());
            //流水用户类型 平台
            fromOmAccountLogPo.setUserType(UserTypeEnum.PLATFORM.getId());
            //支付方式
            fromOmAccountLogPo.setPaymentWay(PaymentWayEnum.OFF_LINE.getId());
            //流水事由
            if(omOrderBillPo.getBillType().equals(BillTypeEnum.PAYMENT_BILL)) {
                fromOmAccountLogPo.setLogMatter(AccountLogMatterEnum.PAYMENT_WITHDRAWAL.getId());
            } else if(omOrderBillPo.getBillType().equals(BillTypeEnum.PROFIT_BILL)) {
                fromOmAccountLogPo.setLogMatter(AccountLogMatterEnum.PROFIT_WITHDRAWAL.getId());
            }
            fromOmAccountLogPo.setCreateBy(addAccountLogBo.getOperator());
            omAccountLogMapper.insert(fromOmAccountLogPo);
            //店铺流水
            OmAccountLogPo toOmAccountLogPo = new OmAccountLogPo();
            toOmAccountLogPo.setParentId(fromOmAccountLogPo.getId());
            SmStorePo smStorePo = smStoreMapper.selectById(omOrderBillPo.getStoreId());
            toOmAccountLogPo.setUserId(smStorePo.getId());
            toOmAccountLogPo.setAccountType(AccountTypeEnum.ONLINE_FUNDS.getId());
            //流水发生金额
            toOmAccountLogPo.setAmount(amount);
            //流水关联的账单，订单id
            toOmAccountLogPo.setOmRelId(fromOmAccountLogPo.getOmRelId());
            //流水类型  收入
            toOmAccountLogPo.setLogType(LogTypeEnm.INCOME.getName());
            //流水用户类型  商家
            toOmAccountLogPo.setUserType(UserTypeEnum.STORE.getId());
            //到账方式
            toOmAccountLogPo.setArrivalWay(PaymentWayEnum.OFF_LINE.getId());
            //流水事由
            if(omOrderBillPo.getBillType().equals(BillTypeEnum.PAYMENT_BILL)) {
                toOmAccountLogPo.setLogMatter(AccountLogMatterEnum.PAYMENT_INCOME.getId());
            } else if(omOrderBillPo.getBillType().equals(BillTypeEnum.PROFIT_BILL)) {
                toOmAccountLogPo.setLogMatter(AccountLogMatterEnum.PROFIT_INCOME.getId());
            }
            toOmAccountLogPo.setCreateBy(addAccountLogBo.getOperator());
            omAccountLogMapper.insert(toOmAccountLogPo);
        } else {
            //todo  如何保证数据生成
        }
    }
}
