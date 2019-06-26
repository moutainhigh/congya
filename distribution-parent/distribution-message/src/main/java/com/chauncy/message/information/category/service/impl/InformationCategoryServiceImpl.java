package com.chauncy.message.information.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.dto.manage.message.information.add.InformationCategoryDto;
import com.chauncy.data.mapper.message.information.category.InformationCategoryMapper;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.message.information.category.service.IInformationCategoryService;
import com.chauncy.data.core.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Service
public class InformationCategoryServiceImpl extends AbstractService<InformationCategoryMapper, MmInformationCategoryPo> implements IInformationCategoryService {

    @Autowired
    private InformationCategoryMapper informationCategoryMapper;

    @Autowired
    private SecurityUtil securityUtil;
    /**
     * 保存店铺资讯分类信息
     *
     * @param informationCategoryDto
     */
    @Override
    public void saveInformationCategory(InformationCategoryDto informationCategoryDto) {
        QueryWrapper<MmInformationCategoryPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationCategoryDto.getName());
        if (null != this.getOne(queryWrapper)) {
            throw new ServiceException(ResultCode.DUPLICATION, "分类名称重复");
        }

        MmInformationCategoryPo mmInformationCategoryPo = new MmInformationCategoryPo();
        BeanUtils.copyProperties(informationCategoryDto, mmInformationCategoryPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        mmInformationCategoryPo.setCreateBy(userName);
        mmInformationCategoryPo.setId(null);
        informationCategoryMapper.insert(mmInformationCategoryPo);
    }
    
    /**
     * 编辑店铺资讯分类信息
     *
     * @param informationCategoryDto
     */
    @Override
    public void editInformationLabel(InformationCategoryDto informationCategoryDto) {
        MmInformationCategoryPo mmInformationCategoryPo = informationCategoryMapper.selectById(informationCategoryDto.getId());

        QueryWrapper<MmInformationCategoryPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationCategoryDto.getName());
        MmInformationCategoryPo oldCategoryPo = this.getOne(queryWrapper);
        if (null != oldCategoryPo && !oldCategoryPo.getName().equals(informationCategoryDto.getName())) {
            throw new ServiceException(ResultCode.DUPLICATION, "资讯分类名称重复");
        }

        BeanUtils.copyProperties(informationCategoryDto, mmInformationCategoryPo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        mmInformationCategoryPo.setUpdateBy(userName);
        informationCategoryMapper.updateById(mmInformationCategoryPo);
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
        MmInformationCategoryPo informationCategoryPo = informationCategoryMapper.selectById(id);
        BeanUtils.copyProperties(informationCategoryPo , informationCategoryVo);
        return informationCategoryVo;
    }



}
