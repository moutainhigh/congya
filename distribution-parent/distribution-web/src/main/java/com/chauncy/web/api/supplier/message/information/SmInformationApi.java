package com.chauncy.web.api.supplier.message.information;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/6/26 15:56
 */
@Api(tags = "商家_资讯管理接口")
@RestController
@RequestMapping("/supplier/information")
@Slf4j
public class SmInformationApi extends BaseApi {

    @Autowired
    private IMmInformationService mmInformationService;
    
    /**
     * 保存资讯信息
     * @param informationDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存资讯信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "informationDto", value = "资讯信息")
                                     InformationDto informationDto) {

        mmInformationService.saveInformation(informationDto);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功");
    }

    /**
     * 编辑资讯信息
     * @param informationDto
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑资讯信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData edit(@Validated(IUpdateGroup.class)  @RequestBody @ApiParam(required = true, name = "informationDto", value = "资讯信息")
                                     InformationDto informationDto) {

        mmInformationService.editInformation(informationDto);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功");
    }


    /**
     * 根据ID查找资讯
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找资讯", notes = "根据ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData<InformationVo> findById(@ApiParam(required = true, value = "id")
                                                @PathVariable Long id) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationService.findById(id));

    }


    /**
     * 根据关联ID删除资讯跟店铺的绑定关系
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除绑定关系", notes = "根据关联ID删除资讯跟店铺的绑定关系")
    @GetMapping("/delRelById/{id}")
    public JsonViewData delRelById(@ApiParam(required = true, value = "id")
                                                @PathVariable Long id) {

        mmInformationService.delRelById(id);
        return new JsonViewData(ResultCode.SUCCESS, "删除成功");

    }


    /**
     * 批量修改资讯状态
     * @param baseUpdateStatusDto
     * @return
     */
    @PostMapping("/editInformationStatus")
    @ApiOperation(value = "批量修改资讯状态")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData editInformationStatus(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "资讯id、修改的状态值")
                                                BaseUpdateStatusDto baseUpdateStatusDto) {

        mmInformationService.editEnabledBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改资讯状态成功");
    }


    /**
     * 批量删除资讯
     *
     * @param ids
     */
    @ApiOperation(value = "批量删除资讯", notes = "根据id批量删除")
    @GetMapping("/delInformationByIds/{ids}")
    public JsonViewData delInformationByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                 @PathVariable Long[] ids) {

        mmInformationService.delInformationByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 分页条件查询
     * @param baseSearchByTimeDto
     * @return
     */
    @ApiOperation(value = "分页条件查询", notes = "根据资讯ID、标题以及创建时间查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationPageInfoVo>> searchPaging(@RequestBody BaseSearchByTimeDto baseSearchByTimeDto) {

        PageInfo<InformationPageInfoVo> smStoreBaseVoPageInfo = mmInformationService.searchPaging(baseSearchByTimeDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                smStoreBaseVoPageInfo);
    }


}
