package com.chauncy.system.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.sys.SysVersionPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.sys.vsersion.SaveVersionDto;
import com.chauncy.data.dto.manage.sys.vsersion.SearchVersionDto;
import com.chauncy.data.dto.manage.sys.vsersion.UpdateCurrentVersionDto;
import com.chauncy.data.vo.app.version.FindVersionVo;
import com.chauncy.data.vo.manage.version.SearchVersionVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * app版本信息 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2020-03-27
 */
public interface ISysVersionService extends Service<SysVersionPo> {

    void saveVersion(SaveVersionDto saveVersionDto, SysUserPo userPo);

    PageInfo<SearchVersionVo> searchVersion(SearchVersionDto searchVersionDto);

    void delByIds(Long[] ids);

    void setCurrent(UpdateCurrentVersionDto updateCurrentVersionDto);

    FindVersionVo findVersion(Integer type);
}
