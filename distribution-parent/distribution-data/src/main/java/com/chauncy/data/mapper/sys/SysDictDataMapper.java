package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysDictDataPo;
import com.chauncy.data.mapper.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 字典数据 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysDictDataMapper extends IBaseMapper<SysDictDataPo> {

    /**
     * 通过dictId和状态获取
     * @param dictId
     * @param status
     * @return
     */
    List<SysDictDataPo> findByDictIdAndStatusOrderBySortOrder(String dictId, Integer status);

    /**
     * 通过dictId删除
     * @param dictId
     */
    void deleteByDictId(String dictId);
}

