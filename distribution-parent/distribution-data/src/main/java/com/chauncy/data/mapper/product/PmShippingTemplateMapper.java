package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmShippingTemplatePo;
import com.chauncy.data.dto.manage.ship.select.SearchPlatTempDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.ship.PlatTemplateVo;

import java.util.List;

/**
 * <p>
 * 运费模版说明表。平台运费模版+商家运费模版 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmShippingTemplateMapper extends IBaseMapper<PmShippingTemplatePo> {

    /**
     * 条件查询平台运费模版字段
     *
     * @param searchPlatTempDto
     * @return
     */
    List<PlatTemplateVo> searchPlatTempByConditions(SearchPlatTempDto searchPlatTempDto);

    /**
     * 根据类型查找运费模版
     * @param type
     * @return
     */
    List<BaseVo> findByType(Integer type);
}
