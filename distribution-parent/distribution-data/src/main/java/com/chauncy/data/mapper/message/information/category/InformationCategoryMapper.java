package com.chauncy.data.mapper.message.information.category;

import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
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
public interface InformationCategoryMapper extends IBaseMapper<MmInformationCategoryPo> {


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
}
