package com.chauncy.mq;

import com.chauncy.web.StartApplication;
import com.chauncy.web.user.mq.Sender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author cheng
 * @create 2019-07-19 16:06
 */

@Slf4j
public class HelloApplicationTests {

    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        ScheduledExecutorService scheduledThreadPool= Executors.newScheduledThreadPool(3);
        scheduledThreadPool.schedule(new Runnable(){
            @Override
            public void run() {
                System.out.println("延迟三秒");
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Test
    public void hello1() throws Exception {
        ExecutorService  fixedThreadPool =Executors. newFixedThreadPool(3);
        for (int i =1; i<=5;i++){
            final int index=i ;
            fixedThreadPool.execute(new Runnable(){
                @Override
                public void run() {
                    try {
                        System.out.println("第" +index + "个线程" +Thread.currentThread().getName());
                        Thread.sleep(1000);
                    }  catch(InterruptedException  e ) {
                        e .printStackTrace();
                    }
                }

            });
        }
    }

}
