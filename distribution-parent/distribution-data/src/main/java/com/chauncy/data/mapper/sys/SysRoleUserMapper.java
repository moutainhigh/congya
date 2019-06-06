package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与角色关系表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysRoleUserMapper extends IBaseMapper<SysRoleUserPo> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<SysRolePo> findByUserId(@Param("userId") String userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(@Param("userId") String userId);


    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<SysRoleUserPo> findByRoleId(@Param("roleId") String roleId);

    /**
     * 删除该用户角色
     * @param userId
     */
    void deleteByUserId(@Param("userId") String userId);

}
