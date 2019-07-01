package com.chauncy.user.service;

import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.user.add.AddUserDto;

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
    boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum);

    /**
     * 新增用户
     * @param addUserDto
     * @return
     */
    boolean saveUser(AddUserDto addUserDto);


}
