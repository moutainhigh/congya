package com.chauncy.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.store.StoreTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.system.SysRoleTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.label.SmStoreLabelPo;
import com.chauncy.data.domain.po.store.rel.SmRelStoreAttributePo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreAccountInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.mapper.store.SmRelStoreAttributeMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.SysRoleMapper;
import com.chauncy.data.mapper.sys.SysRoleUserMapper;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.SmStoreBaseVo;
import com.chauncy.data.vo.manage.store.StoreAccountInfoVo;
import com.chauncy.data.vo.manage.store.StoreBaseInfoVo;
import com.chauncy.data.vo.manage.store.StoreOperationalInfoVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.rel.service.ISmRelStoreAttributeService;
import com.chauncy.store.service.ISmStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
@Service
@Transactional
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
    private PmGoodsAttributeMapper pmGoodsAttributeMapper;
    @Autowired
    private ISmRelStoreAttributeService smRelStoreAttributeService;

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
        //店铺品牌关联插入
        List<SmRelStoreAttributePo> smRelStoreAttributePoList = new ArrayList<>();
        for(Long attributeId : storeBaseInfoDto.getAttributeIds()) {
            SmRelStoreAttributePo smRelStoreAttributePo = new SmRelStoreAttributePo();
            smRelStoreAttributePo.setAttributeId(attributeId);
            smRelStoreAttributePo.setCreateBy(userName);
            smRelStoreAttributePo.setStoreId(smStorePo.getId());
            smRelStoreAttributePoList.add(smRelStoreAttributePo);
        }
        smRelStoreAttributeService.saveBatch(smRelStoreAttributePoList);


        //添加店铺后台账号
        createSysUser(smStorePo);

        return new JsonViewData(ResultCode.SUCCESS, "添加成功", smStorePo);
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
        sysUserPo.setPassword(DEFAULT_PASSWORD);
        sysUserPo.setType(SecurityConstant.USER_TYPE_ADMIN);
        sysUserPo.setSystemType(SecurityConstant.SYS_TYPE_SUPPLIER);
        sysUserPo.setStoreId(smStorePo.getId());
        sysUserPo.setUsername(smStorePo.getUserName());
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
            //获取当前用户
            String user = securityUtil.getCurrUser().getUsername();
            smStorePo.setUpdateBy(user);
            //店铺账户信息插入
            smStoreMapper.updateById(smStorePo);
            return new JsonViewData(ResultCode.SUCCESS, "添加成功", smStorePo);
        } else {
            return new JsonViewData(ResultCode.NO_EXISTS, "店铺不存在");
        }

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
        String attributeIds = storeBaseInfoVo.getAttributeIds();
        QueryWrapper<PmGoodsAttributePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",attributeIds);
        queryWrapper.select("name");
        List<String> pmGoodsAttributeList = (List<String>)(List)pmGoodsAttributeMapper.selectObjs(queryWrapper);
        storeBaseInfoVo.setAttributeName(pmGoodsAttributeList);
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

}
