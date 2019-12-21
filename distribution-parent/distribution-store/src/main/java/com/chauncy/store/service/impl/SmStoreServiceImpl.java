package com.chauncy.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.Constants;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.constant.ServiceConstant;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.goods.GoodsCategoryLevelEnum;
import com.chauncy.common.enums.store.StoreRelationEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.system.SysRoleTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.third.easemob.RegistIM;
import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.rel.SmRelStoreAttributePo;
import com.chauncy.data.domain.po.store.rel.SmRelUserFocusStorePo;
import com.chauncy.data.domain.po.store.rel.SmStoreRelLabelPo;
import com.chauncy.data.domain.po.store.rel.SmStoreRelStorePo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.store.FindStoreCategoryDto;
import com.chauncy.data.dto.app.store.FindStoreParamDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreAccountInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreRelStoreDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchByConditionDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.dto.supplier.store.update.StoreBusinessLicenseDto;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.store.rel.SmRelStoreAttributeMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.store.rel.SmRelUserFocusStoreMapper;
import com.chauncy.data.mapper.store.rel.SmStoreRelLabelMapper;
import com.chauncy.data.mapper.store.rel.SmStoreRelStoreMapper;
import com.chauncy.data.mapper.sys.SysRoleMapper;
import com.chauncy.data.mapper.sys.SysRoleUserMapper;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.store.StoreCategoryDetailVo;
import com.chauncy.data.vo.app.advice.store.StoreHomePageVo;
import com.chauncy.data.vo.app.component.ScreenGoodsParamVo;
import com.chauncy.data.vo.app.component.ScreenParamVo;
import com.chauncy.data.vo.app.component.ScreenStoreParamVo;
import com.chauncy.data.vo.app.store.StoreDetailVo;
import com.chauncy.data.vo.app.store.StorePagingVo;
import com.chauncy.data.vo.app.user.UserNickNameVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.data.vo.manage.store.*;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.rel.service.ISmRelStoreAttributeService;
import com.chauncy.store.rel.service.ISmStoreRelLabelService;
import com.chauncy.store.rel.service.ISmStoreRelStoreService;
import com.chauncy.store.service.ISmStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmStoreServiceImpl extends AbstractService<SmStoreMapper,SmStorePo> implements ISmStoreService {

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private SmStoreMapper smStoreMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SmRelStoreAttributeMapper smRelStoreAttributeMapper;
    @Autowired
    private ISmStoreRelLabelService smStoreRelLabelService;
    @Autowired
    private SmStoreRelLabelMapper smStoreRelLabelMapper;
    @Autowired
    private SmRelUserFocusStoreMapper smRelUserFocusStoreMapper;
    @Autowired
    private SmStoreRelStoreMapper smStoreRelStoreMapper;
    @Autowired
    private PmGoodsCategoryMapper pmGoodsCategoryMapper;
    @Autowired
    private ISmStoreRelStoreService smStoreRelStoreService;
    @Autowired
    private ISmRelStoreAttributeService smRelStoreAttributeService;
    /**
     * APP店铺列表中店铺展示商品列表默认展示排序前四个
     */
    private final static int DEFAULT_SHOW_GOODS_NUM = 4;
    /**
     *  店铺用户默认密码
     */
    private final static String DEFAULT_PASSWORD = "123456";

    /**
     * @Author yeJH
     * @Date 2019/10/19 16:15
     * @Description 获取商家端头像+昵称
     *
     * @Update yeJH
     *
     * @param
     * @return com.chauncy.data.vo.app.user.UserNickNameVo
     **/
    @Override
    public UserNickNameVo getStoreUserInfo() {

        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo) {
            throw new ServiceException(ResultCode.FAIL, "当前用户不是商家用户");
        }
        SmStorePo smStorePo = smStoreMapper.selectById(sysUserPo.getStoreId());
        if(null == smStorePo) {
            throw new ServiceException(ResultCode.FAIL, "当前商家用户不存在");
        }

        UserNickNameVo userNickNameVo = new UserNickNameVo();
        userNickNameVo.setName(smStorePo.getUserName());
        userNickNameVo.setPhoto(smStorePo.getLogoImage());

        return userNickNameVo;
    }

    /**
     * 保存店铺基本信息
     * @param storeBaseInfoDto
     * @return
     */
    @Override
    public JsonViewData saveStore(StoreBaseInfoDto storeBaseInfoDto) {



        QueryWrapper<SmStorePo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        smStoreCategoryPoQueryWrapper.eq("name", storeBaseInfoDto.getName());
        if(null != this.getOne(smStoreCategoryPoQueryWrapper)) {
            throw  new ServiceException(ResultCode.DUPLICATION, "店铺名称已存在");
        }
        smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        smStoreCategoryPoQueryWrapper.eq("user_name", storeBaseInfoDto.getUserName());
        if(null != this.getOne(smStoreCategoryPoQueryWrapper)) {
            throw  new ServiceException(ResultCode.DUPLICATION, "店铺账号已存在");
        }

        SmStorePo smStorePo = new SmStorePo();
        BeanUtils.copyProperties(storeBaseInfoDto, smStorePo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        smStorePo.setCreateBy(userName);
        //店铺信息插入
        smStorePo.setId(null);
        smStoreMapper.insert(smStorePo);

        //绑定店铺关系
        bindingStore(smStorePo.getId(), storeBaseInfoDto.getStoreRelStoreDtoList());

        //批量插入店铺品牌关联记录
        storeBaseInfoDto.setId(smStorePo.getId());
        saveBatchRelStoreAttribute(storeBaseInfoDto, userName);

        //批量插入店铺标签关联记录
        saveBatchRelStoreLabel(storeBaseInfoDto, userName);

        //添加店铺后台账号
        createSysUser(smStorePo);

        /*RegUserBo regStoreBo = new RegUserBo();
        //判断该店铺是否已经注册过IM账号
        if (RegistIM.getUser(String.valueOf(smStorePo.getId())) == null) {
            regStoreBo.setPassword(Constants.PASSWORD);
            regStoreBo.setUsername(String.valueOf(smStorePo.getId()));
            RegistIM.reg(regStoreBo);
        }*/

        return new JsonViewData(ResultCode.SUCCESS, "添加成功", smStorePo);
    }
    /**
     * 编辑店铺基本信息
     * @param storeBaseInfoDto
     * @return
     */
    @Override
    public JsonViewData editStore(StoreBaseInfoDto storeBaseInfoDto) {

        SmStorePo oldSmStore = smStoreMapper.selectById(storeBaseInfoDto.getId());

        QueryWrapper<SmStorePo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        smStoreCategoryPoQueryWrapper.eq("name", storeBaseInfoDto.getName());
        SmStorePo tempSmStorePo = this.getOne(smStoreCategoryPoQueryWrapper);
        if(null != tempSmStorePo && !tempSmStorePo.getId().equals(oldSmStore.getId())) {
            throw  new ServiceException(ResultCode.DUPLICATION, "店铺名称已存在");
        }
        if(!oldSmStore.getUserName().equals(storeBaseInfoDto.getUserName())) {
            throw  new ServiceException(ResultCode.PARAM_ERROR, "店铺账号不可更改");
        }
        if(!oldSmStore.getType().equals(storeBaseInfoDto.getType())) {
            throw  new ServiceException(ResultCode.PARAM_ERROR, "店铺类型不可更改");
        }

        BeanUtils.copyProperties(storeBaseInfoDto, oldSmStore);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        oldSmStore.setCreateBy(userName);
        //店铺信息修改
        smStoreMapper.updateById(oldSmStore);

        //绑定店铺关系
        bindingStore(oldSmStore.getId(), storeBaseInfoDto.getStoreRelStoreDtoList());

        //查询新更改的品牌中缺少的已有品牌是否有关联的商品  如果有则编辑失败(修改  改为店铺下的商品品牌选择跟店铺无关)
        //List<Long> oldAttributeIds = smStoreMapper.selectAttributeIdsById(storeBaseInfoDto.getId());
        List<Long> newAttributeIds = Arrays.asList(storeBaseInfoDto.getAttributeIds());
        //oldAttributeIds 与 newAttributeIds的差集
        /*List<Long> reduceList = oldAttributeIds.stream().filter(item -> !newAttributeIds.contains(item)).collect(toList());
        if(null != reduceList && reduceList.size() > 0 ) {
            throw  new ServiceException(ResultCode.PARAM_ERROR, "修改失败，删除的品牌包含正被使用的关联的品牌");
        }*/

        //将店铺与品牌关联表的记录删除  关联不能全部删除重新创建  因为可能已经有已存在关联，删除差集 needDelList
        /*Map<String, Object> map = new HashMap<>();
        map.put("store_id", storeBaseInfoDto.getId());
        smRelStoreAttributeMapper.deleteByMap(map);*/
        // 获取店铺关联的品牌id
        List<Long> relAttributeIds = smStoreMapper.selectRelAttributeIds(storeBaseInfoDto.getId());
        List<Long> needDelList = relAttributeIds.stream().filter(item -> !newAttributeIds.contains(item)).collect(toList());
        if(null != needDelList && needDelList.size() > 0) {
            QueryWrapper<SmRelStoreAttributePo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(SmRelStoreAttributePo::getStoreId, storeBaseInfoDto.getId())
                    .in(SmRelStoreAttributePo::getAttributeId, needDelList);
            smRelStoreAttributeMapper.delete(queryWrapper);
        }

        //批量插入店铺品牌关联记录  此时需要插入的关联应该为 newAttributeIds  与 relAttributeIds 的差集
        List<Long> needInsertList = newAttributeIds.stream().filter(item -> !relAttributeIds.contains(item)).collect(toList());
        if(null != needInsertList && needInsertList.size() > 0) {
            storeBaseInfoDto.setAttributeIds(needInsertList.toArray(new Long[needInsertList.size()]));
            saveBatchRelStoreAttribute(storeBaseInfoDto, userName);
        }

        //将店铺与标签关联表的记录删除
        List<Long> oldLabelIds = smStoreMapper.selectLabelIdsById(storeBaseInfoDto.getId());
        QueryWrapper<SmStoreRelLabelPo> relLabelPoQueryWrapper = new QueryWrapper<>();
        relLabelPoQueryWrapper.lambda()
                .eq(SmStoreRelLabelPo::getStoreId, storeBaseInfoDto.getId())
                .notIn(SmStoreRelLabelPo::getStoreLabelId, storeBaseInfoDto.getStoreLabelIds());
                //.in(SmStoreRelLabelPo::getStoreLabelId, oldLabelIds)
        smStoreRelLabelMapper.delete(relLabelPoQueryWrapper);
        //批量插入店铺标签关联记录
        saveBatchRelStoreLabel(storeBaseInfoDto, userName);

        return new JsonViewData(ResultCode.SUCCESS, "编辑成功");
    }

    /**
     * 批量插入店铺品牌关联记录
     * @param storeBaseInfoDto
     * @param userName
     */
    private void saveBatchRelStoreAttribute (StoreBaseInfoDto storeBaseInfoDto, String userName) {
        List<SmRelStoreAttributePo> smRelStoreAttributePoList = new ArrayList<>();
        if(null != storeBaseInfoDto.getAttributeIds() && storeBaseInfoDto.getAttributeIds().length > 0) {
            for (Long attributeId : storeBaseInfoDto.getAttributeIds()) {
                SmRelStoreAttributePo smRelStoreAttributePo = new SmRelStoreAttributePo();
                smRelStoreAttributePo.setAttributeId(attributeId);
                smRelStoreAttributePo.setCreateBy(userName);
                smRelStoreAttributePo.setStoreId(storeBaseInfoDto.getId());
                smRelStoreAttributePoList.add(smRelStoreAttributePo);
            }
            smRelStoreAttributeService.saveBatch(smRelStoreAttributePoList);
        }
    }

    /**
     * 批量插入店铺标签关联记录
     * @param storeBaseInfoDto
     * @param userName
     */
    private void saveBatchRelStoreLabel (StoreBaseInfoDto storeBaseInfoDto, String userName) {
        List<SmStoreRelLabelPo> smStoreRelLabelPoList = new ArrayList<>();
        for(Long labelId : storeBaseInfoDto.getStoreLabelIds()) {
            QueryWrapper<SmStoreRelLabelPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(SmStoreRelLabelPo::getStoreId, storeBaseInfoDto.getId())
                    .eq(SmStoreRelLabelPo::getStoreLabelId, labelId);
            if(smStoreRelLabelService.count(queryWrapper) > 0) {
                //已存在
                continue;
            } else {
                SmStoreRelLabelPo smStoreRelLabelPo = new SmStoreRelLabelPo();
                smStoreRelLabelPo.setStoreLabelId(labelId);
                smStoreRelLabelPo.setCreateBy(userName);
                smStoreRelLabelPo.setStoreId(storeBaseInfoDto.getId());
                smStoreRelLabelPoList.add(smStoreRelLabelPo);
            }
        }
        smStoreRelLabelService.saveBatch(smStoreRelLabelPoList);
    }

    /**
     * 绑定店铺关系
     * @param storeId
     * @param storeRelStoreDtoList
     */
    private void bindingStore(Long storeId, List<StoreRelStoreDto> storeRelStoreDtoList) {
        if(null == storeRelStoreDtoList || storeRelStoreDtoList.size() <= 0) {
            return ;
        }

        List<SmStoreRelStorePo> smStoreRelStorePoList = new ArrayList<>();
        for(StoreRelStoreDto storeRelStoreDto : storeRelStoreDtoList) {
            if(storeId.equals(storeRelStoreDto.getParentId())) {
                //店铺不能绑定自身
                break;
            }
            SmStoreRelStorePo smStoreRelStorePo = new SmStoreRelStorePo();
            smStoreRelStorePo.setStoreId(storeId);
            smStoreRelStorePo.setParentId(storeRelStoreDto.getParentId());
            smStoreRelStorePo.setType(storeRelStoreDto.getType());
            Integer count = smStoreRelStoreMapper.selectStoreRelCount(storeRelStoreDto);
            if(count > 0) {
                //关系已存在
            } else {
                //关系不存在
                if(StoreRelationEnum.TEAM_WORK.getId().equals(storeRelStoreDto.getType())) {
                    //团队合作 关系是一条单线不超过5层
                    //店铺没有绑定过其他店铺
                    //被绑定的店铺没有被其他店铺绑定过
                    List<Integer> integerList = smStoreMapper.getTeamWorkCondition(storeId, storeRelStoreDto.getParentId());
                    //被绑定的店铺的关系链的层数 >= 5
                    if(integerList.get(0) >= ServiceConstant.TEAM_WORK_LEVEL) {
                        throw new ServiceException(ResultCode.PARAM_ERROR, "团队合作的关系链层级已达到最高5层");
                    } else if(integerList.get(1) > 0) {
                        throw new ServiceException(ResultCode.PARAM_ERROR, "当前店铺已有绑定团队合作关系的店铺");
                    } else if(integerList.get(2) > 0) {
                        throw new ServiceException(ResultCode.PARAM_ERROR, "当前被绑定店铺已被其他店铺绑定为团队合作关系");
                    }
                } else if(StoreRelationEnum.PRODUCT_AGENT.getId().equals(storeRelStoreDto.getType())) {
                    //产品代理   被绑定的店铺不允许存在在当前店铺的子店铺中（避免形成闭环）
                    Boolean productAgentCondition = smStoreMapper.getProductAgentCondition(storeId, storeRelStoreDto.getParentId());
                    if(true == productAgentCondition) {
                        //true 表示被绑定的店铺在当前店铺的子店铺中 循环绑定  形成闭环
                        throw new ServiceException(ResultCode.PARAM_ERROR, "被绑定店铺是当前店铺的子店铺，绑定错误");
                    }
                }
                smStoreRelStorePoList.add(smStoreRelStorePo);
            }
        }
        if(smStoreRelStorePoList.size() >= 0) {
            smStoreRelStoreService.saveBatch(smStoreRelStorePoList);
        }
    }

    /**
     * 创建店铺管理员角色并且与系统角色ROLE_STORE绑定
     * @param smStorePo
     */
    private void createSysUser(SmStorePo smStorePo) {
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setCreateBy(smStorePo.getCreateBy());
        sysUserPo.setMobile(smStorePo.getOwnerMobile());
        sysUserPo.setNickName(smStorePo.getName());
        sysUserPo.setPassword(new BCryptPasswordEncoder().encode(DEFAULT_PASSWORD));
        sysUserPo.setType(SecurityConstant.USER_TYPE_ADMIN);
        sysUserPo.setSystemType(SecurityConstant.SYS_TYPE_SUPPLIER);
        sysUserPo.setStoreId(smStorePo.getId());
        sysUserPo.setUsername(smStorePo.getUserName());
        sysUserPo.setStatus(SecurityConstant.USER_STATUS_LOCK);
        sysUserMapper.insert(sysUserPo);

        //获取店铺系统角色
        QueryWrapper<SysRolePo> sysRolePoQueryWrapper = new QueryWrapper<>();
        sysRolePoQueryWrapper.eq("name", SysRoleTypeEnum.ROLE_STORE.getName());
        SysRolePo sysRolePo = sysRoleMapper.selectOne(sysRolePoQueryWrapper);

        //关联系统用户跟系统角色
        SysRoleUserPo sysRoleUserPo = new SysRoleUserPo();
        sysRoleUserPo.setUserId(sysUserPo.getId());
        sysRoleUserPo.setRoleId(sysRolePo.getId());
        sysRoleUserPo.setCreateBy(smStorePo.getCreateBy());
        sysRoleUserMapper.insert(sysRoleUserPo);

    }



    /**
     * 保存店铺账户信息
     * @param storeAccountInfoDto
     * @return
     */
    @Override
    public JsonViewData saveStore(StoreAccountInfoDto storeAccountInfoDto) {

        SmStorePo smStorePo = smStoreMapper.selectById(storeAccountInfoDto.getId());

        if(null != smStorePo) {
            //店铺账户信息插入
            BeanUtils.copyProperties(storeAccountInfoDto, smStorePo);
            //获取当前用户
            String user = securityUtil.getCurrUser().getUsername();
            smStorePo.setUpdateBy(user);
            smStoreMapper.updateById(smStorePo);
            return new JsonViewData(ResultCode.SUCCESS, "添加成功");
        } else {
            return new JsonViewData(ResultCode.NO_EXISTS, "店铺不存在");
        }

    }



    /**
     * 编辑店铺账户信息
     * @param storeAccountInfoDto
     * @return
     */
    @Override
    public JsonViewData editStore(StoreAccountInfoDto storeAccountInfoDto) {

        return this.saveStore(storeAccountInfoDto);

    }


    /**
     * 修改店铺经营状态
     * @param baseUpdateStatusDto
     * ids 店铺ID
     * enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    @Override
    public JsonViewData editStoreStatus(BaseUpdateStatusDto baseUpdateStatusDto) {
        smStoreMapper.editStoreStatus(baseUpdateStatusDto);

        //对应的店铺账号也需要修改状态   店铺启用->账号正常  店铺禁用->账号拉黑
        UpdateWrapper<SysUserPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("store_id", Arrays.asList(baseUpdateStatusDto.getId()));
        SysUserPo sysUserPo = new SysUserPo();
        Integer status ;
        if (baseUpdateStatusDto.getEnabled().equals(true)) {
            status = 0;
        } else {
            status = -1;
        }
        sysUserPo.setStatus(status);
        sysUserMapper.update(sysUserPo, updateWrapper);

        return new JsonViewData(ResultCode.SUCCESS, "修改经营状态成功");
    }

    /**
     * 条件查询
     * @param storeSearchDto
     * @return
     */
    @Override
    public PageInfo<SmStoreBaseVo> searchBaseInfo(StoreSearchDto storeSearchDto) {

        Integer pageNo = storeSearchDto.getPageNo()==null ? defaultPageNo : storeSearchDto.getPageNo();
        Integer pageSize = storeSearchDto.getPageSize()==null ? defaultPageSize : storeSearchDto.getPageSize();

        PageInfo<SmStoreBaseVo> smStoreBaseVoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> smStoreMapper.searchBaseInfo(storeSearchDto));
        return smStoreBaseVoPageInfo;
    }


    /**
     * 查询店铺基本信息
     * @param id
     * @return
     */
    @Override
    public StoreBaseInfoVo findBaseById(Long id) {
        if(null == id) {
            SysUserPo sysUserPo = securityUtil.getCurrUser();
            if(null == sysUserPo.getStoreId()) {
                throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
            }
            id = sysUserPo.getStoreId();
        }
        StoreBaseInfoVo storeBaseInfoVo = smStoreMapper.findBaseById(id);
        /*String attributeIds = storeBaseInfoVo.getAttributeIds();
        QueryWrapper<PmGoodsAttributePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",attributeIds);
        queryWrapper.list("name");
        List<String> pmGoodsAttributeList = (List<String>)(List)pmGoodsAttributeMapper.selectObjs(queryWrapper);
        storeBaseInfoVo.setAttributeName(pmGoodsAttributeList);*/
        return storeBaseInfoVo;
    }

    /**
     * 查询店铺账户信息
     *
     * @param id
     * @return
     */
    @Override
    public StoreAccountInfoVo findAccountById(Long id) {
        if(null == id) {
            SysUserPo sysUserPo = securityUtil.getCurrUser();
            if(null == sysUserPo.getStoreId()) {
                throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
            }
            id = sysUserPo.getStoreId();
        }
        return smStoreMapper.findAccountById(id);
    }

    /**
     * 查询店铺运营信息
     *
     * @param id
     * @return
     */
    @Override
    public StoreOperationalInfoVo findOperationalById(Long id) {
        if(null == id) {
            SysUserPo sysUserPo = securityUtil.getCurrUser();
            if(null == sysUserPo.getStoreId()) {
                throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
            }
            id = sysUserPo.getStoreId();
        }
        return smStoreMapper.findOperationalById(id);
    }

    /**
     * 根据账号获取店铺id
     *
     * @param userName
     * @return
     */
    @Override
    public Long findStoreIdByName(String userName) {
       return smStoreMapper.findStoreIdByName(userName);
    }


    /**
     * 商家上传经营资质
     *
     * @param storeBusinessLicenseDto
     * @return
     */
    @Override
    public void uploadBusinessLicense( StoreBusinessLicenseDto storeBusinessLicenseDto) {
        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            //当前登录用户跟操作不匹配
            throw  new ServiceException(ResultCode.FAIL, "操作失败，当前登录用户跟操作不匹配");
        }
        SmStorePo smStorePo = new SmStorePo();
        smStorePo.setBusinessLicense(storeBusinessLicenseDto.getImgUrl());
        smStoreMapper.update(smStorePo,
                new UpdateWrapper<SmStorePo>().lambda().eq(SmStorePo::getId,sysUserPo.getStoreId()));
    }

    /**
     * 条件查询可关联店铺
     *
     * @param storeSearchByConditionDto
     * @return
     */
    @Override
    public PageInfo<RelStoreInfoVo> searchRelStoreInfo(StoreSearchByConditionDto storeSearchByConditionDto) {

        Integer pageNo = storeSearchByConditionDto.getPageNo()==null ? defaultPageNo : storeSearchByConditionDto.getPageNo();
        Integer pageSize = storeSearchByConditionDto.getPageSize()==null ? defaultPageSize : storeSearchByConditionDto.getPageSize();

        PageInfo<RelStoreInfoVo> relStoreInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> smStoreMapper.searchRelStoreInfo(storeSearchByConditionDto));
        return relStoreInfoVoPageInfo;
    }

    /**
     * 用户关注店铺
     *
     * @param storeId 店铺id
     * @param userId  用户id
     * @return
     */
    @Override
    public void userFocusStore(Long storeId, Long userId) {
        SmRelUserFocusStorePo smRelUserFocusStorePo = new SmRelUserFocusStorePo();
        smRelUserFocusStorePo.setStoreId(storeId);
        smRelUserFocusStorePo.setUserId(userId);
        smRelUserFocusStoreMapper.insert(smRelUserFocusStorePo);
    }

    /**
     * 店铺解除绑定
     *
     * @return
     */
    @Override
    public void storeUnbound(Long id) {
        SmStoreRelStorePo smStoreRelStorePo = smStoreRelStoreMapper.selectById(id);
        if(null != smStoreRelStorePo) {
            smStoreRelStoreMapper.deleteById(id);
        } else {
            throw new ServiceException(ResultCode.NO_EXISTS, "绑定的关系不存在");
        }
    }


    /**
     * 获取当前店铺的下级店铺(分店)（模糊搜索）
     *
     * @param storeName
     * @return
     */
    @Override
    public List<BranchInfoVo> searchBranchByName(String storeName) {
        //获取当前用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null == storeId) {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }
        return smStoreMapper.searchBranchByName(storeId, storeName);
    }

    /**
     * app查询店铺列表
     *
     * @return
     */
    @Override
    public PageInfo<StorePagingVo> searchPaging(SearchStoreDto searchStoreDto) {

        Integer pageNo = searchStoreDto.getPageNo()==null ? defaultPageNo : searchStoreDto.getPageNo();
        Integer pageSize = searchStoreDto.getPageSize()==null ? defaultPageSize : searchStoreDto.getPageSize();
        if(null == searchStoreDto.getGoodsNum()) {
            searchStoreDto.setGoodsNum(DEFAULT_SHOW_GOODS_NUM);
        }

        PageInfo<StorePagingVo> storePagingVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> smStoreMapper.searchPaging(searchStoreDto));
        return storePagingVoPageInfo;

    }

    /**
     * @Author yeJH
     * @Date 2019/9/20 16:43
     * @Description 搜索界面-搜索店铺列表
     *
     * @Update yeJH
     *
     * @Param [searchStoreDto]
     * 排序字段（sortFile ）：  COMPREHENSIVE_SORT：综合排序
     *                          SALES_SORT：销量排序
     *                          COLLECTION_NUM：人气
     * 排序方式（sortWay ）：   DESC：降序  ASC：升序
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.store.StorePagingVo>
     **/
    @Override
    public PageInfo<StoreCategoryDetailVo> searchStoreBaseList(SearchStoreDto searchStoreDto) {

        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        searchStoreDto.setUserId(umUserPo.getId());

        Integer pageNo = searchStoreDto.getPageNo()==null ? defaultPageNo : searchStoreDto.getPageNo();
        Integer pageSize = searchStoreDto.getPageSize()==null ? defaultPageSize : searchStoreDto.getPageSize();

        if(null == searchStoreDto.getSortFile()) {
            //默认综合排序
            searchStoreDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if(null == searchStoreDto.getSortWay()) {
            //默认降序
            searchStoreDto.setSortWay(SortWayEnum.DESC);
        }
        searchStoreDto.setGoodsNum(ServiceConstant.TEAM_WORK_LEVEL);

        PageInfo<StoreCategoryDetailVo> goodsBaseInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> smStoreMapper.searchStoreBaseList(searchStoreDto));
        return goodsBaseInfoVoPageInfo;
    }

    /**
     * app获取店铺信息
     *
     * @param storeId
     * @return
     */
    @Override
    public StorePagingVo findById(Long storeId) {
        SmStorePo smStorePo = smStoreMapper.selectById(storeId);
        if(null == smStorePo) {
            throw new ServiceException(ResultCode.NO_EXISTS,"店铺不存在");
        } else if(smStorePo.getEnabled().equals(false)) {
            throw new ServiceException(ResultCode.PARAM_ERROR,"店铺已被禁用");
        }

        return smStoreMapper.findStoreById(storeId);
    }

    /**
     * 获取店铺下商品分类信息
     *
     * @return
     */
    @Override
    public List<SearchCategoryVo> findGoodsCategory(FindStoreCategoryDto findStoreCategoryDto) {
        List<SearchCategoryVo> searchCategoryVoList = new ArrayList<>();
        if(null == findStoreCategoryDto.getGoodsCategoryId()) {
            //根据店铺id获取该店铺下一级分类
            searchCategoryVoList = pmGoodsCategoryMapper.findFirstCategoryByStoreId(findStoreCategoryDto);
        } else {
            PmGoodsCategoryPo pmGoodsCategoryPo = pmGoodsCategoryMapper.selectById(findStoreCategoryDto.getGoodsCategoryId());
            if (pmGoodsCategoryPo.getLevel().equals(GoodsCategoryLevelEnum.FIRST_CATEGORY.getLevel())) {
                if(null == findStoreCategoryDto.getGoodsCategoryId())  {
                    throw new ServiceException(ResultCode.PARAM_ERROR,"分类id不能为空");
                }
                //根据店铺id获取该店铺一级分类下的二级分类
                searchCategoryVoList = pmGoodsCategoryMapper.findSecondCategoryByStoreId(findStoreCategoryDto);
            } else if (pmGoodsCategoryPo.getLevel().equals(GoodsCategoryLevelEnum.SECOND_CATEGORY.getLevel())) {
                if(null == findStoreCategoryDto.getGoodsCategoryId())  {
                    throw new ServiceException(ResultCode.PARAM_ERROR,"分类id不能为空");
                }
                //根据店铺id获取该店铺二级分类下的下三级分类
                searchCategoryVoList = pmGoodsCategoryMapper.findThirdCategoryByStoreId(findStoreCategoryDto);
            }
        }
        return searchCategoryVoList;
    }

    /**
     * app获取店铺详情
     *
     * @return
     */
    @Override
    public StoreDetailVo findDetailById(Long storeId) {
        SmStorePo smStorePo = smStoreMapper.selectById(storeId);
        if(null == smStorePo) {
            throw new ServiceException(ResultCode.NO_EXISTS,"店铺不存在");
        } else if(smStorePo.getEnabled().equals(false)) {
            throw new ServiceException(ResultCode.PARAM_ERROR,"店铺已被禁用");
        }
        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();

        StoreDetailVo storeDetailVo = smStoreMapper.findDetailById(storeId, umUserPo.getId());
        if (Strings.isNotBlank(storeDetailVo.getStoreLabels())){
            storeDetailVo.setStoreLabelList(Splitter.on(",")
                    .omitEmptyStrings().splitToList(storeDetailVo.getStoreLabels()));
        }
        if(Strings.isNotBlank(storeDetailVo.getBusinessLicense())) {
            storeDetailVo.setBusinessLicenseList(Arrays.asList(storeDetailVo.getBusinessLicense().split(",")));
        }
        return storeDetailVo;
    }

    /**
     * @Author yeJH
     * @Date 2019/9/20 19:43
     * @Description 获取筛选店铺的参数  店铺分类  店铺标签
      *
     * @Update yeJH
     *
     * @Param [searchStoreDto]
     * @return com.chauncy.data.vo.app.component.ScreenParamVo
     **/
    @Override
    public ScreenParamVo findScreenStoreParam(FindStoreParamDto findStoreParamDto) {

        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        findStoreParamDto.setUserId(umUserPo.getId());

        ScreenParamVo screenParamVo = new ScreenParamVo();
        ScreenStoreParamVo screenStoreParamVo = smStoreMapper.findScreenStoreParam(findStoreParamDto);
        screenParamVo.setScreenStoreParamVo(screenStoreParamVo);
        return screenParamVo;
    }


    /**
     * 获取店铺首页-店铺详情信息
     *
     * @param storeId
     * @return
     */
    @Override
    public StoreHomePageVo getStoreHomePage(Long storeId) {
        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();

        SmStorePo smStorePo = smStoreMapper.getEnabledStoreById(storeId);
        if(null == smStorePo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "店铺不存在");
        }
        StoreHomePageVo storeHomePageVo = smStoreMapper.getStoreHomePage(storeId, umUserPo.getId());
        if (Strings.isNotBlank(storeHomePageVo.getStoreLabels())){
            storeHomePageVo.setStoreLabelList(Splitter.on(",")
                    .omitEmptyStrings().splitToList(storeHomePageVo.getStoreLabels()));
        }
        return storeHomePageVo;
    }
}
