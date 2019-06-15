package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.product.PmGoodsAttributeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
 * 品牌管理(平台) IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsAttributeMapper extends IBaseMapper<PmGoodsAttributePo> {

    /**
     * 根据不同类型对应的名称查找
     *
     * @param type
     * @param name
     * @return
     */
    PmGoodsAttributePo findByTypeAndName(@Param("type") Integer type, @Param("name") String name);

    /**
     * 按条件查询
     *
     * @param type
     * @param name
     * @param enabled
     * @return
     */
    List<PmGoodsAttributeVo> findByCondition(@Param("type") Integer type, @Param("name") String name, @Param("enabled") Boolean enabled);

    /**
     * 根据分类id下的的商品找出哪些属性被商品引用了
     * @param categoryId
     * @return
     */
    List<PmGoodsAttributePo> loadByCategoryId(@Param("categoryId") Long categoryId);

}
