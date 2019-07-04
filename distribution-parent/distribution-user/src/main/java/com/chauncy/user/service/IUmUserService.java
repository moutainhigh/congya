package com.chauncy.user.service;

import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.dto.app.user.add.BindUserDto;

/**
 * <p>
 * 前端用户 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
public interface IUmUserService extends Service<UmUserPo> {
    /**
     * 验证码是否正确
     * @param verifyCode
     * @param phone
     * @param validCodeEnum
     * @return
     */
    boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum) ;

    /**
     * 新增用户
     * @param addUserDto
     * @return
     */
    boolean saveUser(AddUserDto addUserDto);

    /**
     * 绑定用户
     * @param userDto
     * @return
     */
    boolean bindUser(BindUserDto userDto);

    /**
     * 用户修改密码
     * @param addUserDto
     * @return
     */
    boolean reset(AddUserDto addUserDto);

    /**
     * 登陆成功后修改登陆时间和登录次数
     * @param phone
     * @return
     */
    boolean updateLogin(String phone);


}
