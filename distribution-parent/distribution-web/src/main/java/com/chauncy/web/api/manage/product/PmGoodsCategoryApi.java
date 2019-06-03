package com.chauncy.web.api.manage.product;


import com.chauncy.common.enums.ResultCode;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsSkuCategoryAttributeRelationPo;
import com.chauncy.data.dto.GoodCategoryDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.chauncy.product.service.IPmGoodsSkuCategoryAttributeRelationService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品分类页面控制器
 *
 * @author zhangrt
 * @since 2019-05-21
 */
@Api(description = "商品分类管理接口")
@RestController
@RequestMapping("/manage/product")
@Slf4j
public class PmGoodsCategoryApi extends BaseApi {

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

    @Autowired
    private IPmGoodsSkuCategoryAttributeRelationService categoryAttributeRelationService;

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
        BeanUtils.copyProperties(goodCategoryDto,pmGoodsCategoryPo);
        goodsCategoryService.save(pmGoodsCategoryPo);

        //保存分类与属性的关联
        List<PmGoodsSkuCategoryAttributeRelationPo> categoryAttributeRelationPos= Lists.newArrayList();
        if (!ListUtil.isListNullAndEmpty(goodCategoryDto.getGoodAttributeIds())){
            goodCategoryDto.getGoodAttributeIds().forEach(x->{
                PmGoodsSkuCategoryAttributeRelationPo categoryAttributeRelationPo=new PmGoodsSkuCategoryAttributeRelationPo();
                categoryAttributeRelationPo.setCreateBy(getUser().getUsername()).setGoodsAttributeId(x).
                        setGoodsCategoryId(pmGoodsCategoryPo.getId());
                categoryAttributeRelationPos.add(categoryAttributeRelationPo);

            });
        }
        categoryAttributeRelationService.saveBatch(categoryAttributeRelationPos);
        return setJsonViewData(ResultCode.SUCCESS);
    }


    /**
     * 编辑分类
     *
     */
    @PostMapping("/update")
    @ApiOperation(value = "编辑商品分类")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData update(@RequestBody @Valid @ApiParam(required = true, name = "goodCategoryDto", value = "分类相关信息")
                                              GoodCategoryDto goodCategoryDto,
                                      BindingResult result) {
        //先保存分类
        PmGoodsCategoryPo pmGoodsCategoryPo=new PmGoodsCategoryPo();
        pmGoodsCategoryPo.setCreateBy(getUser().getUsername());
        BeanUtils.copyProperties(goodCategoryDto,pmGoodsCategoryPo);
        goodsCategoryService.save(pmGoodsCategoryPo);

        //保存分类与属性的关联
        List<PmGoodsSkuCategoryAttributeRelationPo> categoryAttributeRelationPos= Lists.newArrayList();
        if (!ListUtil.isListNullAndEmpty(goodCategoryDto.getGoodAttributeIds())){
            goodCategoryDto.getGoodAttributeIds().forEach(x->{
                PmGoodsSkuCategoryAttributeRelationPo categoryAttributeRelationPo=new PmGoodsSkuCategoryAttributeRelationPo();
                categoryAttributeRelationPo.setCreateBy(getUser().getUsername()).setGoodsAttributeId(x).
                        setGoodsCategoryId(pmGoodsCategoryPo.getId());
                categoryAttributeRelationPos.add(categoryAttributeRelationPo);

            });
        }
        categoryAttributeRelationService.saveBatch(categoryAttributeRelationPos);
        return setJsonViewData(ResultCode.SUCCESS);
    }


}
