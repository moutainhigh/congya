package com.chauncy.message.interact.service.impl;

import com.chauncy.common.enums.message.NoticeTypeEnum;
import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.mapper.message.interact.MmUserNoticeMapper;
import com.chauncy.data.vo.app.message.information.interact.UnreadNoticeNumVo;
import com.chauncy.data.vo.app.message.information.interact.UserNoticeListVo;
import com.chauncy.message.interact.service.IMmUserNoticeService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户消息列表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmUserNoticeServiceImpl extends AbstractService<MmUserNoticeMapper, MmUserNoticePo> implements IMmUserNoticeService {

    @Autowired
    private MmUserNoticeMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 获取用户消息列表未读消息数目
     * @return
     */
    @Override
    public UnreadNoticeNumVo getUnreadNoticeNum() {
        UmUserPo userPo = securityUtil.getAppCurrUser();
        UnreadNoticeNumVo unreadNoticeNum = mapper.getUnreadNoticeNum(userPo.getId());
        unreadNoticeNum.setSystemNoticeNum(unreadNoticeNum.getSystemNoticeNum1() + unreadNoticeNum.getSystemNoticeNum2());
        return unreadNoticeNum;
    }

    /**
     * 用户消息列表
     * @param noticeType
     * @return
     */
    @Override
    public PageInfo<UserNoticeListVo> searchPaging(Integer noticeType,  BaseSearchPagingDto baseSearchPagingDto) {

        Integer pageNo = baseSearchPagingDto.getPageNo()==null ? defaultPageNo : baseSearchPagingDto.getPageNo();
        Integer pageSize = baseSearchPagingDto.getPageSize()==null ? defaultPageSize : baseSearchPagingDto.getPageSize();

        UmUserPo userPo = securityUtil.getAppCurrUser();
        PageInfo<UserNoticeListVo> userNoticeListVoPageInfo = new PageInfo<>();
        if(noticeType.equals(NoticeTypeEnum.Express_Logistics.getId())
                || noticeType.equals(NoticeTypeEnum.Task_reward.getId())) {
            //物流快递 跟 任务奖励的消息从 mm_user_notice表获取
            userNoticeListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                    .doSelectPageInfo(() -> mapper.searchUserNoticeList(userPo.getId(), noticeType));
        } else if (noticeType.equals(NoticeTypeEnum.System_Notice.getId())) {
            //系统通知从mm_interact_push表获取
            userNoticeListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                    .doSelectPageInfo(() -> mapper.searchUserSystemNoticeList(userPo.getId()));
        }
        return userNoticeListVoPageInfo;

    }
}
