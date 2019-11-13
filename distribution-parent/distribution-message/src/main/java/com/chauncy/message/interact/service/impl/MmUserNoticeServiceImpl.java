package com.chauncy.message.interact.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.ServiceConstant;
import com.chauncy.common.enums.log.RedEnvelopsLogMatterEnum;
import com.chauncy.common.enums.message.NoticeContentEnum;
import com.chauncy.common.enums.message.NoticeTitleEnum;
import com.chauncy.common.enums.message.NoticeTypeEnum;
import com.chauncy.data.bo.app.message.SaveUserNoticeBo;
import com.chauncy.data.bo.app.message.WithdrawalLogBo;
import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.domain.po.message.interact.MmUserNoticeTimePo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.order.log.OmAccountLogPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.mapper.afterSale.OmAfterSaleOrderMapper;
import com.chauncy.data.mapper.message.interact.MmUserNoticeMapper;
import com.chauncy.data.mapper.message.interact.MmUserNoticeTimeMapper;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.order.log.OmAccountLogMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
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

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

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
    private UmUserMapper umUserMapper;

    @Autowired
    private OmAccountLogMapper omAccountLogMapper;

    @Autowired
    private OmAfterSaleOrderMapper omAfterSaleOrderMapper;

    @Autowired
    private MmUserNoticeTimeMapper mmUserNoticeTimeMapper;

    @Autowired
    private OmGoodsTempMapper omGoodsTempMapper;

    @Autowired
    private OmOrderMapper omOrderMapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * @Author yeJH
     * @Date 2019/10/22 16:40
     * @Description 保存APP内消息
     *
     * @Update yeJH
     *
     * @param  name
     * @param  saveUserNoticeBo
     * @return void
     **/
    @Override
    public void saveUserNotice(String name, SaveUserNoticeBo saveUserNoticeBo) {
        NoticeTitleEnum noticeTitleEnum = NoticeTitleEnum.fromName(name);
        switch(noticeTitleEnum) {
            case SHIPPED:
                OmOrderPo omOrderPo = omOrderMapper.selectById(saveUserNoticeBo.getOrderId());
                if(null != omOrderPo) {
                    //找到订单中的一个商品
                    QueryWrapper<OmGoodsTempPo> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(OmGoodsTempPo::getOrderId, saveUserNoticeBo.getOrderId());
                    queryWrapper.last(" LIMIT 1 ");
                    OmGoodsTempPo omGoodsTempPo = omGoodsTempMapper.selectOne(queryWrapper);
                    MmUserNoticePo mmUserNoticePo = new MmUserNoticePo();
                    mmUserNoticePo.setUserId(omOrderPo.getUmUserId())
                            .setNoticeType(NoticeTypeEnum.EXPRESS_LOGISTICS.getId())
                            .setTitle(noticeTitleEnum.getName())
                            .setContent(MessageFormat.format(NoticeContentEnum.fromName(name).getName(),
                                    omGoodsTempPo.getName()));
                    mapper.insert(mmUserNoticePo);
                }
                break;
            case WITHDRAWAL_SUCCESS:
                //提现成功给用户发送APP内消息  根据提现记录id获取发送消息需要的参数
                List<WithdrawalLogBo> withdrawalLogBoList = mapper.getWithdrawalLog(saveUserNoticeBo.getWithdrawalIdList(),
                        RedEnvelopsLogMatterEnum.WITHDRAWAL.getId());
                if(null != withdrawalLogBoList) {
                    withdrawalLogBoList.forEach(withdrawalLogBo ->  {
                        //提现成功  给用户发送APP内消息
                        MmUserNoticePo mmUserNoticePo = new MmUserNoticePo();
                        if (null != withdrawalLogBo) {
                            mmUserNoticePo.setUserId(withdrawalLogBo.getUserId())
                                    .setNoticeType(NoticeTypeEnum.EXPRESS_LOGISTICS.getId())
                                    .setTitle(NoticeTitleEnum.WITHDRAWAL_SUCCESS.getName())
                                    .setPicture(MessageFormat.format(ServiceConstant.ICON_PATH, "congya"))
                                    .setContent(MessageFormat.format(NoticeContentEnum.WITHDRAWAL_SUCCESS.getName(),
                                            withdrawalLogBo.getUserName(), String.valueOf(withdrawalLogBo.getLogId())));
                            mapper.insert(mmUserNoticePo);
                        }
                    });
                }
                break;
            case RETURN_GOODS:
                //商家同意售后  发送APP内消息给用户
                OmAfterSaleOrderPo queryAfterSaleOrder =
                        omAfterSaleOrderMapper.selectById(saveUserNoticeBo.getAfterSaleOrderId());
                //根据售后订单id获取商品的sku图片
                String skuPic = omAfterSaleOrderMapper.getSkuPicByOrder(queryAfterSaleOrder.getId());
                UmUserPo umUserPo = umUserMapper.selectById(queryAfterSaleOrder.getCreateBy());
                MmUserNoticePo mmUserNoticePo = new MmUserNoticePo();
                if (null != queryAfterSaleOrder) {
                    mmUserNoticePo.setUserId(Long.valueOf(queryAfterSaleOrder.getCreateBy()))
                            .setNoticeType(NoticeTypeEnum.EXPRESS_LOGISTICS.getId())
                            .setTitle(NoticeTitleEnum.RETURN_GOODS.getName())
                            .setPicture(skuPic)
                            .setContent(MessageFormat.format(NoticeContentEnum.RETURN_GOODS.getName(),
                                    umUserPo.getName(), String.valueOf(queryAfterSaleOrder.getId())));
                    mapper.insert(mmUserNoticePo);
                }
                break;
        }
    }

    /**
     * 获取用户消息列表未读消息数目
     *
     * @return
     */
    @Override
    public UnreadNoticeNumVo getUnreadNoticeNum() {
        UmUserPo userPo = securityUtil.getAppCurrUser();
        MmUserNoticeTimePo mmUserNoticeTimePo = mmUserNoticeTimeMapper.selectById(userPo);
        if(null == mmUserNoticeTimePo) {
            //记录不存在 添加记录
            mmUserNoticeTimePo = new MmUserNoticeTimePo();
            mmUserNoticeTimePo.setId(userPo.getId());
            mmUserNoticeTimeMapper.insert(mmUserNoticeTimePo);
        }
        UnreadNoticeNumVo unreadNoticeNumVo = mapper.getUnreadNoticeNum(
                userPo.getId(),
                mmUserNoticeTimePo.getReadTime(),
                userPo.getLevel());
        //未读消息总数
        unreadNoticeNumVo.setSum(unreadNoticeNumVo.getSystemNoticeNum() +
                unreadNoticeNumVo.getExpressNum() +
                unreadNoticeNumVo.getTaskRewardNum());
        return unreadNoticeNumVo;
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
        if (noticeType.equals(NoticeTypeEnum.EXPRESS_LOGISTICS.getId())
                || noticeType.equals(NoticeTypeEnum.TASK_REWARD.getId())) {
            //物流快递 跟 任务奖励的消息从 mm_user_notice表获取
            userNoticeListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                    .doSelectPageInfo(() -> mapper.searchUserNoticeList(userPo.getId(), noticeType));
            //消息设置为已读
            UpdateWrapper<MmUserNoticePo> userNoticePoUpdateWrapper = new UpdateWrapper<>();
            userNoticePoUpdateWrapper.lambda()
                    .eq(MmUserNoticePo::getUserId, userPo.getId())
                    .eq(MmUserNoticePo::getNoticeType, noticeType)
                    .set(MmUserNoticePo::getIsRead, true);
            this.update(userNoticePoUpdateWrapper);
        } else if (noticeType.equals(NoticeTypeEnum.SYSTEM_NOTICE.getId())) {
            //系统通知从mm_interact_push表获取
            userNoticeListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                    .doSelectPageInfo(() -> mapper.searchUserSystemNoticeList(userPo.getId(), userPo.getLevel()));
            //判断消息是否已读  MmUserNoticeTimePo保存用户最近一次访问消息接口的时间
            MmUserNoticeTimePo mmUserNoticeTimePo = mmUserNoticeTimeMapper.selectById(userPo.getId());
            if(null != mmUserNoticeTimePo) {
                //记录已存在 更新访问时间
                mmUserNoticeTimePo.setReadTime(LocalDateTime.now());
                mmUserNoticeTimeMapper.updateById(mmUserNoticeTimePo);
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
            } else {
                //记录不存在 记录数据
                mmUserNoticeTimePo = new MmUserNoticeTimePo();
                mmUserNoticeTimePo.setId(userPo.getId());
                mmUserNoticeTimePo.setReadTime(LocalDateTime.now());
                mmUserNoticeTimeMapper.insert(mmUserNoticeTimePo);
            }
        }
        return userNoticeListVoPageInfo;

    }
}
