package com.chauncy.store.label.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.label.SmStoreLabelPo;
import com.chauncy.data.dto.manage.store.add.StoreLabelDto;
import com.chauncy.data.dto.manage.store.select.StoreLabelSearchDto;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.store.label.SmStoreLabelMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.SmStoreBaseVo;
import com.chauncy.data.vo.manage.store.label.SmStoreLabelVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.label.service.ISmStoreLabelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author yeJH
 * @since 2019/6/15 19:26
 */
@Service
@Transactional
public class SmStoreLabelServiceImpl extends AbstractService<SmStoreLabelMapper, SmStoreLabelPo> implements ISmStoreLabelService {

    @Autowired
    private SmStoreLabelMapper smStoreLabelMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private SecurityUtil securityUtil;
    /**
     * 保存店铺标签信息
     * @param storeLabelDto
     * @return
     */
    @Override
    public SmStoreLabelPo saveStoreLabel(StoreLabelDto storeLabelDto) {

        QueryWrapper<SmStoreLabelPo> smStoreLabelPoQueryWrapper = new QueryWrapper<>();
        SmStoreLabelPo queryWarpper = new SmStoreLabelPo();
        queryWarpper.setName(storeLabelDto.getName());
        smStoreLabelPoQueryWrapper.setEntity(queryWarpper);
        SmStoreLabelPo smStoreLabelPo = this.getOne(smStoreLabelPoQueryWrapper);

        if(null != smStoreLabelPo) {
            throw  new ServiceException(ResultCode.DUPLICATION, "标签名称重复");
        }

        smStoreLabelPo = new SmStoreLabelPo();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStoreLabelPo.setUpdateBy(user);
        BeanUtils.copyProperties(storeLabelDto, smStoreLabelPo);
        smStoreLabelPo.setId(null);
        smStoreLabelMapper.insert(smStoreLabelPo);
        return smStoreLabelPo;
    }

    /**
     * 编辑店铺标签信息
     * @param storeLabelDto
     * @return
     */
    @Override
    public SmStoreLabelPo editStoreLabel(StoreLabelDto storeLabelDto) {
        SmStoreLabelPo oldLabel = smStoreLabelMapper.selectById(storeLabelDto.getId());

        QueryWrapper<SmStoreLabelPo> smStoreLabelPoQueryWrapper = new QueryWrapper<>();
        SmStoreLabelPo queryWarpper = new SmStoreLabelPo();
        queryWarpper.setName(storeLabelDto.getName());
        smStoreLabelPoQueryWrapper.setEntity(queryWarpper);
        SmStoreLabelPo smStoreLabelPo = this.getOne(smStoreLabelPoQueryWrapper);

        if(null != smStoreLabelPo && !smStoreLabelPo.getName().equals(oldLabel.getName())) {
            throw new ServiceException(ResultCode.DUPLICATION, "标签名称重复");
        }

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        oldLabel.setUpdateBy(user);
        BeanUtils.copyProperties(storeLabelDto, oldLabel);
        smStoreLabelMapper.updateById(oldLabel);
        return smStoreLabelPo;
    }

    /**
     * 根据ID查找店铺标签
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object>  findById(Long id) {
        QueryWrapper<SmStoreLabelPo> smStoreLabelPoQueryWrapper = new QueryWrapper<>();
        SmStoreLabelPo queryWarpper = new SmStoreLabelPo();
        queryWarpper.setId(id);
        smStoreLabelPoQueryWrapper.setEntity(queryWarpper);
        smStoreLabelPoQueryWrapper.select("id", "name", "remark", "create_time");
        Map<String, Object> map = this.getMap(smStoreLabelPoQueryWrapper);
        return map;
    }


    /**
     * 条件查询
     * @param storeLabelSearchDto
     * @return
     */
    @Override
    public PageInfo<SmStoreLabelVo> searchPaging(StoreLabelSearchDto storeLabelSearchDto) {

        Integer pageNo = storeLabelSearchDto.getPageNo()==null ? defaultPageNo : storeLabelSearchDto.getPageNo();
        Integer pageSize = storeLabelSearchDto.getPageSize()==null ? defaultPageSize : storeLabelSearchDto.getPageSize();
        String pageSoft = " create_time desc";

        PageInfo<SmStoreLabelVo> smStoreLabelVoPageInfo = PageHelper.startPage(pageNo, pageSize, pageSoft)
                .doSelectPageInfo(() -> smStoreLabelMapper.searchPaging(storeLabelSearchDto));
        return smStoreLabelVoPageInfo;
    }

    /**
     * 查询店铺所有标签
     * @return
     */
    @Override
    public List<SmStoreLabelVo> selectAll() {

        return smStoreLabelMapper.selectAll();

    }


    /**
     * 批量删除标签
     * @param ids
     */
    @Override
    public void delStoreLabelByIds(Long[] ids) {
        for (Long id :ids) {
            QueryWrapper<SmStorePo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_label_id",id);
            List<SmStorePo> smStorePoList = smStoreMapper.selectList(queryWrapper);
            if(null != smStorePoList & smStorePoList.size() > 0 ) {
               throw new ServiceException(ResultCode.FAIL, "删除失败，包含正被店铺使用关联的属性");
            }
        }
        for (Long id :ids) {
            smStoreLabelMapper.deleteById(id);
        }

    }
}
