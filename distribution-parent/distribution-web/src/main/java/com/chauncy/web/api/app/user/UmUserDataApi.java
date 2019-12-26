package com.chauncy.web.api.app.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.Constants;
import com.chauncy.common.enums.log.AccountTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.third.easemob.RegistIM;
import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.common.util.huanxin.HuanXinUtil;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.*;
import com.chauncy.data.dto.app.user.select.SearchMyFriendDto;
import com.chauncy.data.dto.app.user.update.UpdatePhoneDto;
import com.chauncy.data.dto.app.user.update.UpdateUserDataDto;
import com.chauncy.data.valid.group.ISaveGroup;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.order.cart.RealUserVo;
import com.chauncy.data.vo.app.user.*;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.user.service.IUmAreaShippingService;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端用户 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@RestController
@RequestMapping("/app/user/data")
@Api(tags = "APP_前端用户个人资料")
public class UmUserDataApi extends BaseApi {

    @Autowired
    private IUmUserService service;

    @Autowired
    private IUmAreaShippingService shipService;

    @Autowired
    public SecurityUtil securityUtil;

    /**
     * 用户添加收货地址
     *
     * @param addAreaDto
     * @return
     */
    @PostMapping("/addArea")
    @ApiOperation("用户添加收货地址")
    public JsonViewData addArea(@RequestBody @ApiParam(required = true,name = "addAreaDto",value = "用户添加收货地址Dto")
                                @Validated AddAreaDto addAreaDto){

        UmUserPo userPo = securityUtil.getAppCurrUser();

        return new JsonViewData(shipService.addArea(addAreaDto,userPo));
    }

