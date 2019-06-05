package com.chauncy.data.mapper.common;

import com.chauncy.common.base.BaseTest;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.web.StartApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author zhangrt
 * @Date 2019-06-05 18:13
 **/
@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonMapperTest extends BaseTest{

    @Autowired
    private CommonMapper commonMapper;



    @Test
    public void getChildrenIds(){
        List<Long> sys_permission = commonMapper.getChildIds(145995953139093504l, "sys_permission");
        sys_permission.forEach(x->LoggerUtil.error(x.toString()));
    }

}