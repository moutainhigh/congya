package com.chauncy.web.config.properties;

import com.chauncy.web.config.bean.RabbitMQBean;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Author huangwancheng
 * @create 2019-05-14 21:37
 */
@Configuration
@Slf4j
public class RabbitMqProperties {
    /**
     * RabbitMQ集群配置
     */
    @Value("${spring.rabbitmq.addresses}")
    private String addressList;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String queue;

    @Bean
    public RabbitMQBean mqConfigBean() {
        RabbitMQBean mqConfigBean = new RabbitMQBean();

        if (StringUtils.isEmpty(addressList)) {
            throw new InvalidPropertyException(RabbitMQBean.class, "addressList", "rabbitmq.address-list is Empty");
        }

        String[] addressStrArr = addressList.split(",");
        List<Address> addressList = new LinkedList<Address>();
        for (String addressStr : addressStrArr) {
            String[] strArr = addressStr.split(":");

            addressList.add(new Address(strArr[0], Integer.valueOf(strArr[1])));
        }
        mqConfigBean.setAddressList(addressList);

        mqConfigBean.setUsername(username);
        mqConfigBean.setPassword(password);
        mqConfigBean.setPublisherConfirms(publisherConfirms);
        mqConfigBean.setVirtualHost(virtualHost);
        mqConfigBean.setQueue(queue);
        mqConfigBean.setHost(host);
        mqConfigBean.setPort(port);
        return mqConfigBean;
    }

    @Bean("mqConnectionSeckill")
    public Connection mqConnectionSeckill(@Autowired RabbitMQBean mqConfigBean) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //用户名
        factory.setUsername(username);
        //密码
        factory.setPassword(password);
        //虚拟主机路径（相当于数据库名）
        factory.setVirtualHost(virtualHost);
        log.info(mqConfigBean.getAddressList().toString());
        //返回连接
        return factory.newConnection(mqConfigBean.getAddressList());
    }

    @Bean("mqConnectionReceive")
    public Connection mqConnectionReceive(@Autowired RabbitMQBean mqConfigBean) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //用户名
        factory.setUsername(username);
        //密码
        factory.setPassword(password);
        //虚拟主机路径（相当于数据库名）
        factory.setVirtualHost(virtualHost);
        //返回连接
        return factory.newConnection(mqConfigBean.getAddressList());
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(virtualHost);
//        connectionFactory.setPublisherConfirms(true);
//        return connectionFactory;
//    }
}


