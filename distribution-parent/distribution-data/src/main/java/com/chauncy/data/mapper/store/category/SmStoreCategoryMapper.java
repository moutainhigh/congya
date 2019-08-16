package com.chauncy.data.mapper.store.category;

import com.chauncy.data.domain.po.store.category.SmStoreCategoryPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.select.StoreCategorySearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.store.category.SmStoreCategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-16
 */
public interface SmStoreCategoryMapper extends IBaseMapper<SmStoreCategoryPo> {

    /**
     * 修改店铺经营状态
     * @param baseUpdateStatusDto
     * ids 店铺ID
     * enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    int editCategoryStatus(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 条件查询
     *
     * @param storeCategorySearchDto
     * @return
     */
    List<SmStoreCategoryVo> searchPaging(StoreCategorySearchDto storeCategorySearchDto);


    /**
     * 查询所有
     *
     * @return
     */
    List<SmStoreCategoryVo> selectAll();

    /**
     * 获取除已被广告关联的店铺分类ID和name
     *
     * @param name
     * @param associatedIds
     * @return
     */
    List<BaseVo> selectIds(@Param("name") String name, @Param("associatedIds") List<Long> associatedIds);
}
