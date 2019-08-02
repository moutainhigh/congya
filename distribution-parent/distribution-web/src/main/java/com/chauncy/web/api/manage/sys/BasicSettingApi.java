package com.chauncy.web.api.manage.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.dto.manage.sys.UpdateBasicSettingDto;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.Result;
import com.chauncy.system.service.IBasicSettingService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 平台基本设置 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@RestController
@Api(tags = "平台-系统管理-基本参数")
@RequestMapping("/manage/sys/basic")
public class BasicSettingApi extends BaseApi {

    @Autowired
    private IBasicSettingService service;

    @PostMapping(value = "/update")
    @ApiOperation(value = "修改基本参数")
    public JsonViewData update(@RequestBody UpdateBasicSettingDto updateBasicSettingDto) {

        BasicSettingPo basicSettingPo = new BasicSettingPo();
        BeanUtils.copyProperties(updateBasicSettingDto,basicSettingPo);
        basicSettingPo.setUpdateBy(getUser().getUsername());
        service.updateById(basicSettingPo);
        return setJsonViewData(ResultCode.SUCCESS);

    }

    @PostMapping(value = "/view")
    @ApiOperation(value = "修改基本参数")
    public JsonViewData<BasicSettingPo> view() {

        return setJsonViewData(service.getOne(new QueryWrapper<>()));

    }




}
