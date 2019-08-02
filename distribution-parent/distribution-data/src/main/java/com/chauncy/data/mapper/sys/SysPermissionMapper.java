package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.sys.permission.GetPermissionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单权限表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysPermissionMapper extends IBaseMapper<SysPermissionPo> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<SysPermissionPo> findByUserId(@Param("userId") String userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(@Param("userId") String userId);

    /**
     * 通过层级查找
     * 默认升序
     * @param level
     * @return
     */
    List<SysPermissionPo> findByLevelOrderBySortOrder(@Param("level") Integer level);

    /**
     * 通过parendId查找
     * @param parentId
     * @return
     */
    List<SysPermissionPo> findByParentIdOrderBySortOrder(@Param("parentId") String parentId);

    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    List<SysPermissionPo> findByTypeAndStatusOrderBySortOrder(@Param("type") Integer type, @Param("status") Integer status);

    /**
     * 通过名称获取
     * @param title
     * @return
     */
    List<SysPermissionPo> findByTitle(@Param("title") String title);

    /**
     * 模糊搜索
     * @param title
     * @return
     */
    List<SysPermissionPo> findByTitleLikeOrderBySortOrder(@Param("title") String title);

    /**
     * 获取菜单权限
     *
     * @param roleId
     * @return
     */
    List<GetPermissionVo> getPermission(String roleId);

    /**
     * 获取全部权限列表
     * @return
     */
    @Select("select id,title,parent_id from sys_permission where del_flag = 0")
    List<GetPermissionVo> selectAll();
}
