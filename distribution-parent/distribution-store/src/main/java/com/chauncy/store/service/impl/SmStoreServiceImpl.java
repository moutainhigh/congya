package com.chauncy.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.dto.store.StoreAccountInfoDto;
import com.chauncy.data.dto.store.StoreBaseInfoDto;
import com.chauncy.data.dto.store.StoreSearchDto;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.store.SmStoreBaseVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.service.ISmStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    /**
     * 保存店铺基本信息
     * @param storeBaseInfoDto
     * @return
     */
    @Override
    public JsonViewData saveStore(StoreBaseInfoDto storeBaseInfoDto) {

        //todo  添加店铺后台账号


        SmStorePo smStorePo = new SmStorePo();
        BeanUtils.copyProperties(smStorePo, storeBaseInfoDto);
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStorePo.setCreateBy(user);
        //店铺信息插入
        smStoreMapper.insert(smStorePo);

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
     * @param ids 店铺ID
     * @param enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    @Override
    public JsonViewData editStoreStatus(Long[] ids, Boolean enabled) {
        smStoreMapper.editStoreStatus(ids, enabled);
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

}
