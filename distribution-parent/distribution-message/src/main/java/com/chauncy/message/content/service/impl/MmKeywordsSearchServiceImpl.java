package com.chauncy.message.content.service.impl;

import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.content.MmKeywordsSearchPo;
import com.chauncy.data.dto.manage.message.content.add.AddKeyWordsDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.mapper.message.content.MmKeywordsSearchMapper;
import com.chauncy.data.vo.manage.message.content.BootPageVo;
import com.chauncy.data.vo.manage.message.content.KeyWordVo;
import com.chauncy.message.content.service.IMmKeywordsSearchService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 热搜关键字管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Service
public class MmKeywordsSearchServiceImpl extends AbstractService<MmKeywordsSearchMapper, MmKeywordsSearchPo> implements IMmKeywordsSearchService {

    @Autowired
    private MmKeywordsSearchMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 获取关键字类型
     *
     * @return
     */
    @Override
    public List<String> selectKeyWordType() {
        List<String> types = Arrays.asList(KeyWordTypeEnum.values()).stream().map(a->a.getName()).collect(Collectors.toList());
        return types;
    }

    /**
     * 添加关键字
     *
     * @param addKeyWordsDto
     * @return
     */
    @Override
    public void addKeyWords(AddKeyWordsDto addKeyWordsDto) {

        //不同类型下的关键字不能重复
        Map<String,Object> map = Maps.newHashMap();
        map.put("type",addKeyWordsDto.getType());
        List<String> names = mapper.selectByMap(map).stream().map(a->a.getName()).collect(Collectors.toList());
        if (names.contains(addKeyWordsDto.getName())){
            throw new ServiceException(ResultCode.FAIL,"类别"+addKeyWordsDto.getName()+"已存在"+addKeyWordsDto.getName()+"关键字");
        }
        MmKeywordsSearchPo keywordsSearchPo = new MmKeywordsSearchPo();
        BeanUtils.copyProperties(addKeyWordsDto,keywordsSearchPo);
        keywordsSearchPo.setId(null);
        keywordsSearchPo.setCreateBy(securityUtil.getCurrUser().getUsername());
        keywordsSearchPo.setCreateTime(LocalDateTime.now());
        keywordsSearchPo.setUpdateTime(LocalDateTime.now());
        mapper.insert(keywordsSearchPo);
    }

    /**
     * 修改关键字
     *
     * @param updateKeyWordsDto
     * @return
     */
    @Override
    public void updateKeyWords(AddKeyWordsDto updateKeyWordsDto) {
        //分类对应的换键字不能重复
        String name = mapper.selectById(updateKeyWordsDto.getId()).getName();
        Map<String,Object> map = Maps.newHashMap();
        map.put("type",updateKeyWordsDto.getType());
        List<String> names = mapper.selectByMap(map).stream().map(a->a.getName()).collect(Collectors.toList());
        if (names.contains(updateKeyWordsDto.getName()) && !name.equals(updateKeyWordsDto.getName())){
            throw new ServiceException(ResultCode.FAIL,"类别"+updateKeyWordsDto.getName()+"已存在"+updateKeyWordsDto.getName()+"关键字");
        }
        MmKeywordsSearchPo keywordsSearchPo = new MmKeywordsSearchPo();
        BeanUtils.copyProperties(updateKeyWordsDto,keywordsSearchPo);
        keywordsSearchPo.setUpdateBy(securityUtil.getCurrUser().getUsername());
        mapper.updateById(keywordsSearchPo);
    }

    /**
     * 批量删除启动页
     *
     * @param ids
     * @return
     */
    @Override
    public void delKeyWordsByIds(Long[] ids) {
        Arrays.asList(ids).forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.FAIL,"数据库不存在该记录"+a);
            }
        });
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 条件查询关键字信息
     *
     * @param searchContentDto
     * @return
     */
    @Override
    public PageInfo<KeyWordVo> searchKeyWords(SearchContentDto searchContentDto) {
        int pageNo = searchContentDto.getPageNo() == null ? defaultPageNo : searchContentDto.getPageNo();
        int pageSize = searchContentDto.getPageSize() == null ? defaultPageSize : searchContentDto.getPageSize();

        PageInfo<KeyWordVo> keyWordVos = new PageInfo<>();
        keyWordVos = PageHelper.startPage(pageNo,pageSize).doSelectPageInfo(
                ()->mapper.searchKeyWords(searchContentDto)
        );
        return keyWordVos;
    }
}
