package com.chauncy.web.api.common;

import com.chauncy.common.constant.Constants;
import com.chauncy.common.enums.system.LoginType;
import com.chauncy.common.third.easemob.RegistIM;
import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-09-13 11:05
 *
 * 获取当前用户IM账号
 *
 */

@Api(tags = "通用_获取当前用户IM账号")
@RestController
@RequestMapping("/common/getImAccount")
@Slf4j
public class GetImAccountApi {

    @Autowired
    private SecurityUtil securityUtil;

    @ApiOperation("获取当前用户IM账号")
    @GetMapping("/getImAccount/{loginType}")
    public String GetImAccount(@PathVariable String loginType){

        LoginType loginTypeEnum = LoginType.valueOf(loginType);
        String id = "";
        switch (loginTypeEnum) {
            case MANAGE:
                SysUserPo sysUserPo = securityUtil.getCurrUser();
                if (RegistIM.getUser(sysUserPo.getId()) ==null){
                    RegUserBo regUserBo = new RegUserBo();
                    regUserBo.setUsername(sysUserPo.getId());
                    regUserBo.setPassword(Constants.PASSWORD);
                    RegistIM.reg(regUserBo);
                }
                id = sysUserPo.getId();
                break;
            case SUPPLIER:

                //注册用户IM
                SysUserPo sysUserPo1 = securityUtil.getCurrUser();
                if (RegistIM.getUser(sysUserPo1.getId()) ==null){
                    RegUserBo regUserBo = new RegUserBo();
                    regUserBo.setUsername(sysUserPo1.getId());
                    regUserBo.setPassword(Constants.PASSWORD);
                    RegistIM.reg(regUserBo);
                }
                //注册店铺IM
                if (RegistIM.getUser(String.valueOf(sysUserPo1.getStoreId())) ==null){
                    RegUserBo storeBo = new RegUserBo();
                    storeBo.setUsername(String.valueOf(sysUserPo1.getStoreId()));
                    storeBo.setPassword(Constants.PASSWORD);
                    RegistIM.reg(storeBo);
                }
                id = sysUserPo1.getId();
                break;

            case APP_PASSWORD:
            case APP_CODE:
            case THIRD_WECHAT:
                UmUserPo umUserPo = securityUtil.getAppCurrUser();
                if (RegistIM.getUser(String.valueOf(umUserPo.getId())) ==null){
                    RegUserBo regUserBo = new RegUserBo();
                    regUserBo.setUsername(String.valueOf(umUserPo.getId()));
                    regUserBo.setPassword(Constants.PASSWORD);
                    RegistIM.reg(regUserBo);
                }
                id = String.valueOf(umUserPo.getId());
                break;
        }
        return id;
    }
}
