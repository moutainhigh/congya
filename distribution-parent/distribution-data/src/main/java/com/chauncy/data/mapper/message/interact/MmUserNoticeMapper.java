package com.chauncy.data.mapper.message.interact;

import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.message.information.interact.UnreadNoticeNumVo;
import com.chauncy.data.vo.app.message.information.interact.UserNoticeListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户消息列表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-14
 */
public interface MmUserNoticeMapper extends IBaseMapper<MmUserNoticePo> {

    /**
     * 获取用户消息列表未读消息数目
     * @param userId
     * @return
     */
    UnreadNoticeNumVo getUnreadNoticeNum(Long userId);

    /**
     * 物流快递 跟 任务奖励的消息从 mm_user_notice表获取
     * @param userId
     */
    List<UserNoticeListVo> searchUserNoticeList(@Param("userId") Long userId, @Param("noticeType")Integer noticeType);

    /**
     * 系统通知从mm_interact_push表获取
     * @param userId
     * @return
     */
    List<UserNoticeListVo> searchUserSystemNoticeList(Long userId);
}
