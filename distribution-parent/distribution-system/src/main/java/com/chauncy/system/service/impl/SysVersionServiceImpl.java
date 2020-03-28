package com.chauncy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.system.VersionTypeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.sys.SysVersionPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.manage.sys.vsersion.SaveVersionDto;
import com.chauncy.data.dto.manage.sys.vsersion.SearchVersionDto;
import com.chauncy.data.dto.manage.sys.vsersion.UpdateCurrentVersionDto;
import com.chauncy.data.mapper.sys.SysVersionMapper;
import com.chauncy.data.vo.app.goods.SeckillGoodsVo;
import com.chauncy.data.vo.app.version.FindVersionVo;
import com.chauncy.data.vo.manage.version.SearchVersionVo;
import com.chauncy.system.service.ISysVersionService;
import com.chauncy.data.core.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * app版本信息 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2020-03-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysVersionServiceImpl extends AbstractService<SysVersionMapper, SysVersionPo> implements ISysVersionService {

    @Autowired
    private SysVersionMapper mapper;

    @Override
    public void saveVersion(SaveVersionDto saveVersionDto, SysUserPo userPo) {

        LocalDateTime now = LocalDateTime.now();
        SysVersionPo version = new SysVersionPo();
        List<Integer> versionNum = mapper.selectList(null).stream().map(a->a.getVersion()).collect(Collectors.toList());

        if (saveVersionDto.getId() == 0){

            if ( versionNum.contains(saveVersionDto.getVersion())){
                throw new ServiceException(ResultCode.FAIL,String.format("版本号:[%s]已存在！",saveVersionDto.getVersion()));
            }
            BeanUtils.copyProperties(saveVersionDto,version);
            version.setCreateTime(now);
            version.setUpdateTime(now);
            version.setCreateBy(userPo.getUsername());
            version.setId(null);
            mapper.insert(version);
        }else {
            List<Integer> ver = versionNum.stream().filter(a->a != saveVersionDto.getVersion()).collect(Collectors.toList());

            if (ver.contains(saveVersionDto.getVersion())){
                throw new ServiceException(ResultCode.FAIL,String.format("版本号:[%s]已存在！",saveVersionDto.getVersion()));
            }
            version = mapper.selectById(saveVersionDto.getId());
            BeanUtils.copyProperties(saveVersionDto,version);
            version.setUpdateTime(now);
            version.setUpdateBy(userPo.getUsername());
            mapper.updateById(version);

        }
    }

    @Override
    public PageInfo<SearchVersionVo> searchVersion(SearchVersionDto searchVersionDto) {

        Integer pageNo = searchVersionDto.getPageNo()==null ? defaultPageNo : searchVersionDto.getPageNo();
        Integer pageSize = searchVersionDto.getPageSize()==null ? defaultPageSize : searchVersionDto.getPageSize();

        PageInfo<SearchVersionVo> versionVoPageInfo = PageHelper.startPage(pageNo,pageSize).doSelectPageInfo(() ->
                mapper.searchVersion(searchVersionDto));

        return versionVoPageInfo;

    }

    @Override
    public void delByIds(Long[] ids) {
        Arrays.asList(ids).forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.FAIL,"数据库不存在id:[%s]的版本信息"+a);
            }
        });
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void setCurrent(UpdateCurrentVersionDto updateCurrentVersionDto) {

        List<SysVersionPo> sysVersionPos = mapper.selectList(new QueryWrapper<SysVersionPo>().lambda().eq(SysVersionPo::getType,updateCurrentVersionDto.getType()));

        if (ListUtil.isListNullAndEmpty(sysVersionPos)) {
            throw new ServiceException(ResultCode.FAIL,String.format("不存在类型为：【%s】的版本信息", VersionTypeEnum.getVersionTypeEnumById(updateCurrentVersionDto.getType()).getName()));
        }

        Integer type = mapper.selectById(updateCurrentVersionDto.getId()).getType();
        if (type != updateCurrentVersionDto.getType()){
            throw new ServiceException(ResultCode.FAIL,String.format("类型为：【%s】的版本信息不存在ID为：【%s】",VersionTypeEnum.getVersionTypeEnumById(updateCurrentVersionDto.getType()).getName(),updateCurrentVersionDto.getId()));
        }

        //查出已设置为当前版本的记录修改为false
        List<Long> ids = mapper.selectList(new QueryWrapper<SysVersionPo>().lambda()
                .and(obj->obj.eq(SysVersionPo::getType,updateCurrentVersionDto.getType()).eq(SysVersionPo::getCurrentFlag,true)))
                .stream().map(a->a.getId()).collect(Collectors.toList());
        if (!ListUtil.isListNullAndEmpty(ids)) {
            ids.stream().forEach(a -> {
                SysVersionPo sysVersionPo = mapper.selectById(a);
                sysVersionPo.setCurrentFlag(false);
                mapper.updateById(sysVersionPo);
            });
        }

        //设置当前的为默认版本
        SysVersionPo sysVersionPo = mapper.selectById(updateCurrentVersionDto.getId());
        sysVersionPo.setCurrentFlag(true);
        mapper.updateById(sysVersionPo);
    }

    @Override
    public FindVersionVo findVersion(Integer type) {
        return mapper.findVersion(type);
    }
}
