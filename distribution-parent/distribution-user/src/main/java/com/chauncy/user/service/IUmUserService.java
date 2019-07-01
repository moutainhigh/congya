package com.chauncy.user.service;

import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.core.Service;

/**
 * <p>
 * 前端用户 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
public interface IUmUserService extends Service<UmUserPo> {

    boolean validVerifyCode(String verifyCode,String phone);


}
