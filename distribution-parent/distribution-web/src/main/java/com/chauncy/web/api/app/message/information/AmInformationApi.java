package com.chauncy.web.api.app.message.information;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.data.vo.manage.message.information.label.InformationLabelVo;
import com.chauncy.message.information.category.service.IMmInformationCategoryService;
import com.chauncy.message.information.label.service.IMmInformationLabelService;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yeJH
 * @since 2019/7/1 22:24
 */
@Api(tags = "APP_店铺资讯管理接口")
@RestController
@RequestMapping("/app/information")
@Slf4j
public class AmInformationApi extends BaseApi {

    @Autowired
    private IMmInformationService mmInformationService;
    @Autowired
    private IMmInformationCategoryService mmInformationCategoryService;
    @Autowired
    private IMmInformationLabelService mmInformationLabelService;


    /**
     * 查询所有的店铺资讯分类
     * @return
     */
    @ApiOperation(value = "查询所有的店铺资讯分类", notes = "查询所有的店铺资讯分类")
    @GetMapping("/category/selectAll")
    public JsonViewData<InformationCategoryVo> searchCategoryAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationCategoryService.selectAll());
    }

    /**
     * 查询所有的店铺资讯标签
     * @return
     */
    @ApiOperation(value = "查询所有的店铺资讯标签", notes = "查询所有的店铺资讯标签")
    @GetMapping("/label/selectAll")
    public JsonViewData<InformationLabelVo> searchLabelAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationLabelService.selectAll());
    }

    /**
     * app条件查询店铺资讯
     * @param searchInfoByConditionDto
     * @return
     */
    @ApiOperation(value = "分页条件查询", notes = "根据资讯分类，标签，关注，热榜以及内容、标题模糊搜索")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationPagingVo>> searchPaging(@RequestBody SearchInfoByConditionDto searchInfoByConditionDto) {

        PageInfo<InformationPagingVo> smStoreBaseVoPageInfo = mmInformationService.searchPaging(searchInfoByConditionDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                smStoreBaseVoPageInfo);
    }

    /**
     * app根据ID查找店铺资讯
     * @param searchInfoByConditionDto
     * @return
     */
    @ApiOperation(value = "查找店铺资讯", notes = "根据ID查找")
    @GetMapping("/searchPaging")
    public JsonViewData<InformationPagingVo> searchPaging(@RequestBody SearchInfoByConditionDto searchInfoByConditionDto) {

        PageInfo<InformationPagingVo> smStoreBaseVoPageInfo = mmInformationService.searchPaging(searchInfoByConditionDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                smStoreBaseVoPageInfo);
    }

}
