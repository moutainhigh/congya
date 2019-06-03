package com.chauncy.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.vo.JsonViewData;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * <p>
 * 商品属性参数表
 * <p>
 * 类目下:
 * 规格管理(平台)
 * 商品参数管理(平台)
 * 服务说明管理(平台、商家)
 * 购买须知管理(平台)
 * 活动说明管理(平台)
 * <p>
 * 商品下：
 * 类目
 * 标签管理(平台)
 * 规格管理(平台)
 * 品牌管理
 * 参数值
 * <p>
 * 服务说明管理(平台、商家)
 * 活动说明管理(平台)
 * 标签管理(平台）
 * 购买须知管理(平台)
 * 规格管理(平台)
 * 商品参数管理(平台)
 * 品牌管理(平台) 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsAttributeService extends IService<PmGoodsAttributePo> {


    /**
     * 保存商品属性
     * @param goodsAttributePo
     * @return
     */
    JsonViewData saveAttribute (PmGoodsAttributePo goodsAttributePo);

    /**
     * 批量删除属性以及关联的值
     * @param ids
     * @return
     */
    JsonViewData deleteAttributeByIds(Long[] ids);

    /**
     * 更新属性基本数据(不包括属性值)
     *
     * @param goodsAttributePo
     * @return
     */
    JsonViewData edit(PmGoodsAttributePo goodsAttributePo);

    /**
     * 根据ID查找属性以及关联的属性值
     * @param id
     * @return
     */
    JsonViewData findById(Long id);

    /**
     * 条件查询
     *
     * @param type
     * @param name
     * @param enable
     * @return
     */
    JsonViewData search(Integer type,String name,boolean enable);
}
