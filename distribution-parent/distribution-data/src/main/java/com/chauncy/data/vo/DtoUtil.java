package com.chauncy.data.vo;

import com.chauncy.data.domain.po.sys.SysPermissionPo;

/**
 * @Author huangwancheng
 * @create 2019-05-27 15:13
 */
public class DtoUtil {

    public static MenuVo permissionToMenuVo(SysPermissionPo p){

        MenuVo menuVo = new MenuVo();

        menuVo.setId(p.getId());
        menuVo.setParentId(p.getParentId());
        menuVo.setName(p.getName());
        menuVo.setType(p.getType());
        menuVo.setTitle(p.getTitle());
        menuVo.setComponent(p.getComponent());
        menuVo.setPath(p.getPath());
        menuVo.setIcon(p.getIcon());
        menuVo.setUrl(p.getUrl());
        menuVo.setButtonType(p.getButtonType());

        return menuVo;
    }
}

