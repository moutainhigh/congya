package com.chauncy.data.mapper.activity.spell;

import com.chauncy.data.domain.po.activity.spell.AmSpellGroupMemberPo;
import com.chauncy.data.mapper.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 拼团成员表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-31
 */
public interface AmSpellGroupMemberMapper extends IBaseMapper<AmSpellGroupMemberPo> {

    /**
     * @Author zhangrt
     * @Date 2019/10/26 17:22
     * @Description 获取拼团成功订单id
     *
     * @Update
     *
     * @Param [groupMemberId]
     * @return java.util.List<java.lang.Long>
     **/

    List<Long> getSuccessOrders(Long mainId);

}
