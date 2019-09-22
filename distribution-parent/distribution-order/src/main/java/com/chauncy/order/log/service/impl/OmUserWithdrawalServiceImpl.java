package com.chauncy.order.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.log.WithdrawalStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.domain.po.order.OmUserWithdrawalPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;
import com.chauncy.data.mapper.order.OmUserWithdrawalMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.order.log.service.IOmUserWithdrawalService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * App用户提现信息 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmUserWithdrawalServiceImpl extends AbstractService<OmUserWithdrawalMapper, OmUserWithdrawalPo> implements IOmUserWithdrawalService {

    @Autowired
    private OmUserWithdrawalMapper omUserWithdrawalMapper;

    @Autowired
    private IOmAccountLogService omAccountLogService;

    @Autowired
    private SecurityUtil securityUtil;



    /**
     * 平台批量审核用户提现
     *
     * @param batchAuditDto
     * @return
     */
    @Override
    public void batchAudit(BatchAuditDto batchAuditDto) {
        SysUserPo currUser = securityUtil.getCurrUser();

        List<Long> idList = Arrays.asList(batchAuditDto.getIds());
        idList.forEach(id -> {
            OmUserWithdrawalPo omUserWithdrawalPo = omUserWithdrawalMapper.selectById(id);
            if(null == omUserWithdrawalPo) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "的记录不存在");
            } else if(!omUserWithdrawalPo.getWithdrawalStatus().equals(WithdrawalStatusEnum.TO_BE_AUDITED.getId())) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "账单不是待审核状态");
            }
        });
        if(batchAuditDto.getEnabled()) {
            //审核通过状态改为处理中
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.in("id", idList);
            updateWrapper.set("withdrawal_status", WithdrawalStatusEnum.PROCESSING.getId());
            //处理中时间
            updateWrapper.set("processing_time", LocalDateTime.now());
            this.update(updateWrapper);
        } else {
            //审核驳回状态改为审核失败
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.in("id", idList);
            updateWrapper.set("withdrawal_status", WithdrawalStatusEnum.AUDIT_FAIL.getId());
            //结算时间
            updateWrapper.set("settlement_time", LocalDateTime.now());
            //驳回原因
            updateWrapper.set("reject_reason", batchAuditDto.getRejectReason());
            this.update(updateWrapper);

            //APP用户提现红包 审核驳回  添加用户流水
            idList.forEach(id -> {
                //OmUserWithdrawalPo omUserWithdrawalPo = omUserWithdrawalMapper.selectById(id);
                AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
                addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.WITHDRAWAL_FAIL);
                addAccountLogBo.setRelId(id);
                addAccountLogBo.setOperator(currUser.getUsername());
                omAccountLogService.saveAccountLog(addAccountLogBo);
            });
        }
    }

    /**
     * 平台标记状态为处理中的用户提现为已处理
     *
     * @return
     */
    @Override
    public void withdrawalSuccess(Long[] ids) {
        //获取当前店铺用户
        String userName = securityUtil.getCurrUser().getUsername();

        List<Long> idList = Arrays.asList(ids);
        idList.forEach(id -> {
            OmUserWithdrawalPo omUserWithdrawalPo = omUserWithdrawalMapper.selectById(id);
            if(null == omUserWithdrawalPo) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "的记录不存在");
            } else if(!omUserWithdrawalPo.getWithdrawalStatus().equals(WithdrawalStatusEnum.PROCESSING.getId())) {
                throw new ServiceException(ResultCode.NO_EXISTS, "id为" + id + "申请提现不是待处理状态");
            }
        });
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.in("id", idList);
        updateWrapper.set("settlement_time", LocalDateTime.now());
        updateWrapper.set("withdrawal_status", WithdrawalStatusEnum.WITHDRAWAL_SUCCESS.getId());
        this.update(updateWrapper);

        //APP用户提现红包 审核通过 添加后台流水
        idList.forEach(id -> {
            //OmUserWithdrawalPo omUserWithdrawalPo = omUserWithdrawalMapper.selectById(id);
            AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
            addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.WITHDRAWAL_SUCCESS);
            addAccountLogBo.setRelId(id);
            addAccountLogBo.setOperator(userName);
            omAccountLogService.saveAccountLog(addAccountLogBo);
        });
    }
}
