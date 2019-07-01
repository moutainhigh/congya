package com.chauncy.user.service.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.RedisUtil;
import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.common.util.StringUtils;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.user.service.IUmUserService;
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




    @Override
    public boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum) {
        String redisKey=String.format(validCodeEnum.getRedisKey(),phone);
        Object redisValue = redisUtil.get(redisKey);
        if (redisValue==null){
            throw new ServiceException(ResultCode.FAIL,"验证码错误！");
        }
        if (StringUtils.equals(verifyCode.trim(), redisValue.toString().trim())){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(AddUserDto addUserDto) {
        if (!validVerifyCode(addUserDto.getVerifyCode(),addUserDto.getPhone(),ValidCodeEnum.REGISTER_CODE)){
            throw new ServiceException(ResultCode.FAIL,"验证码错误！");
        }
        UmUserPo saveUser = new UmUserPo();
        BeanUtils.copyProperties(addUserDto,saveUser);

        /**
         * 设置一些值
         */
        saveUser.setInviteCode(SnowFlakeUtil.getFlowIdInstance().nextId());
        saveUser.setCreateBy("test");
        return mapper.insert(saveUser)>0;
    }
}
