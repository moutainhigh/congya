package com.chauncy.web.api.manage.product;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeCategoryPo;
import com.chauncy.data.dto.BaseSearchDto;
import com.chauncy.data.dto.good.GoodCategoryDeleteDto;
import com.chauncy.data.dto.good.GoodCategoryDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.chauncy.product.service.IPmGoodsRelAttributeCategoryService;
import com.chauncy.product.service.IPmGoodsSkuCategoryAttributeRelationService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 商品分类页面控制器
 *
 * @author zhangrt
 * @since 2019-05-21
 */
@Api("商品分类管理接口")
@RestController
@RequestMapping("/manage/product")
@Slf4j
public class PmGoodsCategoryApi extends BaseApi {

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

    @Autowired
    private IPmGoodsSkuCategoryAttributeRelationService categoryAttributeRelationService;

    @Autowired
    private IPmGoodsRelAttributeCategoryService relAttributeCategoryService;

    /**
     * 保存分类
     *
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存商品分类")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@RequestBody @Valid @ApiParam(required = true, name = "goodCategoryDto", value = "分类相关信息")
                                                  GoodCategoryDto goodCategoryDto,
                                      BindingResult result) {
        //先保存分类
        PmGoodsCategoryPo pmGoodsCategoryPo=new PmGoodsCategoryPo();
        pmGoodsCategoryPo.setCreateBy(getUser().getUsername());
        if (goodCategoryDto.getParentId()==null){
            pmGoodsCategoryPo.setLevel(1);
        }
        else {
            PmGoodsCategoryPo parentPo = goodsCategoryService.getById(goodCategoryDto.getParentId());
            pmGoodsCategoryPo.setLevel(parentPo.getLevel()+1);
        }
        BeanUtils.copyProperties(goodCategoryDto,pmGoodsCategoryPo);
        goodsCategoryService.save(pmGoodsCategoryPo);

        //保存属性和分类之间的关系
        saveRelAttributeAndCategory(goodCategoryDto, pmGoodsCategoryPo);
        return setJsonViewData(ResultCode.SUCCESS);
    }


    /**
     * 编辑分类
     *
     */
    @PostMapping("/update")
    @ApiOperation(value = "编辑商品分类")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData update(@RequestBody @Validated(IUpdateGroup.class)  @ApiParam(required = true, name = "goodCategoryDto", value = "分类相关信息")
                                              GoodCategoryDto goodCategoryDto,
                                      BindingResult result) {
        //先修改分类
        PmGoodsCategoryPo pmGoodsCategoryPo=new PmGoodsCategoryPo();
        pmGoodsCategoryPo.setUpdateBy(getUser().getUsername());
        BeanUtils.copyProperties(goodCategoryDto,pmGoodsCategoryPo);
        goodsCategoryService.updateById(pmGoodsCategoryPo);

        Map<String,Object> columnMap=Maps.newHashMap("goods_category_id",pmGoodsCategoryPo.getId());
        relAttributeCategoryService.removeByMap(columnMap);

        //保存属性和分类之间的关系
        saveRelAttributeAndCategory(goodCategoryDto, pmGoodsCategoryPo);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 保存属性和分类之间的关系
     * @param goodCategoryDto
     * @param pmGoodsCategoryPo
     */
    private void saveRelAttributeAndCategory(@ApiParam(required = true, name = "goodCategoryDto", value = "分类相关信息")
                                             @Validated(IUpdateGroup.class)
                                             @RequestBody GoodCategoryDto goodCategoryDto, PmGoodsCategoryPo pmGoodsCategoryPo) {
        List<PmGoodsRelAttributeCategoryPo> relAttributeCategoryPos = Lists.newArrayList();
        if (!ListUtil.isListNullAndEmpty(goodCategoryDto.getGoodAttributeIds())) {
            goodCategoryDto.getGoodAttributeIds().forEach(x -> {
                PmGoodsRelAttributeCategoryPo relAttributeCategoryPo = new PmGoodsRelAttributeCategoryPo();
                relAttributeCategoryPo.setCreateBy(getUser().getUsername()).setGoodsAttributeId(x).
                        setGoodsCategoryId(pmGoodsCategoryPo.getId());
                relAttributeCategoryPos.add(relAttributeCategoryPo);

            });
        }
        relAttributeCategoryService.saveBatch(relAttributeCategoryPos);
    }

    /**
     * 修改分类和属性关系时的验证
     * 先查出该分类下哪些属性被该分类下的商品用到
     * 如果修改后的属性不包括所有这些被用到的，验证失败
     * @param goodAttributeIds
     * @param categoryId
     */
    private void validUpdateRelCategoryAndAttribute(List<Long> goodAttributeIds,Long categoryId){

    }

    @PostMapping("/search")
    @ApiOperation(value = "查看商品分类列表")
    public JsonViewData search(@RequestBody @Valid @ApiParam(required = true, name = "baseSearchDto", value = "分类列表查询条件")
                                       BaseSearchDto baseSearchDto,
                               BindingResult result) {

        PmGoodsCategoryPo queryCategory=new PmGoodsCategoryPo();
        Integer pageNo=baseSearchDto.getPageNo()==null?defaultPageNo:baseSearchDto.getPageNo();
        Integer pageSize=baseSearchDto.getPageSize()==null?defaultPageSize:baseSearchDto.getPageSize();
        BeanUtils.copyProperties(baseSearchDto,queryCategory);
        QueryWrapper<PmGoodsCategoryPo> queryWrapper=new QueryWrapper<>(queryCategory);
        PageInfo<PmGoodsCategoryPo> categoryPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> goodsCategoryService.list(queryWrapper));
        return setJsonViewData(categoryPageInfo);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除商品分类")
    public JsonViewData delete(@Validated @RequestBody GoodCategoryDeleteDto goodCategoryDeleteDto, BindingResult bindingResult){
        boolean isSuccess = goodsCategoryService.removeByIds(goodCategoryDeleteDto.getIds());
        return isSuccess?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }






}
