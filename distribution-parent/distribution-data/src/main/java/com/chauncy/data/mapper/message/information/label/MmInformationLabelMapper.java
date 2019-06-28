package com.chauncy.data.mapper.message.information.label;

import com.chauncy.data.domain.po.message.information.label.MmInformationLabelPo;
import com.chauncy.data.dto.manage.message.information.select.InformationLabelSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.information.label.InformationLabelVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface MmInformationLabelMapper extends IBaseMapper<MmInformationLabelPo> {

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
