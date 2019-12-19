package com.chauncy.data.mapper.user;

import com.chauncy.data.domain.po.user.UmImAccountPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.user.UserNickNameVo;

/**
 * <p>
 * 用户IM账号 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-12-18
 */
public interface UmImAccountMapper extends IBaseMapper<UmImAccountPo> {

    /**
     * @Author yeJH
     * @Date 2019/12/18 22:39
     * @Description 通过客服im账号获取对应的店铺
     *
     * @Update yeJH
     *
     * @param  imAccount
     * @return com.chauncy.data.vo.app.user.UserNickNameVo
     **/
    UserNickNameVo getByImAccount(String imAccount);
}
