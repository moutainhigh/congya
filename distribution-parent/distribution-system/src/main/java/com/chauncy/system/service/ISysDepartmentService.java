package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysDepartmentPo;

import java.util.List;

/**
 * <p>
 * 用户组—部门 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */

public interface ISysDepartmentService extends Service<SysDepartmentPo> {

    /**
     * 通过父id获取 升序
     * @param parentId
     * @param openDataFilter 是否开启数据权限
     * @return
     */
    List<SysDepartmentPo> findByParentIdOrderBySortOrder(String parentId, Boolean openDataFilter);

    /**
     * 通过父id和状态获取
     * @param parentId
     * @param status
     * @return
     */
    List<SysDepartmentPo> findByParentIdAndStatusOrderBySortOrder(String parentId, Integer status);

    /**
     * 部门名模糊搜索 升序
     * @param title
     * @param openDataFilter 是否开启数据权限
     * @return
     */
    List<SysDepartmentPo> findByTitleLikeOrderBySortOrder(String title, Boolean openDataFilter);

}
