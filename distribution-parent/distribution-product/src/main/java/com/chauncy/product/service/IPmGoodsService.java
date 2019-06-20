package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.dto.supplier.good.add.AddAssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddGoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.select.FindStandardDto;
import com.chauncy.data.dto.supplier.good.select.SelectAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.BaseGoodsVo;
import com.chauncy.data.vo.supplier.FindSkuAttributeVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;

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
     * 获取商品类型
     *
     * @return
     */
    List<String> findGoodsType();

    /**
     * 获取该商品所在类目下的不同类型的商品属性信息
     *
     * @param selectAttributeDto
     * @return
     */
    List<BaseVo> findAttByTypeAndCat(SelectAttributeDto selectAttributeDto);

    /**
     * 根据不同运费模版类型获取运费信息
     *
     * @param shipType
     * @return
     */
    List<BaseVo> findShipByType(Integer shipType);

    /**
     * 添加商品基本信息
     *
     * @param addGoodBaseDto
     * @return
     */
    void addBase(AddGoodBaseDto addGoodBaseDto);

    /**
     * 根据ID获取商品的基本信息
     *
     * @param id
     * @return
     */
    BaseGoodsVo findBase(Long id);

    /**
     * 修改商品基本信息
     *
     * @param updateGoodBaseDto
     */
    void updateBase(AddGoodBaseDto updateGoodBaseDto);

    /**
     * 根据分类ID查找对应的规格值
     *
     * @param findStandardDto
     * @return
     */
    List<GoodsStandardVo> findStandard(FindStandardDto findStandardDto);

//    /**
//     * 添加商品额外的属性值
//     *
//     * @param addExtraValueDto
//     * @return
//     */
//    void addExtraValue(AddExtraValueDto addExtraValueDto);

    /**
     * 添加sku属性信息
     *
     * @param addSkuAttributeDtoList
     * @return
     */
    void addSkuAttribute(List<AddSkuAttributeDto> addSkuAttributeDtoList);

    /**
     * 根据商品ID获取sku 信息、
     *
     * @param goodsId
     * @return
     */
    List<FindSkuAttributeVo> findSkuAttribute(Long goodsId);

    /**
     * 添加或更新财务信息
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
