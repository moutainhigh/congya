package com.chauncy.message.interact.service.impl;

import com.chauncy.common.enums.message.NoticeTypeEnum;
import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.domain.po.message.interact.MmUserNoticeTimePo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.mapper.message.interact.MmUserNoticeMapper;
import com.chauncy.data.mapper.message.interact.MmUserNoticeTimeMapper;
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

import java.time.LocalDateTime;

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
    private MmUserNoticeTimeMapper mmUserNoticeTimeMapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 获取用户消息列表未读消息数目
     *
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
     *
     * @param noticeType
     * @return
     */
    @Override
    public PageInfo<UserNoticeListVo> searchPaging(Integer noticeType, BaseSearchPagingDto baseSearchPagingDto) {

        Integer pageNo = baseSearchPagingDto.getPageNo() == null ? defaultPageNo : baseSearchPagingDto.getPageNo();
        Integer pageSize = baseSearchPagingDto.getPageSize() == null ? defaultPageSize : baseSearchPagingDto.getPageSize();

        UmUserPo userPo = securityUtil.getAppCurrUser();
        PageInfo<UserNoticeListVo> userNoticeListVoPageInfo = new PageInfo<>();
        if (noticeType.equals(NoticeTypeEnum.Express_Logistics.getId())
                || noticeType.equals(NoticeTypeEnum.Task_reward.getId())) {
            //物流快递 跟 任务奖励的消息从 mm_user_notice表获取
            userNoticeListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                    .doSelectPageInfo(() -> mapper.searchUserNoticeList(userPo.getId(), noticeType));
        } else if (noticeType.equals(NoticeTypeEnum.System_Notice.getId())) {
            //系统通知从mm_interact_push表获取
            userNoticeListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                    .doSelectPageInfo(() -> mapper.searchUserSystemNoticeList(userPo.getId(), userPo.getMemberLevelId()));
            //判断消息是否已读  MmUserNoticeTimePo保存用户最近一次访问消息接口的时间
            MmUserNoticeTimePo mmUserNoticeTimePo = mmUserNoticeTimeMapper.selectById(userPo.getId());
            if(null == mmUserNoticeTimePo) {
                //系统消息全部未读
                userNoticeListVoPageInfo.getList().forEach(userNoticeListVo -> userNoticeListVo.setIsRead(false));
                //记录不存在 添加记录，记录用户访问系统消息的时间
                mmUserNoticeTimePo = new MmUserNoticeTimePo();
                mmUserNoticeTimePo.setId(userPo.getId());
                mmUserNoticeTimePo.setReadTime(LocalDateTime.now());
                mmUserNoticeTimeMapper.insert(mmUserNoticeTimePo);
            } else {
                //系统消息全部未读
                MmUserNoticeTimePo finalMmUserNoticeTimePo = mmUserNoticeTimePo;
                userNoticeListVoPageInfo.getList().forEach(userNoticeListVo -> {
                    if(userNoticeListVo.getCreateTime().isBefore(finalMmUserNoticeTimePo.getReadTime())) {
                        //通知消息发布时间早于用户访问时间   已读
                        userNoticeListVo.setIsRead(true);
                    } else {
                        //通知消息发布时间早于用户访问时间   未读
                        userNoticeListVo.setIsRead(false);
                    }
                });
                //记录已存在 更新访问时间
                mmUserNoticeTimePo.setReadTime(LocalDateTime.now());
                mmUserNoticeTimeMapper.updateById(mmUserNoticeTimePo);
            }
        }
        return userNoticeListVoPageInfo;

    }
}
