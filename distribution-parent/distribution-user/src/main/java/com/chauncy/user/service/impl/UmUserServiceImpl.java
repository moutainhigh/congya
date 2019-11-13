package com.chauncy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.Constants;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.log.AccountTypeEnum;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.message.NoticeContentEnum;
import com.chauncy.common.enums.message.NoticeTitleEnum;
import com.chauncy.common.enums.message.NoticeTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.third.easemob.RegistIM;
import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.common.util.*;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.bo.order.log.AccountLogBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.information.comment.MmCommentLikedPo;
import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.domain.po.message.information.rel.MmInformationLikedPo;
import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.domain.po.order.OmEvaluateLikedPo;
import com.chauncy.data.domain.po.product.PmGoodsLikedPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.domain.po.user.UmRelUserLabelPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.dto.app.user.add.BindUserDto;
import com.chauncy.data.dto.app.user.select.SearchMyFriendDto;
import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.dto.manage.user.update.UpdateUserDto;
import com.chauncy.data.mapper.message.advice.MmAdviceMapper;
import com.chauncy.data.mapper.message.information.comment.MmCommentLikedMapper;
import com.chauncy.data.mapper.message.information.comment.MmInformationCommentMapper;
import com.chauncy.data.mapper.message.information.rel.MmInformationLikedMapper;
import com.chauncy.data.mapper.message.interact.MmFeedBackMapper;
import com.chauncy.data.mapper.message.interact.MmUserNoticeMapper;
import com.chauncy.data.mapper.order.OmEvaluateLikedMapper;
import com.chauncy.data.mapper.product.PmGoodsLikedMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmRelUserLabelMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.user.*;
import com.chauncy.data.vo.manage.user.detail.UmUserDetailVo;
import com.chauncy.data.vo.manage.user.detail.UmUserRelVo;
import com.chauncy.data.vo.manage.user.idCard.SearchIdCardVo;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import com.chauncy.user.service.IUmUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.security.SecurityUtil;
import org.assertj.core.util.Lists;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端用户 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:config/redis.properties")
public class UmUserServiceImpl extends AbstractService<UmUserMapper, UmUserPo> implements IUmUserService {


    @Autowired
    private UmUserMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UmRelUserLabelMapper relUserLabelMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SmStoreMapper smStoreMapper;
    @Autowired
    private MmFeedBackMapper feedBackMapper;
    @Autowired
    private MmUserNoticeMapper mmUserNoticeMapper;

    @Autowired
    private MmInformationLikedMapper informationLikedMapper;

    @Autowired
    private MmCommentLikedMapper commentLikedMapper;

    @Autowired
    private OmEvaluateLikedMapper evaluateLikedMapper;

    @Autowired
    private PmGoodsLikedMapper goodsLikedMapper;

    @Autowired
    private MmInformationCommentMapper informationCommentMapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private MmAdviceMapper adviceMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;


