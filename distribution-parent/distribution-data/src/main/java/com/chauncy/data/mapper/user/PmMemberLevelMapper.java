package com.chauncy.data.mapper.user;

import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.supplier.MemberLevelInfos;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmMemberLevelMapper extends IBaseMapper<PmMemberLevelPo> {

    /**
     * 获得最大级别的信息
     * @return
     *
     *
     */
    @Select("SELECT * FROM `pm_member_level`\n" +
            "WHERE  level=(select MAX(level) from pm_member_level where del_flag=0)\n")
    PmMemberLevelPo loadMaxLevel();

    /**
     * 查找所有会员等级id和名称
     *
     * @return
     */
    @Select("select id,level_name as name from `pm_member_level` where del_flag=false")
    List<BaseVo> searchMemberLevel();

    /**
     * 查找所有会员等级id和名称
     * @return
     */
    @Select("select id as memberLevelId,level_name as levelName from `pm_member_level` where del_flag=false")
    List<MemberLevelInfos> memberLevelInfos ();

    /**
     * 获取最低等级的会员
     * @return
     */
    @Select ("SELECT min(level) as lowestLevel FROM `pm_member_level` where del_flag=false")
    Integer getLowestLevelId ();
}
