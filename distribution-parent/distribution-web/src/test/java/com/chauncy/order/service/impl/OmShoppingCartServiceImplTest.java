package com.chauncy.order.service.impl;

import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.test.service.UserService;
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
   public void searchByIds() {
       userService.updateLevel(1l);
   }

   @Test
    public void firstSecond(){
       shoppingCartService.getPayUserMessage(1146432777142534145l);
   }

}