package com.chauncy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.RedisUtil;
import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.common.util.StringUtils;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.dto.app.user.add.BindUserDto;
import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.mapper.message.interact.MmFeedBackMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.user.UserDataVo;
import com.chauncy.data.vo.manage.user.idCard.SearchIdCardVo;
import com.chauncy.data.vo.supplier.PmGoodsVo;
import com.chauncy.user.service.IUmUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private MmFeedBackMapper feedBackMapper;


    @Override
    public boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum) {
        String redisKey = String.format(validCodeEnum.getRedisKey(), phone);
        Object redisValue = redisUtil.get(redisKey);
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
