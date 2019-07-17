package com.chauncy.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.goods.GoodsCategoryLevelEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.system.SysRoleTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.rel.SmRelStoreAttributePo;
import com.chauncy.data.domain.po.store.rel.SmRelUserFocusStorePo;
import com.chauncy.data.domain.po.store.rel.SmStoreRelStorePo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.app.store.FindStoreCategoryDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreAccountInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreRelStoreDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchByConditionDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.dto.supplier.store.update.StoreBusinessLicenseDto;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.store.rel.SmRelStoreAttributeMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.store.rel.SmRelUserFocusStoreMapper;
import com.chauncy.data.mapper.store.rel.SmStoreRelStoreMapper;
import com.chauncy.data.mapper.sys.SysRoleMapper;
import com.chauncy.data.mapper.sys.SysRoleUserMapper;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.app.store.StorePagingVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.data.vo.manage.store.*;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.rel.service.ISmRelStoreAttributeService;
import com.chauncy.store.rel.service.ISmStoreRelStoreService;
import com.chauncy.store.service.ISmStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        //添加店铺后台账号
        createSysUser(smStorePo);

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
        //查询新更改的品牌中缺少的已有品牌是否有关联的商品  如果有则编辑失败
        List<Long> oldAttributeIds = smStoreMapper.selectAttributeIdsById(storeBaseInfoDto.getId());
        List<Long> newAttributeIds = Arrays.asList(storeBaseInfoDto.getAttributeIds());
        //oldAttributeIds 与 newAttributeIds的差集
        List<Long> reduceList = oldAttributeIds.stream().filter(item -> !newAttributeIds.contains(item)).collect(toList());
        if(null != reduceList && reduceList.size() > 0 ) {
            throw  new ServiceException(ResultCode.PARAM_ERROR, "修改失败，包含正被使用的关联的品牌");
        }


        //绑定店铺关系
        bindingStore(oldSmStore.getId(), storeBaseInfoDto.getStoreRelStoreDtoList());

        //将店铺与品牌关联表的记录删除
        Map<String, Object> map = new HashMap<>();
        map.put("store_id", storeBaseInfoDto.getId());
        smRelStoreAttributeMapper.deleteByMap(map);

        //批量插入店铺品牌关联记录
        saveBatchRelStoreAttribute(storeBaseInfoDto, userName);

        return new JsonViewData(ResultCode.SUCCESS, "编辑成功");
    }

    /**
     * 批量插入店铺品牌关联记录
     * @param storeBaseInfoDto
     * @param userName
     */
    private void saveBatchRelStoreAttribute (StoreBaseInfoDto storeBaseInfoDto, String userName) {
        List<SmRelStoreAttributePo> smRelStoreAttributePoList = new ArrayList<>();
        for(Long attributeId : storeBaseInfoDto.getAttributeIds()) {
            SmRelStoreAttributePo smRelStoreAttributePo = new SmRelStoreAttributePo();
            smRelStoreAttributePo.setAttributeId(attributeId);
            smRelStoreAttributePo.setCreateBy(userName);
            smRelStoreAttributePo.setStoreId(storeBaseInfoDto.getId());
            smRelStoreAttributePoList.add(smRelStoreAttributePo);
        }
        smRelStoreAttributeService.saveBatch(smRelStoreAttributePoList);
    }

    /**
     * 绑定店铺关系
     * @param storeId
     * @param storeRelStoreDtoList
     */
    private void bindingStore(Long storeId, List<StoreRelStoreDto> storeRelStoreDtoList) {
        if(null == storeRelStoreDtoList) {
            return ;
        }
        //删除店铺关系
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_id", storeId);
        smStoreRelStoreMapper.delete(queryWrapper);

        List<SmStoreRelStorePo> smStoreRelStorePoList = new ArrayList<>();
        for(StoreRelStoreDto storeRelStoreDto : storeRelStoreDtoList) {
            SmStoreRelStorePo smStoreRelStorePo = new SmStoreRelStorePo();
            smStoreRelStorePo.setStoreId(storeId);
            smStoreRelStorePo.setParentId(storeRelStoreDto.getParentId());
            smStoreRelStorePo.setType(storeRelStoreDto.getType());
            QueryWrapper<SmStoreRelStorePo> relQueryWrapper = new QueryWrapper<>(smStoreRelStorePo);
            Integer count = smStoreRelStoreMapper.selectCount(relQueryWrapper);
            if(count > 0) {
                //关系已存在
            } else {
                //关系不存在
                smStoreRelStorePoList.add(smStoreRelStorePo);
            }
        }
        smStoreRelStoreService.saveBatch(smStoreRelStorePoList);
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
        StoreBaseInfoVo storeBaseInfoVo = smStoreMapper.findBaseById(id);
        /*String attributeIds = storeBaseInfoVo.getAttributeIds();
        QueryWrapper<PmGoodsAttributePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",attributeIds);
        queryWrapper.select("name");
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
    public StoreAccountInfoVo findAccountById(@Param("id") Long id) {

        return smStoreMapper.findAccountById(id);
    }

    /**
     * 查询店铺运营信息
     *
     * @param id
     * @return
     */
    @Override
    public StoreOperationalInfoVo findOperationalById(@Param("id") Long id) {
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
        SmStorePo smStorePo = new SmStorePo();
        smStorePo.setBusinessLicense(storeBusinessLicenseDto.getImgUrl());
        smStoreMapper.update(smStorePo, new UpdateWrapper<SmStorePo>().eq("id",storeBusinessLicenseDto.getId()));
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
     * 获取店铺下商品列表
     *
     * @return
     */
    @Override
    public PageInfo<GoodsBaseInfoVo> searchStoreGoodsPaging(FindStoreCategoryDto findStoreCategoryDto) {
        return null;
    }
}
