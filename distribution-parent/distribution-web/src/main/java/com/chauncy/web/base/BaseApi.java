package com.chauncy.web.base;

import com.chauncy.common.enums.ResultCode;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.vo.JsonViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    protected static int defaultPageSize = 10;

    protected static int defaultPageNo = 1;

    protected UserDetails getUser() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    protected JsonViewData setJsonViewData(ResultCode resultCode) {
        return new JsonViewData(resultCode);
    }

    protected JsonViewData setJsonViewData(ResultCode resultCode, String message) {
        return new JsonViewData(resultCode, message);
    }

    protected JsonViewData setJsonViewData(Object data) {
        return new JsonViewData(data);
    }




}
