package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysVersionPo;
import com.chauncy.data.dto.manage.sys.vsersion.SearchVersionDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.version.FindVersionVo;
import com.chauncy.data.vo.manage.version.SearchVersionVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * app版本信息 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2020-03-27
 */
public interface SysVersionMapper extends IBaseMapper<SysVersionPo> {

    List<SearchVersionVo> searchVersion(SearchVersionDto searchVersionDto);

    @Select("select * from sys_version where del_flag = 0 and current_flag = 1 and type = #{type}")
    FindVersionVo findVersion(Integer type);

}
