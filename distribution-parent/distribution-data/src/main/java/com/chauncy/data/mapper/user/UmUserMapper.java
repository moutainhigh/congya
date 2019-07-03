package com.chauncy.data.mapper.user;

import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 前端用户 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
public interface UmUserMapper extends IBaseMapper<UmUserPo> {

    /**
     * 登陆成功后修改登录次数和登录时间
     * @param phone 手机号码
     * @return
     */
    int updateLogin(@Param("phone") String phone);

}
