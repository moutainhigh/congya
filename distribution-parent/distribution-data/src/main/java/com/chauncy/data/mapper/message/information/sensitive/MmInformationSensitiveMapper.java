package com.chauncy.data.mapper.message.information.sensitive;

import com.chauncy.data.domain.po.message.information.sensitive.MmInformationSensitivePo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.information.sensitive.InformationSensitiveVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface MmInformationSensitiveMapper extends IBaseMapper<MmInformationSensitivePo> {

    /**
     * 条件查询
     *
     * @param baseSearchDto
     * @return
     */
    List<InformationSensitiveVo> searchPaging(BaseSearchDto baseSearchDto);

}
