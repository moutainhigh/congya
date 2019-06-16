package com.chauncy.data.mapper.store.label;

import com.chauncy.data.domain.po.store.label.SmStoreLabelPo;
import com.chauncy.data.dto.manage.store.select.StoreLabelSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.store.label.SmStoreLabelVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-15
 */
public interface SmStoreLabelMapper extends IBaseMapper<SmStoreLabelPo> {

    /**
     * 条件查询
     *
     * @param storeLabelSearchDto
     * @return
     */
    List<SmStoreLabelVo> searchPaging(StoreLabelSearchDto storeLabelSearchDto);


    /**
     * 条件查询
     *
     * @return
     */
   List<SmStoreLabelVo> searchAll();



}
