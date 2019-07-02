package com.chauncy.data.mapper.message.information;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.dto.manage.message.information.select.InformationSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface MmInformationMapper extends IBaseMapper<MmInformationPo> {

    /**
     * 查询资讯基本信息
     * @param id
     * @return
     */
    InformationVo findById(@Param("id") Long id);

    /**
     * 分页条件查询
     * @param informationSearchDto
     * @return
     */
    List<InformationPageInfoVo> searchPaging(InformationSearchDto informationSearchDto);

}
