package com.chauncy.data.mapper.activity.reduced;

import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.dto.app.product.SearchReducedGoodsDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.goods.ActivityGoodsVo;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;

import java.util.List;

/**
 * <p>
 * 满减活动管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface AmReducedMapper extends IBaseMapper<AmReducedPo> {


    /**
     * @Author yeJH
     * @Date 2019/9/28 18:21
     * @Description 满减活动去凑单商品列表对应的商品一级分类
     *
     * @Update yeJH
     *
     * @param  activityId
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    List<BaseVo> findReducedGoodsCategory(Long activityId);

    /**
     * @Author yeJH
     * @Date 2019/9/28 18:21
     * @Description 商品详情页，购物车页面点击去凑单获取满减商品列表
     *
     * @Update yeJH
     *
     * @param  searchReducedGoodsDto
     * @return java.util.List<com.chauncy.data.vo.app.goods.ActivityGoodsVo>
     **/
    List<ActivityGoodsVo> searchReducedGoods(SearchReducedGoodsDto searchReducedGoodsDto);

    /**
     * 条件查询满减活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    List<SearchActivityListVo> searchReduceList(SearchActivityListDto searchActivityListDto);

    /**
     * 查看满减详情
     *
     * @param id
     * @return
     */
    SearchActivityListVo findReducedById(Long id);
}
