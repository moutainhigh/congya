package com.chauncy.store.label.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.store.label.SmStoreLabelPo;
import com.chauncy.data.dto.manage.store.add.StoreLabelDto;
import com.chauncy.data.dto.manage.store.select.StoreLabelSearchDto;
import com.chauncy.data.mapper.store.label.SmStoreLabelMapper;
import com.chauncy.data.vo.JsonViewData;
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
    private SecurityUtil securityUtil;
    /**
     * 保存店铺标签信息
     * @param storeLabelDto
     * @return
     */
    @Override
    public JsonViewData saveStoreLabel(StoreLabelDto storeLabelDto) {

        QueryWrapper<SmStoreLabelPo> smStoreLabelPoQueryWrapper = new QueryWrapper<>();
        SmStoreLabelPo queryWarpper = new SmStoreLabelPo();
        queryWarpper.setName(storeLabelDto.getName());
        smStoreLabelPoQueryWrapper.setEntity(queryWarpper);
        SmStoreLabelPo smStoreLabelPo = this.getOne(smStoreLabelPoQueryWrapper);

        if(null != smStoreLabelPo) {
            return new JsonViewData(ResultCode.DUPLICATION, "标签名称重复");
        }

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStoreLabelPo.setUpdateBy(user);
        BeanUtils.copyProperties(storeLabelDto, smStoreLabelPo);
        smStoreLabelMapper.insert(smStoreLabelPo);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功", smStoreLabelPo);
    }

    /**
     * 编辑店铺标签信息
     * @param storeLabelDto
     * @return
     */
    @Override
    public JsonViewData editStoreLabel(StoreLabelDto storeLabelDto) {
        SmStoreLabelPo oldLabel = smStoreLabelMapper.selectById(storeLabelDto.getId());

        QueryWrapper<SmStoreLabelPo> smStoreLabelPoQueryWrapper = new QueryWrapper<>();
        SmStoreLabelPo queryWarpper = new SmStoreLabelPo();
        queryWarpper.setName(storeLabelDto.getName());
        smStoreLabelPoQueryWrapper.setEntity(queryWarpper);
        SmStoreLabelPo smStoreLabelPo = this.getOne(smStoreLabelPoQueryWrapper);

        if(null != smStoreLabelPo && !smStoreLabelPo.getName().equals(oldLabel.getName())) {
            return new JsonViewData(ResultCode.DUPLICATION, "标签名称重复");
        }

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStoreLabelPo.setUpdateBy(user);
        BeanUtils.copyProperties(storeLabelDto, smStoreLabelPo);
        smStoreLabelMapper.updateById(smStoreLabelPo);
        return new JsonViewData(ResultCode.SUCCESS, "编辑成功", smStoreLabelPo);
    }

    /**
     * 根据ID查找店铺标签
     *
     * @param id
     * @return
     */
    @Override
    public JsonViewData findById(Long id) {
        QueryWrapper<SmStoreLabelPo> smStoreLabelPoQueryWrapper = new QueryWrapper<>();
        SmStoreLabelPo queryWarpper = new SmStoreLabelPo();
        queryWarpper.setId(id);
        smStoreLabelPoQueryWrapper.setEntity(queryWarpper);
        smStoreLabelPoQueryWrapper.select("id", "name", "remark", "create_time");
        Map<String, Object> map = this.getMap(smStoreLabelPoQueryWrapper);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功", map);
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
    public JsonViewData searchAll() {

        List<SmStoreLabelVo> smStoreLabelVoList = smStoreLabelMapper.searchAll();
        return new JsonViewData(ResultCode.SUCCESS, "查找成功", smStoreLabelVoList);

    }
}
