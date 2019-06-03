package com.chauncy.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
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
public interface IPmGoodsAttributeValueService extends IService<PmGoodsAttributeValuePo> {

    /**
     * 根据属性ID添加属性值
     *
     * @param goodsAttributeValuePo
     * @return
     */
    JsonViewData saveAttValue(PmGoodsAttributeValuePo goodsAttributeValuePo);

    /**
     * 更新属性值
     *
     * @param pmGoodsAttributeValuePo
     * @return
     */
    JsonViewData editValue(PmGoodsAttributeValuePo pmGoodsAttributeValuePo);

    /**
     * 批量删除属性值
     *
     * @param ids
     * @return
     */
    JsonViewData delAttValueByIds(Long[] ids);

    JsonViewData findValueById(Long id);

}
