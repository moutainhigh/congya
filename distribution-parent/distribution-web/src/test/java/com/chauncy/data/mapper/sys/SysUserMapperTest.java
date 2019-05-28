//package com.chauncy.data.mapper.sys;
//
//import com.chauncy.common.base.BaseTest;
//import com.chauncy.common.util.LoggerUtil;
//import com.chauncy.data.domain.po.sys.SysUserPo;
//import com.chauncy.web.StartApplication;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//
///**
// * @Author zhangrt
// * @Date 2019-05-23 16:51
// **/
//@SpringBootTest(classes = StartApplication.class)
//@RunWith(SpringRunner.class)
//public class SysUserMapperTest  {
//
//    @Autowired
//    SysUserMapper sysUserMapper;
//
//
//    @Test
//    public void  selectone(){
//        SysUserPo sysUserPo = sysUserMapper.selectById(1);
//        LoggerUtil.info(sysUserPo.toString());
//
//    }
//
//}