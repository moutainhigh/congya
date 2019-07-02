package com.chauncy.web.api.supplier.evaluate;

import com.chauncy.data.dto.supplier.good.select.SearchEvaluatesDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.evaluate.SearchEvaluateVo;
import com.chauncy.order.evaluate.service.IOmEvaluateService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-07-01 21:13
 *
 * 商家端评价管理
 */
@RestController
@Api(tags = "商家_评价管理")
@Slf4j
@RequestMapping("/supplier/evaluate")
public class EvaluateManageApi {

    @Autowired
    private IOmEvaluateService service;

    /**
     * 条件查询评价信息
     * @param searchEvaluateDto
     * @return
     */
    @ApiOperation("条件分页查询评价信息")
    @PostMapping("/searchEvaluate")
    public JsonViewData<PageInfo<SearchEvaluateVo>> searchEvaluate(@RequestBody @ApiParam(required = true,name = "searchEvaluateDto",value = "条件查找评价信息")
                                      @Validated SearchEvaluatesDto searchEvaluateDto){

        return new JsonViewData(service.searchEvaluate(searchEvaluateDto));
    }

}