    /**
     * 用户修改收货地址
     *
     * @param updateAreaDto
     * @return
     */
    @PostMapping("/updateArea")
    @ApiOperation("用户修改收货地址")
    public JsonViewData updateArea(@RequestBody @ApiParam(required = true,name = "updateAreaDto",value = "用户修改收货地址Dto")
                                   @Validated(IUpdateGroup.class) AddAreaDto updateAreaDto){

        UmUserPo userPo = securityUtil.getAppCurrUser();
        shipService.updateArea(updateAreaDto,userPo);

        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    @GetMapping("/delArea/{id}")
    @ApiOperation("删除收货地址")
    public JsonViewData delArea(@ApiParam(required = true,name = "id",value = "收货地址ID")
                                @PathVariable Long id){
        shipService.delArea(id);
        return new JsonViewData(ResultCode.SUCCESS);

    }

    /**
     * 获取用户的账户余额
     * @param accountType
     * @return
     */
    /*@GetMapping("/getAccount/{accountType}")
    @ApiOperation("获取用户的账户余额")
    public JsonViewData getAccount(@ApiParam(required = true,name = "accountType",value = "accountType账目类型   \nRED_ENVELOPS(红包)   \nSHOP_TICKET(购物券)")
                                @PathVariable AccountTypeEnum accountType){


        //获取当前用户
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if(null == umUserPo) {
            return new JsonViewData(ResultCode.NO_LOGIN, "未登陆或登陆已超时");
        }
        return new JsonViewData(ResultCode.SUCCESS, "查询成功", umUserService.getAccount(accountType, umUserPo));

    }*/

    /**
     * 查找用户收货地址
     *
     * @return
     */
    @GetMapping("/findShipArea")
    @ApiOperation("查找用户收货地址")
    public JsonViewData<List<ShipAreaVo>> findShipArea(){
        Long userId = Long.valueOf(securityUtil.getAppCurrUser().getId());
        return new JsonViewData(shipService.findShipArea(userId));
    }

    @GetMapping("/findShipAreas/{userId}")
    @ApiOperation("查找用户收货地址")
    public JsonViewData<List<ShipAreaVo>> findShipArea(@ApiParam(required = true,name = "userId",value = "用户ID")
                                                       @PathVariable Long userId){

            return new JsonViewData(shipService.findShipArea(userId));
        }

    @GetMapping("/findDefaultShipArea")
    @ApiOperation("查找用户默认收货地址")
    public JsonViewData<List<ShipAreaVo>> findDefaultShipArea(){
        Long userId=getAppCurrUser().getId();
        return new JsonViewData(shipService.findDefaultShipArea(userId));
    }

    /**
     *用户反馈信息
     *
     * @return
     */
    @PostMapping("/addFeedBack")
    @ApiOperation("用户反馈信息")
    public JsonViewData addFeedBack(@RequestParam(value = "content") String content){

        UmUserPo userPo = securityUtil.getAppCurrUser();
        service.addFeedBack(content,userPo);

        return new JsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/register")
    @ApiOperation("新用户注册")
    public JsonViewData register(@Validated(ISaveGroup.class) @RequestBody AddUserDto userDto){
        String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptPass);

        return service.saveUser(userDto)?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }

    @PostMapping("/reset")
    @ApiOperation("忘记密码")
    public JsonViewData reset(@Validated(IUpdateGroup.class) @RequestBody AddUserDto userDto){
        String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptPass);

        return service.reset(userDto)?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);

    }

    @PostMapping("/binding")
    @ApiOperation("第三方登录绑定")
    public JsonViewData binding(@Validated @RequestBody BindUserDto userDto){
        String encryptPass = new BCryptPasswordEncoder().encode("20191126");
        userDto.setPassword(encryptPass);
        return service.bindUser(userDto)?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }


    @PostMapping("/certification")
    @ApiOperation("实名认证")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData certification(@Validated @RequestBody AddIdCardDto addIdCardDto){
        UmUserPo appCurrUser = getAppCurrUser();
        UmUserPo updateUserPo = new UmUserPo();
        BeanUtils.copyProperties(addIdCardDto,updateUserPo);
        updateUserPo.setId(appCurrUser.getId()).setUpdateBy(appCurrUser.getPhone());
        umUserService.updateById(updateUserPo);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/getRealUser")
    @ApiOperation("获取实名认证信息")
    public JsonViewData<RealUserVo> certification() {
        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        RealUserVo realUserVo=new RealUserVo();
        realUserVo.setStatus(1).setBackPhoto(appCurrUser.getBackPhoto()).setTrueName(appCurrUser.getTrueName()).
                setFrontPhoto(appCurrUser.getFrontPhoto()).setIdCard(appCurrUser.getIdCard());
        if (appCurrUser.getIdCard()==null){
            realUserVo.setStatus(0);
        }

        return setJsonViewData(realUserVo);

    }

    @PostMapping("/get_phone")
    @ApiOperation("获得当前登录用户手机")
    public JsonViewData phone(){
        UmUserPo appCurrUser = getAppCurrUser();
        if (appCurrUser==null){
            return setJsonViewData(ResultCode.FAIL,"用户尚未登录！");
        }
        else {
            return setJsonViewData(appCurrUser.getPhone());
        }
    }


    @PostMapping("/old_phone_check")
    @ApiOperation("原有手机号码验证")
    public JsonViewData oldphone(@Validated(ISaveGroup.class) @RequestBody UpdatePhoneDto updatePhoneDto){
        boolean isTrue = umUserService.validVerifyCode(updatePhoneDto.getVerifyCode(), updatePhoneDto.getPhone()
                , ValidCodeEnum.OLD_BIND_PHONE_CODE);
        return isTrue?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/updatePayPass")
    @ApiOperation("设置与修改支付密码")
    public JsonViewData updatePayPass( @RequestBody PayPasswordDto payPasswordDto){
        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        boolean isTrue = umUserService.validVerifyCode(payPasswordDto.getVerifyCode(), appCurrUser.getPhone()
                , ValidCodeEnum.UPDATE_PAY_PASSWORD_CODE);
        if (!isTrue){
            return setJsonViewData(ResultCode.PARAM_ERROR,"验证码错误！");
        }
        UmUserPo updateUser=new UmUserPo();
        updateUser.setId(appCurrUser.getId()).setPayPassword(payPasswordDto.getPassword());
        umUserService.updateById(updateUser);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/new_phone_check")
    @ApiOperation("新手机号码验证")
    public JsonViewData newphone(@Validated(IUpdateGroup.class) @RequestBody UpdatePhoneDto updatePhoneDto){
        boolean isTrue = umUserService.validVerifyCode(updatePhoneDto.getVerifyCode(), updatePhoneDto.getPhone()
                , ValidCodeEnum.NEW_BIND_PHONE_CODE);
        if (!isTrue){
            return setJsonViewData(ResultCode.FAIL,"验证码错误！");
        }
        UmUserPo updateUser=new UmUserPo();
        updateUser.setPhone(updatePhoneDto.getPhone()).setId(getAppCurrUser().getId());

        umUserService.updateById(updateUser);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/update")
    @ApiOperation("个人头像 昵称 性别 邀请码更改")
    public JsonViewData update(@Validated @RequestBody UpdateUserDataDto updateUserDataDto){
        //设置邀请码
        if (updateUserDataDto.getInviteCode()!=null&&updateUserDataDto.getInviteCode()!=0){
            umUserService.setParent(updateUserDataDto.getInviteCode(),getAppCurrUser().getId());
        }

        UmUserPo updateUser = new UmUserPo();
        BeanUtils.copyProperties(updateUserDataDto, updateUser,"inviteCode");
        updateUser.setId(getAppCurrUser().getId());
        umUserService.updateById(updateUser);

        //当用户修改昵称时修改环信账号昵称
//        if (!getAppCurrUser().getName().equals(updateUserDataDto.getName()) && updateUserDataDto.getName() != null ) {
//            //判断该用户是否已经注册过IM账号
//            if (RegistIM.getUser(getAppCurrUser().getId().toString()) != null) {
//                RegistIM.modifyIMUserNickName(getAppCurrUser().getId().toString(),updateUserDataDto.getName());
//            }
//        }
//
//        if (RegistIM.getUser(getAppCurrUser().getId().toString()) == null) {
//            RegUserBo regUserBo = new RegUserBo();
//            regUserBo.setPassword(Constants.PASSWORD);
//            regUserBo.setUsername(getAppCurrUser().getId().toString());
//            regUserBo.setNickname(updateUserDataDto.getName());
//            RegistIM.reg(regUserBo);
//        }

        if (!getAppCurrUser().getName().equals(updateUserDataDto.getName()) && updateUserDataDto.getName() != null ) {
            //判断该用户是否已经注册过客服IM账号
            if (new HuanXinUtil().getHXUserInfo(getAppCurrUser().getId().toString()) != null) {
                new HuanXinUtil().changeUserNickname(getAppCurrUser().getId().toString(),updateUserDataDto.getName());
            }
        }
        if (new HuanXinUtil().getHXUserInfo(getAppCurrUser().getId().toString()) != null) {
            new HuanXinUtil().createUser(getAppCurrUser().getId().toString(),Constants.PASSWORD,updateUserDataDto.getName());
        }

        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/getNameByCode/{inviteCode}")
    @ApiOperation("根据邀请码获取用户昵称 inviteCode:邀请码")
    public JsonViewData<UserNickNameVo> getNameByCode(@PathVariable Long inviteCode){
        QueryWrapper<UmUserPo> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(UmUserPo::getInviteCode,inviteCode).select(UmUserPo::getName,UmUserPo::getPhoto);
        UmUserPo queryUser=umUserService.getOne(queryWrapper);
        UserNickNameVo userNickNameVo=new UserNickNameVo();
        if (queryUser==null){
            return setJsonViewData(ResultCode.NO_EXISTS);
        }
        userNickNameVo.setName(queryUser.getName());
        userNickNameVo.setPhoto(queryUser.getPhoto());
        return setJsonViewData(userNickNameVo);

    }


    @PostMapping("/get")
    @ApiOperation("获取个人基本信息")
    public JsonViewData<UserDataVo> get(){
        return setJsonViewData(service.getUserDataVo(getAppCurrUser().getPhone()));
    }

    /**
     *
     * App我的页面需要的数据
     *
     * @return
     */
    @ApiOperation("我的数据")
    @PostMapping("/getMyDataStatistics")
    public JsonViewData<MyDataStatisticsVo> getMyDataStatistics(){

        UmUserPo userPo = securityUtil.getAppCurrUser();

        return setJsonViewData(service.getMyDataStatistics(userPo));
    }

    /**
     * @Author chauncy
     * @Date 2019-09-18 10:26
     * @Description //条件分页查询我的粉丝
     *
     * @Update chauncy
     *
     * @Param [searchMyFriendDto]
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.user.SearchMyFriendVo>>
     **/
    @ApiOperation("条件分页查询我的粉丝")
    @PostMapping("/searchMyFriend")
    public JsonViewData<PageInfo<SearchMyFriendVo>> searchMyFriend(@RequestBody @ApiParam(required = true,name ="",value = "条件分页查询我的粉丝")
                                                         @Validated SearchMyFriendDto searchMyFriendDto){

        UmUserPo umUserPo = securityUtil.getAppCurrUser();

        return setJsonViewData(service.searchMyFriend(searchMyFriendDto,umUserPo));
    }
}
