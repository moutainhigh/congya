package com.chauncy.web.api.manage.message.advice;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.SaveStoreClassificationDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchClassificationStoreDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchStoreClassificationDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchStoresDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;
import com.chauncy.message.advice.IMmAdviceRelTabAssociationService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表与广告选项卡表关联表，广告位置为具体店铺分类下面不同选项卡+推荐的店铺，多重关联选项卡 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/manage/message/advice")
@Api(tags = "平台_广告运营管理_首页有店+店铺分类详情")
public class MmAdviceRelTabAssociationApi extends BaseApi {

    @Autowired
    private IMmAdviceRelTabAssociationService service;


    /**
     * 分页查询店铺分类
     *
     * @param searchStoreClassificationDto
     * @return
     */
    @PostMapping("/searchStoreClassification")
    @ApiOperation("分页查询店铺分类")
    public JsonViewData<PageInfo<BaseVo>> searchStoreClassification(@RequestBody @ApiParam(required = true,name = "searchStoreClassificationDto",value = "分页查询店铺分类条件参数")
                                                                    @Validated SearchStoreClassificationDto searchStoreClassificationDto){
        return setJsonViewData(service.searchStoreClassification(searchStoreClassificationDto));
    }

    /**
     * 分页查询店铺分类下店铺信息
     *
     * @param searchClassificationStoreDto
     * @return
     */
    @PostMapping("/searchClassificationStore")
    @ApiOperation("分页查询店铺分类下店铺信息")
    public JsonViewData<PageInfo<BaseVo>> searchClassificationStore(@RequestBody @ApiParam(required = true,name = "searchStoreClassificationDto",value = "分页查询店铺分类下店铺条件参数")
                                                      @Validated SearchClassificationStoreDto searchClassificationStoreDto){
        return setJsonViewData(service.searchClassificationStore(searchClassificationStoreDto));
    }

    /**
     * 首页有店+店铺分类详情
     *
     * 保存广告位置为首页有店+店铺分类详情的信息
     *
     * @param saveStoreClassificationDto
     * @return
     */
    @ApiOperation("保存广告位置为首页有店+店铺分类详情的信息")
    @PostMapping("/saveStoreClassification")
    public JsonViewData saveStoreClassification(@RequestBody @ApiParam(required = true,name = "saveStoreClassificationDto",value = "保存广告位置为首页有店+店铺分类详情的信息")
                                               @Validated SaveStoreClassificationDto saveStoreClassificationDto){

        service.saveStoreClassification(saveStoreClassificationDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * 条件查询关联的店铺
     *
     * 选项卡关联的店铺的分页
     *
     * @param searchStoresDto
     * @return
     */
    @PostMapping("/searchStores")
    @ApiOperation("条件分页查询已经关联的店铺")
    public JsonViewData<PageInfo<StoreVo>> searchStores(@RequestBody @ApiParam(required = true,name = "searchStoresDto",value = "条件分页查询已经关联的店铺")
                                                        @Validated SearchStoresDto searchStoresDto){

        return setJsonViewData(service.searchStores(searchStoresDto));
    }

}
