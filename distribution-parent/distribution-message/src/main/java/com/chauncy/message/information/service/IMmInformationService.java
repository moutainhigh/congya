package com.chauncy.message.information.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.message.information.select.FindInfoParamDto;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;
import com.chauncy.data.vo.app.component.ScreenParamVo;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.message.information.InformationBaseVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface IMmInformationService extends Service<MmInformationPo> {

    /**
     * 保存资讯
     * @param informationDto
     */
    void saveInformation(InformationDto informationDto);
    /**
     * 编辑资讯
     * @param informationDto
     */
    void editInformation(InformationDto informationDto);

    /**
     * 批量删除资讯
     * @param ids
     */
    void delInformationByIds(Long[] ids);

    /**
     * 后台根据ID查找资讯
     * @param id 资讯id
     * @return
     */
    InformationVo findById(Long id);

    /**
     * app根据ID查找资讯
     * @param id 资讯id
     * @return
     */
    InformationBaseVo findBaseById(Long id);

    /**
     * 根据关联ID删除资讯跟店铺的绑定关系
     * @param id 资讯商品关联id
     * @return
     */
    void delRelById(Long id);

    /**
     * 后台分页条件查询
     * @param baseSearchByTimeDto
     * @return
     */
    PageInfo<InformationPageInfoVo> searchPaging(BaseSearchByTimeDto baseSearchByTimeDto);
    /**
     * app分页条件查询
     * @param searchInfoByConditionDto
     * @return
     */
    PageInfo<InformationPagingVo> searchPaging(SearchInfoByConditionDto searchInfoByConditionDto);
    /**
     * 用户点赞资讯
     * @param infoId  资讯id
     * @param userId  用户id
     * @return
     */
    void likeInfo(Long infoId, Long userId);
    /**
     * 审核资讯
     * @param batchAuditDto
     */
    void verifyInfo(BatchAuditDto batchAuditDto);

    /**
     * 根据资讯id获取关联的商品
     *
     * @param baseSearchDto
     * @return
     */
    //PageInfo<GoodsBaseInfoVo> searchGoodsById(BaseSearchDto baseSearchDto);

    /**
     * 批量修改资讯状态
     * @param baseUpdateStatusDto
     */
    void editInformationStatus(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 用户转发资讯成功
     * @param infoId
     * @param userId
     */
    void forwardInfo(Long infoId, Long userId);

    /**
     * 店铺详情-首页-动态
     * @param storeId
     * @return
     */
    PageInfo<InformationPagingVo> searchInformationList(Long storeId, BaseSearchPagingDto baseSearchPagingDto);


    /**
     * @Author yeJH
     * @Date 2019/10/17 14:42
     * @Description 根据筛选资讯的条件获取资讯对应的资讯标签，内容分类等参数
     *
     * @Update yeJH
     *
     * @param  findInfoParamDto
     * @return com.chauncy.data.vo.app.component.ScreenParamVo
     **/
    ScreenParamVo findScreenInfoParam(FindInfoParamDto findInfoParamDto);

    /**
     * 获取关注的店铺更新的资讯数目
     * @return
     */
    Integer getFocusInfoSum();
}
