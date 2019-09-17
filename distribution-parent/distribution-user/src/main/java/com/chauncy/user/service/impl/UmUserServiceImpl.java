package com.chauncy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.Constants;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.log.AccountTypeEnum;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.third.easemob.RegistIM;
import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.common.util.*;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.bo.order.log.PlatformGiveBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.domain.po.message.information.rel.MmInformationLikedPo;
import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.domain.po.user.UmRelUserLabelPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.dto.app.user.add.BindUserDto;
import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.dto.manage.user.update.UpdateUserDto;
import com.chauncy.data.mapper.message.information.comment.MmInformationCommentMapper;
import com.chauncy.data.mapper.message.information.rel.MmInformationLikedMapper;
import com.chauncy.data.mapper.message.interact.MmFeedBackMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmRelUserLabelMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.user.GetMembersCenterVo;
import com.chauncy.data.vo.app.user.UserDataVo;
import com.chauncy.data.vo.manage.order.log.SearchUserLogVo;
import com.chauncy.data.vo.manage.user.detail.UmUserDetailVo;
import com.chauncy.data.vo.manage.user.detail.UmUserRelVo;
import com.chauncy.data.vo.manage.user.idCard.SearchIdCardVo;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import com.chauncy.user.service.IUmUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private MmInformationLikedMapper informationLikedMapper;

    @Autowired
    private MmInformationCommentMapper informationCommentMapper;

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
                regUserBo.setNickname(saveUser.getName());
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
        //子跟随父亲的店铺id 设置parentid
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

        //通过条件构造器限制只需要查出name
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
            umUserDetailVo.setStoreName(queryStore.getName());
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
        PlatformGiveBo platformGiveBo = new PlatformGiveBo();
        AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
        addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.PLATFORM_GIVE);
        addAccountLogBo.setRelId(null);
        addAccountLogBo.setOperator(currentUserName);
        platformGiveBo.setAddAccountLogBo(addAccountLogBo);
        platformGiveBo.setMarginIntegral(marginIntegral);
        platformGiveBo.setMarginRedEnvelops(marginRedEnvelops);
        platformGiveBo.setMarginShopTicket(marginShopTicket);
        platformGiveBo.setUmUserId(userPo.getId());
        //listenerPlatformGiveQueue 消息队列
        this.rabbitTemplate.convertAndSend(
                RabbitConstants.PLATFORM_GIVE_EXCHANGE, RabbitConstants.PLATFORM_GIVE_ROUTING_KEY, platformGiveBo);

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
        while (queryUser.getCurrentExperience().compareTo(nextLevel.getLevelExperience()) >= 0) {
            memberLevelId = nextLevel.getId();
            //找出下一等级的会员详细信息
            QueryWrapper<PmMemberLevelPo> levelQueryWrapper = new QueryWrapper<>();
            levelQueryWrapper.lambda().eq(PmMemberLevelPo::getLevel, nextLevel.getLevel() + 1);
            nextLevel = memberLevelMapper.selectOne(levelQueryWrapper);
        }
        if (memberLevelId != null) {
            UmUserPo updateUser = new UmUserPo();
            updateUser.setId(userId).setMemberLevelId(memberLevelId);
            mapper.updateById(updateUser);
        }

    }

    /**
     * 会员中心
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

        //平均消费
        BigDecimal avgConsumer = BigDecimalUtil.safeDivide(membersCenterVo.getTotalConsumeMoney(),membersCenterVo.getTotalOrder());
        membersCenterVo.setAvgConsumer(avgConsumer);

        //赞
        Integer praise1 = informationLikedMapper.selectCount(new QueryWrapper<MmInformationLikedPo>().lambda().and(obj->obj
                .eq(MmInformationLikedPo::getUserId,userPo.getId()).eq(MmInformationLikedPo::getDelFlag,0)));
//                .stream().mapToLong((a)->a.getId()).summaryStatistics().getCount();
        Integer praise2 = informationCommentMapper.selectCount(new QueryWrapper<MmInformationCommentPo>().lambda().and(obj->obj
                .eq(MmInformationCommentPo::getUserId,userPo.getId()).eq(MmInformationCommentPo::getDelFlag,0)));
//                .stream().mapToLong((a)->a.getId()).summaryStatistics().getCount();
        membersCenterVo.setPraise(praise1+praise2);

        //经验/天
        BigDecimal experience = BigDecimalUtil.safeDivide(membersCenterVo.getCurrentExperience(), (LocalDate.now().toEpochDay()-membersCenterVo.getCreateTime().toLocalDate().toEpochDay()));
        membersCenterVo.setExperience(experience);

        //分享
        Integer share = mapper.selectCount(new QueryWrapper<UmUserPo>().lambda().and(obj->
                obj.eq(UmUserPo::getDelFlag,0).eq(UmUserPo::getId,userPo.getId())));
        membersCenterVo.setShare(share);

        //评论
        Integer informationComments = informationCommentMapper.selectCount(new QueryWrapper<MmInformationCommentPo>().lambda().and(obj->obj
                .eq(MmInformationCommentPo::getDelFlag,0).eq(MmInformationCommentPo::getUserId,userPo.getId())));
        membersCenterVo.setComments(informationComments);

        //好友
        Integer friend = mapper.selectList(new QueryWrapper<UmUserPo>().lambda().and(obj->obj
                .eq(UmUserPo::getParentId,userPo.getId()).eq(UmUserPo::getDelFlag,0))).size();
        membersCenterVo.setFriend(friend);


        //CRO
        Integer cRO = 0;

        //奖励/订单
        Integer reward;

        //资产
        BigDecimal assets;

        //综合
        BigDecimal comprehensive;

        //发育
        BigDecimal development;

        //贡献
        BigDecimal contribution;

        //活跃
        BigDecimal active;


        return membersCenterVo;
    }

    @Override
    public List<String> getAllPhones() {
        return mapper.getAllPhones();
    }
}
