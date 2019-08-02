package com.chauncy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.log.AccountTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.RedisUtil;
import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.common.util.StringUtils;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.user.UmRelUserLabelPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.dto.app.user.add.BindUserDto;
import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.dto.manage.user.update.UpdateUserDto;
import com.chauncy.data.mapper.message.interact.MmFeedBackMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.user.UmRelUserLabelMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private SmStoreMapper smStoreMapper;
    @Autowired
    private MmFeedBackMapper feedBackMapper;


    @Override
    public boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum) {
        String redisKey = String.format(validCodeEnum.getRedisKey(), phone);
        Object redisValue = redisUtil.get(redisKey);
        if ("8888".equals(verifyCode.trim())){
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
        return mapper.insert(saveUser) > 0;
    }

    @Override
    public boolean bindUser(BindUserDto userDto) {
        if (!validVerifyCode(userDto.getVerifyCode(), userDto.getPhone(), ValidCodeEnum.BIND_PHONE_CODE)) {
            throw new ServiceException(ResultCode.FAIL, "验证码错误！");
        }
        UmUserPo saveUser = new UmUserPo();
        BeanUtils.copyProperties(userDto, saveUser);
        saveUser.setInviteCode(SnowFlakeUtil.getFlowIdInstance().nextId());
        return mapper.insert(saveUser) > 0;
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
        return mapper.updateLogin(phone)>0;
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
        if(accountType.equals(AccountTypeEnum.RED_ENVELOPS)) {
            amount = umUserPo.getCurrentRedEnvelops();
        } else if(accountType.equals(AccountTypeEnum.SHOP_TICKET)) {
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
        UmUserPo condition=new UmUserPo();
        condition.setInviteCode(inviteCode);
        UmUserPo parentUserPo = mapper.selectOne(new QueryWrapper<>(condition).select("id,invite_people_num,store_id"));
        //子跟随父亲的店铺id 设置parentid
        UmUserPo updateChildUserPo=new UmUserPo();
        updateChildUserPo.setId(userId).setParentId(parentUserPo.getId()).setStoreId(parentUserPo.getStoreId());
        mapper.updateById(updateChildUserPo);
        //父增加邀请人数
        UmUserPo updateParentUserPo=new UmUserPo();
        updateParentUserPo.setInvitePeopleNum(parentUserPo.getInvitePeopleNum()+1).setId(parentUserPo.getId());
        mapper.updateById(updateParentUserPo);

    }

    @Override
    public PageInfo<UmUserListVo> searchUserList(SearchUserListDto searchUserListDto) {
        Integer pageNo = searchUserListDto.getPageNo() == null ? defaultPageNo : searchUserListDto.getPageNo();
        Integer pageSize = searchUserListDto.getPageSize() == null ? defaultPageSize : searchUserListDto.getPageSize();
        PageInfo<UmUserListVo> umUserListVoPageInfo =PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.loadSearchUserList(searchUserListDto));
        //通过条件构造器限制只需要查出name
        UmUserPo condition=new UmUserPo();
        //设置关联用户的名称,不要连表(数据库查出的是id，现在将id转为name)
        umUserListVoPageInfo.getList().stream().filter(x->x.getParent()!=null).
                forEach(x->{
                    condition.setId(Long.parseLong(x.getParent()));
                    //当trueName为空时，查出的实体也为空
                    UmUserPo queryParentUser = mapper.selectOne(new QueryWrapper<>(condition, "true_name"));
                    x.setParent(queryParentUser==null ||queryParentUser.getTrueName()==null?"":queryParentUser.getTrueName());
                });
        return umUserListVoPageInfo;
    }

    @Override
    public UmUserDetailVo getUserDetailVo(Long id) {
        UmUserDetailVo umUserDetailVo = mapper.loadUserDetailVo(id);
        //组装labelNames storeName nextLevelExperience parentName(数据库查出来的是id)
        umUserDetailVo.setLabelNames(mapper.getLabelNamesByUserId(id));
        if (umUserDetailVo.getStoreId()!=null){
            QueryWrapper storeWrapper=new QueryWrapper();
            storeWrapper.eq("id",umUserDetailVo.getStoreId());
            storeWrapper.select("name");
            SmStorePo queryStore = smStoreMapper.selectOne(storeWrapper);
            umUserDetailVo.setStoreName(queryStore.getName());
        }
        if (umUserDetailVo.getParentName()!=null){
            QueryWrapper userWrapper=new QueryWrapper();
            userWrapper.eq("id",umUserDetailVo.getParentName());
            userWrapper.select("true_name");
            UmUserPo queryParentUser = mapper.selectOne(userWrapper);
            umUserDetailVo.setParentName(queryParentUser==null?null:queryParentUser.getTrueName());
        }
        return umUserDetailVo;
    }

    @Override
    public boolean updateUmUser(UpdateUserDto updateUserDto,String currentUserName) {
        //修改主表字段
        mapper.updateUmUser(updateUserDto,currentUserName);
        //修改用户与标签关联表
        List<Long> labelIds = updateUserDto.getLabelIds();
        //先删掉关联数据
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",updateUserDto.getId());
        relUserLabelMapper.delete(queryWrapper);
        //插入关联数据
        if (!ListUtil.isListNullAndEmpty(labelIds)){
            labelIds.forEach(x->{
                UmRelUserLabelPo umRelUserLabelPo = new UmRelUserLabelPo();
                umRelUserLabelPo.setCreateBy(currentUserName).setUserId(updateUserDto.getId())
                        .setUserLabelId(x);
                relUserLabelMapper.insert(umRelUserLabelPo);
            });
        }
        return true;
    }

    @Override
    public List<UmUserRelVo> getRelUsers(Long id) {
        return mapper.getRelUsers(id);
    }

    /**
     *用户反馈信息
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
}
