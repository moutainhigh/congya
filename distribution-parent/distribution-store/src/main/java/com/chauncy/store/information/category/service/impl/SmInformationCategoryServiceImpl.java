package com.chauncy.store.information.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.store.information.category.SmInformationCategoryPo;
import com.chauncy.data.domain.po.store.information.category.SmInformationCategoryPo;
import com.chauncy.data.domain.po.store.information.label.SmInformationLabelPo;
import com.chauncy.data.dto.manage.store.add.InformationCategoryDto;
import com.chauncy.data.dto.manage.store.add.InformationLabelDto;
import com.chauncy.data.mapper.store.information.category.SmInformationCategoryMapper;
import com.chauncy.data.vo.manage.store.information.category.InformationCategoryVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.information.category.service.ISmInformationCategoryService;
import com.chauncy.data.core.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
public class SmInformationCategoryServiceImpl extends AbstractService<SmInformationCategoryMapper, SmInformationCategoryPo> implements ISmInformationCategoryService {

    @Autowired
    private SmInformationCategoryMapper informationCategoryMapper;

    @Autowired
    private SecurityUtil securityUtil;
    /**
     * 保存店铺资讯分类信息
     *
     * @param informationCategoryDto
     */
    @Override
    public void saveInformationCategory(InformationCategoryDto informationCategoryDto) {
        QueryWrapper<SmInformationCategoryPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationCategoryDto.getName());
        if (null != this.getOne(queryWrapper)) {
            throw new ServiceException(ResultCode.DUPLICATION, "分类名称重复");
        }

        SmInformationCategoryPo smInformationCategoryPo = new SmInformationCategoryPo();
        BeanUtils.copyProperties(informationCategoryDto,smInformationCategoryPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        smInformationCategoryPo.setCreateBy(userName);
        smInformationCategoryPo.setId(null);
        informationCategoryMapper.insert(smInformationCategoryPo);
    }
    
    /**
     * 编辑店铺资讯分类信息
     *
     * @param informationCategoryDto
     */
    @Override
    public void editInformationLabel(InformationCategoryDto informationCategoryDto) {
        SmInformationCategoryPo smInformationCategoryPo = informationCategoryMapper.selectById(informationCategoryDto.getId());

        QueryWrapper<SmInformationCategoryPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationCategoryDto.getName());
        SmInformationCategoryPo oldCategoryPo = this.getOne(queryWrapper);
        if (null != oldCategoryPo && !oldCategoryPo.getName().equals(informationCategoryDto.getName())) {
            throw new ServiceException(ResultCode.DUPLICATION, "资讯分类名称重复");
        }

        BeanUtils.copyProperties(informationCategoryDto,smInformationCategoryPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        smInformationCategoryPo.setUpdateBy(userName);
        informationCategoryMapper.updateById(smInformationCategoryPo);
    }

    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    @Override
    public InformationCategoryVo findById(Long id) {
        InformationCategoryVo informationCategoryVo = new InformationCategoryVo();
        SmInformationCategoryPo informationCategoryPo = informationCategoryMapper.selectById(id);
        BeanUtils.copyProperties(informationCategoryPo , informationCategoryVo);
        return informationCategoryVo;
    }



}
