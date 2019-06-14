package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.dto.manage.good.add.GoodAttributeValueDto;
import com.chauncy.data.vo.JsonViewData;

/**
 * <p>
 * 存储产品参数信息的表
 * <p>
 * 规格值
 * 参数值 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsAttributeValueService extends Service<PmGoodsAttributeValuePo> {

    /**
     * 根据属性ID添加属性值
     *
     * @param goodAttributeValueDto
     * @return
     */
    JsonViewData saveAttValue(GoodAttributeValueDto goodAttributeValueDto);

    /**
     * 更新属性值
     *
     * @param goodAttributeValueDto
     * @return
     */
    JsonViewData editValue(GoodAttributeValueDto goodAttributeValueDto);

    /**
     * 批量删除属性值
     *
     * @param ids
     * @return
     */
    JsonViewData delAttValueByIds(Long[] ids);

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    JsonViewData findValueById(Long id);

}
