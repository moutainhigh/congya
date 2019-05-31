package com.chauncy.product.service;

import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 通过属性ID attributeID删除
     */

    void deleteByAttributeId(Long ProductAttributeId);

}
