package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.select.AssociationGoodsDto;
import com.chauncy.data.dto.manage.good.update.RejectGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddAssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddGoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddOrUpdateSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.select.FindStandardDto;
import com.chauncy.data.dto.supplier.good.select.SearchExcelDto;
import com.chauncy.data.dto.supplier.good.select.SearchGoodInfosDto;
import com.chauncy.data.dto.supplier.good.select.SelectAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdatePublishStatusDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.supplier.*;
import com.chauncy.data.vo.supplier.good.AssociationGoodsVo;
import com.chauncy.data.vo.supplier.good.ExcelGoodVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

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
    Long addBase(AddGoodBaseDto addGoodBaseDto);

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
     * @param addOrUpdateSkuAttributeDto
     * @return
     */
    void addOrUpdateSkuAttribute(AddOrUpdateSkuAttributeDto addOrUpdateSkuAttributeDto);

    /**
     * 根据商品ID获取商品属性sku 信息、
     *
     * @param goodsId
     * @return
     */
    List<Map<String,Object>> findSkuAttribute(Long goodsId);

    /**
     * 根据商品ID查找财务的sku信息
     *
     * @param goodsId
     * @return
     */
    GetSkuFinanceInfoVo findSkuFinance(Long goodsId);

    /**
     * 添加或更新财务信息
     *
     * @param updateSkuFinanceDto
     * @return
     */
    void updateSkuFinance(List<UpdateSkuFinanceDto> updateSkuFinanceDto);

    /**
     * 根据商品ID查找运营信息
     *
     * @param goodsId
     * @return
     */
    FindGoodOperationVo findGoodOperation(Long goodsId);

    /**
     * 添加或更新运营信息
     *
     * 平台审核并更新运营信息
     *
     * @param updateGoodOperationDto
     * @return
     */
    void updateGoodOperation(UpdateGoodOperationDto updateGoodOperationDto);

    /**
     * 根据商品ID查找销售信息
     *
     * @param goodsId
     * @return
     */
    FindGoodSellerVo findGoodSeller(Long goodsId);

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

    /**
     * 平台驳回商品审核，审核不通过
     *
     * @param rejectGoodsDto
     */
    void rejectGoods(RejectGoodsDto rejectGoodsDto);

    /**
     * 增加商品库存
     * @param goodId 商品id
     * @param number 增量
     */
    boolean  updateStock(long goodId,int number);

    /**
     * 提交商品审核
     *
     * @param goodsIds
     */
    void submitAudit(Long[] goodsIds);

    /**
     * 上架或下架商品
     *
     * @param updatePublishStatusDt
     */
    void publishStatus(UpdatePublishStatusDto updatePublishStatusDt);

    /**
     * 修改应用标签
     *
     * @param updatePublishStatusDto
     */
    void updateStarStatus(UpdatePublishStatusDto updatePublishStatusDto);

    /**
     * 条件查询商品信息
     *
     * @param searchGoodInfosDto
     */
    PageInfo<PmGoodsVo> searchGoodsInfo(SearchGoodInfosDto searchGoodInfosDto);

    /**
     * 统计商品记录
     *
     * @return
     */
    GoodStatisticsVo statisticsGood();

    /**
     * 获取分类下的商品属性信息 typeList:商品类型；brandList:品牌；labelList:标签；platformServiceList:平台服务说明;
     * merchantServiceList:商家服务说明；paramList:商品参数；platformShipList:平台运费模版;merchantShipList:店铺运费模版
     *
     * @param categoryId
     * @return
     */
    AttributeVo findAttributes(Long categoryId);

    /**
     * 查询导入商品信息
     * @param searchExcelDto
     * @return
     */
    PageInfo<ExcelGoodVo> searchExcelGoods(SearchExcelDto searchExcelDto);

    /**
     * 批量删除商品
     *
     * @param ids
     * @return
     */
    void delGoodsByIds(Long[] ids);

    /**
     * 条件查询需要被关联商品信息
     *
     * @param associationGoodsDto
     * @return
     */
    PageInfo<BaseVo> searchAssociationGoods (AssociationGoodsDto associationGoodsDto);

    /**
     * 查询已被关联的商品信息
     * @param associationGoodsDto
     * @return
     */
    PageInfo<AssociationGoodsVo> searchAssociatedGoods (AssociationGoodsDto associationGoodsDto);

    /**
     * 批量删除关联商品
     *
     * @param ids
     * @return
     */
    void delAssociationsByIds (Long[] ids);
}
