package com.chauncy.store.service.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.BeanUtils;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.dto.store.StoreAccountInfoDto;
import com.chauncy.data.dto.store.StoreBaseInfoDto;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.service.ISmStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        BeanUtils.copyBeanProp(smStorePo, storeBaseInfoDto);
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
}
