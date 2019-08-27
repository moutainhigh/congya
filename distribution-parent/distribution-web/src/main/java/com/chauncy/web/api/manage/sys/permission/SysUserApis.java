package com.chauncy.web.api.manage.sys.permission;


import com.chauncy.data.domain.po.sys.SysRolePermissionPo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.sys.user.select.SearchUsersByConditionDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.sys.permission.GetPermissionVo;
import com.chauncy.data.vo.sys.permission.SearchUsersByConditionVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.system.service.*;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.chauncy.common.constant.SecurityConstant.SYS_TYPE_MANAGER;
import static com.chauncy.common.constant.SecurityConstant.SYS_TYPE_SUPPLIER;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Slf4j
@Api(tags = "平台-系统管理")
@CacheConfig(cacheNames = "user")
@Transactional
@RestController
@RequestMapping("/manage/sys")
public class SysUserApis extends BaseApi {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Autowired
    private ISysDepartmentService departmentService;

    @Autowired
    private ISysRoleUserService iUserRoleService;

    @Autowired
    private ISysUserRoleCatchService userRoleCatchService;

    @Autowired
    private ISysDepartmentHeaderService departmentHeaderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 多条件分页获取用户列表
     *
     * @param searchUsersByConditionDto
     * @return
     */
    @PostMapping("/searchUsersByCondition")
    @ApiOperation("多条件分页获取用户列表")
    public JsonViewData<PageInfo<SearchUsersByConditionVo>> searchUsersByCondition(@RequestBody @ApiParam(required = true, name = "searchUsersByConditionDto", value = "多条件分页获取用户列表")
                                                                                   @Validated SearchUsersByConditionDto searchUsersByConditionDto) {

        //获取当前用户
        SysUserPo userPo = securityUtil.getCurrUser();
        Integer systemType;
        Long storeId = null;
        if (userPo.getSystemType() == SYS_TYPE_MANAGER){
            systemType = SYS_TYPE_MANAGER;
        }else {
            systemType = SYS_TYPE_SUPPLIER;
            storeId = userPo.getStoreId();
        }

        Integer pageNo = searchUsersByConditionDto.getPageNo() == null ? defaultPageNo : searchUsersByConditionDto.getPageNo();
        Integer pageSize = searchUsersByConditionDto.getPageSize() == null ? defaultPageSize : searchUsersByConditionDto.getPageSize();

        Long finalStoreId = storeId;
        PageInfo<SearchUsersByConditionVo> searchUsersByConditionVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> userService.searchUsersByCondition(searchUsersByConditionDto,systemType, finalStoreId));
        searchUsersByConditionVoPageInfo.getList().forEach(u -> {
            // 关联角色
            List<SysRolePo> list = userRoleCatchService.findByUserId(u.getId());
            List<BaseVo> roleList = Lists.newArrayList();
            StringBuffer roleName = new StringBuffer();
            list.forEach(a -> {
                BaseVo role = new BaseVo();
                role.setId(Long.valueOf(a.getId()));
                role.setName(a.getName());
                roleList.add(role);
                roleName.append(a.getName()).append(" ");
            });
            u.setRoleList(roleList);
            u.setRoleName(String.valueOf(roleName));

        });
        return setJsonViewData(searchUsersByConditionVoPageInfo);
    }


    @PostMapping(value = "/getAllByPage")
    @ApiOperation(value = "分页获取角色")
    public JsonViewData<PageInfo<SysRolePo>> getRoleByPage(@RequestBody @ApiParam(required = true,name = "分页查询条件",value = "baseSearchDto")
                                                           @Validated BaseSearchDto baseSearchDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        return setJsonViewData(roleService.getRoleByPage(baseSearchDto,userPo));
    }

    /**
     * 获取菜单权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/getPermission/{roleId}")
    @ApiOperation("获取菜单权限")
    public JsonViewData<List<GetPermissionVo>> getPermission(@ApiParam(required = false,name="roleId",value = "权限ID")
                                                             @PathVariable String roleId){
        SysUserPo userPo = securityUtil.getCurrUser();
        return setJsonViewData(permissionService.getPermission(roleId,userPo));
    }

}


