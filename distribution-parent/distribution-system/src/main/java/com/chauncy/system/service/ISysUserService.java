package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.sys.user.select.SearchUsersByConditionDto;
import com.chauncy.data.vo.sys.SearchVo;
import com.chauncy.data.vo.sys.permission.SearchUsersByConditionVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@CacheConfig(cacheNames = "user")
public interface ISysUserService extends Service<SysUserPo> {

    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
//    @Cacheable(key = "#username")
    SysUserPo findByUsername(String username);

    /**
     * 通过手机获取用户
     *
     * @param mobile
     * @return
     */
    SysUserPo findByMobile(String mobile);

    /**
     * 通过邮件和状态获取用户
     *
     * @param email
     * @return
     */
    SysUserPo findByEmail(String email);

    /**
     * 多条件分页获取用户
     *
     * @param user
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<SysUserPo> findByCondition(SysUserPo user, SearchVo searchVo, Pageable pageable);

    /**
     * 通过部门id获取
     *
     * @param departmentId
     * @return
     */
    List<SysUserPo> findByDepartmentId(String departmentId);

    /**
     * 多条件分页获取用户列表
     *
     * @param searchUsersByConditionDto
     * @return
     */
    List<SearchUsersByConditionVo> searchUsersByCondition(SearchUsersByConditionDto searchUsersByConditionDto);
}
