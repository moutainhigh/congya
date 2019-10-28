package com.chauncy.data.mapper.message.interact;

import com.chauncy.data.bo.app.message.WithdrawalLogBo;
import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.message.information.interact.UnreadNoticeNumVo;
import com.chauncy.data.vo.app.message.information.interact.UserNoticeListVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
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
     * @Author yeJH
     * @Date 2019/10/23 15:37
     * @Description 提现成功给用户发送APP内消息  根据提现记录id获取发送消息需要的参数
     *
     * @Update yeJH
     *
     * @param  withdrawalIdList  提现记录id
     * @param  logMatter  用户提现记录对应流水事件 RedEnvelopsLogMatterEnum.WITHDRAWAL
     * @return com.chauncy.data.bo.app.message.WithdrawalLogBo
     **/
    List<WithdrawalLogBo> getWithdrawalLog(@Param("withdrawalIdList") List<Long> withdrawalIdList,
                                     @Param("logMatter") Integer logMatter);

    /**
     * @Author yeJH
     * @Date 2019/9/18 10:41
     * @Description  获取用户消息列表未读消息数目
     *
     * @Update yeJH
     *
     * @param userId   用户id
     * @param readTime   用户最近一次查看系统消息的时间
     * @param userLevel  用户等级level
     * @return com.chauncy.data.vo.app.message.information.interact.UnreadNoticeNumVo
     **/
    UnreadNoticeNumVo getUnreadNoticeNum(
            @Param("userId") Long userId,
            @Param("readTime")LocalDateTime readTime,
            @Param("userLevel") Integer userLevel
            );

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
    List<UserNoticeListVo> searchUserSystemNoticeList(
            @Param("userId") Long userId,
            @Param("userLevel") Integer userLevel);

}
