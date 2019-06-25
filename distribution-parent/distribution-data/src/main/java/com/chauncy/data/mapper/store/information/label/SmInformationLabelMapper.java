package com.chauncy.data.mapper.store.information.label;

import com.chauncy.data.domain.po.store.information.label.SmInformationLabelPo;
import com.chauncy.data.dto.manage.store.select.InformationLabelSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.store.information.label.InformationLabelVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface SmInformationLabelMapper extends IBaseMapper<SmInformationLabelPo> {

    /**
     * 条件查询
     *
     * @param informationLabelSearchDto
     * @return
     */
    List<InformationLabelVo> searchPaging(InformationLabelSearchDto informationLabelSearchDto);


    /**
     * 查询所有
     *
     * @return
     */
    List<InformationLabelVo> selectAll();


}
