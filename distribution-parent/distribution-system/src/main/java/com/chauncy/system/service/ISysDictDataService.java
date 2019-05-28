package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysDictDataPo;

import java.util.List;

/**
 * <p>
 * 字典数据 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface ISysDictDataService extends Service<SysDictDataPo> {

    /**
     * 多条件获取
     * @param dictData
     * @param pageable
     * @return
     */
//    Page<SysDictDataPo> findByCondition(SysDictDataPo dictData, Pageable pageable);

    /**
     * 通过dictId获取启用字典 已排序
     * @param dictId
     * @return
     */
    List<SysDictDataPo> findByDictId(String dictId);

    /**
     * 通过dictId删除
     * @param dictId
     */
    void deleteByDictId(String dictId);

}
