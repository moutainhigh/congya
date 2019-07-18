package com.chauncy.data.mapper.product;

import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.select.AssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.select.SearchExcelDto;
import com.chauncy.data.dto.supplier.good.select.SearchGoodInfosDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.supplier.good.InformationRelGoodsVo;
import com.chauncy.data.vo.supplier.PmGoodsVo;
import com.chauncy.data.vo.supplier.good.ExcelGoodVo;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateGoodsInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品信息 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsMapper extends IBaseMapper<PmGoodsPo> {

    /**
     * 查找该商品对应的属性值
     *
     * @param goodsId
     * @return
     */
    List<GoodsValueBo> findGoodsValue(@Param("goodsId") Long goodsId, @Param("attributeId") Long attributeId);

    /**
     * 条件查询商品信息
     *
     * @param searchGoodInfosDto
     * @return
     */
    List<PmGoodsVo> searchGoodsInfo(SearchGoodInfosDto searchGoodInfosDto);

    /**
     *
     * 获取店铺下商品列表
     * 店铺id
     * 一级分类id
     * 商品列表： 1.店铺全部商品； 2.店铺推荐商品； 3.店铺新品列表； 4.店铺活动商品； 5.明星单品列表（按时间降序）； 6.最新推荐（按排序数值降序）
     * 排序内容;  1.综合排序  2.销量排序  3.价格排序
     * 排序方式   1.降序   2.升序
     *
     * @return
     */
    List<GoodsBaseInfoVo> searchInfoBasePaging(SearchStoreGoodsDto searchStoreGoodsDto);

    /**
     * 根据资讯id获取关联的商品
     * @param baseSearchDto
     * @return
     */
    List<GoodsBaseInfoVo> searchGoodsByInfoId(BaseSearchDto baseSearchDto);

    /**
     * 查询导入商品信息
     * @param searchExcelDto
     * @return
     */
    List<ExcelGoodVo> searchExcelGoods(SearchExcelDto searchExcelDto);

    /**
     * 根据资讯id获取关联商品信息
     *
     * @param id
     * @return
     */
    List<InformationRelGoodsVo> searchRelGoodsByInfoId(@Param("id") long id);
    /**
     * 库存模板获取分配店铺商品信息
     *
     * @param id  店铺id
     * @return
     */
    List<BaseBo> selectDistributionGoods(@Param("id") long id);
    /**
     * 库存模板获取自有店铺商品信息
     *
     * @param id  店铺id
     * @return
     */
    List<BaseBo> selectOwnGoods(@Param("id") long id);
    /**
     * 库存模板id获取询商品信息
     *
     * @param id
     */
    List<StockTemplateGoodsInfoVo> searchGoodsInfoByTemplateId(@Param("id") long id);

    int updateStock(@Param("goodId") long goodId,@Param("number") int number);

    /**
     * 获取商品的id和name
     *
     * @param associationGoodsDto
     */
    List<BaseVo> selectIds (AssociationGoodsDto associationGoodsDto);
}
