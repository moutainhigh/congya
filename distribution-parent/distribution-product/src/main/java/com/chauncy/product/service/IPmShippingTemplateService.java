package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmShippingTemplatePo;
import com.chauncy.data.dto.manage.ship.add.AddShipTemplateDto;
import com.chauncy.data.dto.manage.ship.delete.DelListDto;
import com.chauncy.data.dto.manage.ship.select.SearchPlatTempDto;
import com.chauncy.data.dto.manage.ship.update.EnableTemplateDto;
import com.chauncy.data.dto.manage.ship.update.VerifyTemplateDto;
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
     * @param addShipTemplateDto
     */
    void addShipTemplate(AddShipTemplateDto addShipTemplateDto);

    /**
     * 批量删除计算运费列表
     *
     * @param delListDto
     * @return
     */
    void delByIds(DelListDto delListDto);

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

    /**
     * 批量修改模版的审核状态
     *
     * @param verifyTemplateDto
     * @return
     */
    void verifyTemplate(VerifyTemplateDto verifyTemplateDto);

    /**
     * 批量启用或禁用模版
     *
     * @param enableTemplateDto
     * @return
     */
    void enableTemplate(EnableTemplateDto enableTemplateDto);
}
