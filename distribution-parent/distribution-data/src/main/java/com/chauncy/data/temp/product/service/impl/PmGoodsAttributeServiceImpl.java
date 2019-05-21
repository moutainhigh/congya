package com.chauncy.data.temp.product.service.impl;

import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.temp.product.service.IPmGoodsAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
品牌管理(平台) 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsAttributeServiceImpl extends ServiceImpl<PmGoodsAttributeMapper, PmGoodsAttributePo> implements IPmGoodsAttributeService {

}
