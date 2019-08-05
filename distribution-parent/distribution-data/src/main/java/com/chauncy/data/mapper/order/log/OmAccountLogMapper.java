package com.chauncy.data.mapper.order.log;

import com.chauncy.data.domain.po.order.log.OmAccountLogPo;
import com.chauncy.data.dto.app.order.log.SearchUserLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchUserWithdrawalDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.data.vo.manage.order.log.SearchUserWithdrawalVo;
import com.chauncy.data.vo.manage.order.log.UserLogDetailVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 账目流水表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
public interface OmAccountLogMapper extends IBaseMapper<OmAccountLogPo> {

    /**
     * 平台流水
     *
     * @param searchPlatformLogDto
     * @return
     */
    List<SearchPlatformLogVo> searchPlatformLogPaging(SearchPlatformLogDto searchPlatformLogDto);

    /**
     * 查询用户红包，购物券流水
     *
     * @param searchUserLogDto
     * @return
     */
    List<UserLogDetailVo> searchUserLogPaging(SearchUserLogDto searchUserLogDto);

    List<SearchUserWithdrawalVo> searchUserWithdrawalPaging(SearchUserWithdrawalDto searchUserWithdrawalDto);
}
