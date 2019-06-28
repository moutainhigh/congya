package com.chauncy.web.api.manage.product;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.MyBaseTree;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeCategoryPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.good.add.GoodCategoryDto;
import com.chauncy.data.dto.manage.good.delete.GoodCategoryDeleteDto;
import com.chauncy.data.dto.manage.good.select.SearchGoodCategoryDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.product.AttributeIdNameTypeVo;
import com.chauncy.data.vo.manage.product.CategoryRelAttributeVo;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.chauncy.product.service.IPmGoodsRelAttributeCategoryService;
import com.chauncy.product.service.IPmGoodsSkuCategoryAttributeRelationService;
import com.chauncy.web.base.BaseApi;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

/**
 * 商品分类页面控制器
 *
 * @author zhangrt
 * @since 2019-05-21
 */
@Api(description = "商品分类管理接口")
@RestController
@RequestMapping("/manage/product/category")
@Slf4j
public class PmGoodsCategoryApi extends BaseApi {

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

    @Autowired
    private IPmGoodsSkuCategoryAttributeRelationService categoryAttributeRelationService;

    @Autowired
    private IPmGoodsRelAttributeCategoryService relAttributeCategoryService;

    @Autowired
    private IPmGoodsAttributeService attributeService;

    /**
     * 保存分类
     *
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存商品分类")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@RequestBody @Valid @ApiParam(required = true, name = "goodCategoryDto", value = "分类相关信息")
                                                  GoodCategoryDto goodCategoryDto) {
        if (!ListUtil.isListNullAndEmpty(goodCategoryDto.getGoodAttributeIds())){
            if (goodCategoryDto.getLevel()!=3){
                return setJsonViewData(ResultCode.PARAM_ERROR,"只有三级分类才允许分类和属性关联:当前为%s级分类",goodCategoryDto.getLevel());
            }
        }
        //先保存分类
        PmGoodsCategoryPo pmGoodsCategoryPo=new PmGoodsCategoryPo();
        pmGoodsCategoryPo.setCreateBy(getUser().getUsername());
        BeanUtils.copyProperties(goodCategoryDto,pmGoodsCategoryPo);
        pmGoodsCategoryPo.setId(null);
        goodsCategoryService.save(pmGoodsCategoryPo);


        //保存属性和分类之间的关系
        saveRelAttributeAndCategory(goodCategoryDto, pmGoodsCategoryPo);
        return setJsonViewData(pmGoodsCategoryPo.getId());
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
        //验证分类关联属性能否被修改
        validUpdateRelCategoryAndAttribute(goodCategoryDto.getGoodAttributeIds(),goodCategoryDto.getId());
        //先修改分类
        PmGoodsCategoryPo pmGoodsCategoryPo=new PmGoodsCategoryPo();
        pmGoodsCategoryPo.setUpdateBy(getUser().getUsername());
        BeanUtils.copyProperties(goodCategoryDto,pmGoodsCategoryPo);
        goodsCategoryService.updateById(pmGoodsCategoryPo);

        //三级分类才允许有关联
       if (goodCategoryDto.getLevel()==3) {
           Map<String, Object> columnMap = Maps.newHashMap("goods_category_id", pmGoodsCategoryPo.getId());
           relAttributeCategoryService.removeByMap(columnMap);

           //保存属性和分类之间的关系
           saveRelAttributeAndCategory(goodCategoryDto, pmGoodsCategoryPo);
       }
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
            relAttributeCategoryService.saveBatch(relAttributeCategoryPos);
        }
    }

    /**
     * 修改分类和属性关系时的验证
     * 先查出该分类下哪些属性被该分类下的商品用到
     * 如果修改后的属性不包括所有这些被用到的，验证失败
     * @param goodAttributeIds
     * @param categoryId
     */
    private void validUpdateRelCategoryAndAttribute(List<Long> goodAttributeIds,Long categoryId){
        //找出已经被引用的商品属性
        List<PmGoodsAttributePo> notAllowDelAttributes = attributeService.findByCategoryId(categoryId);
        if (ListUtil.isListNullAndEmpty(notAllowDelAttributes)){
            return;
        }
        notAllowDelAttributes.forEach(x->{
            if (!goodAttributeIds.contains(x.getId())){
                throw new ServiceException(ResultCode.PARAM_ERROR,"修改出错，%属性不允许删除：已被该分类下的商品所关联",x.getName());
            }
        });


    }

