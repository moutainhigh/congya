package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.vo.sys.permission.GetPermissionVo;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface ISysPermissionService extends Service<SysPermissionPo> {

    /**
     * 通过层级查找
     * 默认升序
     * @param level
     * @return
     */
    List<SysPermissionPo> findByLevelOrderBySortOrder(Integer level);

    /**
     * 通过parendId查找
     * @param parentId
     * @return
     */
    List<SysPermissionPo> findByParentIdOrderBySortOrder(String parentId);

    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    List<SysPermissionPo> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

    /**
     * 通过名称获取
     * @param title
     * @return
     */
    List<SysPermissionPo> findByTitle(String title);

    /**
     * 模糊搜索
     * @param title
     * @return
     */
    List<SysPermissionPo> findByTitleLikeOrderBySortOrder(String title);

    /**
     * 获取菜单权限
     *
     * @param roleId
     * @return
     */
    List<GetPermissionVo> getPermission(String roleId);
}
