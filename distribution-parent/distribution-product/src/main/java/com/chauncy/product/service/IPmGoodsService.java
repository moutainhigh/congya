package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.dto.supplier.good.add.AddAssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddGoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddExtraValueDto;
import com.chauncy.data.dto.supplier.good.add.AddSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.vo.JsonViewData;
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
     * @param addGoodBaseDto
     * @return
     */
    void addBase(@ModelAttribute AddGoodBaseDto addGoodBaseDto);

    /**
     * 根据分类ID查找对应的规格值
     *
     * @param categoryId
     * @return
     */
    List<PmGoodsAttributeValueVo> searchStandard(Long categoryId);

    /**
     *添加商品额外的属性值
     *
     * @param addExtraValueDto
     * @return
     */
    void addExtraValue(AddExtraValueDto addExtraValueDto);

    /**
     * 添加sku属性信息
     *
     * @param addSkuAttributeDtoList
     * @return
     */
    void addSkuAttribute(List<AddSkuAttributeDto> addSkuAttributeDtoList);

    /**
     *添加或更新财务信息
     *
     * @param updateSkuFinanceDto
     * @return
     */
    void updateSkuFinance(UpdateSkuFinanceDto updateSkuFinanceDto);

    /**
     * 添加或更新财务信息
     *
     * @param updateGoodOperationDto
     * @return
     */
    void updateGoodOperation(UpdateGoodOperationDto updateGoodOperationDto);

    /**
     * 销售角色添加或更新商品信息
     *
     * @param updateGoodSellerDto
     * @return
     */
    void updateGoodSeller(UpdateGoodSellerDto updateGoodSellerDto);

    /**
     * 添加商品关联
     *
     * @param associationDto
     * @return
     */
    void addAssociationGoods(AddAssociationGoodsDto associationDto);
}
