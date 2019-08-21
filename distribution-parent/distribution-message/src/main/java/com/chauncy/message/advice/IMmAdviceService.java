package com.chauncy.message.advice;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveClassificationAdviceDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAssociatedClassificationDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchInformationCategoryDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.advice.ClassificationVo;
import com.chauncy.data.vo.manage.message.advice.FindBaiHuoAdviceVo;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 广告基本信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface IMmAdviceService extends Service<MmAdvicePo> {

    /**
     * 获取广告位置
     * @return
     */
    Object findAdviceLocation();

    /**
     * 条件分页获取广告信息及其对应的详情
     *
     * @param searchAdvicesDto
     * @return
     */
    PageInfo<SearchAdvicesVo> searchAdvices(SearchAdvicesDto searchAdvicesDto);

    /**
     * 批量删除广告
     *
     * @param idList
     */
    void deleteAdvices(List<Long> idList);

    /**
     * 保存充值入口/拼团鸭广告
     *
     * @param saveOtherAdviceDto
     * @return
     */
    void saveOtherAdvice(SaveOtherAdviceDto saveOtherAdviceDto);

    /**
     * 添加推荐的分类:葱鸭百货分类推荐/资讯分类推荐
     *
     * @param saveClassificationAdviceDto
     * @return
     */
    void saveGoodsCategoryAdvice(SaveClassificationAdviceDto saveClassificationAdviceDto);

    /**
     * 条件分页查询获取广告位置为葱鸭百货分类推荐/资讯分类推荐已经关联的分类信息
     *
     * @param searchAssociatedClassificationDto
     * @return
     */
    PageInfo<ClassificationVo> searchAssociatedClassification(SearchAssociatedClassificationDto searchAssociatedClassificationDto);

    /**
     * 分页查找需要广告位置资讯分类推荐需要关联的资讯分类
     *
     * @return
     * @param searchInformationCategoryDto
     */
    PageInfo<BaseVo> searchInformationCategory(SearchInformationCategoryDto searchInformationCategoryDto);

    /**
     * 批量启用或禁用,同一个广告位只能有一个是启用状态
     *
     * @param baseUpdateStatusDto
     * @return
     */
    void editEnabled(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 查找广告位为葱鸭百货的所有广告
     *
     * @return
     */
//    List<FindBaiHuoAdviceVo> findAdvice();
}
