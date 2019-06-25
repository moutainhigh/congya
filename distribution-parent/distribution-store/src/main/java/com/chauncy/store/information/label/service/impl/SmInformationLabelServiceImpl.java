package com.chauncy.store.information.label.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.store.information.label.SmInformationLabelPo;
import com.chauncy.data.dto.manage.store.add.InformationLabelDto;
import com.chauncy.data.dto.manage.store.select.InformationLabelSearchDto;
import com.chauncy.data.mapper.store.information.label.SmInformationLabelMapper;
import com.chauncy.data.vo.manage.store.information.label.InformationLabelVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.information.label.service.ISmInformationLabelService;
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
public class SmInformationLabelServiceImpl extends AbstractService<SmInformationLabelMapper, SmInformationLabelPo>
        implements ISmInformationLabelService {

    @Autowired
    private SmInformationLabelMapper smInformationLabelMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 保存店铺资讯标签信息
     *
     * @param informationLabelDto
     */
    @Override
    public void saveInformationLabel(InformationLabelDto informationLabelDto) {
        QueryWrapper<SmInformationLabelPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationLabelDto.getName());
        if (null != this.getOne(queryWrapper)) {
            throw new ServiceException(ResultCode.DUPLICATION, "标签名称重复");
        }

        SmInformationLabelPo smInformationLabelPo = new SmInformationLabelPo();
        BeanUtils.copyProperties(informationLabelDto,smInformationLabelPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        smInformationLabelPo.setCreateBy(userName);
        smInformationLabelPo.setId(null);
        smInformationLabelMapper.insert(smInformationLabelPo);
    }

    /**
     * 编辑店铺资讯标签信息
     *
     * @param informationLabelDto
     */
    @Override
    public void editInformationLabel(InformationLabelDto informationLabelDto) {
        SmInformationLabelPo smInformationLabelPo = smInformationLabelMapper.selectById(informationLabelDto.getId());

        QueryWrapper<SmInformationLabelPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationLabelDto.getName());
        SmInformationLabelPo oldLabelPo = this.getOne(queryWrapper);
        if (null != oldLabelPo && !oldLabelPo.getName().equals(smInformationLabelPo.getName())) {
            throw new ServiceException(ResultCode.DUPLICATION, "资讯标签名称重复");
        }

        BeanUtils.copyProperties(informationLabelDto,smInformationLabelPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        smInformationLabelPo.setUpdateBy(userName);
        smInformationLabelMapper.updateById(smInformationLabelPo);
    }


    /**
     * 根据ID查找店铺标签
     *
     * @param id
     * @return
     */
    @Override
    public InformationLabelVo findById(Long id) {
        InformationLabelVo informationLabelVo = new InformationLabelVo();
        SmInformationLabelPo smInformationLabelPo = smInformationLabelMapper.selectById(id);
        BeanUtils.copyProperties(smInformationLabelPo , informationLabelVo);
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

        PageInfo<InformationLabelVo> smStoreLabelVoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> smInformationLabelMapper.searchPaging(informationLabelSearchDto));
        return smStoreLabelVoPageInfo;
    }

    /**
     * 查询店铺资讯所有标签
     *
     * @return
     */
    @Override
    public List<InformationLabelVo> selectAll() {
        return smInformationLabelMapper.selectAll();
    }


    /**
     * 批量删除标签
     * @param ids
     */
    @Override
    public void delInformationLabelByIds(Long[] ids) {
        for (Long id :ids) {
            QueryWrapper<SmInformationLabelPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("info_label_id",id);
            Integer count = smInformationLabelMapper.selectCount(queryWrapper);
            if(count > 0 ) {
                throw new ServiceException(ResultCode.FAIL, "删除失败，包含正被店铺资讯使用关联的属性");
            }
        }
        //批量删除标签
        smInformationLabelMapper.deleteBatchIds(Arrays.asList(ids));

    }

}
