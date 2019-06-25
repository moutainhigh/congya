package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmShippingTemplatePo;
import com.chauncy.data.dto.manage.ship.add.AddAmountTemplateDto;
import com.chauncy.data.dto.manage.ship.select.SearchPlatTempDto;
import com.chauncy.data.vo.manage.ship.PlatTemplateVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 运费模版说明表。平台运费模版+商家运费模版 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmShippingTemplateService extends Service<PmShippingTemplatePo> {

    /**
     * 添加按金额计算运费模版
     *
     * @param addAmountTemplateDto
     */
    void addShipTemplate(AddAmountTemplateDto addAmountTemplateDto);

    /**
     * 批量删除按金额计算运费列表
     *
     * @param amountIds
     * @return
     */
    void delAmountByIds(Long[] amountIds);

    /**
     * 批量删除运费模版
     *
     * @param templateIds
     * @return
     */
    void delTemplateByIds(Long[] templateIds);

    /**
     * 条件查询平台运费模版字段
     *
     * @param searchPlatTempDto
     * @return
     */
    PageInfo<PlatTemplateVo> searchPlatTempByConditions(SearchPlatTempDto searchPlatTempDto);
}
