package com.chauncy.data.mapper.message.information.rel;

import com.chauncy.data.domain.po.message.information.rel.MmInformationForwardPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户资讯转发表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-29
 */
public interface MmInformationForwardMapper extends IBaseMapper<MmInformationForwardPo> {

    /**
     * 用户转发资讯成功
     * @param infoId
     * @param userId
     * @return
     */
    MmInformationForwardPo selectForUpdate(@Param("infoId") Long infoId, @Param("userId") Long userId);
}
