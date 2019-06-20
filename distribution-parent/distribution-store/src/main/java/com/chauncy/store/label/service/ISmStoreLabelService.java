package com.chauncy.store.label.service;

import com.chauncy.data.domain.po.store.label.SmStoreLabelPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.store.add.StoreLabelDto;
import com.chauncy.data.dto.manage.store.select.StoreLabelSearchDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.label.SmStoreLabelVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-15
 */
public interface ISmStoreLabelService extends Service<SmStoreLabelPo> {


    /**
     * 保存店铺标签信息
     * @param storeLabelDto
     * @return
     */
    SmStoreLabelPo saveStoreLabel(StoreLabelDto storeLabelDto);

    /**
     * 编辑店铺标签信息
     * @param storeLabelDto
     * @return
     */
    SmStoreLabelPo editStoreLabel(StoreLabelDto storeLabelDto);
    /**
     * 根据ID查找店铺标签
     *
     * @param id
     * @return
     */
    Map<String, Object> findById(Long id);

    /**
     * 条件查询
     * @param storeLabelSearchDto
     * @return
     */
    PageInfo<SmStoreLabelVo> searchPaging(StoreLabelSearchDto storeLabelSearchDto);

    /**
     * 查询店铺所有标签
     * @return
     */
    List<SmStoreLabelVo> selectAll();


    /**
     * 批量删除标签
     * @param ids
     */
    void delStoreLabelByIds(Long[] ids);
}
