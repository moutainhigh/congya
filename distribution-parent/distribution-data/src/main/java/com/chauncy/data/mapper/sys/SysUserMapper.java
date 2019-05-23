package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysUserPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserPo> {



}
