package com.chauncy.web.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.user.service.IUmUserService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019-05-30 10:52
 **/
public abstract class BaseApi {

    @Autowired
    protected HttpServletRequest httpRequest;

    @Autowired
    protected HttpServletResponse httpResponse;

    @Autowired
    protected HttpSession httpSession;

    @Autowired
    protected IUmUserService umUserService;


    protected static int defaultPageSize = 10;

    protected static int defaultPageNo = 1;

    protected static String defaultSoft="sort desc";

    protected UserDetails getUser() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    /**
     * 获取当前登录app用户
     * @return
     */
    public UmUserPo getAppCurrUser(){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UmUserPo userPo=new UmUserPo();
        userPo.setPhone(user.getUsername());
        return umUserService.getOne(new QueryWrapper<>(userPo));
    }

    protected JsonViewData setJsonViewData(ResultCode resultCode) {
        return new JsonViewData(resultCode);
    }

    protected JsonViewData setJsonViewData(ResultCode resultCode, String message,Object... args) {
        return new JsonViewData(resultCode, String.format(message,args));
    }

    protected JsonViewData setJsonViewData(Object data) {
        return new JsonViewData(data);
    }

    /**
     * 多对多关系修改时验证id是否被其他表用过，
     * 若用过则不允许删除（即修改后该id仍需存在）
     * @param field
     * @param updateIds
     * @param tableName
     *//*
    protected void vaildManyToMany(String field, List<Long> updateIds,List<String> tableName){


    }*/




}
