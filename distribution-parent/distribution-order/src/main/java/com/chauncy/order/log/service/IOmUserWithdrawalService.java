package com.chauncy.order.log.service;

import com.chauncy.data.domain.po.order.OmUserWithdrawalPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;

/**
 * <p>
 * App用户提现信息 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
public interface IOmUserWithdrawalService extends Service<OmUserWithdrawalPo> {

    /**
     * 平台批量审核用户提现
     *
     * @param batchAuditDto
     * @return
     */
    void batchAudit(BatchAuditDto batchAuditDto);

    /**
     * 平台标记状态为处理中的用户提现为已处理
     * @return
     */
    void withdrawalSuccess(Long[] id);
}
