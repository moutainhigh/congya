package com.chauncy.data.mapper.message.information.category;

import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface MmInformationCategoryMapper extends IBaseMapper<MmInformationCategoryPo> {


    /**
     * 条件查询
     *
     * @param baseSearchDto
     * @return
     */
    List<InformationCategoryVo> searchPaging(BaseSearchDto baseSearchDto);


    /**
     * 查询所有
     *
     * @return
     */
    List<InformationCategoryVo> selectAll();

    /**
     * 分页查找需要广告位置为资讯分类推荐需要关联的资讯分类
     *
     * @return
     * @param name
     */
    List<BaseVo> searchInformationCategory(String name);
}
