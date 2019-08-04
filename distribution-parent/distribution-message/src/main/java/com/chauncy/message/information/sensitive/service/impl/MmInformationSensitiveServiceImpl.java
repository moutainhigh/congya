package com.chauncy.message.information.sensitive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.message.information.sensitive.MmInformationSensitivePo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationSensitiveDto;
import com.chauncy.data.mapper.message.information.sensitive.MmInformationSensitiveMapper;
import com.chauncy.data.vo.manage.message.information.sensitive.InformationSensitiveVo;
import com.chauncy.message.information.sensitive.service.IMmInformationSensitiveService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.security.util.SecurityUtil;
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
 * @since 2019-06-26
 */
@Service
public class MmInformationSensitiveServiceImpl extends AbstractService<MmInformationSensitiveMapper, MmInformationSensitivePo> implements IMmInformationSensitiveService {

    @Autowired
    private MmInformationSensitiveMapper mmInformationSensitiveMapper;
    @Autowired
    private SecurityUtil securityUtil;
    /**
     * 保存资讯敏感词信息
     *
     * @param informationSensitiveDto
     */
    @Override
    public void saveInformationSensitive(InformationSensitiveDto informationSensitiveDto) {
        QueryWrapper<MmInformationSensitivePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationSensitiveDto.getName());
        if (null != this.getOne(queryWrapper)) {
            throw new ServiceException(ResultCode.DUPLICATION, "敏感词名称重复");
        }

        MmInformationSensitivePo mmInformationSensitivePo = new MmInformationSensitivePo();
        BeanUtils.copyProperties(informationSensitiveDto, mmInformationSensitivePo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        mmInformationSensitivePo.setCreateBy(userName);
        mmInformationSensitivePo.setId(null);
        mmInformationSensitiveMapper.insert(mmInformationSensitivePo);
    }

    /**
     * 编辑资讯敏感词信息
     *
     * @param informationSensitiveDto
     */
    @Override
    public void editInformationSensitive(InformationSensitiveDto informationSensitiveDto) {
        MmInformationSensitivePo mmInformationSensitivePo = mmInformationSensitiveMapper.selectById(informationSensitiveDto.getId());

        QueryWrapper<MmInformationSensitivePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", informationSensitiveDto.getName());
        MmInformationSensitivePo oldSensitivePo = this.getOne(queryWrapper);
        if (null != oldSensitivePo && !oldSensitivePo.getName().equals(informationSensitiveDto.getName())) {
            throw new ServiceException(ResultCode.DUPLICATION, "资讯敏感词名称重复");
        }

        BeanUtils.copyProperties(informationSensitiveDto, mmInformationSensitivePo);
        //获取当前用户
        String userName = securityUtil.getCurrUser().getUsername();
        mmInformationSensitivePo.setUpdateBy(userName);
        mmInformationSensitiveMapper.updateById(mmInformationSensitivePo);
    }

    /**
     * 根据ID查找店铺敏感词
     *
     * @param id
     * @return
     */
    @Override
    public InformationSensitiveVo findById(Long id) {
        InformationSensitiveVo informationSensitiveVo = new InformationSensitiveVo();
        MmInformationSensitivePo informationSensitivePo = mmInformationSensitiveMapper.selectById(id);
        BeanUtils.copyProperties(informationSensitivePo , informationSensitiveVo);
        return informationSensitiveVo;
    }


    /**
     * 根据敏感词ID、敏感词名称查询
     *
     * @return
     */
    @Override
    public PageInfo<InformationSensitiveVo> searchPaging(BaseSearchDto baseSearchDto) {

        Integer pageNo = baseSearchDto.getPageNo()==null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize()==null ? defaultPageSize : baseSearchDto.getPageSize();


        PageInfo<InformationSensitiveVo> informationSensitiveVoPageInfo = PageHelper.startPage(pageNo, pageSize, " create_time desc")
                .doSelectPageInfo(() -> mmInformationSensitiveMapper.searchPaging(baseSearchDto));
        return informationSensitiveVoPageInfo;
    }

    /**
     * 批量禁用启用
     *
     * @param baseUpdateStatusDto
     */
    @Override
    public void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto) {
        UpdateWrapper<MmInformationSensitivePo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", baseUpdateStatusDto.getId());
        MmInformationSensitivePo mmInformationSensitivePo = new MmInformationSensitivePo();
        mmInformationSensitivePo.setEnabled(baseUpdateStatusDto.getEnabled());
        mmInformationSensitiveMapper.update(mmInformationSensitivePo, updateWrapper);
    }

    /**
     * 批量删除敏感词
     * @param ids
     */
    @Override
    public void delInformationSensitiveByIds(Long[] ids) {
        //批量删除敏感词
        mmInformationSensitiveMapper.deleteBatchIds(Arrays.asList(ids));
    }

}
