package com.chauncy.message.content.service.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.message.content.MmBootPagePo;
import com.chauncy.data.dto.manage.message.content.add.AddBootPageDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.mapper.message.MmBootPageMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.message.content.BootPageVo;
import com.chauncy.message.content.service.IMmBootPageService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 启动页管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Service
public class MmBootPageServiceImpl extends AbstractService<MmBootPageMapper, MmBootPagePo> implements IMmBootPageService {

    @Autowired
    private MmBootPageMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 添加启动页
     *
     * @param addBootPageDto
     * @return
     */
    @Override
    public void addBootPage(AddBootPageDto addBootPageDto) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("name",addBootPageDto.getName());
        List<String> names = mapper.selectByMap(map).stream().map(a->a.getName()).collect(Collectors.toList());
        //启动页名称不能重复
        if (names!=null && names.size()!=0){
            throw new ServiceException(ResultCode.FAIL,"该名称已存在,请重新输入");
        }
        MmBootPagePo bootPagePo = new MmBootPagePo();
        BeanUtils.copyProperties(addBootPageDto,bootPagePo);
        bootPagePo.setCreateBy(securityUtil.getCurrUser().getUsername());
        bootPagePo.setId(null);
        bootPagePo.setCreateTime(LocalDateTime.now());
        bootPagePo.setUpdateTime(LocalDateTime.now());
        mapper.insert(bootPagePo);
    }

    /**
     * 修改启动页
     *
     * @param updateBootPage
     * @return
     */
    @Override
    public void updateBootPage(AddBootPageDto updateBootPage) {
        //名称不能重复
        Map<String,Object> map = Maps.newHashMap();
        map.put("name",updateBootPage.getName());
        List<String> names = mapper.selectByMap(map).stream().map(a->a.getName()).collect(Collectors.toList());
        String name = mapper.selectById(updateBootPage.getId()).getName();//启动页名称不能重复
        if ((names!=null && names.size()!=0)&& !updateBootPage.getName().equals(name)){
            throw new ServiceException(ResultCode.FAIL,"该名称已存在,请重新输入");
        }
        MmBootPagePo bootPagePo = new MmBootPagePo();
        BeanUtils.copyProperties(updateBootPage,bootPagePo);
        bootPagePo.setUpdateBy(securityUtil.getCurrUser().getUsername());
        mapper.updateById(bootPagePo);
    }

    /**
     * 批量删除启动页
     * @param ids
     * @return
     */
    @Override
    public void delBootPageByIds(Long[] ids) {
        Arrays.asList(ids).forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.FAIL,"数据库不存在该文章"+a);
            }
        });
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 查找启动页
     *
     * @param searchContentDto
     * @return
     */
    @Override
    public PageInfo<BootPageVo> searchPages(SearchContentDto searchContentDto) {

        int pageNo = searchContentDto.getPageNo() == null ? defaultPageNo : searchContentDto.getPageNo();
        int pageSize = searchContentDto.getPageSize() == null ? defaultPageSize : searchContentDto.getPageSize();

        PageInfo<BootPageVo> bootPageVos = new PageInfo<>();
        bootPageVos = PageHelper.startPage(pageNo,pageSize).doSelectPageInfo(
                ()->mapper.searchPages(searchContentDto)
        );
        return bootPageVos;
    }


}
