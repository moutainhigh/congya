package com.chauncy.web.api.common;

import com.chauncy.data.areaService.IAreaRegionService;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.area.AreaCityVo;
import com.chauncy.data.vo.area.AreaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-28 16:25
 *
 * 省市区街道管理
 */
@Api(tags = "通用_省市区街道")
@RestController
@RequestMapping("/common/area")
@Slf4j
public class AreaRegionApi {

    @Autowired
    private IAreaRegionService service;

    /**
     * 获取省市区
     *
     * @return
     */
    @GetMapping("/searchList")
    @ApiOperation("获取省市区地址")
    public JsonViewData<List<AreaVo>> searchList(){

        return new JsonViewData(service.searchList());
    }

    /**
     * 获取省市
     *
     * @return
     */
    @GetMapping("/search")
    @ApiOperation("获取省市地址")
    public JsonViewData<List<AreaCityVo>> search(){

        return new JsonViewData(service.search());
    }

    /**
     * 根据区县编号获取街道信息
     *
     * @param parentCode
     * @return
     */
    @GetMapping("/findStreet/{parentCode}")
    @ApiOperation("根据区县编号获取街道信息")
    public JsonViewData<List<AreaVo>> findStreet(@ApiParam(required = true,name = "parentCode",value = "区县编号")
                                   @PathVariable("parentCode") String parentCode){
        return new JsonViewData<>(service.findStreet(parentCode));
    }
}
