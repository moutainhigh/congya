package com.chauncy.data.mapper.message.information;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.dto.manage.message.information.select.InformationSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.message.information.InformationBaseVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
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
     * 后台查询资讯基本信息
     * @param id
     * @return
     */
    InformationVo findById(@Param("id") Long id);

    /**
     * app查询资讯基本信息
     * @param id
     * @return
     */
    InformationBaseVo findBaseById(@Param("id") Long id);

    /**
     * 后台分页条件查询
     * @param informationSearchDto
     * @return
     */
    List<InformationPageInfoVo> searchInfoPaging(InformationSearchDto informationSearchDto);

    /**
     * app分页条件查询
     * @param searchInfoByConditionDto
     * @return
     */
    List<InformationPagingVo> searchInfoBasePaging(SearchInfoByConditionDto searchInfoByConditionDto);

}
