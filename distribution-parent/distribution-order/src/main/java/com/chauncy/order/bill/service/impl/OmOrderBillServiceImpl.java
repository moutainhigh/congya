package com.chauncy.order.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.order.BillSettlementEnum;
import com.chauncy.common.enums.order.BillStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.order.bill.OmOrderBillPo;
import com.chauncy.data.domain.po.store.rel.SmStoreBankCardPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.update.BillBatchAuditDto;
import com.chauncy.data.dto.manage.order.bill.update.BillCashOutDto;
import com.chauncy.data.dto.manage.order.bill.update.BillDeductionDto;
import com.chauncy.data.mapper.order.bill.OmOrderBillMapper;
import com.chauncy.data.mapper.store.rel.SmStoreBankCardMapper;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.order.bill.BillSettlementVo;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private SmStoreBankCardMapper smStoreBankCardMapper;

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
    public void billDeduction(BillDeductionDto billDeductionDto) {
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
    public void billSettlementSuccess(Long id) {
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

    }
}
