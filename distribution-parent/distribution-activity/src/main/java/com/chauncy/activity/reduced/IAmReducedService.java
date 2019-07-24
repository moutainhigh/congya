package com.chauncy.activity.reduced;

import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.reduced.add.SaveReducedDto;

/**
 * <p>
 * 满减活动管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface IAmReducedService extends Service<AmReducedPo> {

    /**
     * 保存满减活动信息
     * @param saveReducedDto
     * @return
     */
    void saveReduced(SaveReducedDto saveReducedDto);

}