    @Override
    public boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum) {
        String redisKey = String.format(validCodeEnum.getRedisKey(), phone);
        Object redisValue = redisUtil.get(redisKey);
        if ("8888".equals(verifyCode.trim())) {
            return true;
        }
        if (redisValue == null) {
            return false;
        }
        if (StringUtils.equals(verifyCode.trim(), redisValue.toString().trim())) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(AddUserDto addUserDto) {
        if (!validVerifyCode(addUserDto.getVerifyCode(), addUserDto.getPhone(), ValidCodeEnum.REGISTER_CODE)) {
            throw new ServiceException(ResultCode.FAIL, "验证码错误！");
        }
        UmUserPo saveUser = new UmUserPo();
        BeanUtils.copyProperties(addUserDto, saveUser);

        /**
         * 设置一些值
         */
        saveUser.setInviteCode(SnowFlakeUtil.getFlowIdInstance().nextId());

        Boolean isSuccess = mapper.insert(saveUser) > 0;

        /** 注册IM账号**/
        registIM(saveUser, isSuccess);

        return isSuccess;
    }

    @Override
    public boolean bindUser(BindUserDto userDto) {
        if (!validVerifyCode(userDto.getVerifyCode(), userDto.getPhone(), ValidCodeEnum.BIND_PHONE_CODE)) {
            throw new ServiceException(ResultCode.FAIL, "验证码错误！");
        }
        UmUserPo saveUser = new UmUserPo();
        BeanUtils.copyProperties(userDto, saveUser);
        saveUser.setInviteCode(SnowFlakeUtil.getFlowIdInstance().nextId());
        Boolean isSuccess = mapper.insert(saveUser) > 0;

        registIM(saveUser, isSuccess);

        return isSuccess;
    }

    /**
     * 注册IM账号
     *
     * @param saveUser
     */
    private void registIM(UmUserPo saveUser, Boolean isSuccess) {
        if (isSuccess) {
            RegUserBo regUserBo = new RegUserBo();
            //判断该用户是否已经注册过IM账号
            if (RegistIM.getUser(saveUser.getId().toString()) == null) {
                regUserBo.setPassword(Constants.PASSWORD);
                regUserBo.setUsername(saveUser.getId().toString());
                regUserBo.setNickname(saveUser.getPhone());
                RegistIM.reg(regUserBo);
            }
        }
    }

    @Override
    public boolean reset(AddUserDto addUserDto) {
        if (!validVerifyCode(addUserDto.getVerifyCode(), addUserDto.getPhone(), ValidCodeEnum.RESET_PASSWORD_CODE)) {
            throw new ServiceException(ResultCode.FAIL, "验证码错误！");
        }
        UmUserPo updateUser = new UmUserPo();
        updateUser.setPassword(addUserDto.getPassword());
        UmUserPo condition = new UmUserPo();
        condition.setPhone(addUserDto.getPhone());
        return mapper.update(updateUser, new UpdateWrapper<>(condition)) > 0;

    }

    @Override
    public boolean updateLogin(String phone) {
        return mapper.updateLogin(phone) > 0;
    }

    /**
     * 获取用户的账户余额
     *
     * @param accountType
     * @return
     */
    @Override
    public BigDecimal getAccount(AccountTypeEnum accountType, UmUserPo umUserPo) {
        BigDecimal amount = new BigDecimal(0);
        if (accountType.equals(AccountTypeEnum.RED_ENVELOPS)) {
            amount = umUserPo.getCurrentRedEnvelops();
        } else if (accountType.equals(AccountTypeEnum.SHOP_TICKET)) {
            amount = umUserPo.getCurrentShopTicket();
        }
        return amount;
    }

    @Override
    public PageInfo<SearchIdCardVo> searchIdCardVos(SearchUserIdCardDto searchUserIdCardDto) {
        Integer pageNo = searchUserIdCardDto.getPageNo() == null ? defaultPageNo : searchUserIdCardDto.getPageNo();
        Integer pageSize = searchUserIdCardDto.getPageSize() == null ? defaultPageSize : searchUserIdCardDto.getPageSize();
        PageInfo<SearchIdCardVo> searchIdCardVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.loadSearchIdCardVos(searchUserIdCardDto));
        return searchIdCardVoPageInfo;
    }

    @Override
    public UserDataVo getUserDataVo(String phone) {
        return mapper.loadUserDataVo(phone);
    }

    @Override
    public void setParent(Long inviteCode, Long userId) {
        UmUserPo condition = new UmUserPo();
        condition.setInviteCode(inviteCode);
        UmUserPo parentUserPo = mapper.selectOne(new QueryWrapper<>(condition).select("id,invite_people_num,store_id"));
        //子跟随父亲的店铺id 设置 parentid
        UmUserPo updateChildUserPo = new UmUserPo();
        updateChildUserPo.setId(userId).setParentId(parentUserPo.getId()).setStoreId(parentUserPo.getStoreId());
        mapper.updateById(updateChildUserPo);
        //父增加邀请人数
        UmUserPo updateParentUserPo = new UmUserPo();
        updateParentUserPo.setInvitePeopleNum(parentUserPo.getInvitePeopleNum() + 1).setId(parentUserPo.getId());
        mapper.updateById(updateParentUserPo);

    }

    @Override
    public PageInfo<UmUserListVo> searchUserList(SearchUserListDto searchUserListDto) {
        Integer pageNo = searchUserListDto.getPageNo() == null ? defaultPageNo : searchUserListDto.getPageNo();
        Integer pageSize = searchUserListDto.getPageSize() == null ? defaultPageSize : searchUserListDto.getPageSize();
        PageInfo<UmUserListVo> umUserListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.loadSearchUserList(searchUserListDto));

        //通过条件构造器限制只需要 查出name
        UmUserPo condition = new UmUserPo();
        //设置关联用户的名称,不要连表(数据库查出的是id，现在将id转为name)
        umUserListVoPageInfo.getList().stream().filter(x -> x.getParent() != null).
                forEach(x -> {
                    condition.setId(Long.parseLong(x.getParent()));
                    //当trueName为空时，查出的实体也为空
                    UmUserPo queryParentUser = mapper.selectOne(new QueryWrapper<>(condition, "true_name"));
                    x.setParent(queryParentUser == null || queryParentUser.getTrueName() == null ? "" : queryParentUser.getTrueName());
                });
        return umUserListVoPageInfo;
    }

    @Override
    public UmUserDetailVo getUserDetailVo(Long id) {
        UmUserDetailVo umUserDetailVo = mapper.loadUserDetailVo(id);
        //组装labelNames storeName nextLevelExperience parentName(数据库查出来的是id)
        umUserDetailVo.setLabelNames(mapper.getLabelNamesByUserId(id));
        if (umUserDetailVo.getStoreId() != null) {
            QueryWrapper storeWrapper = new QueryWrapper();
            storeWrapper.eq("id", umUserDetailVo.getStoreId());
            storeWrapper.select("name");
            SmStorePo queryStore = smStoreMapper.selectOne(storeWrapper);
            if (queryStore != null) {
                umUserDetailVo.setStoreName(queryStore.getName());
            }
        }
        if (umUserDetailVo.getParentName() != null) {
            QueryWrapper userWrapper = new QueryWrapper();
            userWrapper.eq("id", umUserDetailVo.getParentName());
            userWrapper.select("true_name");
            UmUserPo queryParentUser = mapper.selectOne(userWrapper);
            umUserDetailVo.setParentName(queryParentUser == null ? null : queryParentUser.getTrueName());
        }
        return umUserDetailVo;
    }

    @Override
    public boolean updateUmUser(UpdateUserDto updateUserDto, String currentUserName) {
        UmUserPo userPo = mapper.selectById(updateUserDto.getId());
        //积分差额
        BigDecimal marginIntegral = BigDecimalUtil.safeSubtract(updateUserDto.getCurrentIntegral()
                , userPo.getCurrentIntegral());
        updateUserDto.setTotalAddIntegral(marginIntegral);
        //红包差额
        BigDecimal marginRedEnvelops = BigDecimalUtil.safeSubtract(updateUserDto.getCurrentRedEnvelops()
                , userPo.getCurrentRedEnvelops());
        updateUserDto.setTotalAddRedEnvelops(marginRedEnvelops);
        //购物券差额
        BigDecimal marginShopTicket = BigDecimalUtil.safeSubtract(updateUserDto.getCurrentShopTicket()
                , userPo.getCurrentShopTicket());
        updateUserDto.setTotalAddShopTicket(marginShopTicket);
        //修改主表字段
        mapper.updateUmUser(updateUserDto, currentUserName);
        //更改等级时，顺便更改用户表冗余字段level和经验值
        if (updateUserDto.getMemberLevelId()!=0&&(!updateUserDto.getMemberLevelId().equals(userPo.getMemberLevelId()))){
            PmMemberLevelPo queryMemberLevel = memberLevelMapper.selectById(updateUserDto.getMemberLevelId());
            UmUserPo updateUser=new UmUserPo();
            updateUser.setId(updateUserDto.getId()).setMemberLevelId(updateUserDto.getMemberLevelId())
                    .setLevel(queryMemberLevel.getLevel()).setCurrentExperience(queryMemberLevel.getLevelExperience());
            mapper.updateById(updateUser);
        }
        if (!ListUtil.isListNullAndEmpty(updateUserDto.getLabelIds())) {
            //修改用户与标签关联表
            List<Long> labelIds = updateUserDto.getLabelIds();
            //先删掉关联数据
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", updateUserDto.getId());
            relUserLabelMapper.delete(queryWrapper);
            //插入关联数据
            if (!ListUtil.isListNullAndEmpty(labelIds)) {
                labelIds.forEach(x -> {
                    UmRelUserLabelPo umRelUserLabelPo = new UmRelUserLabelPo();
                    umRelUserLabelPo.setCreateBy(currentUserName).setUserId(updateUserDto.getId())
                            .setUserLabelId(x);
                    relUserLabelMapper.insert(umRelUserLabelPo);
                });
            }
        }
        //系统赠送流水生成
        AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
        addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.PLATFORM_GIVE);
        addAccountLogBo.setRelId(null);
        addAccountLogBo.setOperator(currentUserName);
        addAccountLogBo.setMarginIntegral(marginIntegral);
        addAccountLogBo.setMarginRedEnvelops(marginRedEnvelops);
        addAccountLogBo.setMarginShopTicket(marginShopTicket);
        addAccountLogBo.setUmUserId(userPo.getId());
        //listenerPlatformGiveQueue 消息队列
        this.rabbitTemplate.convertAndSend(
                RabbitConstants.ACCOUNT_LOG_EXCHANGE, RabbitConstants.ACCOUNT_LOG_ROUTING_KEY, addAccountLogBo);

        return true;
    }

    @Override
    public List<UmUserRelVo> getRelUsers(Long id) {
        return mapper.getRelUsers(id);
    }

    /**
     * 用户反馈信息
     *
     * @return
     */
    @Override
    public void addFeedBack(String content, UmUserPo userPo) {
        MmFeedBackPo feedBackPo = new MmFeedBackPo();
        feedBackPo.setContent(content);
        feedBackPo.setCreateBy(userPo.getName());
        feedBackPo.setId(null);
        feedBackPo.setUserId(userPo.getId());
        feedBackMapper.insert(feedBackPo);
    }


    @Override
    public void updateLevel(Long userId) {
        UmUserPo queryUser = mapper.selectById(userId);

        PmMemberLevelPo nextLevel = memberLevelMapper.getNextLevelByUserId(userId);
        //要修改的会员等级id
        Long memberLevelId = null;
        //当用户经验值大于等级所需经验值的时候,进行升级
        while (nextLevel!=null&&queryUser.getCurrentExperience().compareTo(nextLevel.getLevelExperience()) >= 0) {
            memberLevelId = nextLevel.getId();
            //找出下一等级的会员详细信息
            QueryWrapper<PmMemberLevelPo> levelQueryWrapper = new QueryWrapper<>();
            levelQueryWrapper.lambda().eq(PmMemberLevelPo::getLevel, nextLevel.getLevel() + 1);
            nextLevel = memberLevelMapper.selectOne(levelQueryWrapper);
        }
        if (memberLevelId != null) {
            PmMemberLevelPo queryLevel = memberLevelMapper.selectById(memberLevelId);
            UmUserPo updateUser = new UmUserPo();
            updateUser.setId(userId).setMemberLevelId(memberLevelId).setLevel(queryLevel.getLevel());
            mapper.updateById(updateUser);

            //会员升级  发送APP内消息中心推送
            MmUserNoticePo mmUserNoticePo = new MmUserNoticePo();
            mmUserNoticePo.setUserId(userId)
                    .setNoticeType(NoticeTypeEnum.TASK_REWARD.getId())
                    .setTitle(NoticeTitleEnum.UPGRADE.getName())
                    .setContent(MessageFormat.format(NoticeContentEnum.UPGRADE.getName(), queryLevel.getLevel()));
            mmUserNoticeMapper.insert(mmUserNoticePo);
        }

    }

    /**
     * 会员中心
     *
     * 综合
     * 消费 累计消费额
     * 资产 累计购物券+累计红包+累计积分*0.2
     * 发育 经验/天（总的经验值/从注册到当前日期的天数）
     * 收益 累计红包
     * 贡献 好友累计消费总额（一级会员的消费金额）
     *
     * 订单数 累计已完成订单数 （现在是没有已完成的订单）
     * 金额/单 累计消费额/累计计完成订单数
     * 奖励/单 （累计返券+积分）/累计完成订单数
     * 经验/天 累计经验/注册天数
     * 好友 直推好友数量（第一代）
     * 收入  累计佣金
     *
     * @param userPo
     * @return
     */
    @Override
    public GetMembersCenterVo getMembersCenter(UmUserPo userPo) {

        GetMembersCenterVo membersCenterVo = mapper.getMembersCenter(userPo.getId());

        //经验值百分比
        BigDecimal percentage = BigDecimalUtil.safeDivide(membersCenterVo.getCurrentExperience(),membersCenterVo.getSumExperience());
        membersCenterVo.setPercentage(percentage);

        //订单数
        membersCenterVo.setTotalOrder(userPo.getTotalOrder());

        //金额/单 累计消费额/累计计完成订单数
        membersCenterVo.setPricePerOrder(BigDecimalUtil.safeDivide(userPo.getTotalConsumeMoney(),userPo.getTotalOrder()));

        //奖励/单  （累计返券+积分）/累计完成订单数
        BigDecimal reward = BigDecimalUtil.safeDivide(BigDecimalUtil.safeAdd(userPo.getTotalShopTicket(),userPo.getTotalIntegral()),userPo.getTotalOrder());
        membersCenterVo.setRewardPerOrder(reward);

        // 经验/天 累计经验/注册天数
        BigDecimal experience = BigDecimalUtil.safeDivide(membersCenterVo.getCurrentExperience(), (LocalDate.now().toEpochDay()-membersCenterVo.getCreateTime().toLocalDate().toEpochDay()));
        membersCenterVo.setExperiencePerDay(experience);

        //好友 直推好友数量（第一代）
        Integer friend = mapper.selectList(new QueryWrapper<UmUserPo>().lambda().and(obj->obj
                .eq(UmUserPo::getParentId,userPo.getId()).eq(UmUserPo::getDelFlag,0))).size();
        membersCenterVo.setFriend(friend);

        //收入  累计佣金
        membersCenterVo.setIncome(userPo.getTotalRedEnvelops());

        //贡献 好友累计消费总额（一级会员的消费金额）
        List<UmUserPo> friends = mapper.selectList(new QueryWrapper<UmUserPo>().lambda().and(obj->obj
                        .eq(UmUserPo::getParentId,userPo.getId())));
        BigDecimal contributions = friends.stream().map(a->a.getTotalConsumeMoney()).reduce(BigDecimal.ZERO,BigDecimal::add);

        //消费
        BigDecimal totalConsumeMoney = userPo.getTotalConsumeMoney();
        //资产
        BigDecimal assets = BigDecimalUtil.safeAdd(userPo.getTotalShopTicket(),userPo.getTotalRedEnvelops(),BigDecimalUtil.safeMultiply(userPo.getTotalIntegral(),new BigDecimal(0.2)));
        //发育
        BigDecimal development = experience;
        //收益
        BigDecimal earnings = userPo.getTotalRedEnvelops();
        //贡献
        BigDecimal contributions1 = contributions;

        List<BigDecimal> datas = Lists.newArrayList(
                totalConsumeMoney,
                assets,
                development,
                earnings,
                contributions1
        );
        //获取最大值
        BigDecimal max = datas.stream().max((u1, u2)->u1.compareTo(u2)).get();

        //消费比例
        membersCenterVo.setTotalConsumeMoneyProportion(BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(totalConsumeMoney,max,4,new BigDecimal(0.00)),100));

        //资产比例
        membersCenterVo.setAssetsProportion(BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(assets,max,4,new BigDecimal(0.00)),100));

        //发育比例
        membersCenterVo.setDevelopmentProportion(BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(development,max,4,new BigDecimal(0.00)),100));

        //收益比例
        membersCenterVo.setEarningsProportion(BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(earnings,max,4,new BigDecimal(0.00)),100));

        //贡献比例
        membersCenterVo.setContributionProportion(BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(contributions1,max,4,new BigDecimal(0.00)),100));

        //综合比例
        membersCenterVo.setComprehensiveProportion(BigDecimalUtil.safeMultiply(100,
                BigDecimalUtil.safeDivide(
                        BigDecimalUtil.safeDivide(
                                BigDecimalUtil.safeAdd(totalConsumeMoney,assets,development,earnings,contributions1),
                        max,4,new BigDecimal(0.00)),
                new BigDecimal(5),4,new BigDecimal(0.00))));



        //平均消费
