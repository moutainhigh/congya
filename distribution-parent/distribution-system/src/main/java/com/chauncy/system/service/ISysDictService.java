package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysDictPo;

import java.util.List;

/**
 * <p>
 *  字典 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface ISysDictService extends Service<SysDictPo> {


    /**
     * 排序获取全部
     * @return
     */
    List<SysDictPo> findAllOrderBySortOrder();

    /**
     * 通过type获取
     * @param type
     * @return
     */
    SysDictPo findByType(String type);

    /**
     * 模糊搜索
     * @param key
     * @return
     */
    List<SysDictPo> findByTitleOrTypeLike(String key);

}
