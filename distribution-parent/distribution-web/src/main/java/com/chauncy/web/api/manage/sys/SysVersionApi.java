package com.chauncy.web.api.manage.sys;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.manage.sys.vsersion.SaveVersionDto;
import com.chauncy.data.dto.manage.sys.vsersion.SearchVersionDto;
import com.chauncy.data.dto.manage.sys.vsersion.UpdateCurrentVersionDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.version.SearchVersionVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.system.service.ISysVersionService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * app版本信息 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2020-03-27
 */
@RestController
@RequestMapping("/sys-version")
@Api(tags = "平台-app版本信息")
public class SysVersionApi extends BaseApi {

    @Autowired
    private ISysVersionService service;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @Author chauncy
     * @Date 2020-03-27 20:44
     * @param  saveVersionDto
     * @return
     *       保存版本信息
     **/
    @ApiOperation(value = "保存版本信息",notes = "新增时id传0")
    @PostMapping("/save")
    public JsonViewData saveVersion(@RequestBody @ApiParam(required = true,name = "saveVersionDto",value = "保存版本信息")
                                    @Validated SaveVersionDto saveVersionDto){
        SysUserPo userPo = securityUtil.getCurrUser();
        service.saveVersion(saveVersionDto,userPo);

        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * @Author chauncy
     * @Date 2020-03-27 21:37
     * @param  searchVersionDto
     * @return
     *       条件分页查询版本信息
     **/
    @ApiOperation(value = "条件分页查询版本信息")
    @PostMapping("/search")
    public JsonViewData<PageInfo<SearchVersionVo>> searchVersion(@RequestBody @ApiParam(required = true,name = "searchVersionDto",value = "条件分页查询版本信息")
                                                                 @Validated SearchVersionDto searchVersionDto){
        return setJsonViewData(service.searchVersion(searchVersionDto));
    }

    /**
     * @Author chauncy
     * @Date 2020-03-27 21:57
     * @param  ids
     * @return
     *       根据ids批量删除版本信息
     **/
    @ApiOperation(value = "根据ids批量删除版本信息")
    @GetMapping("/batch-del/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true,name = "ids",value = "版本信息id集合") @PathVariable Long[] ids){

        service.delByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    @ApiOperation(value = "设置默认(当前)版本")
    @PostMapping("/default")
    public JsonViewData setCurrent(@RequestBody @ApiParam(required = true,name = "updateCurrentVersionDto",value = "设置默认(当前)版本")
                                   @Validated UpdateCurrentVersionDto updateCurrentVersionDto) {

        service.setCurrent(updateCurrentVersionDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

}
