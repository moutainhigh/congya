package com.chauncy.store.service.impl;

import com.chauncy.common.enums.ResultCode;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.store.SmStorePo;
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
     * 保存店铺信息
     * @param smStorePo
     * @return
     */
    public JsonViewData saveStore(SmStorePo smStorePo) {
        LocalDateTime date = LocalDateTime.now();
        smStorePo.setCreateTime(date);
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStorePo.setCreateBy(user);

        smStoreMapper.insertStore(smStorePo);

        return new JsonViewData(ResultCode.SUCCESS, "添加成功", smStorePo);
    }
}
