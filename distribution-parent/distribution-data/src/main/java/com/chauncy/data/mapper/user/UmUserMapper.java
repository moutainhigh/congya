package com.chauncy.data.mapper.user;

import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.vo.app.user.UserDataVo;
import com.chauncy.data.vo.manage.user.idCard.SearchIdCardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 前端用户 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
public interface UmUserMapper extends IBaseMapper<UmUserPo> {

    /**
     * 登陆成功后修改登录次数和登录时间
     * @param phone 手机号码
     * @return
     */
    int updateLogin(@Param("phone") String phone);

    /**
     * 查找导入商品列表
     * @param searchUserIdCardDto
     * @return
     */
    List<SearchIdCardVo> loadSearchIdCardVos(SearchUserIdCardDto searchUserIdCardDto);

    /**
     * 根据手机号码获取用户基本信息
     * @param phone
     * @return
     */
    UserDataVo loadUserDataVo(@Param("phone") String phone);



}
