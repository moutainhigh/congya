package com.chauncy.data.mapper.message.information.rel;

import com.chauncy.data.domain.po.message.information.rel.MmInformationLikedPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户资讯点赞表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-29
 */
public interface MmInformationLikedMapper extends IBaseMapper<MmInformationLikedPo> {

    /**
     * 用户资讯点赞、取消点赞
     * @param infoId
     * @param userId
     * @return
     */
    MmInformationLikedPo selectForUpdate(@Param("infoId") Long infoId, @Param("userId") Long userId);
}
