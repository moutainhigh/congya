package com.chauncy.message.information.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationCategoryDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.category.MmInformationCategoryMapper;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.message.information.category.service.IMmInformationCategoryService;
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
public class MmInformationCategoryServiceImpl extends AbstractService<MmInformationCategoryMapper, MmInformationCategoryPo> implements IMmInformationCategoryService {

    @Autowired
    private MmInformationCategoryMapper mmInformationCategoryMapper;

    @Autowired
    private MmInformationMapper mmInformationMapper;

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
        mmInformationCategoryMapper.insert(mmInformationCategoryPo);
    }
    
    /**
     * 编辑店铺资讯分类信息
     *
     * @param informationCategoryDto
     */
    @Override
    public void editInformationCategory(InformationCategoryDto informationCategoryDto) {
        MmInformationCategoryPo mmInformationCategoryPo = mmInformationCategoryMapper.selectById(informationCategoryDto.getId());

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
        mmInformationCategoryMapper.updateById(mmInformationCategoryPo);
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
        MmInformationCategoryPo informationCategoryPo = mmInformationCategoryMapper.selectById(id);
        BeanUtils.copyProperties(informationCategoryPo , informationCategoryVo);
        return informationCategoryVo;
    }


    /**
     * 根据分类ID、分类名称查询
     *
     * @return
     */
    @Override
    public PageInfo<InformationCategoryVo> searchPaging(BaseSearchDto baseSearchDto) {

        Integer pageNo = baseSearchDto.getPageNo()==null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize()==null ? defaultPageSize : baseSearchDto.getPageSize();

        PageInfo<InformationCategoryVo> informationCategoryVoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> mmInformationCategoryMapper.searchPaging(baseSearchDto));
        return informationCategoryVoPageInfo;
    }


    /**
     * 查询店铺资讯所有分类
     *
     * @return
     */
    @Override
    public List<InformationCategoryVo> selectAll() {
        return mmInformationCategoryMapper.selectAll();
    }

    /**
     * 批量禁用启用
     *
     * @param baseUpdateStatusDto
     */
    @Override
    public void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto) {
        UpdateWrapper<MmInformationCategoryPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", baseUpdateStatusDto.getId());
        MmInformationCategoryPo mmInformationCategoryPo = new MmInformationCategoryPo();
        mmInformationCategoryPo.setEnabled(baseUpdateStatusDto.getEnabled());
        mmInformationCategoryMapper.update(mmInformationCategoryPo, updateWrapper);
    }


    /**
     * 批量删除分类
     * @param ids
     */
    @Override
    public void delInformationCategoryByIds(Long[] ids) {
        for (Long id :ids) {
            QueryWrapper<MmInformationPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("info_category_id",id);
            Integer count = mmInformationMapper.selectCount(queryWrapper);
            if(count > 0 ) {
                throw new ServiceException(ResultCode.FAIL, "删除失败，包含正被店铺资讯使用关联的分类");
            }
        }
        //批量删除分类
        mmInformationCategoryMapper.deleteBatchIds(Arrays.asList(ids));

    }

}
