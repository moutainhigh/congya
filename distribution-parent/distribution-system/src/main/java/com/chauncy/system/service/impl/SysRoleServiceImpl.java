package com.chauncy.system.service.impl;

import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysRolePermissionPo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.mapper.sys.SysRoleMapper;
import com.chauncy.data.mapper.sys.SysRolePermissionMapper;
import com.chauncy.system.service.ISysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysRoleServiceImpl extends AbstractService<SysRoleMapper, SysRolePo> implements ISysRoleService {

 @Autowired
 private SysRoleMapper mapper;

 @Autowired
 private SysRolePermissionMapper rolePermissionMapper;

 @Override
 public List<SysRolePo> findByDefaultRole(Boolean defaultRole) {
  return mapper.findByDefaultRole(defaultRole);
 }

    /**
     * 分页获取角色
     *
     * @param baseSearchDto
     * @return
     */
    @Override
    public PageInfo<SysRolePo> getRoleByPage(BaseSearchDto baseSearchDto,SysUserPo userPo) {
        Integer pageNo = baseSearchDto.getPageNo() == null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize() == null ? defaultPageSize : baseSearchDto.getPageSize();

        Integer systemType;
        Long storeId = null;
        if (userPo.getSystemType()== SecurityConstant.SYS_TYPE_MANAGER){
            systemType = 1;
        }else {
            systemType = 2;
            storeId = userPo.getStoreId();
        }

        Long finalStoreId = storeId;
        PageInfo<SysRolePo> sysRolePoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.getRoleByPage(baseSearchDto,systemType, finalStoreId));

        sysRolePoPageInfo.getList().forEach(a->{
            List<SysRolePermissionPo> permissions = rolePermissionMapper.findByRoleId(a.getId());
            a.setPermissions(permissions);
        });
        return sysRolePoPageInfo;
    }
}