    @PostMapping("/search")
    @ApiOperation(value = "查看商品分类列表")

    public JsonViewData<SearchCategoryVo> search(@RequestBody @Valid @ApiParam(required = true, name = "baseSearchDto", value = "分类列表查询条件")
                                                         SearchGoodCategoryDto categoryDto) {
        Integer pageNo=categoryDto.getPageNo()==null?defaultPageNo:categoryDto.getPageNo();
        Integer pageSize=categoryDto.getPageSize()==null?defaultPageSize:categoryDto.getPageSize();
        return setJsonViewData(goodsCategoryService.searchList(categoryDto,pageNo,pageSize));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除商品分类")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData delete(@Validated @RequestBody GoodCategoryDeleteDto goodCategoryDeleteDto, BindingResult bindingResult){
        boolean isSuccess = goodsCategoryService.removeByIds(goodCategoryDeleteDto.getIds());
        return isSuccess?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "商品分类启用或禁用,子级跟着父级的状态改变")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData updateStatus(@RequestBody BaseUpdateStatusDto baseUpdateStatus){
        //找出所有下级id
        List<Long> allIds = goodsCategoryService.findChildIds(baseUpdateStatus.getId()[0], "pm_goods_category");
        List<PmGoodsCategoryPo> categoryPos= Lists.newArrayListWithExpectedSize(allIds.size());
        allIds.forEach(x->{
            PmGoodsCategoryPo categoryPo=new PmGoodsCategoryPo();
            categoryPo.setId(x);
            categoryPo.setEnabled(baseUpdateStatus.getEnabled());
            categoryPos.add(categoryPo);
        });

        boolean isSuccess = goodsCategoryService.updateBatchById(categoryPos);
        return isSuccess?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }

    @PostMapping("/view/{id}")
    @ApiOperation(value = "查看分类详情")
    public JsonViewData<Map<String,Object>> view(@PathVariable Long id){
        Map<String, Object> map = goodsCategoryService.findById(id);
        if (!map.get("attributionList").toString().equals("")){
            map.put("attributionList",JSONUtils.toList(map.get("attributionList")));
        }
        return new JsonViewData<Map<String,Object>>(map);
    }
    @PostMapping("/find_by_parentid")
    @ApiOperation(value = "选择商品分类")
    public JsonViewData findByParentId(Long parentId){
        PmGoodsCategoryPo condition=new PmGoodsCategoryPo();
        QueryWrapper<PmGoodsCategoryPo> queryWrapper=new QueryWrapper<>(condition,"id","name");
        if (parentId==null){
            condition.setLevel(1);
        }
        else {
            condition.setParentId(parentId);
        }
        return setJsonViewData(goodsCategoryService.listMaps(queryWrapper));
    }


    @PostMapping("/find_all_category")
    @ApiOperation(value = "联动查询所有分类")
    public JsonViewData findGoodsCategoryTreeVo(){
        List<GoodsCategoryTreeVo> goodsCategoryTreeVo = goodsCategoryService.findGoodsCategoryTreeVo();
        return setJsonViewData(MyBaseTree.build(goodsCategoryTreeVo));
    }

    @PostMapping("/find_attribute")
    @ApiOperation(value = "查找所有属性")
    public JsonViewData<CategoryRelAttributeVo> findAttribute(){
        //这里只能硬编码   查出分类下需要查找的各种属性
        List<AttributeIdNameTypeVo> attributeIdNameTypeVos = attributeService.findAttributeIdNameTypeVos(Lists.newArrayList(7, 4, 1, 6));
        Map<Integer,List<AttributeIdNameTypeVo>> map=attributeIdNameTypeVos.stream().collect(Collectors.groupingBy(AttributeIdNameTypeVo::getType));
        CategoryRelAttributeVo categoryRelAttributeVo=new CategoryRelAttributeVo();
        categoryRelAttributeVo.setAttributeList(map.get(4));
        categoryRelAttributeVo.setPurchaseList(map.get(6));
        categoryRelAttributeVo.setServiceList(map.get(1));
        categoryRelAttributeVo.setSpecificationList(map.get(7));
        return setJsonViewData(categoryRelAttributeVo);
    }








}
