package com.chauncy.web.config.bean;

import com.rabbitmq.client.Address;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-14 21:42
 */
@Component
public class RabbitMQBean {

    /**
     * RabbitMQ集群配置
     */
    private List<Address> addressList;
    private String username;
    private String password;
    private boolean publisherConfirms;
    private String virtualHost;
    private String queue;
    private String host;
    private int port;

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPublisherConfirms() {
        return publisherConfirms;
    }

    public void setPublisherConfirms(boolean publisherConfirms) {
        this.publisherConfirms = publisherConfirms;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

