package com.chauncy.data.mapper.user;

import com.chauncy.data.bo.manage.pay.PayUserMessage;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.dto.manage.user.update.UpdateUserDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.user.GetMembersCenterVo;
import com.chauncy.data.vo.app.user.MyDataStatisticsVo;
import com.chauncy.data.vo.app.user.UserDataVo;
import com.chauncy.data.vo.manage.message.interact.push.UmUsersVo;
import com.chauncy.data.vo.manage.user.detail.UmUserDetailVo;
import com.chauncy.data.vo.manage.user.detail.UmUserRelVo;
import com.chauncy.data.vo.manage.user.idCard.SearchIdCardVo;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
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
     * 推送信息时需要获取app用户信息
     * 
     * @param searchUserListDto
     * @return
     */
    List<UmUsersVo> loadSearchUsers(SearchUserListDto searchUserListDto);

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

    /**
     * 获取用户列表
     * @param searchUserListDto
     * @return
     */
    List<UmUserListVo> loadSearchUserList(SearchUserListDto searchUserListDto);

    /**
     * 获取用户详情
     * @param id
     * @return
     */
    UmUserDetailVo loadUserDetailVo(@Param("id") Long id);

    /**
     * 管理端更改用户信息
     * 修改当前红包  总的也要改变
     * @param updateUserDto
     * @return
     */
    int updateUmUser(@Param("t") UpdateUserDto updateUserDto,@Param("currentUserName") String currentUserName);

    /**
     * 获取用户的标签名称
     * @param id
     * @return
     */
    String getLabelNamesByUserId(@Param("id") Long id);

    /**
     * 关联用户
     * @param id
     * @return
     */
    List<UmUserRelVo>  getRelUsers(@Param("id") Long id);

    /**
     * 根据ids获取用户信息
     * @param userIds
     * @return
     */
    List<UmUsersVo> getUsersByIds(@Param("userIds") List<Long> userIds);

    /**
     * 获取用户所属店铺
     * @param userId
     * @return
     */
    Long getUserStoreId(@Param("userId") Long userId);

    /**
     * 获取userId
     * @param userId
     * @return
     */
    List<PayUserMessage> getPayUserMessage(@Param("userId") Long userId);

    /**
     * 用户增加某些数据
     * @param userPo
     * @return
     */
    int updateAdd(UmUserPo userPo);

    /**
     * 根据用户id获取红包赠送比例
     * @param userId
     * @return
     */
    BigDecimal getPacketPresent(Long userId);

    /**
     * 会员中心
     *
     * @param userId
     * @return
     */
    @Select("select a.photo,a.name as user_name,a.current_experience,a.total_order,a.total_consume_money,a.create_time," +
            "b.actor,b.level_name,b.level_experience as sum_experience " +
            "from um_user a,pm_member_level b " +
            "where a.id = #{userId} and a.member_level_id = b.id and a.del_flag = 0 and b.del_flag = 0")
    GetMembersCenterVo getMembersCenter(Long userId);

    /**
     * 获得所有用户的手机
     * @return
     */
    List<String> getAllPhones();

    /**
     * App我的页面需要的数据
     * @param userId
     * @return
     */
    MyDataStatisticsVo getMyDataStatistics(Long userId);
}
