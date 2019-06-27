package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.BaseTree;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.select.SearchAttributeByNamePageDto;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.data.vo.manage.product.SearchAttributeVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsCategoryServiceImpl extends AbstractService<PmGoodsCategoryMapper, PmGoodsCategoryPo> implements IPmGoodsCategoryService {

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Override
    public Map<String, Object> findById(Long id) {
        return categoryMapper.loadById(id);
    }

    @Override
    public List<Long> findAttributeIdsByNamesAndCategoryId(List<String> names, Integer type, Long cId) {
        return categoryMapper.getAttributeIdsByNamesAndCategoryId(names, type, cId);
    }

    @Override
    public Long findAttributeIdsByNameAndCategoryId(String name, Integer type, Long cId) {
        return categoryMapper.getAttributeIdsByNameAndCategoryId(name,type,cId);
    }

    @Override
    public List<SearchAttributeVo> findAttributeVo(SearchAttributeByNamePageDto searchAttributeByNamePageDto) {
        return categoryMapper.loadAttributeVo(searchAttributeByNamePageDto);
    }

    @Override
    public Map<String, Object> searchList(BaseSearchDto baseSearchDto, Integer pageNo, Integer pageSize) {
        Map<String,Object> map= Maps.newHashMap();
        Integer totalCount = categoryMapper.loadCount(baseSearchDto);
        map.put("totalCount",totalCount);
        if (totalCount==0){
            map.put("categoryList", Lists.newArrayList());
        }
        else {
            List<SearchCategoryVo> searchCategoryVos = categoryMapper.loadSearchCategoryVoList(baseSearchDto, pageSize, (pageNo-1)*pageSize);
            List<SearchCategoryVo> categoryList = BaseTree.build(searchCategoryVos);
            map.put("categoryList",categoryList);
        }
        return map;
    }

    @Override
    public List<GoodsCategoryTreeVo> findGoodsCategoryTreeVo() {
        return categoryMapper.loadGoodsCategoryTreeVo();
    }
}
