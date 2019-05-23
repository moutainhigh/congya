package com.chauncy.system.service;

import com.chauncy.data.domain.po.sys.SysUserPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface ISysUserService extends IService<SysUserPo> {

    /**
     *登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    String login(String username,String password);

    /**
     * 注册用户
     * @param sysUserPo
     * @return
     */
    SysUserPo register(SysUserPo sysUserPo);

    /**
     * 登出
     * @param token
     */
    void logout(String token);

}
