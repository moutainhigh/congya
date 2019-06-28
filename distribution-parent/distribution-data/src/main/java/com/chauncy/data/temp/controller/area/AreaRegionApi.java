package com.chauncy.data.temp.controller.area;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.data.areaService.IAreaRegionService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 中国行政地址信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@RestController
@RequestMapping("area-region-po")
@Api(tags = "中国行政地址信息")
public class AreaRegionApi {

 @Autowired
 private IAreaRegionService service;


}
