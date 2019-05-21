package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商品属性参数表

类目下:
规格管理(平台)
商品参数管理(平台)
服务说明管理(平台、商家)
购买须知管理(平台)
活动说明管理(平台)

商品下：
类目
标签管理(平台)
规格管理(平台)
品牌管理
参数值

服务说明管理(平台、商家)
活动说明管理(平台)
标签管理(平台）
购买须知管理(平台)
规格管理(平台)
商品参数管理(平台)
品牌管理(平台) Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsAttributeMapper extends BaseMapper<PmGoodsAttributePo> {

}
