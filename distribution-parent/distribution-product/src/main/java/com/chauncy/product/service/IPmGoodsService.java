package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.dto.manage.good.add.GoodBaseDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.Result;
import com.chauncy.data.vo.supplier.PmGoodsAttributeValueVo;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsService extends Service<PmGoodsPo> {

    /**
     * 添加商品基本信息
     * @param goodBaseDto
     * @return
     */
    JsonViewData addBase(@ModelAttribute GoodBaseDto goodBaseDto);

    /**
     * 根据ID查找商品信息
     *
     * @param id
     * @return
     */
    JsonViewData findBaseById(Long id);

    /**
     * 根据分类ID查找对应的规格值
     *
     * @param categoryId
     * @return
     */
    JsonViewData searchStandard(Long categoryId);
}
