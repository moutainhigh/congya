package com.chauncy.user.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmMemberLevelService extends Service<PmMemberLevelPo> {


    /**
     * 获得最大级别的信息
     * @return
     */
    PmMemberLevelPo findByMaxLevel();


}
