package com.chauncy.store.service.impl;

import com.chauncy.common.enums.store.StoreTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreAccountInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.mapper.store.SmRelStoreAttributeMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.SmStoreBaseVo;
import com.chauncy.data.vo.manage.store.StoreAccountInfoVo;
import com.chauncy.data.vo.manage.store.StoreBaseInfoVo;
import com.chauncy.data.vo.manage.store.StoreOperationalInfoVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.service.ISmStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    private SmRelStoreAttributeMapper smRelStoreAttributeMapper;

    /**
     * 保存店铺基本信息
     * @param storeBaseInfoDto
     * @return
     */
    @Override
    public JsonViewData saveStore(StoreBaseInfoDto storeBaseInfoDto) {

        //todo  添加店铺后台账号


        SmStorePo smStorePo = new SmStorePo();
        BeanUtils.copyProperties(storeBaseInfoDto, smStorePo);
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStorePo.setCreateBy(user);
        StoreTypeEnum storeTypeEnum = StoreTypeEnum.getStoreTypeById(storeBaseInfoDto.getType());
        smStorePo.setType(storeTypeEnum.getTypeName());
        //店铺信息插入
        smStorePo.setId(null);
        smStoreMapper.insert(smStorePo);
        //店铺品牌关联插入
        smRelStoreAttributeMapper.insertByBatch(storeBaseInfoDto.getAttributeIds(), smStorePo.getId(), user);

        return new JsonViewData(ResultCode.SUCCESS, "添加成功", smStorePo);
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
        return null;
    }

    /**
     * 查询店铺账户信息
     *
     * @param id
     * @return
     */
    @Override
    public StoreAccountInfoVo findAccountById(@Param("id") Long id) {
        return null;
    }

    /**
     * 查询店铺运营信息
     *
     * @param id
     * @return
     */
    @Override
    public StoreOperationalInfoVo findOperationalById(@Param("id") Long id) {
        return null;
    }


}
