package com.chauncy.order.service.impl;

import com.chauncy.common.enums.message.NoticeTitleEnum;
import com.chauncy.data.bo.app.message.SaveUserNoticeBo;
import com.chauncy.message.interact.service.IMmUserNoticeService;
import com.chauncy.order.logistics.impl.OmOrderLogisticsServiceImpl;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.StartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author zhangrt
 * @Date 2019/7/11 12:20
 **/
@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OmShoppingCartServiceImplTest {

    @Autowired
    private OmShoppingCartServiceImpl shoppingCartService;
    @Autowired
    private OmOrderServiceImpl orderService;
    @Autowired
    private IMmUserNoticeService mmUserNoticeService;
    @Autowired
    private OmOrderLogisticsServiceImpl omOrderLogisticsService;


    @Autowired
    private IUmUserService userService;

    /* @Test
     public void searchByIds() {
         List<SettleAccountsDto> totalCarVos= Lists.newArrayList(
                 new SettleAccountsDto(1147887217915179009L,1),
                 new SettleAccountsDto(1147887218905034753L,1),
                 new SettleAccountsDto(1147887219978776577L,1),
                 new SettleAccountsDto(1148259822342352897L,1),
                 new SettleAccountsDto(1148258455756460033L,1)
         );
         shoppingCartService.searchByIds(totalCarVos);
     }*/
    @Test
    public void updateUserLevel() {
        userService.list().forEach(  x->userService.updateLevel(x.getId()));


    }

    @Test
    public void firstSecond() {

        omOrderLogisticsService.saveSignedNotice("190320936170295296");
        //订单发货成功  发送APP内消息给用户
        SaveUserNoticeBo saveUserNoticeBo = new SaveUserNoticeBo();
        saveUserNoticeBo.setOrderId(190320936170295296L);
        mmUserNoticeService.saveUserNotice(NoticeTitleEnum.SHIPPED.name(), saveUserNoticeBo);


    }


}