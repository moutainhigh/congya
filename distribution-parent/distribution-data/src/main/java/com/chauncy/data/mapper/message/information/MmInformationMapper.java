package com.chauncy.data.mapper.message.information;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.dto.app.component.ShareDto;
import com.chauncy.data.dto.app.message.information.select.FindInfoParamDto;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.component.ScreenInfoParamVo;
import com.chauncy.data.vo.app.message.information.InformationBaseVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface MmInformationMapper extends IBaseMapper<MmInformationPo> {

    /**
     * 后台查询资讯基本信息
     * @param id
     * @return
     */
    InformationVo findById(@Param("id") Long id);

    /**
     * app查询资讯基本信息
     * @param mmInformationId
     * @param userId
     * @return
     */
    InformationBaseVo findBaseById(@Param("mmInformationId") Long mmInformationId, @Param("userId") Long userId);

    /**
     * 后台分页条件查询
     * @param baseSearchByTimeDto
     * @return
     */
    List<InformationPageInfoVo> searchInfoPaging(BaseSearchByTimeDto baseSearchByTimeDto);

    /**
     * app分页条件查询
     * @param searchInfoByConditionDto
     * @return
     */
    List<InformationPagingVo> searchInfoBasePaging(SearchInfoByConditionDto searchInfoByConditionDto);

    /**
     * 查询资讯关联商品id
     * @param id
     * @return
     */
    List<Long> selectRelGoodsIdsById(Long id);

    /**
     * 店铺详情-首页-动态
     * @param storeId
     * @param userId
     * @return
     */
    List<InformationPagingVo> searchInformationList(@Param("storeId")Long storeId, @Param("userId") Long userId);
    /**
     * 不用updateById  update a=a+1
     *
     * @param favoritesId
     */
    @Update("update mm_information set collection_num = collection_num+1 where id = #{favoritesId}")
    void addFavorites(Long favoritesId);

    /**
     * 不用updateById  update a=a-1
     *
     * @param favoritesId
     */
    @Update("update mm_information set collection_num = collection_num-1 where id = #{favoritesId} and collection_num > 0")
    void delFavorites(Long favoritesId);

    /**
     * 分享商品
     *
     * @param shareDto
     * @return
     */
    void shareInformation(ShareDto shareDto);

    /**
     * @Author yeJH
     * @Date 2019/10/17 14:44
     * @Description 根据筛选资讯的条件获取资讯对应的资讯标签，内容分类等参数
     *
     * @Update yeJH
     *
     * @param  findInfoParamDto
     * @return com.chauncy.data.vo.app.component.ScreenInfoParamVo
     **/
    ScreenInfoParamVo findScreenInfoParam(FindInfoParamDto findInfoParamDto);

    /**
     * 获取关注的店铺更新的资讯数目
     * @param userId
     * @param readTime
     * @return
     */
    Integer getFocusInfoSum(@Param("userId") Long userId, @Param("readTime") LocalDateTime readTime);
}
