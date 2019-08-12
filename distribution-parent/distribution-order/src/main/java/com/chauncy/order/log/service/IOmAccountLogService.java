package com.chauncy.order.log.service;

import com.chauncy.data.bo.order.log.AddAccountLogBo;
import com.chauncy.data.domain.po.order.log.OmAccountLogPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.order.log.SearchUserLogDto;
import com.chauncy.data.dto.app.order.log.UserWithdrawalDto;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchUserWithdrawalDto;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.data.vo.manage.order.log.SearchUserLogVo;
import com.chauncy.data.vo.manage.order.log.SearchUserWithdrawalVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 账目流水表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
public interface IOmAccountLogService extends Service<OmAccountLogPo> {

    /**
     * 平台流水
     * @param searchPlatformLogDto
     * @return
     */
    PageInfo<SearchPlatformLogVo> searchPlatformLogPaging(SearchPlatformLogDto searchPlatformLogDto);

    /**
     * 保存流水
     * @param addAccountLogBo
     * @return
     */
    void saveAccountLog(AddAccountLogBo addAccountLogBo);

    /**
     * 查询用户红包，购物券流水
     * @param searchUserLogDto
     * @return
     */
    SearchUserLogVo searchUserLogPaging(SearchUserLogDto searchUserLogDto);

    /**
     * 用户红包提现
     * @param userWithdrawalDto
     * @return
     */
    void userWithdrawal(UserWithdrawalDto userWithdrawalDto);

    /**
     * 用户提现列表
     * @param searchUserWithdrawalDto
     * @return
     */
    PageInfo<SearchUserWithdrawalVo> searchUserWithdrawalPaging(SearchUserWithdrawalDto searchUserWithdrawalDto);

}
