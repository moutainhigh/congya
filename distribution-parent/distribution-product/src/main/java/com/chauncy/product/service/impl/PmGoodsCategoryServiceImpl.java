package com.chauncy.product.service.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.TreeUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.BaseTree;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.dto.app.advice.category.select.GoodsCategoryVo;
import com.chauncy.data.dto.app.product.FindGoodsCategoryDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.select.SearchAttributeByNamePageDto;
import com.chauncy.data.dto.manage.good.select.SearchThirdCategoryDto;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.data.vo.manage.product.SearchAttributeVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.data.vo.manage.product.SearchThirdCategoryVo;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * @Author yeJH
     * @Date 2019/10/17 12:35
     * @Description  获取商品分类  没有参数返回一级分类  有一级分类id返回二三级分类
     *
     * @Update yeJH
     *
     * @param  findGoodsCategoryDto
     * @return java.util.List<com.chauncy.data.dto.app.advice.category.select.GoodsCategoryVo>
     **/
    @Override
    public List<GoodsCategoryVo> findAllCategory(FindGoodsCategoryDto findGoodsCategoryDto) {

        List<GoodsCategoryVo> goodsCategoryVoList = new ArrayList<>();
        if(null == findGoodsCategoryDto.getCategoryId()) {
            //查询商品一级分类
            goodsCategoryVoList = categoryMapper.findFirstCategory();
        } else {
            //根据商品一级分类查找二三级分类
            goodsCategoryVoList = categoryMapper.findChildrenCategory(findGoodsCategoryDto.getCategoryId());
        }

        return goodsCategoryVoList;


    }

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

    /**
     * 联动查询葱鸭百货广告位关联的商品分类
     *
     * @return
     */
    @Override
    public List<GoodsCategoryVo> findGoodsCategory(Long adviceId) {

        List<GoodsCategoryVo> goodsCategoryVos = categoryMapper.findGoodsCategoryTree(adviceId);
        List<GoodsCategoryVo> goodsCategoryVoList = Lists.newArrayList();

        //将goodsCategoryVos转为树结构
        try {
            goodsCategoryVoList = TreeUtil.getTree(goodsCategoryVos,"categoryId","parentId","children");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        }
        return goodsCategoryVoList;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-07 15:46
     * @Description //联动查询所有二级分类
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo>
     **/
    @Override
    public List<GoodsCategoryTreeVo> FindAllSecondCategory() {
        return categoryMapper.FindAllSecondCategory();
    }

    /**
     * @Author chauncy
     * @Date 2019-10-07 16:39
     * @Description //条件分页查询所有第三级分类信息
     *
     * @Update chauncy
     *
     * @param  searchThirdCategoryDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.product.SearchThirdCategoryVo>
     **/
    @Override
    public PageInfo<SearchThirdCategoryVo> searchThirdCategory(SearchThirdCategoryDto searchThirdCategoryDto) {

        Integer pageNo=searchThirdCategoryDto.getPageNo()==null?defaultPageNo:searchThirdCategoryDto.getPageNo();
        Integer pageSize=searchThirdCategoryDto.getPageSize()==null?defaultPageSize:searchThirdCategoryDto.getPageSize();

        PageInfo<SearchThirdCategoryVo> pageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->categoryMapper.searchThirdCategory(searchThirdCategoryDto));

        pageInfo.getList().stream().forEach(a->{
            PmGoodsCategoryPo goodsCategoryPo = categoryMapper.selectById(a.getId());
            if (goodsCategoryPo == null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在该分类ID:[%s]",a.getId()));
            }
            String level3 = goodsCategoryPo.getName();
            PmGoodsCategoryPo goodsCategoryPo2 = categoryMapper.selectById(goodsCategoryPo.getParentId());
            String level2 = goodsCategoryPo2.getName();
            String level1 = categoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();

            String categoryName = level1 + "/" + level2 + "/" + level3;
            a.setCategoryName(categoryName);
        });

        return pageInfo;
    }
}
