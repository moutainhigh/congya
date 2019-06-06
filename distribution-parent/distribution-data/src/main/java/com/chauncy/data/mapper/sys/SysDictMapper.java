package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysDictPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  字典数据处理层 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysDictMapper extends IBaseMapper<SysDictPo> {

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
    List<SysDictPo> findByType(String type);

    /**
     * 模糊搜索
     * @param key
     * @return
     */
    List<SysDictPo> findByTitleOrTypeLike(@Param("key") String key);

}
