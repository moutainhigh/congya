package com.chauncy.message.information.label.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.message.information.label.MmInformationLabelPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationLabelDto;
import com.chauncy.data.dto.manage.message.information.select.InformationLabelSearchDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.label.MmInformationLabelMapper;
import com.chauncy.data.vo.manage.message.information.label.InformationLabelVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.message.information.label.service.IMmInformationLabelService;
import com.chauncy.data.core.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInformationLabelServiceImpl extends AbstractService<MmInformationLabelMapper, MmInformationLabelPo>
        implements IMmInformationLabelService {

    @Autowired
    private MmInformationLabelMapper mmInformationLabelMapper;
    @Autowired
    private MmInformationMapper mmInformationMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 保存店铺资讯标签信息
     *
     * @param informationLabelDto
     */
    @Override
    public void saveInformationLabel(InformationLabelDto informationLabelDto) {
        QueryWrapper<MmInformationLabelPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationLabelDto.getName());
        if (null != this.getOne(queryWrapper)) {
            throw new ServiceException(ResultCode.DUPLICATION, "标签名称重复");
        }

        MmInformationLabelPo mmInformationLabelPo = new MmInformationLabelPo();
        BeanUtils.copyProperties(informationLabelDto, mmInformationLabelPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        mmInformationLabelPo.setCreateBy(userName);
        mmInformationLabelPo.setId(null);
        mmInformationLabelMapper.insert(mmInformationLabelPo);
    }

    /**
     * 编辑店铺资讯标签信息
     *
     * @param informationLabelDto
     */
    @Override
    public void editInformationLabel(InformationLabelDto informationLabelDto) {
        MmInformationLabelPo mmInformationLabelPo = mmInformationLabelMapper.selectById(informationLabelDto.getId());

        QueryWrapper<MmInformationLabelPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationLabelDto.getName());
        MmInformationLabelPo oldLabelPo = this.getOne(queryWrapper);
        if (null != oldLabelPo && !oldLabelPo.getName().equals(mmInformationLabelPo.getName())) {
            throw new ServiceException(ResultCode.DUPLICATION, "资讯标签名称重复");
        }

        BeanUtils.copyProperties(informationLabelDto, mmInformationLabelPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        mmInformationLabelPo.setUpdateBy(userName);
        mmInformationLabelMapper.updateById(mmInformationLabelPo);
    }


    /**
     * 根据ID查找店铺资讯标签
     *
     * @param id
     * @return
     */
    @Override
    public InformationLabelVo findById(Long id) {
        InformationLabelVo informationLabelVo = new InformationLabelVo();
        MmInformationLabelPo mmInformationLabelPo = mmInformationLabelMapper.selectById(id);
        BeanUtils.copyProperties(mmInformationLabelPo, informationLabelVo);
        return informationLabelVo;
    }

    /**
     * 根据标签ID、标签名称查询
     *
     * @return
     */
    @Override
    public PageInfo<InformationLabelVo> searchPaging(InformationLabelSearchDto informationLabelSearchDto) {

        Integer pageNo = informationLabelSearchDto.getPageNo()==null ? defaultPageNo : informationLabelSearchDto.getPageNo();
        Integer pageSize = informationLabelSearchDto.getPageSize()==null ? defaultPageSize : informationLabelSearchDto.getPageSize();

        PageInfo<InformationLabelVo> informationLabelVoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> mmInformationLabelMapper.searchPaging(informationLabelSearchDto));
        return informationLabelVoPageInfo;
    }

    /**
     * 查询店铺资讯所有标签
     *
     * @return
     */
    @Override
    public List<InformationLabelVo> selectAll() {
        return mmInformationLabelMapper.selectAll();
    }


    /**
     * 批量禁用启用
     *
     * @param baseUpdateStatusDto
     */
    @Override
    public void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto) {
        UpdateWrapper<MmInformationLabelPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", Arrays.asList(baseUpdateStatusDto.getId()));
        MmInformationLabelPo mmInformationLabelPo = new MmInformationLabelPo();
        mmInformationLabelPo.setEnabled(baseUpdateStatusDto.getEnabled());
        mmInformationLabelMapper.update(mmInformationLabelPo, updateWrapper);
    }

    /**
     * 批量删除标签
     * @param ids
     */
    @Override
    public void delInformationLabelByIds(Long[] ids) {
        for (Long id :ids) {
            QueryWrapper<MmInformationPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("info_label_id",id);
            Integer count = mmInformationMapper.selectCount(queryWrapper);
            if(count > 0 ) {
                throw new ServiceException(ResultCode.FAIL, "删除失败，包含正被店铺资讯使用关联的标签");
            }
        }
        //批量删除标签
        mmInformationLabelMapper.deleteBatchIds(Arrays.asList(ids));

    }

}