//        BigDecimal avgConsumer = BigDecimalUtil.safeDivide(membersCenterVo.getTotalConsumeMoney(),membersCenterVo.getTotalOrder());

        //赞
        //用户资讯点赞
        Integer praise1 = informationLikedMapper.selectCount(new QueryWrapper<MmInformationLikedPo>().lambda().and(obj->obj
                .eq(MmInformationLikedPo::getUserId,userPo.getId()).eq(MmInformationLikedPo::getDelFlag,0)));
        //用户资讯评论点赞
        Integer praise2 = commentLikedMapper.selectCount(new QueryWrapper<MmCommentLikedPo>().lambda().and(obj->obj
                .eq(MmCommentLikedPo::getUserId,userPo.getId()).eq(MmCommentLikedPo::getDelFlag,0)));
//                .stream().mapToLong((a)->a.getId()).summaryStatistics().getCount();
        //商品点赞
        Integer praise3 = goodsLikedMapper.selectCount(new QueryWrapper<PmGoodsLikedPo>().lambda().and(obj->obj
                .eq(PmGoodsLikedPo::getUserId,userPo.getId()).eq(PmGoodsLikedPo::getIsLiked,1)));

        //商品评论点赞
        Integer praise4 = evaluateLikedMapper.selectCount(new QueryWrapper<OmEvaluateLikedPo>().lambda().and(obj->obj
                .eq(OmEvaluateLikedPo::getUserId,userPo.getId()).eq(OmEvaluateLikedPo::getIsLiked,1)));

        membersCenterVo.setPraise(praise1+praise2+praise3+praise4);

        //分享
        Integer share = mapper.selectOne(new QueryWrapper<UmUserPo>().lambda().and(obj->
                obj.eq(UmUserPo::getDelFlag,0).eq(UmUserPo::getId,userPo.getId()))).getShareNum();
        membersCenterVo.setShare(share);

        //评论
        Integer informationComments = informationCommentMapper.selectCount(new QueryWrapper<MmInformationCommentPo>().lambda().and(obj->obj
                .eq(MmInformationCommentPo::getDelFlag,0).eq(MmInformationCommentPo::getUserId,userPo.getId())));
        membersCenterVo.setComments(informationComments);


        return membersCenterVo;
    }

    @Override
    public List<String> getAllPhones() {
        return mapper.getAllPhones();
    }

    /**
     * App我的页面需要的数据
     *
     * @param userPo
     * @return
     */
    @Override
    public MyDataStatisticsVo getMyDataStatistics(UmUserPo userPo) {

        MyDataStatisticsVo myDataStatisticsVo = mapper.getMyDataStatistics(userPo.getId());

        //好友 直推好友数量（第一代）
        Integer friend = mapper.selectList(new QueryWrapper<UmUserPo>().lambda().and(obj->obj
                .eq(UmUserPo::getParentId,userPo.getId()).eq(UmUserPo::getDelFlag,0))).size();
        myDataStatisticsVo.setFansNum(friend);

        //红包   展示红包对应的金额
        //获取系统基本设置
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        // 计算红包等价多少金额 用户红包余额/money_to_current_red_envelops(个人消费的订单金额1元=多少红包)
        BigDecimal equalAmount = BigDecimalUtil.safeDivide(myDataStatisticsVo.getRedEnvelopeNum(),
                basicSettingPo.getMoneyToCurrentRedEnvelops());
        myDataStatisticsVo.setRedEnvelopeNum(equalAmount);

        PersonalCenterPictureVo topPicture = adviceMapper.getTopPicture();
        PersonalCenterPictureVo topUpPicture =adviceMapper.getTopUpPicture();
        myDataStatisticsVo.setTopPicture(null == topPicture ? new PersonalCenterPictureVo() : topPicture);
        myDataStatisticsVo.setTopUpPicture(null == topUpPicture ? new PersonalCenterPictureVo() : topUpPicture);

        return myDataStatisticsVo;
    }

    /**
     * @Author chauncy
     * @Date 2019-09-18 10:26
     * @Description //条件分页查询我的粉丝
     *
     * @Update chauncy
     *
     * @Param [searchMyFriendDto, umUserPo]
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.user.SearchMyFriendVo>
     **/
    @Override
    public PageInfo<SearchMyFriendVo> searchMyFriend(SearchMyFriendDto searchMyFriendDto, UmUserPo umUserPo) {

        Integer pageNo = searchMyFriendDto.getPageNo() == null ? defaultPageNo : searchMyFriendDto.getPageNo();
        Integer pageSize = searchMyFriendDto.getPageSize() == null ? defaultPageSize : searchMyFriendDto.getPageSize();

        PageInfo<SearchMyFriendVo> searchMyFriendVoPageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->mapper.searchMyFriend(umUserPo.getId()));


        return searchMyFriendVoPageInfo;
    }
}
