package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysUserMapper extends IBaseMapper<SysUserPo> {

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    List<SysUserPo> findByUsername(@Param("username") String username);

    /**
     * 通过手机获取用户
     * @param mobile
     * @return
     */
    List<SysUserPo> findByMobile(@Param("mobile") String mobile);

    /**
     * 通过邮件获取用户
     * @param email
     * @return
     */
    List<SysUserPo> findByEmail(@Param("email") String email);

    /**
     * 通过部门id获取
     * @param departmentId
     * @return
     */
    List<SysUserPo> findByDepartmentId(@Param("departmentId") String departmentId);

}
