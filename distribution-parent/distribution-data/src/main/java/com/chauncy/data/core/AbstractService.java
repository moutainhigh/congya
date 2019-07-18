package com.chauncy.data.core;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.common.FindGoodsBaseByConditionDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.common.goods.GoodsBaseVo;
import com.chauncy.data.vo.supplier.MemberLevelInfos;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 *  基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author huangwancheng
 * @since 2019-05-22
 */
public abstract class AbstractService<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements Service<T> {


    protected static int defaultPageSize = 10;

    protected static int defaultPageNo = 1;

    protected static String defaultSoft="sort desc";


    @Autowired
    private IBaseMapper<T> IBaseMapper;

    @Override
    public Map<String, Object> findByUserUame(String username) {
        return IBaseMapper.findByUserName(username);
    }

    @Override
    public List<Long> findChildIds(@Param("parentId") Long parentId, @Param("tableName") String tableName){
        return IBaseMapper.loadChildIds(parentId,tableName);
    }


    @Override
    public List<Long> findParentIds(@Param("id") Long id, @Param("tableName") String tableName){
        return IBaseMapper.loadParentIds(id,tableName);
    }



    /**
     *根据name和数据库名称查询对应的名字
     * @param names
     * @param tableName
     * @return
     */
    @Override
    public List<Long> findIdByNamesInAndTableName(List<String> names, String tableName, String concatWhereSql){
        return IBaseMapper.loadIdByNamesInAndTableName(names,tableName,concatWhereSql);
    }

    /**
     * 批量禁用启用
     *
     * @param baseUpdateStatusDto
     */
    public void editEnabledBatch(BaseUpdateStatusDto baseUpdateStatusDto) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", baseUpdateStatusDto.getId());
        updateWrapper.set("enabled", baseUpdateStatusDto.getEnabled());
        this.update(updateWrapper);
    }

    /**
     * 获取全部会员ID和名称
     * @return
     */
    @Override
    public List<MemberLevelInfos> findAllMemberLevel() {
        List<MemberLevelInfos> memberLevelInfos = IBaseMapper.memberLevelInfos ();
        memberLevelInfos.stream ().filter (a -> a.getLevel() == 1).forEach (a -> {
            a.setLevelName (a.getLevelName () + "/全部用户");
        });
        return memberLevelInfos;
    }

    /**
     * 条件获取商品的基础信息，作为给需要选择的功能的展示
     *
     * @param findGoodsBaseByConditionDto
     * @return
     */
    @Override
    public PageInfo<GoodsBaseVo> findGoodsBaseByCondition(FindGoodsBaseByConditionDto findGoodsBaseByConditionDto) {

        Integer pageNo = findGoodsBaseByConditionDto.getPageNo() == null ? defaultPageNo : findGoodsBaseByConditionDto.getPageNo();
        Integer pageSize = findGoodsBaseByConditionDto.getPageSize() == null ? defaultPageSize : findGoodsBaseByConditionDto.getPageSize();
        PageInfo<GoodsBaseVo> goodsBaseVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> IBaseMapper.findGoodsBaseByCondition(findGoodsBaseByConditionDto));
        goodsBaseVoPageInfo.getList().forEach(a->{
            PmGoodsCategoryPo goodsCategoryPo3 = IBaseMapper.findCategoryById(a.getCategoryId());
            String level3 = goodsCategoryPo3.getName();
            PmGoodsCategoryPo goodsCategoryPo2 = IBaseMapper.findCategoryById(goodsCategoryPo3.getParentId());
            String level2 = goodsCategoryPo2.getName();
            String level1 = IBaseMapper.findCategoryById(goodsCategoryPo2.getParentId()).getName();
            String categoryName = level1 + "/" + level2 + "/" + level3;
            a.setCategoryName (categoryName);
        });

        return goodsBaseVoPageInfo;
    }
}
