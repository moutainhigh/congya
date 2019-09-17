package com.chauncy.user.service;

import com.chauncy.common.enums.log.AccountTypeEnum;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.dto.app.user.add.BindUserDto;
import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.dto.manage.user.update.UpdateUserDto;
import com.chauncy.data.vo.app.user.UserDataVo;
import com.chauncy.data.vo.manage.user.detail.UmUserDetailVo;
import com.chauncy.data.vo.manage.user.detail.UmUserRelVo;
import com.chauncy.data.vo.manage.user.idCard.SearchIdCardVo;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 前端用户 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
public interface IUmUserService extends Service<UmUserPo> {
    /**
     * 验证码是否正确
     * @param verifyCode
     * @param phone
     * @param validCodeEnum
     * @return
     */
    boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum) ;

    /**
     * 新增用户
     * @param addUserDto
     * @return
     */
    boolean saveUser(AddUserDto addUserDto);

    /**
     * 绑定用户
     * @param userDto
     * @return
     */
    boolean bindUser(BindUserDto userDto);

    /**
     * 用户修改密码
     * @param addUserDto
     * @return
     */
    boolean reset(AddUserDto addUserDto);

    /**
     * 登陆成功后修改登陆时间和登录次数
     * @param phone
     * @return
     */
    boolean updateLogin(String phone);

    /**
     * 获取用户的账户余额
     * @param accountType
     * @return
     */
    BigDecimal getAccount(AccountTypeEnum accountType, UmUserPo umUserPo);

    /**
     * 查找导入商品列表
     * @param searchUserIdCardDto
     * @return
     */
    PageInfo<SearchIdCardVo> searchIdCardVos(SearchUserIdCardDto searchUserIdCardDto);


    /**
     * 根据手机号码获取用户基本信息
     * @param phone
     * @return
     */
    UserDataVo getUserDataVo( String phone);

    /**
     *用户填写邀请码
     * @param inviteCode
     * @param userId
     */
    void  setParent(Long inviteCode,Long userId);


    /**
     * 获取用户列表
     * @param searchUserListDto
     * @return
     */
    PageInfo<UmUserListVo> searchUserList(SearchUserListDto searchUserListDto);


    /**
     * 获取用户详情
     * @param id
     * @return
     */
    UmUserDetailVo getUserDetailVo( Long id);


    /**
     * 管理端更改用户信息
     * 修改当前红包  总的也要改变
     * @param updateUserDto
     * @return
     */
    boolean updateUmUser( UpdateUserDto updateUserDto, String currentUserName);

    /**
     * 关联用户不包括本身
     * @param id
     * @return
     */
    List<UmUserRelVo>  getRelUsers(@Param("id") Long id);


    /**
     *用户反馈信息
     *
     * @return
     */
    void addFeedBack(String content, UmUserPo userPo);

    /**
     * 判断用户经验值是否达到升级的门槛，如果是，则进行升级
     * @param userId
     */
    void updateLevel(Long userId);

    /**
     * 会员中心
     *
     * @param userPo
     * @return
     */
    GetMembersCenterVo getMembersCenter(UmUserPo userPo);

    /**
     * 获取所有前端用户的手机
     * @return
     */
    List<String> getAllPhones();
}
