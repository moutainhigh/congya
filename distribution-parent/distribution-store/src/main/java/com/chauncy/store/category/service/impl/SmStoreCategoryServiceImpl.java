package com.chauncy.store.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.store.category.SmStoreCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreCategoryDto;
import com.chauncy.data.dto.manage.store.select.StoreCategorySearchDto;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.store.category.SmStoreCategoryMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.category.SmStoreCategoryVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.category.service.ISmStoreCategoryService;
import com.chauncy.data.core.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-16
 */
@Service
@Transactional
public class SmStoreCategoryServiceImpl extends AbstractService<SmStoreCategoryMapper, SmStoreCategoryPo> implements ISmStoreCategoryService {

    @Autowired
    private SmStoreCategoryMapper smStoreCategoryMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private SecurityUtil securityUtil;
    
    /**
     * 保存店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    @Override
    public SmStoreCategoryPo saveStoreCategory(StoreCategoryDto storeCategoryDto) {

        QueryWrapper<SmStoreCategoryPo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        SmStoreCategoryPo queryWarpper = new SmStoreCategoryPo();
        queryWarpper.setName(storeCategoryDto.getName());
        smStoreCategoryPoQueryWrapper.setEntity(queryWarpper);
        SmStoreCategoryPo smStoreCategoryPo = this.getOne(smStoreCategoryPoQueryWrapper);

        if(null != smStoreCategoryPo) {
            throw  new ServiceException(ResultCode.DUPLICATION, "分类名称重复");
        }

        smStoreCategoryPo = new SmStoreCategoryPo();
        BeanUtils.copyProperties(storeCategoryDto, smStoreCategoryPo);
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        smStoreCategoryPo.setUpdateBy(user);
        smStoreCategoryPo.setId(null);
        smStoreCategoryMapper.insert(smStoreCategoryPo);
        return smStoreCategoryPo;
    }



    /**
     * 编辑店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    @Override
    public SmStoreCategoryPo editStoreCategory(StoreCategoryDto storeCategoryDto) {
        SmStoreCategoryPo oldCategory = smStoreCategoryMapper.selectById(storeCategoryDto.getId());

        QueryWrapper<SmStoreCategoryPo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        SmStoreCategoryPo queryWarpper = new SmStoreCategoryPo();
        queryWarpper.setName(storeCategoryDto.getName());
        smStoreCategoryPoQueryWrapper.setEntity(queryWarpper);
        SmStoreCategoryPo smStoreCategoryPo = this.getOne(smStoreCategoryPoQueryWrapper);

        if(null != smStoreCategoryPo && !smStoreCategoryPo.getName().equals(oldCategory.getName())) {
            throw new ServiceException(ResultCode.DUPLICATION, "分类名称重复");
        }

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        oldCategory.setUpdateBy(user);
        BeanUtils.copyProperties(storeCategoryDto, oldCategory);
        smStoreCategoryMapper.updateById(oldCategory);
        return oldCategory;
    }


    /**
     * 修改店铺分类启用状态
     * @param baseUpdateStatusDto
     * ids 店铺分类ID
     * enabled 店铺分类启用状态修改 true 启用 false 禁用
     * @return
     */
    @Override
    public void editCategoryStatus(BaseUpdateStatusDto baseUpdateStatusDto) {
        smStoreCategoryMapper.editCategoryStatus(baseUpdateStatusDto);
    }

    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(Long id) {
        QueryWrapper<SmStoreCategoryPo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        SmStoreCategoryPo queryWarpper = new SmStoreCategoryPo();
        queryWarpper.setId(id);
        smStoreCategoryPoQueryWrapper.setEntity(queryWarpper);
        smStoreCategoryPoQueryWrapper.select("id", "name", "icon", "sort", "enabled");
        Map<String, Object> map = this.getMap(smStoreCategoryPoQueryWrapper);
        return map;
    }


    /**
     * 条件查询
     * @param storeCategorySearchDto
     * @return
     */
    @Override
    public PageInfo<SmStoreCategoryVo> searchPaging(StoreCategorySearchDto storeCategorySearchDto) {

        Integer pageNo = storeCategorySearchDto.getPageNo()==null ? defaultPageNo : storeCategorySearchDto.getPageNo();
        Integer pageSize = storeCategorySearchDto.getPageSize()==null ? defaultPageSize : storeCategorySearchDto.getPageSize();

        PageInfo<SmStoreCategoryVo> smStoreCategoryVoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> smStoreCategoryMapper.searchPaging(storeCategorySearchDto));
        return smStoreCategoryVoPageInfo;
    }

    /**
     * 查询店铺所有分类
     * @return
     */
    @Override
    public List<SmStoreCategoryVo> selectAll() {

        return smStoreCategoryMapper.selectAll();

    }


    /**
     * 批量删除分类
     * @param ids
     */
    @Override
    public void delStoreCategoryByIds(Long[] ids) {
        for (Long id :ids) {
            QueryWrapper<SmStorePo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_category_id",id);
            List<SmStorePo> smStorePoList = smStoreMapper.selectList(queryWrapper);
            if(null != smStorePoList & smStorePoList.size() > 0 ) {
                throw new ServiceException(ResultCode.FAIL, "删除失败，包含正被店铺使用关联的属性");
            }
        }
        for (Long id :ids) {
            smStoreCategoryMapper.deleteById(id);
        }

    }


}
