package com.chauncy.store.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.store.category.SmStoreCategoryPo;
import com.chauncy.data.dto.manage.store.add.StoreCategoryDto;
import com.chauncy.data.mapper.store.category.SmStoreCategoryMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.category.service.ISmStoreCategoryService;
import com.chauncy.data.core.AbstractService;
import com.github.pagehelper.PageHelper;
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
    private SecurityUtil securityUtil;
    
    /**
     * 保存店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    @Override
    public JsonViewData saveStoreCategory(StoreCategoryDto storeCategoryDto) {

        QueryWrapper<SmStoreCategoryPo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        SmStoreCategoryPo queryWarpper = new SmStoreCategoryPo();
        queryWarpper.setName(storeCategoryDto.getName());
        smStoreCategoryPoQueryWrapper.setEntity(queryWarpper);
        SmStoreCategoryPo smStoreCategoryPo = this.getOne(smStoreCategoryPoQueryWrapper);

        if(null != smStoreCategoryPo) {
            return new JsonViewData(ResultCode.DUPLICATION, "分类名称重复");
        }

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStoreCategoryPo.setUpdateBy(user);
        BeanUtils.copyProperties(storeCategoryDto, smStoreCategoryPo);
        smStoreCategoryMapper.insert(smStoreCategoryPo);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功", smStoreCategoryPo);
    }



    /**
     * 编辑店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    @Override
    public JsonViewData editStoreCategory(StoreCategoryDto storeCategoryDto) {
        SmStoreCategoryPo oldCategory = smStoreCategoryMapper.selectById(storeCategoryDto.getId());

        QueryWrapper<SmStoreCategoryPo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        SmStoreCategoryPo queryWarpper = new SmStoreCategoryPo();
        queryWarpper.setName(storeCategoryDto.getName());
        smStoreCategoryPoQueryWrapper.setEntity(queryWarpper);
        SmStoreCategoryPo smStoreCategoryPo = this.getOne(smStoreCategoryPoQueryWrapper);

        if(null != smStoreCategoryPo && !smStoreCategoryPo.getName().equals(oldCategory.getName())) {
            return new JsonViewData(ResultCode.DUPLICATION, "分类名称重复");
        }

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        smStoreCategoryPo.setUpdateBy(user);
        BeanUtils.copyProperties(storeCategoryDto, smStoreCategoryPo);
        smStoreCategoryMapper.updateById(smStoreCategoryPo);
        return new JsonViewData(ResultCode.SUCCESS, "编辑成功", smStoreCategoryPo);
    }

    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    @Override
    public JsonViewData findById(Long id) {
        QueryWrapper<SmStoreCategoryPo> smStoreCategoryPoQueryWrapper = new QueryWrapper<>();
        SmStoreCategoryPo queryWarpper = new SmStoreCategoryPo();
        queryWarpper.setId(id);
        smStoreCategoryPoQueryWrapper.setEntity(queryWarpper);
        smStoreCategoryPoQueryWrapper.select("id", "name", "icon", "sort", "enabled");
        Map<String, Object> map = this.getMap(smStoreCategoryPoQueryWrapper);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功", map);
    }


    /**
     * 条件查询
     * @param storeCategorySearchDto
     * @return
     */
    /*@Override
    public PageInfo<SmStoreCategoryVo> searchPaging(StoreCategorySearchDto storeCategorySearchDto) {

        Integer pageNo = storeCategorySearchDto.getPageNo()==null ? defaultPageNo : storeCategorySearchDto.getPageNo();
        Integer pageSize = storeCategorySearchDto.getPageSize()==null ? defaultPageSize : storeCategorySearchDto.getPageSize();
        String pageSoft = " create_time desc";

        PageInfo<SmStoreCategoryVo> smStoreCategoryVoPageInfo = PageHelper.startPage(pageNo, pageSize, pageSoft)
                .doSelectPageInfo(() -> smStoreCategoryMapper.searchPaging(storeCategorySearchDto));
        return smStoreCategoryVoPageInfo;
    }*/

    /**
     * 查询店铺所有标签
     * @return
     */
    /*@Override
    public JsonViewData searchAll() {

        List<SmStoreCategoryVo> smStoreCategoryVoList = smStoreCategoryMapper.searchAll();
        return new JsonViewData(ResultCode.SUCCESS, "查找成功", smStoreCategoryVoList);

    }*/

}
