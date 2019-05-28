package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysDepartmentPo;
import com.chauncy.data.mapper.Mapper;

import java.util.List;

/**
 * <p>
 * 用户组—部门 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysDepartmentMapper extends Mapper<SysDepartmentPo> {


    /**
     * 通过父id获取 升序
     * @param parentId
     * @return
     */
    List<SysDepartmentPo> findByParentIdOrderBySortOrder(String parentId);

    /**
     * 通过父id获取 升序 数据权限
     * @param parentId
     * @param departmentIds
     * @return
     */
    List<SysDepartmentPo> findByParentIdAndIdInOrderBySortOrder(String parentId, List<String> departmentIds);

    /**
     * 通过父id和状态获取 升序
     * @param parentId
     * @param status
     * @return
     */
    List<SysDepartmentPo> findByParentIdAndStatusOrderBySortOrder(String parentId, Integer status);

    /**
     * 部门名模糊搜索 升序
     * @param title
     * @return
     */
    List<SysDepartmentPo> findByTitleLikeOrderBySortOrder(String title);

    /**
     * 部门名模糊搜索 升序 数据权限
     * @param title
     * @param departmentIds
     * @return
     */
    List<SysDepartmentPo> findByTitleLikeAndIdInOrderBySortOrder(String title, List<String> departmentIds);


}
