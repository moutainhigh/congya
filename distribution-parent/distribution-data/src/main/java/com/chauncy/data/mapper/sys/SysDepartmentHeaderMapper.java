package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysDepartmentHeaderPo;
import com.chauncy.data.mapper.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 部门与部门负责人关系 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysDepartmentHeaderMapper extends IBaseMapper<SysDepartmentHeaderPo> {

    /**
     * 通过部门和负责人类型获取
     * @param departmentId
     * @param type
     * @return
     */
    List<SysDepartmentHeaderPo> findByDepartmentIdAndType(String departmentId, Integer type);

    /**
     * 通过部门id删除
     * @param departmentId
     */
    void deleteByDepartmentId(String departmentId);

    /**
     * 通过userId删除
     * @param userId
     */
    void deleteByUserId(String userId);

}
